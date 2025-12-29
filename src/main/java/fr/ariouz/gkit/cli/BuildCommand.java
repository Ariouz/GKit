package fr.ariouz.gkit.cli;

import fr.ariouz.gkit.build.artifact.ArtifactBuilder;
import fr.ariouz.gkit.cli.options.ProfileOption;
import fr.ariouz.gkit.config.ConfigException;
import fr.ariouz.gkit.config.ConfigProvider;
import fr.ariouz.gkit.config.models.BuildArtifact;
import fr.ariouz.gkit.config.models.GKitConfig;
import fr.ariouz.gkit.util.Colors;
import picocli.CommandLine.*;

import java.io.File;
import java.util.concurrent.Callable;

@Command (
		name="build",
		description = "Build artifact"
)
public class BuildCommand implements Callable<Integer> {

	@Mixin
	private ProfileOption profileOption;

	@Option(
			names = {"--native"},
			description = "Build native image after artifact build"
	)
	private boolean buildNative;


	@Override
	public Integer call() {
		try {
			GKitConfig config = ConfigProvider.getConfig(profileOption.profile);

			File artifact = new ArtifactBuilder().build(config.getBuildConfig());
			System.out.println("Artifact successfully built at " + artifact.getAbsolutePath());


		} catch (RuntimeException e) {
			System.err.println(Colors.RED + e.getMessage());
			return 1;
		} catch (Exception e) {
			System.err.println("An unexpected error occured:");
			e.printStackTrace();
			return 2;
		}

		return 0;
	}
}
