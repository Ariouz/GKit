package fr.ariouz.gkit.config;

public class ProjectConfig {

	private String name;
	private String version;
	private String mainClass;

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
}
