package nl.utwente.fmt.atg.standalone;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public enum Language {
	/** The Universal Attack Tree meta-model. */
	UAT("UATMM", "meta/UATMM.ecore"),
	/** Attack Tree Analyzer XML format. */
	ATA("ATA"),
	/** ADTool XML format. */
	AD_TOOL("ADTool"),
	/** ATCalc textual format. */
	AT_CALC("ATCalc"), 
	/** ADTool Binary Tree XML format*/
	AD_TOOL_BINARY("ADTBin"),
	UAT_BINARY("UATBin", "meta/UATMM.ecore"),;
	
	private final String name;
	private final String location;
	
	/** Constructs a language without location. */
	private Language(String name) {
		this(name, null);
	}
	
	/** Constructs a language with possibly null location. */
	private Language(String name, String location) {
		this.name = name;
		this.location = location;
	}
	
	/** Returns the name of this language. */
	public String getName() {
		return name;
	}
	
	/** Returns the (possibly <code>null</code>) location of the language definition. */
	public String getLocation() {
		return location;
	}
	
	private static final Map<String,Language> languageMap;
	
	static {
		languageMap = new TreeMap<>();
		for (Language l: Language.values()) {
			languageMap.put(l.getName(), l);
		}
	}
	
	/** Returns the language with a given name. */
	public static Language getLanguage(String name) {
		return languageMap.get(name);
	}
	
	/** Returns the map from language names to languages. */
	public static Map<String,Language> getLanguageMap() {
		return Collections.unmodifiableMap(languageMap);
	}
}
