package fr.ariouz.gkit.cli;

import fr.ariouz.gkit.build.BuildException;
import fr.ariouz.gkit.build.image.NativeImageBuilder;
import fr.ariouz.gkit.build.image.arg.BuildArgException;
import fr.ariouz.gkit.cli.options.ProfileOption;
import fr.ariouz.gkit.config.ConfigException;
import fr.ariouz.gkit.config.ConfigProvider;
import fr.ariouz.gkit.config.models.GKitConfig;
import fr.ariouz.gkit.util.Colors;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Option;

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
		return ErrorHandler.runWithErrorHandling(() -> {
			GKitConfig config = ConfigProvider.getConfig(profileOption.profile);

			if (dryRun) System.out.println(Colors.YELLOW + "[Dry-Run Mode Enabled]" + Colors.RESET);
			new NativeImageBuilder().buildNativeImage(config, dryRun);

			return 0;
		});
	}

}
