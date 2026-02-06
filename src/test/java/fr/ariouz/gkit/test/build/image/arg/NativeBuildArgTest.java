package fr.ariouz.gkit.test.build.image.arg;

import fr.ariouz.gkit.build.image.arg.BuildArgException;
import fr.ariouz.gkit.build.image.arg.NativeBuildArg;
import fr.ariouz.gkit.build.image.arg.NativeBuildArgParser;
import fr.ariouz.gkit.build.image.arg.renderer.AbstractNativeBuildArgRenderer;
import fr.ariouz.gkit.config.ConfigMerger;
import fr.ariouz.gkit.config.models.NativeConfig;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NativeBuildArgTest {

	static class TestStringRenderer extends AbstractNativeBuildArgRenderer<String> {

		protected TestStringRenderer() {
			super(String.class);
		}

		@Override
		protected List<String> renderValue(NativeBuildArg arg, String value) {
			return List.of(value == null ? "" : value);
		}
	}

	static class TestIntegerRenderer extends AbstractNativeBuildArgRenderer<Integer> {

		protected TestIntegerRenderer() {
			super(Integer.class);
		}

		@Override
		protected List<String> renderValue(NativeBuildArg arg, Integer value) {
			return List.of(value == null ? "" : String.valueOf(value));
		}
	}


	@Test
	void abstractWrongExpectedType_throwsException() {
		TestStringRenderer renderer = new TestStringRenderer();
		assertThatThrownBy(() -> renderer.render(null, List.of(2)))
		.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void abstractExpectStringButInteger_isValid() {
		TestStringRenderer renderer = new TestStringRenderer();
		assertThat(renderer.render(null, 2)).containsExactly("2");
	}

	@Test
	void abstractExpectString_isValid() {
		TestStringRenderer renderer = new TestStringRenderer();
		assertThat(renderer.render(null, "2")).containsExactly("2");
	}

	@Test
	void abstractExpectStringBuyNull_isEmpty() {
		TestStringRenderer renderer = new TestStringRenderer();
		assertThat(renderer.render(null, null)).containsExactly("");
	}

	@Test
	void abstractExpectInteger_isValid() {
		TestIntegerRenderer renderer = new TestIntegerRenderer();
		assertThat(renderer.render(null, 2)).containsExactly("2");
	}

	@Test
	void invalidArgs_throwsBuildArgException() {
		NativeConfig nativeConfig = new NativeConfig();
		nativeConfig.setBuildArgs(List.of(
				Map.of("foo", "invalid")
		));
		assertThatThrownBy(() -> new NativeBuildArgParser().parseBuildArgs(nativeConfig))
				.isInstanceOf(BuildArgException.class);
	}

	@Test
	void profileBuildArgs_areConcatenatedForLists() {
		String initAtBuildTime = NativeBuildArg.INITIALIZE_AT_BUILD_TIME.getConfigKey();
		NativeConfig base = new NativeConfig();
		base.setBuildArgs(List.of(
				Map.of(initAtBuildTime, List.of("foo", "bar"))
		));

		NativeConfig profile = new NativeConfig();
		profile.setBuildArgs(List.of(
				Map.of(initAtBuildTime, List.of("foobar"))
		));

		NativeConfig merged = ConfigMerger.merge(base, profile);

		List<String> args = new NativeBuildArgParser()
				.parseBuildArgs(merged);

		assertThat(args).containsExactly(
				"--initialize-at-build-time=foo",
				"--initialize-at-build-time=bar",
				"--initialize-at-build-time=foobar"
		);
	}

	@Test
	void profileBoolean_overridesBase() {
		String fallback = NativeBuildArg.FALLBACK_IMAGE.getConfigKey();

		NativeConfig base = new NativeConfig();
		base.setBuildArgs(List.of(
				Map.of(fallback, false)
		));

		NativeConfig profile = new NativeConfig();
		profile.setBuildArgs(List.of(
				Map.of(fallback, true)
		));

		NativeConfig merged = ConfigMerger.merge(base, profile);

		List<String> args = new NativeBuildArgParser()
				.parseBuildArgs(merged);

		assertThat(args).doesNotContain("--no-fallback");
	}

	@Test
	void buildArgsAreSorted_byEnumOrder() {
		NativeConfig nativeConfig = new NativeConfig();
		nativeConfig.setBuildArgs(List.of(
				Map.of(NativeBuildArg.OPTIMIZATION_LEVEL.getConfigKey(), 2),
				Map.of(NativeBuildArg.FALLBACK_IMAGE.getConfigKey(), false)
		));

		List<String> args = new NativeBuildArgParser()
				.parseBuildArgs(nativeConfig);

		assertThat(args).containsSubsequence(
				"--no-fallback",
				"-O2"
		);
	}

	@Test
	void buildArgIsDefinedTwice_throwsBuildArgException() {
		NativeConfig nativeConfig = new NativeConfig();
		nativeConfig.setBuildArgs(List.of(
				Map.of(NativeBuildArg.OPTIMIZATION_LEVEL.getConfigKey(), 2),
				Map.of(NativeBuildArg.OPTIMIZATION_LEVEL.getConfigKey(), 2)
		));

		assertThatThrownBy(() -> new NativeBuildArgParser().parseBuildArgs(nativeConfig))
				.isInstanceOf(BuildArgException.class);

	}

	@Test
	void parserInvalidArgType_throwsBuildArgException() {
		NativeConfig nativeConfig = new NativeConfig();
		nativeConfig.setBuildArgs(List.of(
				Map.of(NativeBuildArg.INITIALIZE_AT_BUILD_TIME.getConfigKey(), "invalid")
		));

		assertThatThrownBy(() -> new NativeBuildArgParser().parseBuildArgs(nativeConfig))
				.isInstanceOf(BuildArgException.class);

	}


}
