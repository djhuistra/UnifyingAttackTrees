package nl.utwente.fmt.atg.standalone.transformer;

import java.io.File;

import nl.utwente.fmt.atg.standalone.Language;

public class ComposedTransformer implements ITransformer {
	private final ITransformer first;
	private final ITransformer second;

	/** Constructs a transformed by chaining two existing transformers. */
	public ComposedTransformer(ITransformer first, ITransformer second) {
		assert first.getTargetLanguage() == second.getSourceLanguage();
		this.first = first;
		this.second = second;
	}

	@Override
	public Language getSourceLanguage() {
		return first.getSourceLanguage();
	}

	@Override
	public Language getTargetLanguage() {
		return second.getTargetLanguage();
	}

	public Object execute(String inputPath, String outputPath) throws Exception {
		// Create temporary metamodel instance file.
		File tempModelInstance = File.createTempFile(
				"IntermediateModel", ".tmp");
		// delete temporary file when the program is exited
		tempModelInstance.deleteOnExit();

		String modelFileURI = tempModelInstance.toURI().getPath();
		first.execute(inputPath, modelFileURI);
		return second.execute(modelFileURI, outputPath);
	}

}
