package fr.ariouz.gkit.test.build.artifact;

import fr.ariouz.gkit.build.BuildException;
import fr.ariouz.gkit.build.ProcessRunner;
import fr.ariouz.gkit.build.artifact.ArtifactBuilder;
import fr.ariouz.gkit.config.models.BuildArtifact;
import fr.ariouz.gkit.config.models.BuildConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ArtifactBuilderTest {

	static class TestArtifactBuilder extends ArtifactBuilder {
		@Override
		protected ProcessRunner getProcessRunner() {
			return new ProcessRunner() {
				@Override
				public void run(String name, List<String> command, File workdir, boolean dryRun) {
					// no-op
				}
			};
		}
	}

	private BuildConfig baseConfig(Path projectDir, String artifactPath) {
		BuildConfig config = new BuildConfig();
		config.setProjectDir(projectDir.toString());

		BuildArtifact artifact = new BuildArtifact();
		artifact.setCommand("echo build");
		artifact.setPath(artifactPath);

		config.setArtifact(artifact);
		return config;
	}

	@Test
	void build_dryRun_doesNotCheckArtifactExists(@TempDir Path tempDir) {
		ArtifactBuilder builder = new TestArtifactBuilder();
		BuildConfig config = baseConfig(tempDir, "missing.jar");

		File result = builder.build(config, true);

		assertThat(result.getPath()).endsWith("missing.jar");
	}

	@Test
	void build_artifactExists_isValid(@TempDir Path tempDir) throws Exception {
		Path artifact = tempDir.resolve("app.jar");
		Files.createFile(artifact);

		ArtifactBuilder builder = new TestArtifactBuilder();
		BuildConfig config = baseConfig(tempDir, "app.jar");

		File result = builder.build(config, false);

		assertThat(result).exists();
	}

	@Test
	void build_artifactMissing_throwsException(@TempDir Path tempDir) {
		ArtifactBuilder builder = new TestArtifactBuilder();
		BuildConfig config = baseConfig(tempDir, "missing.jar");

		assertThatThrownBy(() -> builder.build(config, false))
				.isInstanceOf(BuildException.class)
				.hasMessageContaining("artifact not found");
	}
}
