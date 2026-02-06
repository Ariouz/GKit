package fr.ariouz.gkit.test.build.image.arg.renderer;

import fr.ariouz.gkit.build.image.arg.BuildArgException;
import fr.ariouz.gkit.build.image.arg.NativeBuildArg;
import fr.ariouz.gkit.build.image.arg.renderer.RawArgsRenderer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RawArgsRendererTest {

	@Test
	void nullArg_isEmpty() {
		RawArgsRenderer renderer = new RawArgsRenderer();
		List<String> values = renderer.render(NativeBuildArg.INITIALIZE_AT_BUILD_TIME, null);
		assertThat(values).isEmpty();
	}

	@Test
	void rawArgsEmptyString_hasFiltered() {
		RawArgsRenderer renderer = new RawArgsRenderer();
		List<String> values = renderer.render(NativeBuildArg.RAW_ARGS, List.of("--enable-preview", ""));
		assertThat(values).containsExactly("--enable-preview");
	}

	@Test
	void rawArgs_arePassedAsIs() {
		RawArgsRenderer renderer = new RawArgsRenderer();
		List<String> values = renderer.render(NativeBuildArg.RAW_ARGS, List.of("--enable-preview", "-Ob"));
		assertThat(values).containsExactly("--enable-preview", "-Ob");
	}

	@Test
	void rawArgs_emptyArgs() {
		RawArgsRenderer renderer = new RawArgsRenderer();
		List<String> values = renderer.render(NativeBuildArg.RAW_ARGS, List.of());
		assertThat(values).isEmpty();
	}

	@Test
	void rawArgs_rejectsNonStringElements() {
		RawArgsRenderer renderer = new RawArgsRenderer();
		assertThatThrownBy(() ->
				renderer.render(NativeBuildArg.RAW_ARGS, List.of("foo", 4))
		).isInstanceOf(BuildArgException.class);
	}

}
