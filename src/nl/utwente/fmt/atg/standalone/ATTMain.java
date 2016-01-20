package nl.utwente.fmt.atg.standalone;

import java.io.File;
import java.io.IOException;

import nl.utwente.fmt.atg.standalone.metatransformations.ADTool2UATMM;
import nl.utwente.fmt.atg.standalone.metatransformations.UATMM2ATCalc;

/**
 * Main class of standalone transformation project.
 * 
 * This class reads the input argument for a file, creates a temporary store and 
 * runs a sequence of transformations
 *
 */
public class ATTMain {

	public static void main(String[] args) throws IOException{

		if(args.length > 0) {
		  	File jarPath=new File(ATTMain.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	        String propertiesPath=jarPath.getParentFile().getAbsolutePath();
	        String filePath=propertiesPath+File.separator+args[0];
	        System.out.println("Input FilePath: "+filePath);
	        
	        
	        // Create temporary metamodel instance file.
	        File tempModelInstance = File.createTempFile("AttackTree-MetaModel-Instance", ".tmp"); 
	        String modelFileURI = tempModelInstance.toURI().toString();
	        
	        //delete temporary file when the program is exited
	        tempModelInstance.deleteOnExit();
	        
	        
	        // Try the transformation sequence. 
	        // Currently hardcoded sequence. From ADTool2MM, and then MM2ATCalc.
			try {
				
				// From Input -> MM (Select 1)
				new ADTool2UATMM(filePath, modelFileURI).execute();
				//new ATA2MMStandalone().execute();
				
				
				// from MM -> Output (Select 1)
				new UATMM2ATCalc(modelFileURI).execute();
				//new MM2ADTool().execute();
				
			} catch (Exception e) {
				// TODO Error is ignored;
				//e.printStackTrace();
			}
			
		} else {
			System.out.println("Specify <input_file>");
		}
	}

}
