package fr.ariouz.gkit.config;

import java.util.HashMap;

public class GKitConfig {

	private ProjectConfig project;
	private BuildConfig build;
	private NativeConfig nativeImage;
	private HashMap<String, GKitConfig> profiles;

	public ProjectConfig getProject() {
		return project;
	}

	public BuildConfig getBuildConfig() {
		return build;
	}

	public NativeConfig getNativeImage() {
		return nativeImage;
	}

	public void setProject(ProjectConfig project) {
		this.project = project;
	}

	public void setBuild(BuildConfig build) {
		this.build = build;
	}

	public void setNativeImage(NativeConfig nativeImage) {
		this.nativeImage = nativeImage;
	}

	public void setProfiles(HashMap<String, GKitConfig> profiles) {
		this.profiles = profiles;
	}

	public HashMap<String, GKitConfig> getProfiles() {
		return profiles;
	}

	public GKitConfig getProfile(String profile) {
		return profiles.get(profile);
	}

}
