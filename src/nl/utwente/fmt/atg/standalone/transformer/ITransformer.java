package nl.utwente.fmt.atg.standalone.transformer;

import nl.utwente.fmt.atg.standalone.Language;

/** Interface for a transformer between source and target models. */
public interface ITransformer {
	/**
	 * Transforms the source model in a given input file
	 * into a target model stored in a given output file.
	 * @param inputPath file name of the source model
	 * @param outputPath file name of the target model
	 * @return the target model
	 * @throws Exception if an error occurred during reading, transforming or writing
	 */
	public Object execute(String inputPath, String outputPath) throws Exception ;
	
	/** The language expected for the input models of this transformer. */
	public Language getSourceLanguage();
	
	/** The language produced by this transformer. */
	public Language getTargetLanguage();
	
	/**
	 * Returns a composed transformer consisting of this transformer,
	 * chained with another transformer
	 * @param other the other transformer; the input language of other should
	 * coincide with the output language of this transformer
	 * @return the composed transformer
	 * @throws IllegalArgumentException if the input language of other
	 * does not coincide with this transformer's output language
	 */
	public default ITransformer compose(ITransformer other) {
		if (getTargetLanguage() != other.getSourceLanguage()) {
			throw new IllegalArgumentException(String.format("Output language %s and input language %s "
					+ "of composed transformers do not coincide", getTargetLanguage(), other.getSourceLanguage()));
		}
		return new ComposedTransformer(this, other);
	}
}
