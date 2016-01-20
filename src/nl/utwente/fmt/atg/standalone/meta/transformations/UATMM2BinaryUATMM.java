package nl.utwente.fmt.atg.standalone.meta.transformations;

import java.util.ArrayList;
import java.util.List;

import nl.utwente.fmt.atg.standalone.AbstractStandaloneTransformation;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.models.IModel;

public class UATMM2BinaryUATMM extends AbstractStandaloneTransformation {

	private String inputFilePath;
	
	public UATMM2BinaryUATMM(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}
	
	@Override
	public IEolExecutableModule createModule() {
		return new EolModule();
	}

	@Override
	public List<IModel> getModels() throws Exception {
		List<IModel> models = new ArrayList<IModel>();
		models.add(createEmfModel2("Model", inputFilePath, 
				"models/UATMM.ecore", true, true));
		return models;
	}

	@Override
	public String getSource() throws Exception {
		return "transformations/UATMM2Binary.eol";
	}

	@Override
	public void postProcess() {
		
	}
}
