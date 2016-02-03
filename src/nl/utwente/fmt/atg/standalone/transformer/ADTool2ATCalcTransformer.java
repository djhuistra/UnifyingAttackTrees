package nl.utwente.fmt.atg.standalone.transformer;

import java.io.File;
import java.io.IOException;

public class ADTool2ATCalcTransformer implements ITransformer {

	private String inputFilePath;
	private String outputFilePath;
	
	private EpsilonTransformer ADTool2UATMM;
	private EpsilonTransformer UATMM2ATCalc;
	
	public ADTool2ATCalcTransformer(String inputfilePath, String outputfilePath){
		this.inputFilePath = inputfilePath;
		this.outputFilePath = outputfilePath;
	}
	
	public void execute(String inputPath, String outputPath){
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
		EpsilonTransformer ADTool2UATMM = new ADTool2UAT();
		
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
        EpsilonTransformer UATMM2ATCalc = new UAT2ATCalc();
		try {
			ADTool2UATMM.execute(inputFilePath, modelFileURI);
			UATMM2ATCalc.execute(modelFileURI, outputFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}


}
