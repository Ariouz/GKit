package fr.ariouz.gkit.config;

import fr.ariouz.gkit.config.models.GKitConfig;

public class ConfigProvider {

	private static GKitConfig config;

	public static GKitConfig getConfig(String profile) {
		if (config == null) config = ConfigLoader.load(profile);
		return config;
	}

	public static GKitConfig getConfig() {
		return getConfig(null);
	}
}
