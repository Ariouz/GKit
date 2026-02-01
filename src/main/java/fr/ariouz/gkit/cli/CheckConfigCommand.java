package fr.ariouz.gkit.cli;


import fr.ariouz.gkit.build.image.arg.NativeBuildArgParser;
import fr.ariouz.gkit.cli.options.ProfileOption;
import fr.ariouz.gkit.config.ConfigException;
import fr.ariouz.gkit.config.ConfigPrinter;
import fr.ariouz.gkit.config.ConfigProvider;
import fr.ariouz.gkit.config.models.GKitConfig;
import fr.ariouz.gkit.util.Colors;
import fr.ariouz.gkit.util.StatusPrefix;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(
		name = "check-config",
		description = "Preview parsed config with profile overrides applied"
)
public class CheckConfigCommand implements Callable<Integer> {

	@Mixin
	private ProfileOption profileOption;


	@Override
	public Integer call() {
		return ErrorHandler.runWithErrorHandling(() -> {
			System.out.println(Colors.BLUE + "Performing config checks..." + Colors.RESET);
			System.out.println(Colors.BLUE + "Loading config..." + Colors.RESET);
			GKitConfig config = ConfigProvider.getConfig(profileOption.profile);

			System.out.println(StatusPrefix.SUCCESS + " Config loaded successfully." + Colors.RESET);

			System.out.println(Colors.YELLOW + "\nEffective configuration:" + Colors.RESET);
			System.out.println(Colors.YELLOW + "Active profile: " + Colors.RESET +
					(profileOption.profile != null ? profileOption.profile : "default"));
			System.out.println();
			ConfigPrinter.print(config);

			System.out.println(Colors.BLUE + "\nChecking build args..." + Colors.RESET);
			new NativeBuildArgParser().parseBuildArgs(config.getNativeImage());
			System.out.println(StatusPrefix.SUCCESS + " Build args are valid." + Colors.RESET);

			return 0;
		});
	}
}
