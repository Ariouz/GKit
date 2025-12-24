package fr.ariouz.gkit.cli;


import fr.ariouz.gkit.config.ConfigLoader;
import fr.ariouz.gkit.config.ConfigPrinter;
import fr.ariouz.gkit.config.GKitConfig;
import picocli.CommandLine.*;

@Command(
		name = "check-config",
		description = "Preview parsed config with profile overrides applied"
)
public class CheckConfigCommand implements Runnable {

	@Option(
			names= {"-p", "--profile"},
			description = "Profile to use"
	)
	private String profile;


	@Override
	public void run() {
		GKitConfig config;

		if (profile == null) config = ConfigLoader.load(null);
		else config = ConfigLoader.load(profile);

		System.out.println("Config loaded successfully");
		System.out.println("Active profile: " + profile);
		System.out.println("Effective configuration: \n");

		ConfigPrinter.print(config);
	}
}
