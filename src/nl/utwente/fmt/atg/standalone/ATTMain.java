package nl.utwente.fmt.atg.standalone;

import static nl.utwente.fmt.atg.standalone.ATTMain.Language.AD_TOOL;
import static nl.utwente.fmt.atg.standalone.ATTMain.Language.ATA;
import static nl.utwente.fmt.atg.standalone.ATTMain.Language.AT_CALC;
import static nl.utwente.fmt.atg.standalone.ATTMain.Language.UAT;

import java.util.Arrays;

import nl.utwente.fmt.atg.standalone.meta.transformations.*;

public class ATTMain {
	public final static Language DEFAULT_SOURCE = UAT;
	public final static Language DEFAULT_TARGET = AD_TOOL;

	public static void main(String[] args) throws Exception {
		if (args.length != 0 && args.length != 2) {
			System.err.printf("Usage: ATTMain [source target]%n");
			System.err.printf("Parameters: source, target chosen out of %s%n", languageList());
			System.err.printf("Default: source = %s, target = %s%n", DEFAULT_SOURCE, DEFAULT_TARGET);
			System.exit(1);
		}
		EpsilonStandaloneExample example;
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
		example = getTransformer(source, target);
		if (example == null) {
			System.err.printf("Can't transform from %s to %s%n", source, target);
			System.exit(1);
		}
		example.execute();
	}

	/**
	 * Returns a transformer from a given source to target language
	 * @param source Source language for transformation
	 * @param target Target language for transformation
	 * @return a standalone transformation from source to target language, or <code>null</code>
	 * if no such transformation can be defined
	 */
	private static EpsilonStandaloneExample getTransformer(Language source,
			Language target) {
		EpsilonStandaloneExample example;
		if (source == ATA && target == UAT) {
			example = new ATA2UATMM();
		} else if (source == AD_TOOL && target == UAT) {
			example = new ADTool2UATMM();
		} else if (source == UAT && target == AD_TOOL) {
			example = new UATMM2ADTool();
		} else if (source == UAT && target == AT_CALC) {
			example = new UATMM2ATCalc();
		} else {
			example = null;
		}
		return example;
	}
	
	static public String languageList() {
		return Arrays.asList(Language.values()).toString();
	}
	
	static public enum Language {
		UAT,
		ATA,
		AD_TOOL,
		AT_CALC,;
	}
}