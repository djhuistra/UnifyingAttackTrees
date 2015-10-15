package nl.utwente.fmt.atg.standalone;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.EglTemplateFactoryModuleAdapter;
import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.models.IModel;

public class MM2ATCalcStandalone extends EpsilonStandaloneExample {

	
	@Override
	public IEolExecutableModule createModule() {
		return new EglTemplateFactoryModuleAdapter(new EglTemplateFactory());
	}

	@Override
	public List<IModel> getModels() throws Exception {
		List<IModel> models = new ArrayList<IModel>();
		models.add(createEmfModel("UATMM", "models/Instance.model", "models/UATMM.ecore", true, true));
		return models;
	}

	@Override
	public String getSource() throws Exception {
		return "transformations/MM(ADToolDomains)2ATA.egl";
	}

	@Override
	public void postProcess() {
		System.out.println(result);
	}
}
