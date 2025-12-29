package fr.ariouz.gkit.config;

import fr.ariouz.gkit.config.models.GKitConfig;
import fr.ariouz.gkit.util.YamlErrorFormatter;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.MarkedYAMLException;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigLoader {

	public static GKitConfig load(String profile) throws ConfigException{
		Path path = Path.of("gkit.yml");

		if (!Files.exists(path)) {
			throw new RuntimeException("gkit.yml not found in current directory");
		}

		try (InputStream input = Files.newInputStream(path)) {

			LoaderOptions options = new LoaderOptions();
			Constructor constructor = new Constructor(GKitConfig.class, options);
			Yaml yaml = new Yaml(constructor);

			GKitConfig base = yaml.load(input);

			if (base == null) throw new ConfigException("gkit.yml seems to be empty");
			ConfigDefaultApplier.apply(base);

			if (profile == null || base.getProfiles() == null) {
				return base;
			}

			GKitConfig profileConfig = base.getProfile(profile);
			if (profileConfig == null) {
				throw new ConfigException("Profile '"+profile+"' not found");
			}

			return ConfigMerger.merge(base, profileConfig);
		} catch (MarkedYAMLException e) {
			throw YamlErrorFormatter.formatYamlError(e);
		} catch (Exception e) {
			throw new ConfigException("Failed to load config: " + e.getMessage(), e);
		}
	}

}
