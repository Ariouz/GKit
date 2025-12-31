package fr.ariouz.gkit.cli;


import fr.ariouz.gkit.cli.options.ProfileOption;
import fr.ariouz.gkit.config.ConfigException;
import fr.ariouz.gkit.config.ConfigPrinter;
import fr.ariouz.gkit.config.ConfigProvider;
import fr.ariouz.gkit.config.models.GKitConfig;
import fr.ariouz.gkit.util.Colors;
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
		try {
			GKitConfig config = ConfigProvider.getConfig(profileOption.profile);

			System.out.println("Config loaded successfully");
			System.out.println("Active profile: " + profileOption.profile);
			System.out.println("Effective configuration: \n");

			ConfigPrinter.print(config);
		} catch (ConfigException e) {
			System.err.println(Colors.RED + e.getMessage() + Colors.RESET);
			return 1;
		} catch (Exception e) {
			System.err.println("An unexpected error occured:");
			e.printStackTrace();
			return 2;
		}
		return 0;
	}
}
