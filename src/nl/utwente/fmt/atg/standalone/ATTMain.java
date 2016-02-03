package nl.utwente.fmt.atg.standalone;

import java.io.File;
import java.util.EnumMap;
import java.util.Map;

import nl.utwente.fmt.atg.standalone.transformer.ADTool2UAT;
import nl.utwente.fmt.atg.standalone.transformer.ATA2UAT;
import nl.utwente.fmt.atg.standalone.transformer.ITransformer;
import nl.utwente.fmt.atg.standalone.transformer.UAT2ADTool;
import nl.utwente.fmt.atg.standalone.transformer.UAT2ATCalc;

public class ATTMain {
	/** Matrix of transformers. */
	private static final Map<Language, Map<Language, ITransformer>> transformers;

	static {
		transformers = new EnumMap<>(Language.class);
		for (Language l : Language.values()) {
			transformers.put(l, new EnumMap<>(Language.class));
		}
		register(ADTool2UAT.instance());
		register(ATA2UAT.instance());
		register(UAT2ADTool.instance());
		register(UAT2ATCalc.instance());
	}

	/** Adds a new transformer to the matrix. */
	private static void register(ITransformer trafo) {
		Language src = trafo.getSourceLanguage();
		Language tgt = trafo.getTargetLanguage();
		Map<Language, ITransformer> fromSrcTrafos = transformers.get(src);
		if (!fromSrcTrafos.containsKey(tgt)) {
			close(fromSrcTrafos, trafo);
			for (Map<Language, ITransformer> trafoMap : transformers.values()) {
				if (trafoMap.containsKey(src) && !trafoMap.containsKey(tgt)) {
					ITransformer toTgt = trafoMap.get(src).compose(trafo);
					close(trafoMap, toTgt);
				}
			}
		}
	}

	/**
	 * Builds the transitive closure for a given new transformer, by putting
	 * it in front of all existing transformers starting from the target
	 * language of the new transformer; and adds those transformers to
	 * an existing map if the map did not contain a value to that target language.
	 */
	private static void close(Map<Language, ITransformer> map, ITransformer trafo) {
		Language tgt = trafo.getTargetLanguage();
		map.putIfAbsent(tgt, trafo);
		for (ITransformer next : transformers.get(tgt).values()) {
			map.putIfAbsent(next.getTargetLanguage(), trafo.compose(next));
		}
	}

	public static void main(String[] args) throws Exception {
		if (args.length != 4) {
			System.err
					.printf("Usage: ATTMain source-language target-language source-model target-model%n");
			System.err.printf("Parameters: source, target chosen out of %s%n",
					languageList());
			System.exit(1);
		}
		ITransformer transformer;
		Language source = Language.getLanguage(args[0]);
		if (source == null) {
			System.err.printf("Source = %s is not a regognised language%n",
					args[0]);
			System.err.printf("Choose from: %s%n", languageList());
			System.exit(1);
		}
		Language target = Language.getLanguage(args[1]);
		if (target == null) {
			System.err.printf("Source = %s is not a regognised language",
					args[1]);
			System.err.printf("Choose from: %s%n", languageList());
			System.exit(1);
		}
		File jarPath = new File(ATTMain.class.getProtectionDomain()
				.getCodeSource().getLocation().getPath());
		String propertiesPath = System.getProperty("user.dir");//jarPath.getParentFile().getAbsolutePath();
		String inputFileName = args[2];
		String inputFilePath = propertiesPath + File.separator + inputFileName;
		String outputFileName = args[3];
		String outputFilePath = propertiesPath + File.separator
				+ outputFileName;
		transformer = getTransformer(source, target);
		if (transformer == null) {
			System.err
					.printf("Can't transform from %s to %s%n", source, target);
			System.exit(1);
		}
		transformer.execute(inputFilePath, outputFilePath);
	}

	/**
	 * Returns a transformer from a given source to target language
	 * 
	 * @param source
	 *            Source language for transformation
	 * @param target
	 *            Target language for transformation
	 * @return a standalone transformation from source to target language, or
	 *         <code>null</code> if no such transformation can be defined
	 */
	private static ITransformer getTransformer(Language source, Language target) {
		return transformers.get(source).get(target);
	}

	static public String languageList() {
		return Language.getLanguageMap().keySet().toString();
	}
}