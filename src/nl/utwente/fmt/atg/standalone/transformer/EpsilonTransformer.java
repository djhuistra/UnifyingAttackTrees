package nl.utwente.fmt.atg.standalone.transformer;

import static nl.utwente.fmt.atg.standalone.transformer.EpsilonTransformer.Role.SOURCE;
import static nl.utwente.fmt.atg.standalone.transformer.EpsilonTransformer.Role.TARGET;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import nl.utwente.fmt.atg.standalone.Language;

import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.plainxml.PlainXmlModel;
import org.eclipse.epsilon.eol.IEolExecutableModule;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;

/**
 * Generic abstract class that specifies the abstract meta-info required for
 * running a transformation with java.
 */
public abstract class EpsilonTransformer implements ITransformer {
	/** The source language of this transformer. */
	private final Language sourceLanguage;
	/** The target language of this transformer. */
	private final Language targetLanguage;

	/**
	 * Constructs a transformer from a given source language to a given target
	 * language.
	 */
	public EpsilonTransformer(Language sourceLanguage, Language targetLanguage) {
		this.sourceLanguage = sourceLanguage;
		this.targetLanguage = targetLanguage;
	}

	@Override
	public Language getSourceLanguage() {
		return sourceLanguage;
	}

	@Override
	public Language getTargetLanguage() {
		return targetLanguage;
	}

	public abstract IEolExecutableModule createModule();

	/** Returns the filename of the transformation definition. */
	public abstract String getTransformation();

	/**
	 * Converts the file names of the source and target models into IModels.
	 * 
	 * @param inputPath
	 *            file name of the source model
	 * @param outputPath
	 *            file name of the target model
	 * @throws EolModelLoadingException
	 *             if one of the models could not be loaded
	 * @throws URISyntaxException
	 *             if one of the file names is not a valid URI
	 * @throws FileNotFoundException
	 *             if one of the files did not exist
	 */
	public abstract IModel[] getModels(String inputPath, String outputPath)
			throws EolModelLoadingException, URISyntaxException,
			FileNotFoundException;

	public Object execute(String inputPath, String outputPath) throws Exception {
		IEolExecutableModule module = createModule();
		module.parse(toFileURI(getTransformation()));

		if (module.getParseProblems().size() > 0) {
			System.err.println("Parse errors occured...");
			for (ParseProblem problem : module.getParseProblems()) {
				System.err.println(problem.toString());
			}
			return null;
		}
		for (IModel model : getModels(inputPath, outputPath)) {
			module.getContext().getModelRepository().addModel(model);
		}
		Object result = module.execute();
		module.getContext().getModelRepository().dispose();
		return result;
	}

	/** Returns the language corresponding to a given role. */
	protected Language getLanguage(Role role) {
		return role == SOURCE ? getSourceLanguage() : getTargetLanguage();
	}

	protected EmfModel createEmfModel(Role role, String model)
			throws FileNotFoundException, EolModelLoadingException,
			URISyntaxException {
		Language language = getLanguage(role);
		assert language.getLocation() != null : String.format(
				"Can't create EMF model for language %s", language);
		StringProperties props = new StringProperties();
		props.put(EmfModel.PROPERTY_NAME, language.getName());
		props.put(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI,
				toFileURI(language.getLocation()));
		props.put(EmfModel.PROPERTY_MODEL_URI, model);
		props.put(EmfModel.PROPERTY_READONLOAD, "" + (role == SOURCE));
		props.put(EmfModel.PROPERTY_STOREONDISPOSAL, "" + (role == TARGET));

		EmfModel result = new EmfModel();
		result.load(props, null);
		return result;
	}

	protected PlainXmlModel createPlainXmlModel(Role role, String model)
			throws FileNotFoundException, EolModelLoadingException,
			URISyntaxException {
		StringProperties props = new StringProperties();
		props.put(PlainXmlModel.PROPERTY_NAME, getLanguage(role).getName());
		props.put(PlainXmlModel.PROPERTY_URI, model);
		props.put(PlainXmlModel.PROPERTY_READONLOAD, "" + (role == SOURCE));
		props.put(PlainXmlModel.PROPERTY_STOREONDISPOSAL, "" + (role == TARGET));

		PlainXmlModel result = new PlainXmlModel();
		result.load(props, null);
		return result;
	}

	/**
	 * Converts a file name into a file URI string, replacing any references to
	 * "bin" directories in the path to "sub".
	 */
	protected URI toFileURI(String fileName) throws FileNotFoundException,
			URISyntaxException {
		URI result;
		URL fileURL = Language.class.getResource(fileName);
		if (fileURL == null) {
			throw new FileNotFoundException(fileName + " not found");
		}
		URI fileURI = fileURL.toURI();
		if (fileURI.toString().contains("bin")) {
			result = new URI(fileURI.toString().replaceAll("bin", "src"));
		} else {
			result = fileURI;
		}
		return result;
	}

	/** Role of a model or language in a transformation. */
	protected enum Role {
		SOURCE, TARGET, ;
	}
}