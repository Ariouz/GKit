package fr.ariouz.gkit.cli;

import fr.ariouz.gkit.build.BuildException;
import fr.ariouz.gkit.build.artifact.ArtifactBuilder;
import fr.ariouz.gkit.build.image.NativeImageBuilder;
import fr.ariouz.gkit.build.image.arg.BuildArgException;
import fr.ariouz.gkit.cli.options.ProfileOption;
import fr.ariouz.gkit.config.ConfigException;
import fr.ariouz.gkit.config.ConfigProvider;
import fr.ariouz.gkit.config.models.BuildConfig;
import fr.ariouz.gkit.config.models.GKitConfig;
import fr.ariouz.gkit.util.Colors;
import fr.ariouz.gkit.util.StatusPrefix;
import picocli.CommandLine.*;

import java.io.File;
import java.nio.file.Path;
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
			description = "Build generated artifact to native image"
	)
	private boolean buildNative;

	@Option (
			names="--dry-run",
			description = "Run native-image in --dry-run mode, only applied if --native is set."
	)
	private boolean dryRun;


	@Override
	public Integer call() {
		return ErrorHandler.runWithErrorHandling(() -> {
			GKitConfig config = ConfigProvider.getConfig(profileOption.profile);

			if (dryRun) System.out.println(Colors.YELLOW + "[Dry-Run Mode Enabled]" + Colors.RESET);
			buildArtifactStep(config);
			if (buildNative)  new NativeImageBuilder().buildNativeImage(config, dryRun);

			return 0;
		});
	}

	private void buildArtifactStep(GKitConfig config) {
		BuildConfig buildConfig = config.getBuildConfig();
		System.out.println(Colors.CYAN + "Building artifact..." + Colors.RESET);
		File artifact = new ArtifactBuilder().build(buildConfig, dryRun);
		if (dryRun) System.out.println(StatusPrefix.DRY_RUN + " Artifact would be built at: " +
				Path.of(buildConfig.getProjectDir(), buildConfig.getArtifact().getPath())
				+ Colors.RESET);
		else System.out.println(StatusPrefix.SUCCESS + " Artifact successfully built: " +
				artifact
				+ Colors.RESET);
	}


}
