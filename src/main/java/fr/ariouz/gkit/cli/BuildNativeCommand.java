package fr.ariouz.gkit.cli;

import fr.ariouz.gkit.build.BuildException;
import fr.ariouz.gkit.build.artifact.ArtifactBuilder;
import fr.ariouz.gkit.build.image.NativeImageBuilder;
import fr.ariouz.gkit.cli.options.ProfileOption;
import fr.ariouz.gkit.config.ConfigException;
import fr.ariouz.gkit.config.ConfigProvider;
import fr.ariouz.gkit.config.models.GKitConfig;
import fr.ariouz.gkit.util.Colors;
import fr.ariouz.gkit.util.StatusPrefix;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Option;

import java.io.File;
import java.util.concurrent.Callable;

@Command (
		name="native",
		description = "Build native image"
)
public class BuildNativeCommand implements Callable<Integer> {

	@Mixin
	private ProfileOption profileOption;

	@Option (
			names="--dry-run",
			description = "Run native-image in --dry-run mode."
	)
	private boolean dryRun;


	@Override
	public Integer call() {
		try {
			GKitConfig config = ConfigProvider.getConfig(profileOption.profile);

			if (dryRun) System.out.println(Colors.YELLOW + "[Dry-Run Mode Enabled]" + Colors.RESET);
			new NativeImageBuilder().buildNativeImage(config, dryRun);

		} catch (ConfigException e) {
			System.err.println(Colors.RED + "Configuration Error: " + e.getMessage() + Colors.RESET);
			return 1;
		} catch (BuildException e) {
			System.err.println(Colors.RED + "Build Failed: " + e.getMessage() + Colors.RESET);
			return 1;
		} catch (Exception e) {
			System.err.println(Colors.RED + "An unexpected error occurred:" + Colors.RESET);
			e.printStackTrace();
			return 2;
		}

		return 0;
	}

}
