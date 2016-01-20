package nl.utwente.fmt.atg.standalone;

import static nl.utwente.fmt.atg.standalone.ATTMain.Language.AD_TOOL;
import static nl.utwente.fmt.atg.standalone.ATTMain.Language.AD_TOOL_BINARY;
import static nl.utwente.fmt.atg.standalone.ATTMain.Language.ATA;
import static nl.utwente.fmt.atg.standalone.ATTMain.Language.AT_CALC;
import static nl.utwente.fmt.atg.standalone.ATTMain.Language.UAT;

import java.io.File;
import java.util.Arrays;

import nl.utwente.fmt.atg.standalone.meta.transformations.*;
import nl.utwente.fmt.atg.standalone.transformers.ADTool2ADToolBinary;
import nl.utwente.fmt.atg.standalone.transformers.ADTool2ATCalcTransformer;
import nl.utwente.fmt.atg.standalone.transformers.ITransformer;

public class ATTMain {
	public final static Language DEFAULT_SOURCE = AD_TOOL;
	public final static Language DEFAULT_TARGET = AD_TOOL_BINARY;

	public static void main(String[] args) throws Exception {
		if (args.length != 0 && args.length != 3) {
			System.err.printf("Usage: ATTMain [source target input-model]%n");
			System.err.printf("Parameters: source, target chosen out of %s%n", languageList());
			System.err.printf("Default: source = %s, target = %s%n", DEFAULT_SOURCE, DEFAULT_TARGET);
			System.exit(1);
		}
		ITransformer transformer;
		Language source = args.length == 0 ? DEFAULT_SOURCE : Language.valueOf(args[0]);
		if (source == null) {
			System.err.printf("Source = %s is not a regognised language", args[0]);
			System.err.printf("Choose from: ", languageList());
			System.exit(1);
		}
		Language target = args.length == 0 ? DEFAULT_TARGET : Language.valueOf(args[1]);
		if (target == null) {
			System.err.printf("Source = %s is not a regognised language", args[1]);
			System.err.printf("Choose from: ", languageList());
			System.exit(1);
		}
		String inputFilePath = null;
		String inputFileName = args.length == 0 ? "ADTI.xml" : args[2];
		if (inputFileName == null) {
			System.err.printf("Invalid input file");
			System.exit(1);
		} else {
			File jarPath=new File(ATTMain.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	        String propertiesPath=jarPath.getParentFile().getAbsolutePath();
	        inputFilePath=propertiesPath+File.separator+inputFileName;
		}
		transformer = getTransformer(source, target, inputFilePath);
		if (transformer == null) {
			System.err.printf("Can't transform from %s to %s%n", source, target);
			System.exit(1);
		}
		transformer.initialize();
		transformer.execute();
	}

	/**
	 * Returns a transformer from a given source to target language
	 * @param source Source language for transformation
	 * @param target Target language for transformation
	 * @param inputFilePath File path of the input file of the transformation.
	 * @return a Transformer from source to target language, or <code>null</code>
	 * if no such Transformer is defined
	 */
	private static ITransformer getTransformer(Language source,
			Language target, String inputFilePath) {
		ITransformer transformer;
		if(source == AD_TOOL && target == AT_CALC){
			transformer = new ADTool2ATCalcTransformer(inputFilePath, "ATCalcInput.txt");
		} else if(source == AD_TOOL && target == AD_TOOL_BINARY){
			transformer = new ADTool2ADToolBinary(inputFilePath, "ADtoolBinary.xml");
//		if (source == ATA && target == UAT) {
//			example = new ATA2UATMM();
//		} else if (source == AD_TOOL && target == UAT) {
//			example = new ADTool2UATMM();
//		} else if (source == UAT && target == AD_TOOL) {
//			example = new UATMM2ADTool();
//		} else if (source == UAT && target == AT_CALC) {
//			example = new UATMM2ATCalc();
		} else {
			transformer = null;
		}
		return transformer;
	}
	
	static public String languageList() {
		return Arrays.asList(Language.values()).toString();
	}
	
	static public enum Language {
		UAT,
		ATA,
		AD_TOOL,
		AD_TOOL_BINARY,
		AT_CALC,;
	}
}