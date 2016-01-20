package nl.utwente.fmt.atg.standalone.transformers;

import java.io.File;
import java.io.IOException;

import nl.utwente.fmt.atg.standalone.EpsilonStandaloneExample;
import nl.utwente.fmt.atg.standalone.meta.transformations.*;

public class ADTool2ADToolBinary implements ITransformer {

	private String inputFilePath;
	private String outputFilePath;
	
	private EpsilonStandaloneExample ADTool2UATMM;
	private EpsilonStandaloneExample UATMM2ATCalc;
	private EpsilonStandaloneExample UATMM2BinaryUATMM;
	
	public ADTool2ADToolBinary(String inputfilePath, String outputfilePath){
		this.inputFilePath = inputfilePath;
		this.outputFilePath = outputfilePath;
	}
	
	public void initialize(){
		
        // Create temporary metamodel instance file.
        File tempModelInstance = null;
		try {
			tempModelInstance = File.createTempFile("AttackTree-MetaModel-Instance", ".tmp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    String modelFileURI = tempModelInstance.toURI().getPath();
        
	    //delete temporary file when the program is exited
	    tempModelInstance.deleteOnExit();
	    
	    // Initialize first transformation.
		ADTool2UATMM = new ADTool2UATMM(inputFilePath, modelFileURI);
		
		// Initalize the 2Binary Transformation.
		UATMM2BinaryUATMM = new UATMM2BinaryUATMM(modelFileURI);
		
		// We assume the file does not exist, thus create it and obtain its URI.
        File tmp2 = new File(outputFilePath);
        try {
			tmp2.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String outputFileURI2 = tmp2.toURI().getPath();
  
        // Initalize second transformation;
		UATMM2ATCalc = new UATMM2ADTool(modelFileURI, outputFileURI2);
		
	}
	
	public void execute(){
		try {
			ADTool2UATMM.execute();
			UATMM2BinaryUATMM.execute();
			UATMM2ATCalc.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}


}
