package nl.utwente.fmt.atg.standalone.transformer;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import nl.utwente.fmt.atg.standalone.Language;

import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.etl.EtlModule;

public class ATA2UAT extends EpsilonTransformer {
	/** The singleton instance of this transformer. */
	private static ATA2UAT INSTANCE;
	
	/** Returns the singleton instance of this transformer. */
	public static ATA2UAT instance() {
		if (INSTANCE == null) {
			INSTANCE = new ATA2UAT();
		}
		return INSTANCE;
	}
	
	/** Constructor for the singleton instance of this transformer. */
	private ATA2UAT() {
		super(Language.ATA, Language.UAT);
	}

	@Override
	public IEolExecutableModule createModule() {
		return new EtlModule();
	}

	@Override
	public IModel[] getModels(String inputPath, String outputPath)
			throws EolModelLoadingException, URISyntaxException, FileNotFoundException {
		IModel source = createPlainXmlModel(Role.SOURCE, inputPath);
		IModel target = createEmfModel(Role.TARGET, outputPath);
		return new IModel[] { source, target };
	}

	@Override
	public String getTransformation() {
		return "transformation/ATA2UAT(ADToolDomains).etl";
	}
}
