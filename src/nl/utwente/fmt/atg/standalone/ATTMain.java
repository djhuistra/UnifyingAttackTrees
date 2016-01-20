package nl.utwente.fmt.atg.standalone;

import static nl.utwente.fmt.atg.standalone.ATTMain.Language.AD_TOOL;
import static nl.utwente.fmt.atg.standalone.ATTMain.Language.ATA;
import static nl.utwente.fmt.atg.standalone.ATTMain.Language.AT_CALC;
import static nl.utwente.fmt.atg.standalone.ATTMain.Language.UAT;
import nl.utwente.fmt.atg.standalone.meta.transformations.*;

public class ATTMain {
	public final static Language DEFAULT_SOURCE = UAT;
	public final static Language DEFAULT_TARGET = AD_TOOL;

	public static void main(String[] args) throws Exception {
		if (args.length != 0 && args.length != 2) {
			System.err.printf("Usage: ATTMain [source target]%n");
			System.err.printf("Parameters: source, target chosen out of %s%n", (Object) Language.values());
			System.err.printf("Default: source = %s, target = %s%n", DEFAULT_SOURCE, DEFAULT_TARGET);
			System.exit(1);
		}
		EpsilonStandaloneExample example;
		Language source = args.length == 0 ? DEFAULT_SOURCE : Language.valueOf(args[0]);
		if (source == null) {
			System.err.printf("Source = %s is not a regognised language", args[0]);
			System.err.printf("Choose from: ", (Object) Language.values());
			System.exit(1);
		}
		Language target = args.length == 0 ? DEFAULT_TARGET : Language.valueOf(args[1]);
		if (target == null) {
			System.err.printf("Source = %s is not a regognised language", args[1]);
			System.err.printf("Choose from: ", (Object) Language.values());
			System.exit(1);
		}
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
			System.err.printf("Can't transform from %s to %s%n", source, target);
			System.exit(1);
		}
		example.execute();
	}
	
	static public enum Language {
		UAT,
		ATA,
		AD_TOOL,
		AT_CALC,;
	}
}
