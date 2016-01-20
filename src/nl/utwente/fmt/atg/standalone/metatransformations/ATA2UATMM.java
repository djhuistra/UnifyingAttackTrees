package nl.utwente.fmt.atg.standalone.metatransformations;

import java.util.ArrayList;
import java.util.List;

import nl.utwente.fmt.atg.standalone.EpsilonStandaloneExample;

import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.etl.EtlModule;

public class ATA2UATMM extends EpsilonStandaloneExample {
	
	@Override
	public IEolExecutableModule createModule() {
		return new EtlModule();
	}

	@Override
	public List<IModel> getModels() throws Exception {
		List<IModel> models = new ArrayList<IModel>();

		models.add(createEmfModel("UATMM", "models/Instance.model", "models/UATMM.ecore", false, true));
		models.add(createPlainXmlModel3("ATA", "models/ATA_SMALL.xml", true, false));
		return models;
	}

	@Override
	public String getSource() throws Exception {
		return "transformations/ATA2MM(ADToolDomains).etl";
	}

	@Override
	public void postProcess() {
		//System.out.println(result);
	}
}
