package nl.utwente.fmt.atg.standalone;

import java.io.File;
import java.io.IOException;

import nl.utwente.fmt.atg.standalone.meta.transformations.*;

public class ATTMainOld{


	public static void main(String[] args) throws IOException{

		if(args.length > 0) {
		  	File jarPath=new File(ATTMain.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	        String propertiesPath=jarPath.getParentFile().getAbsolutePath();
	        String filePath=propertiesPath+File.separator+args[0];
	        System.out.println("Input FilePath: "+filePath);
	        
	        
	        // Create temporary metamodel instance file.
	        File tempModelInstance = File.createTempFile("AttackTree-MetaModel-Instance", ".tmp"); 
	        String modelFileURI = tempModelInstance.toURI().getPath();
	        
	        //delete temporary file when the program is exited
	        tempModelInstance.deleteOnExit();
	        
	        
	        // Try the transformation sequence. 
	        // Currently hardcoded sequence. From ADTool2MM, and then MM2ATCalc.
			try {
				
				// From Input -> MM (Select 1)
				new ADTool2UATMM(filePath, modelFileURI).execute();
				//new ATA2MMStandalone().execute();
				
				// from MM -> Output (Select 1)
				
		        // TMP: Create a new file, and give it's URI as output to transformer.
		        File tmp = new File("ATCalcInput.txt");
		        tmp.createNewFile();
		        String outputFileURI = tmp.toURI().getPath();
		        
				new UATMM2ATCalc(modelFileURI, outputFileURI).execute();
				
		        // TMP: Create a new file, and give it's URI as output to transformer.
		        File tmp2 = new File("ADToolInput.xml");
		        tmp2.createNewFile();
		        String outputFileURI2 = tmp2.toURI().getPath();

				new UATMM2ADTool(modelFileURI, outputFileURI2).execute();
				
			} catch (Exception e) {
				// TODO Error is ignored;
				//e.printStackTrace();
			}
			
		} else {
			System.out.println("Specify <input_file>");
		}
	}
}