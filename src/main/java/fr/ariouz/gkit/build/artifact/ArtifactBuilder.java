package fr.ariouz.gkit.build.artifact;

import fr.ariouz.gkit.build.BuildException;
import fr.ariouz.gkit.config.models.BuildArtifact;
import fr.ariouz.gkit.config.models.BuildConfig;
import fr.ariouz.gkit.util.OSHelper;
import fr.ariouz.gkit.util.StatusPrefix;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class ArtifactBuilder {

	public File build(BuildConfig config) {
		return build(config, false);
	}

	public File build(BuildConfig config, boolean dryRun) {
		BuildArtifact buildArtifact = config.getArtifact();
		try {
			ProcessBuilder pb = new ProcessBuilder();

			List<String> command = OSHelper.isWindows()
					? List.of("cmd.exe", "/c", buildArtifact.getCommand())
					: List.of("sh", "-c", buildArtifact.getCommand());

			if (dryRun) {
				System.out.println(StatusPrefix.DRY_RUN + " Would run: " + String.join(" ", command));
				return null;
			}

			pb.command(command);
			pb.directory(new File(config.getProjectDir()));
			pb.inheritIO();

			Process process = pb.start();
			int exitValue = process.waitFor();

			if (exitValue != 0) {
				throw new BuildException(
						"Build failed with exit code " + exitValue
				);
			}

			File artifact = Path.of(config.getProjectDir(), buildArtifact.getPath()).toFile();
			if (!artifact.exists()) {
				throw new BuildException(
						"Build succeeded but artifact not found: "
								+ buildArtifact.getPath()
								+ "\nCheck build.artifact.path in gkit.yml"
				);
			}

			return artifact;

		} catch (IOException e) {
			throw new BuildException("Failed to start build process", e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new BuildException("Build process was interrupted", e);
		}
	}


}
