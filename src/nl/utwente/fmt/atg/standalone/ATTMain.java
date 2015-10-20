package nl.utwente.fmt.atg.standalone;

import java.io.File;
import java.io.IOException;

public class ATTMain {

	public static void main(String[] args) throws IOException{

		if(args.length > 0) {
		  	File jarPath=new File(ATTMain.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	        String propertiesPath=jarPath.getParentFile().getAbsolutePath();
	        String filePath=propertiesPath+"\\"+args[0];
	        //System.out.println("FilePath: "+filePath);
	        
	        File tempModelInstance = File.createTempFile("AttackTree-MetaModel-Instance", ".tmp"); 
	        
	      //delete temporary file when the program is exited
	        tempModelInstance.deleteOnExit();
	        
	        //File modelFile = new File(propertiesPath+"\\Instance.model");
	        //modelFile.createNewFile();
	        
	        String modelFileURI = tempModelInstance.toURI().toString();
	        
	        //ToDO: Kijk naar TEMP FIles
	        // e.g. http://stackoverflow.com/questions/617414/create-a-temporary-directory-in-java
	        
	        
	        //prop.load(new FileInputStream(propertiesPath+"/resource/locations.properties"));
			try {
				
				// From Input -> MM (Select 1)
				new ADTool2MM(filePath, modelFileURI).execute();
//				new ATA2MMStandalone().execute();
				
				
				// from MM -> Output (Select 1)
				new MM2ATCalcStandalone(modelFileURI).execute();
		//		new MM2ADTool().execute();
				
			} catch (Exception e) {
				// TODO Error is ignored;
				//e.printStackTrace();
			}
			
		} else {
			System.out.println("Specify <input_file>");
		}
	}

}
