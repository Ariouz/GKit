package fr.ariouz.gkit.config.models;

@ConfigObject
public class BuildConfig {

	@ConfigDefault(".")
	private String projectDir;
	private BuildArtifact artifact;

	public String getProjectDir() {
		return projectDir;
	}

	public BuildArtifact getArtifact() {
		return artifact;
	}

	public void setProjectDir(String projectDir) {
		this.projectDir = projectDir;
	}

	public void setArtifact(BuildArtifact artifact) {
		this.artifact = artifact;
	}
}
