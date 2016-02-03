package nl.utwente.fmt.atg.standalone.transformer;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import nl.utwente.fmt.atg.standalone.Language;

import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.etl.EtlModule;

public class ADTool2UAT extends EpsilonTransformer {
	/** The singleton instance of this transformer. */
	private static ADTool2UAT INSTANCE;
	
	/** Returns the singleton instance of this transformer. */
	public static ADTool2UAT instance() {
		if (INSTANCE == null) {
			INSTANCE = new ADTool2UAT();
		}
		return INSTANCE;
	}
	
	/** Constructor for the singleton instance of this transformer. */
	private ADTool2UAT() {
		super(Language.AD_TOOL, Language.UAT);
	}

	@Override
	public IEolExecutableModule createModule() {
		return new EtlModule();
	}

	@Override
	public IModel[] getModels(String inputPath, String outputPath) throws EolModelLoadingException, URISyntaxException, FileNotFoundException {
		IModel source = createPlainXmlModel(Role.SOURCE, inputPath);
		IModel target = createEmfModel(Role.TARGET, outputPath);
		return new IModel[] { source, target };
	}

	@Override
	public String getTransformation() {
		return "transformation/ADTool2UAT.etl";
	}
}
