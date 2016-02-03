package nl.utwente.fmt.atg.standalone.transformer;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import nl.utwente.fmt.atg.standalone.Language;

import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.etl.EtlModule;

/**
 * Transformer from the AT metamodel to the ADTool input format.
 * @author David Huistra
 */
public class UAT2ADTool extends EpsilonTransformer {
	/** The singleton instance of this transformer. */
	private static UAT2ADTool INSTANCE;
	
	/** Returns the singleton instance of this transformer. */
	public static UAT2ADTool instance() {
		if (INSTANCE == null) {
			INSTANCE = new UAT2ADTool();
		}
		return INSTANCE;
	}
	
	/** Constructor for the singleton instance of this transformer. */
	private UAT2ADTool() {
		super(Language.UAT, Language.AD_TOOL);
	}

	@Override
	public IEolExecutableModule createModule() {
		return new EtlModule();
	}

	@Override
	public IModel[] getModels(String inputPath, String outputPath)
			throws EolModelLoadingException, URISyntaxException, FileNotFoundException {
		IModel source = createEmfModel(Role.SOURCE, inputPath);
		IModel target = createPlainXmlModel(Role.TARGET, outputPath);
		return new IModel[] { source, target };
	}

	@Override
	public String getTransformation() {
		return "transformation/UAT2ADTool.etl";
	}
}
