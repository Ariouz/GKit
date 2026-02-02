package fr.ariouz.gkit.test.build.image.arg;

import fr.ariouz.gkit.build.image.arg.BuildArgException;
import fr.ariouz.gkit.build.image.arg.NativeBuildArg;
import fr.ariouz.gkit.build.image.arg.NativeBuildArgParser;
import fr.ariouz.gkit.config.ConfigMerger;
import fr.ariouz.gkit.config.models.NativeConfig;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NativeBuildArgTest {

	@Test
	void booleanFlag_false_rendersInvertedFlag() {
		NativeConfig nativeConfig = new NativeConfig();
		nativeConfig.setBuildArgs(List.of(
				Map.of(NativeBuildArg.FALLBACK_IMAGE.getConfigKey(), false)
		));

		List<String> args = new NativeBuildArgParser().parseBuildArgs(nativeConfig);

		assertThat(args).containsExactly("--no-fallback");
	}

	@Test
	void booleanFlag_true_rendersInvertedEmptyFlag() {
		NativeConfig nativeConfig = new NativeConfig();
		nativeConfig.setBuildArgs(List.of(
				Map.of(NativeBuildArg.FALLBACK_IMAGE.getConfigKey(), true)
		));

		List<String> args = new NativeBuildArgParser().parseBuildArgs(nativeConfig);

		assertThat(args).isEmpty();
	}

	@Test
	void booleanFlag_true_rendersFlag() {
		NativeConfig nativeConfig = new NativeConfig();
		nativeConfig.setBuildArgs(List.of(
				Map.of(NativeBuildArg.VERBOSE_OUTPUT.getConfigKey(), true)
		));

		List<String> args = new NativeBuildArgParser().parseBuildArgs(nativeConfig);

		assertThat(args).containsExactly("--verbose");
	}

	@Test
	void booleanFlag_false_rendersEmptyFlag() {
		NativeConfig nativeConfig = new NativeConfig();
		nativeConfig.setBuildArgs(List.of(
				Map.of(NativeBuildArg.VERBOSE_OUTPUT.getConfigKey(), false)
		));

		List<String> args = new NativeBuildArgParser().parseBuildArgs(nativeConfig);

		assertThat(args).isEmpty();
	}

	@Test
	void compactBooleanFlag_false_isEmpty() {
		NativeConfig nativeConfig = new NativeConfig();
		nativeConfig.setBuildArgs(List.of(
				Map.of(NativeBuildArg.GEN_DEBUG_INFO.getConfigKey(), false)
		));

		List<String> args = new NativeBuildArgParser().parseBuildArgs(nativeConfig);

		assertThat(args).isEmpty();
	}

	@Test
	void compactBooleanFlag_true_isValid() {
		NativeConfig nativeConfig = new NativeConfig();
		nativeConfig.setBuildArgs(List.of(
				Map.of(NativeBuildArg.GEN_DEBUG_INFO.getConfigKey(), true)
		));

		List<String> args = new NativeBuildArgParser().parseBuildArgs(nativeConfig);

		assertThat(args).containsExactly("-g");
	}

	@Test
	void listKeyValue_isExpandedToMultipleArgs() {
		NativeConfig nativeConfig = new NativeConfig();
		nativeConfig.setBuildArgs(List.of(
				Map.of(NativeBuildArg.INITIALIZE_AT_BUILD_TIME.getConfigKey(), List.of("foo", "bar"))
		));

		List<String> args = new NativeBuildArgParser()
				.parseBuildArgs(nativeConfig);

		assertThat(args).containsExactly(
				"--initialize-at-build-time=foo",
				"--initialize-at-build-time=bar"
		);
	}

	@Test
	void listKeyValue_isOnlyOneArg() {
		NativeConfig nativeConfig = new NativeConfig();
		nativeConfig.setBuildArgs(List.of(
				Map.of(NativeBuildArg.INITIALIZE_AT_BUILD_TIME.getConfigKey(), List.of("foo"))
		));

		List<String> args = new NativeBuildArgParser()
				.parseBuildArgs(nativeConfig);

		assertThat(args).containsExactly(
				"--initialize-at-build-time=foo"
		);
	}

	@Test
	void listKeyValue_isEmpty() {
		NativeConfig nativeConfig = new NativeConfig();
		nativeConfig.setBuildArgs(List.of(
				Map.of(NativeBuildArg.INITIALIZE_AT_BUILD_TIME.getConfigKey(), List.of())
		));

		List<String> args = new NativeBuildArgParser()
				.parseBuildArgs(nativeConfig);

		assertThat(args).isEmpty();
	}

    @Test
    void keyValue_isNull() {
        NativeConfig nativeConfig = new NativeConfig();
        nativeConfig.setBuildArgs(List.of(
                Map.of(NativeBuildArg.LIBC.getConfigKey(), null)
        ));

        List<String> args = new NativeBuildArgParser().parseBuildArgs(nativeConfig);
        assertThat(args).isEmpty();
    }

	@Test
	void keyValue_isValid() {
		NativeConfig nativeConfig = new NativeConfig();
		nativeConfig.setBuildArgs(List.of(
				Map.of(NativeBuildArg.LIBC.getConfigKey(), "test")
		));

		List<String> args = new NativeBuildArgParser().parseBuildArgs(nativeConfig);
		assertThat(args).containsExactly("--libc=test");
	}

	@Test
	void keyValue_isEmpty() {
		NativeConfig nativeConfig = new NativeConfig();
		nativeConfig.setBuildArgs(List.of(
				Map.of(NativeBuildArg.LIBC.getConfigKey(), "")
		));

		List<String> args = new NativeBuildArgParser().parseBuildArgs(nativeConfig);
		assertThat(args).isEmpty();
	}

	@Test
	void compactKeyValue_isEmpty() {
		NativeConfig nativeConfig = new NativeConfig();
		nativeConfig.setBuildArgs(List.of(
				Map.of(NativeBuildArg.OPTIMIZATION_LEVEL.getConfigKey(), "")
		));

		List<String> args = new NativeBuildArgParser().parseBuildArgs(nativeConfig);
		assertThat(args).isEmpty();
	}

	@Test
	void compactKeyValue_acceptsInteger() {
		NativeConfig nativeConfig = new NativeConfig();
		nativeConfig.setBuildArgs(List.of(
				Map.of(NativeBuildArg.OPTIMIZATION_LEVEL.getConfigKey(), 2)
		));

		List<String> args = new NativeBuildArgParser()
				.parseBuildArgs(nativeConfig);

		assertThat(args).containsExactly("-O2");
	}

	@Test
	void rawArgs_arePassedAsIs() {
		NativeConfig nativeConfig = new NativeConfig();
		nativeConfig.setBuildArgs(List.of(
				Map.of(NativeBuildArg.RAW_ARGS.getConfigKey(), List.of("--enable-preview", "-Ob"))
		));

		List<String> args = new NativeBuildArgParser()
				.parseBuildArgs(nativeConfig);

		assertThat(args).containsExactly("--enable-preview", "-Ob");
	}

	@Test
	void rawArgs_emptyArgs() {
		NativeConfig nativeConfig = new NativeConfig();
		nativeConfig.setBuildArgs(List.of(
				Map.of(NativeBuildArg.RAW_ARGS.getConfigKey(), List.of())
		));

		List<String> args = new NativeBuildArgParser()
				.parseBuildArgs(nativeConfig);

		assertThat(args).isEmpty();
	}

	@Test
	void rawArgs_rejectsNonStringElements() {
		NativeConfig nativeConfig = new NativeConfig();
		nativeConfig.setBuildArgs(List.of(
				Map.of(NativeBuildArg.RAW_ARGS.getConfigKey(), List.of(Map.of("test", false)))
		));

		assertThatThrownBy(() ->
				new NativeBuildArgParser().parseBuildArgs(nativeConfig)
		)
				.isInstanceOf(BuildArgException.class)
				.hasMessageContaining("rawArgs");
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
	void args_are_sorted_by_enum_order() {
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


}
