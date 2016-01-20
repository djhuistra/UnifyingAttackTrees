package nl.utwente.fmt.atg.standalone.meta.transformations;

import java.util.ArrayList;
import java.util.List;

import nl.utwente.fmt.atg.standalone.AbstractStandaloneTransformation;

import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.etl.EtlModule;

public class UATMM2ADTool extends AbstractStandaloneTransformation {
	
	private String inputFilePath;
	private String outputFilePath;
	
	public UATMM2ADTool(String filePath, String output) {
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

		// Input model
		models.add(createEmfModel2("UATMM", inputFilePath, "models/UATMM.ecore", true, false));
		
		// Output model
		models.add(createPlainXmlModel4("ADTool", outputFilePath, false, true));

		return models;
	}

	@Override
	public String getSource() throws Exception {
		return "transformations/UATMM2ADTool.etl";
	}

	@Override
	public void postProcess() {
		//Output is written to output model
	}
}
