package nl.utwente.fmt.atg.standalone;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.plainxml.PlainXmlModel;
import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IModel;

public abstract class EpsilonStandaloneExample {
  
  protected IEolExecutableModule module;
  protected List<Variable> parameters = new ArrayList<Variable>();
  
  protected Object result;
  
  public abstract IEolExecutableModule createModule();
  
  public abstract String getSource() throws Exception;
  
  public abstract List<IModel> getModels() throws Exception;
  
  public void postProcess() {};
  
  public void preProcess() {};
  
  public void execute() throws Exception {
    
    module = createModule();
    module.parse(getFileURI(getSource()));
    
    if (module.getParseProblems().size() > 0) {
      System.err.println("Parse errors occured...");
      for (ParseProblem problem : module.getParseProblems()) {
        System.err.println(problem.toString());
      }
      return;
    }for (IModel model : getModels()) {
      module.getContext().getModelRepository().addModel(model);
    }
    
    for (Variable parameter : parameters) {
      module.getContext().getFrameStack().put(parameter);
    }
    
    preProcess();
    result = execute(module);
    postProcess();
    
    module.getContext().getModelRepository().dispose();
  }
  
  public List<Variable> getParameters() {
    return parameters;
  }
  
  protected Object execute(IEolExecutableModule module) 
      throws EolRuntimeException {
    return module.execute();
  }
  
  protected EmfModel createEmfModel(String name, String model, 
      String metamodel, boolean readOnLoad, boolean storeOnDisposal) 
          throws EolModelLoadingException, URISyntaxException {
    EmfModel emfModel = new EmfModel();
    StringProperties properties = new StringProperties();
    properties.put(EmfModel.PROPERTY_NAME, name);
    properties.put(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI,
    		getFileURI(metamodel).toString());
    properties.put(EmfModel.PROPERTY_MODEL_URI, 
    		getFileURI(model).toString());
    properties.put(EmfModel.PROPERTY_READONLOAD, readOnLoad + "");
    properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, 
        storeOnDisposal + "");
    emfModel.load(properties, null);
    return emfModel;
  }
  
  protected EmfModel createEmfModel2(String name, String model, 
	      String metamodel, boolean readOnLoad, boolean storeOnDisposal) 
	          throws EolModelLoadingException, URISyntaxException {
	    EmfModel emfModel = new EmfModel();
	    StringProperties properties = new StringProperties();
	    properties.put(EmfModel.PROPERTY_NAME, name);
	    properties.put(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI,
	    		getFileURI(metamodel).toString());
	    properties.put(EmfModel.PROPERTY_MODEL_URI, model);
	    properties.put(EmfModel.PROPERTY_READONLOAD, readOnLoad + "");
	    properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, 
	        storeOnDisposal + "");
	    emfModel.load(properties, null);
	    return emfModel;
	  }
  
//  protected PlainXmlModel createPlainXmlModel(String name, String model, 
//	      boolean readOnLoad, boolean storeOnDisposal) throws EolModelLoadingException {
//	  
//	  PlainXmlModel XmlModel = new PlainXmlModel();
//	  StringProperties properties = new StringProperties();
//	  
//	  properties.put(XmlModel.PROPERTY_NAME, name);
//	  properties.put(XmlModel.PROPERTY_FILE, model);
//	  properties.put(XmlModel.PROPERTY_READONLOAD, readOnLoad + "");
//	  properties.put(XmlModel.PROPERTY_STOREONDISPOSAL, 
//	        storeOnDisposal + "");
//	  
//	  XmlModel.load(properties, (IRelativePathResolver) null);
//
//	  
//	  return XmlModel;
//  }
  
  protected PlainXmlModel createPlainXmlModel3(String name, String model, 
	      boolean readOnLoad, boolean storeOnDisposal) throws EolModelLoadingException, URISyntaxException {
	  
	  PlainXmlModel XmlModel = new PlainXmlModel();
	  StringProperties properties = new StringProperties();
	  
	  properties.put(PlainXmlModel.PROPERTY_NAME, name);
	  properties.put(PlainXmlModel.PROPERTY_URI, getFileURI(model).toString());
	  properties.put(PlainXmlModel.PROPERTY_READONLOAD, readOnLoad + "");
	  properties.put(PlainXmlModel.PROPERTY_STOREONDISPOSAL, 
	        storeOnDisposal + "");
	  
	  XmlModel.load(properties, null);

	  
	  return XmlModel;
  }
  
  protected PlainXmlModel createPlainXmlModel4(String name, String model, 
	      boolean readOnLoad, boolean storeOnDisposal) throws EolModelLoadingException, URISyntaxException {
	  
	  PlainXmlModel XmlModel = new PlainXmlModel();
	  StringProperties properties = new StringProperties();
	  
	  properties.put(PlainXmlModel.PROPERTY_NAME, name);
	  properties.put(PlainXmlModel.PROPERTY_URI, model);
	  properties.put(PlainXmlModel.PROPERTY_READONLOAD, readOnLoad + "");
	  properties.put(PlainXmlModel.PROPERTY_STOREONDISPOSAL, 
	        storeOnDisposal + "");
	  
	  XmlModel.load(properties, null);

	  
	  return XmlModel;
  }
  
//  protected PlainXmlModel createPlainXMLModel2(String name, String model, 
//	      boolean readOnLoad, boolean storeOnDisposal) throws EolModelLoadingException {
//	  
//	  PlainXmlModel XMLmodel = new PlainXmlModel();
//	  
//	  XMLmodel.setName(name);
//	  try {
//		XMLmodel.setFile(getFile(model));
//	} catch (URISyntaxException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	  //XMLmodel.setUri("/ATA2MMStandalone/src/nl/utwente/fmt/atg/standalone/models/ATA_SMALL.xml");  
//	  XMLmodel.load();
//
//	  
//	  return XMLmodel;
//  }

  protected EmfModel createEmfModelByURI(String name, String model, 
      String metamodel, boolean readOnLoad, boolean storeOnDisposal) 
          throws EolModelLoadingException, URISyntaxException {
    EmfModel emfModel = new EmfModel();
    StringProperties properties = new StringProperties();
    properties.put(EmfModel.PROPERTY_NAME, name);
    properties.put(EmfModel.PROPERTY_METAMODEL_URI, metamodel);
    properties.put(EmfModel.PROPERTY_MODEL_URI, 
    		getFileURI(model).toString());
    properties.put(EmfModel.PROPERTY_READONLOAD, readOnLoad + "");
    properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, 
        storeOnDisposal + "");
    emfModel.load(properties, null);
    return emfModel;
  }
  
	protected URI getFileURI(String fileName) throws URISyntaxException {
		
		URI binUri = EpsilonStandaloneExample.class.
				getResource(fileName).toURI();
		URI uri = null;
		
		if (binUri.toString().indexOf("bin") > -1) {
			uri = new URI(binUri.toString().replaceAll("bin", "src"));
		}
		else {
			uri = binUri;
		}
		
		return uri;
	}
  
}