package fr.ariouz.gkit.cli;


import fr.ariouz.gkit.config.ConfigException;
import fr.ariouz.gkit.config.ConfigLoader;
import fr.ariouz.gkit.config.ConfigPrinter;
import fr.ariouz.gkit.config.models.GKitConfig;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(
		name = "check-config",
		description = "Preview parsed config with profile overrides applied"
)
public class CheckConfigCommand implements Callable<Integer> {

	@Option(
			names= {"-p", "--profile"},
			description = "Profile to use"
	)
	private String profile;


	@Override
	public Integer call() {
		try {
			GKitConfig config = ConfigLoader.load(profile);

			System.out.println("Config loaded successfully");
			System.out.println("Active profile: " + profile);
			System.out.println("Effective configuration: \n");

			ConfigPrinter.print(config);
		} catch (ConfigException e) {
			System.err.println(e.getMessage());
			return 1;
		} catch (Exception e) {
			System.err.println("An unexpected error occured:");
			e.printStackTrace();
			return 2;
		}
		return 0;
	}
}
