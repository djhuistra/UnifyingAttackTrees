package nl.utwente.fmt.atg.standalone.meta.transformations;

import java.util.ArrayList;
import java.util.List;

import nl.utwente.fmt.atg.standalone.EpsilonStandaloneExample;

import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.etl.EtlModule;

public class ADTool2UATMM extends EpsilonStandaloneExample {
	
	private String inputFilePath;
	private String outputFilePath;
	public ADTool2UATMM(String filePath, String output) {
		inputFilePath = filePath;
		outputFilePath = output;
	}

	@Override
	public IEolExecutableModule createModule() {
		return new EtlModule();
	}

	@Override
	public List<IModel> getModels() throws Exception {
		List<IModel> models = new ArrayList<IModel>();
		
		models.add(createPlainXmlModel4("ADTool", inputFilePath, true, false));
		//models.add(createEmfModel("UATMM", "models/Instance.model", "models/UATMM.ecore", false, true));
		models.add(createEmfModel2("UATMM", outputFilePath, "models/UATMM.ecore", false, true));
		//models.add(createPlainXmlModel3("ADTool", "models/ADTI.xml", true, false));
		
		return models;
	}

	@Override
	public String getSource() throws Exception {
		return "transformations/ADTool2UATMM.etl";
	}

	@Override
	public void postProcess() {
		//System.out.println(result);
	}
}
