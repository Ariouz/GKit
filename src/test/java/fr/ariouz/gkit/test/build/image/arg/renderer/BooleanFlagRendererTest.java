package fr.ariouz.gkit.test.build.image.arg.renderer;

import fr.ariouz.gkit.build.image.arg.NativeBuildArg;
import fr.ariouz.gkit.build.image.arg.renderer.BooleanFlagRenderer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BooleanFlagRendererTest {

	@Test
	void booleanFlagNull_isEmpty() {
		BooleanFlagRenderer renderer = new BooleanFlagRenderer();
		List<String> values = renderer.render(NativeBuildArg.FALLBACK_IMAGE, null);
		assertThat(values).isEmpty();
	}

	@Test
	void booleanFlagFalse_rendersInvertedFlag() {
		BooleanFlagRenderer renderer = new BooleanFlagRenderer(true);
		List<String> values = renderer.render(NativeBuildArg.FALLBACK_IMAGE, false);
		assertThat(values).containsExactly("--no-fallback");
	}

	@Test
	void booleanFlag_true_rendersInvertedEmptyFlag() {
		BooleanFlagRenderer renderer = new BooleanFlagRenderer(true);
		List<String> values = renderer.render(NativeBuildArg.FALLBACK_IMAGE, true);
		assertThat(values).isEmpty();
	}

	@Test
	void booleanFlag_true_rendersFlag() {
		BooleanFlagRenderer renderer = new BooleanFlagRenderer();
		List<String> values = renderer.render(NativeBuildArg.VERBOSE_OUTPUT, true);
		assertThat(values).containsExactly("--verbose");
	}

	@Test
	void booleanFlag_false_rendersEmptyFlag() {
		BooleanFlagRenderer renderer = new BooleanFlagRenderer();
		List<String> values = renderer.render(NativeBuildArg.VERBOSE_OUTPUT, false);
		assertThat(values).isEmpty();
	}

}
