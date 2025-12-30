package fr.ariouz.gkit.build.image;

import fr.ariouz.gkit.build.BuildException;
import fr.ariouz.gkit.config.models.GKitConfig;
import fr.ariouz.gkit.util.Colors;
import fr.ariouz.gkit.util.NativeUtil;
import fr.ariouz.gkit.util.StatusPrefix;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class NativeImageBuilder {

	public void buildNativeImage(GKitConfig config, boolean dryRun) {
		System.out.println(Colors.CYAN + "Generating GraalVM Native Image..." + Colors.RESET);

		ProcessBuilder pb = getProcessBuilder(config, dryRun);

		if (dryRun) {
			System.out.println(StatusPrefix.DRY_RUN +
					" Would run: " + String.join(" ", pb.command()));
			System.out.println(StatusPrefix.DRY_RUN +
					" Native image would be generated at " +
					Path.of(config.getBuildConfig().getProjectDir(),
							config.getNativeImage().getOutput()));
			return;
		}

		runProcess(pb);
		System.out.println(StatusPrefix.SUCCESS +
				" Native image generated successfully." + Colors.RESET);
	}

	private void runProcess(ProcessBuilder pb) {
		try {
			Process process = pb.start();
			int exitCode = process.waitFor();
			if (exitCode != 0)
				throw new BuildException("Build process failed with exit code " + exitCode);
		} catch (IOException e) {
			throw new BuildException("Failed to start build process", e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new BuildException("Build process was interrupted", e);
		}
	}

	private static ProcessBuilder getProcessBuilder(GKitConfig config, boolean dryRun) {
		File nativeImage = NativeUtil.getNativeImage();
		if (nativeImage == null)
			throw new BuildException("native-image not found");

		String projectDir = config.getBuildConfig().getProjectDir();

		File jarArtifact = new File(projectDir,
				config.getBuildConfig().getArtifact().getPath());
		if (!jarArtifact.exists() && !dryRun)
			throw new BuildException("jar artifact not found");

		List<String> command = List.of(
				nativeImage.getAbsolutePath(),
				"-jar", jarArtifact.getAbsolutePath(),
				"-o", config.getNativeImage().getOutput()
		);

		ProcessBuilder pb = new ProcessBuilder(command);
		pb.inheritIO();
		pb.directory(new File(projectDir));
		return pb;
	}
}

