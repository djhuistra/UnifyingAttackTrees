package nl.utwente.fmt.atg.standalone.transformer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import nl.utwente.fmt.atg.standalone.Language;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.etl.EtlModule;

/**
 * Transformer from the AT metamodel to the ADTool input format.
 * @author David Huistra
 */
public class UAT2UATBinary extends EpsilonTransformer {
	/** The singleton instance of this transformer. */
	private static UAT2UATBinary INSTANCE;
	
	/** Returns the singleton instance of this transformer. */
	public static UAT2UATBinary instance() {
		if (INSTANCE == null) {
			INSTANCE = new UAT2UATBinary();
		}
		return INSTANCE;
	}
	
	/** Constructor for the singleton instance of this transformer. */
	private UAT2UATBinary() {
		super(Language.UAT, Language.UAT_BINARY);
	}

	@Override
	public IEolExecutableModule createModule() {
		return new EolModule();
	}

	@Override
	public IModel[] getModels(String inputPath, String outputPath)
			throws EolModelLoadingException, URISyntaxException, FileNotFoundException {
		// EOL transformation only alters the input model, does not create output model
		return new IModel[] {createEmfModel(Role.BOTH, new File(inputPath).toURI().getPath() ) };
	}

	@Override
	public String getTransformation() {
		return "transformation/UAT2Binary.eol";
	}

	/*
	 * As EOL only modifies the input path, we copy the (temporary) input path file to the output file
	 */
	@Override
	public Object execute(String inputPath, String outputPath) throws Exception {
		Object result = super.execute(inputPath, outputPath);
		
		Path inputPathPath = Paths.get(new File(inputPath).toURI());
		String content = new String(Files.readAllBytes(inputPathPath));
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(outputPath), "utf-8"))) {
			writer.write(content);
		}
		return result;
	}
}
