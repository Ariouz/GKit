package fr.ariouz.gkit.build.artifact;

import fr.ariouz.gkit.build.BuildException;
import fr.ariouz.gkit.build.ProcessRunner;
import fr.ariouz.gkit.config.models.BuildArtifact;
import fr.ariouz.gkit.config.models.BuildConfig;
import fr.ariouz.gkit.util.OSHelper;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class ArtifactBuilder {

	public File build(BuildConfig config) {
		return build(config, false);
	}

	public File build(BuildConfig config, boolean dryRun) {
		BuildArtifact buildArtifact = config.getArtifact();

		List<String> command = OSHelper.isWindows()
				? List.of("cmd.exe", "/c", buildArtifact.getCommand())
				: List.of("sh", "-c", buildArtifact.getCommand());

		new ProcessRunner().run(
				"Build artifact",
				command,
				new File(config.getProjectDir()),
				dryRun
		);

		File artifact = Path.of(config.getProjectDir(), buildArtifact.getPath()).toFile();
		if (!artifact.exists() && !dryRun) {
			throw new BuildException(
					"Build succeeded but artifact not found: "
							+ buildArtifact.getPath()
							+ "\nCheck build.artifact.path in gkit.yml"
			);
		}

		return artifact;
	}


}
