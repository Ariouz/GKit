package fr.ariouz.gkit.build.image;

import fr.ariouz.gkit.build.BuildException;
import fr.ariouz.gkit.build.ProcessRunner;
import fr.ariouz.gkit.build.image.arg.NativeBuildArgParser;
import fr.ariouz.gkit.config.models.GKitConfig;
import fr.ariouz.gkit.util.Colors;
import fr.ariouz.gkit.util.NativeUtil;
import fr.ariouz.gkit.util.StatusPrefix;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class NativeImageBuilder {

	public void buildNativeImage(GKitConfig config, boolean dryRun) {
		System.out.println(Colors.CYAN + "Generating GraalVM Native Image..." + Colors.RESET);

		File nativeImage = getNativeImage();
		if (nativeImage == null)
			throw new BuildException("native-image not found");

		String projectDir = config.getBuildConfig().getProjectDir();

		File jarArtifact = new File(projectDir,
				config.getBuildConfig().getArtifact().getPath());
		if (!jarArtifact.exists() && !dryRun)
			throw new BuildException("jar artifact not found");

		List<String> command = new ArrayList<>(List.of(
				nativeImage.getAbsolutePath(),
				"-jar", jarArtifact.getAbsolutePath(),
				"-o", config.getNativeImage().getOutput()
		));

		command.addAll(new NativeBuildArgParser().parseBuildArgs(config.getNativeImage()));

		new ProcessRunner().run(
				"Build native image",
				command,
				new File(projectDir),
				dryRun
		);

		if (dryRun) {
			System.out.println(StatusPrefix.DRY_RUN +
					" Native image would be generated at " +
					Path.of(config.getBuildConfig().getProjectDir(),
							config.getNativeImage().getOutput()));
		} else
			System.out.println(StatusPrefix.SUCCESS +
				" Native image generated successfully." + Colors.RESET);
	}

	protected File getNativeImage() {
		return NativeUtil.getNativeImage();
	}

}

