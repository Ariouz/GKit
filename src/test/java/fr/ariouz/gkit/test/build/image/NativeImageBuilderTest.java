package fr.ariouz.gkit.test.build.image;

import fr.ariouz.gkit.build.BuildException;
import fr.ariouz.gkit.build.image.NativeImageBuilder;
import fr.ariouz.gkit.config.models.GKitConfig;
import fr.ariouz.gkit.config.models.BuildConfig;
import fr.ariouz.gkit.config.models.BuildArtifact;
import fr.ariouz.gkit.config.models.NativeConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.*;

public class NativeImageBuilderTest {

	@TempDir
	Path tempDir;

	@Test
	void nativeImageNotFound_throwsBuildException() {
		NativeImageBuilder builder = new NativeImageBuilder() {
			@Override
			protected File getNativeImage() {
				return null;
			}
		};

		GKitConfig config = baseConfig("missing.jar");

		assertThatThrownBy(() ->
				builder.buildNativeImage(config, true)
		).isInstanceOf(BuildException.class)
				.hasMessageContaining("native-image not found");
	}

	@Test
	void jarNotFoundAndNotDryRun_throwsBuildException() {
		NativeImageBuilder builder = new NativeImageBuilder() {
			@Override
			protected File getNativeImage() {
				return tempDir.resolve("native-image").toFile();
			}
		};

		GKitConfig config = baseConfig("missing.jar");

		assertThatThrownBy(() ->
				builder.buildNativeImage(config, false)
		).isInstanceOf(BuildException.class)
				.hasMessageContaining("jar artifact not found");
	}

	@Test
	void jarNotFoundButDryRunIsEnabled_throwsBuildException() {
		NativeImageBuilder builder = new NativeImageBuilder() {
			@Override
			protected File getNativeImage() {
				return tempDir.resolve("native-image").toFile();
			}
		};

		GKitConfig config = baseConfig("missing.jar");

		assertThatCode(() ->
				builder.buildNativeImage(config, true)
		).doesNotThrowAnyException();
	}

	@Test
	void dryRunIsEnabled() throws Exception {
		File jar = tempDir.resolve("app.jar").toFile();
		assertThat(jar.createNewFile()).isTrue();

		NativeImageBuilder builder = new NativeImageBuilder() {
			@Override
			protected File getNativeImage() {
				return tempDir.resolve("native-image").toFile();
			}
		};

		GKitConfig config = baseConfig("app.jar");

		assertThatCode(() ->
				builder.buildNativeImage(config, true)
		).doesNotThrowAnyException();
	}

	private GKitConfig baseConfig(String jarPath) {
		GKitConfig config = new GKitConfig();

		BuildConfig build = new BuildConfig();
		build.setProjectDir(tempDir.toString());

		BuildArtifact artifact = new BuildArtifact();
		artifact.setPath(jarPath);
		build.setArtifact(artifact);

		NativeConfig nativeImage = new NativeConfig();
		nativeImage.setOutput("app-native");

		config.setBuild(build);
		config.setNativeImage(nativeImage);

		return config;
	}
}
