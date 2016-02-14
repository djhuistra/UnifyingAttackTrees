package nl.utwente.fmt.atg.standalone.transformer;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;

import nl.utwente.fmt.atg.standalone.Language;

import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplateFactoryModuleAdapter;
import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;

public class UAT2ATCalc extends EpsilonTransformer {
	/** The singleton instance of this transformer. */
	private static UAT2ATCalc INSTANCE;
	
	/** Returns the singleton instance of this transformer. */
	public static UAT2ATCalc instance() {
		if (INSTANCE == null) {
			INSTANCE = new UAT2ATCalc();
		}
		return INSTANCE;
	}
	
	/** Constructor for the singleton instance of this transformer. */
	private UAT2ATCalc() {
		super(Language.UAT, Language.AT_CALC);
	}

	@Override
	public IEolExecutableModule createModule() {
		return new EglTemplateFactoryModuleAdapter(new EglTemplateFactory());
	}

	@Override
	public IModel[] getModels(String inputPath, String outputPath)
			throws EolModelLoadingException, URISyntaxException, FileNotFoundException {
		// EGL transformation does not have output model, only generated output
		return new IModel[] { createEmfModel(Role.SOURCE, inputPath) };
	}

	@Override
	public String getTransformation() {
		return "transformation/UAT(ADToolDomains)2ATCalc.egl";
	}

	@Override
	public Object execute(String inputPath, String outputPath) throws Exception {
		Object result = super.execute(inputPath, outputPath);
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(outputPath), "utf-8"))) {
			writer.write(result.toString());
		}
		return result;
	}
}
