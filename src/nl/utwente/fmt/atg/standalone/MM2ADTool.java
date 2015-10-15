package nl.utwente.fmt.atg.standalone;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.etl.EtlModule;

public class MM2ADTool extends EpsilonStandaloneExample {
	
	@Override
	public IEolExecutableModule createModule() {
		return new EtlModule();
	}

	@Override
	public List<IModel> getModels() throws Exception {
		List<IModel> models = new ArrayList<IModel>();

		models.add(createEmfModel("UATMM", "models/Instance.model", "models/UATMM.ecore", true, false));
		models.add(createPlainXmlModel3("ADTool", "models/test.xml", false, true));
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
