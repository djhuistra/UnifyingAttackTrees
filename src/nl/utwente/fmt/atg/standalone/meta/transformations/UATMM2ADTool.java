package nl.utwente.fmt.atg.standalone.meta.transformations;

import java.util.ArrayList;
import java.util.List;

import nl.utwente.fmt.atg.standalone.EpsilonStandaloneExample;

import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.etl.EtlModule;

public class UATMM2ADTool extends EpsilonStandaloneExample {
	
	private String inputFilePath;
	
	public UATMM2ADTool(String modelFileURI) {
		inputFilePath = modelFileURI;
	}
	
	@Override
	public IEolExecutableModule createModule() {
		return new EtlModule();
	}

	@Override
	public List<IModel> getModels() throws Exception {
		List<IModel> models = new ArrayList<IModel>();

		// Input model
		models.add(createEmfModel2("UATMM", inputFilePath, "models/UATMM.ecore", true, false));
		
		// Output model
		models.add(createPlainXmlModel3("ADTool", "models/temp.xml", false, true));

		return models;
	}

	@Override
	public String getSource() throws Exception {
		return "transformations/UATMM2ADTool.etl";
	}

	@Override
	public void postProcess() {
		System.out.println(result.toString());
	}
}
