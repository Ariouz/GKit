package fr.ariouz.gkit.config.models;

import java.util.HashMap;
import java.util.Map;

@ConfigObject
public class ProjectConfig {

	private String name;
	private String version;
	private String mainClass;

	private Map<String, String> variables = new HashMap<>();

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	public String getMainClass() {
		return mainClass;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}

	public Map<String, String> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, String> variables) {
		this.variables = variables;
	}
}
