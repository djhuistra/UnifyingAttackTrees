package nl.utwente.fmt.atg.standalone;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplateFactoryModuleAdapter;
import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.models.IModel;

public class MM2ATCalcStandalone extends EpsilonStandaloneExample {

	private String inputFilePath;
	
	public MM2ATCalcStandalone(String modelFileURI) {
		inputFilePath = modelFileURI;
	}

	@Override
	public IEolExecutableModule createModule() {
		return new EglTemplateFactoryModuleAdapter(new EglTemplateFactory());
	}

	@Override
	public List<IModel> getModels() throws Exception {
		List<IModel> models = new ArrayList<IModel>();
		models.add(createEmfModel2("UATMM", inputFilePath, "models/UATMM.ecore", true, false));
		return models;
	}

	@Override
	public String getSource() throws Exception {
		return "transformations/MM(ADToolDomains)2ATA.egl";
	}

	@Override
	public void postProcess() {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream("ATCalcInput.txt"), "utf-8"))) {
		writer.write(result.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
