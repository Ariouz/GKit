package fr.ariouz.gkit.config;

import fr.ariouz.gkit.config.models.GKitConfig;
import fr.ariouz.gkit.config.variables.ConfigExpander;
import fr.ariouz.gkit.config.variables.EnvVariableResolver;
import fr.ariouz.gkit.config.variables.GKitVariableResolver;

import java.util.List;

public class ConfigProvider {

	private static GKitConfig config;

	public static GKitConfig getConfig(String profile) {
		if (config == null) {
			config = ConfigLoader.load(profile);
			ConfigExpander expander = new ConfigExpander(
					List.of(
							new EnvVariableResolver(),
							new GKitVariableResolver(config.getProject().getVariables())
					)
			);
			expander.expandConfig(config);
		}
		return config;
	}

	public static GKitConfig getConfig() {
		return getConfig(null);
	}
}
