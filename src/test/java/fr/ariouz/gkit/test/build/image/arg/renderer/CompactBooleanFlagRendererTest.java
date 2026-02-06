package fr.ariouz.gkit.test.build.image.arg.renderer;

import fr.ariouz.gkit.build.image.arg.NativeBuildArg;
import fr.ariouz.gkit.build.image.arg.renderer.BooleanFlagRenderer;
import fr.ariouz.gkit.build.image.arg.renderer.CompactBooleanFlagRenderer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CompactBooleanFlagRendererTest {

	@Test
	void compactBooleanFlagNull_isEmpty() {
		CompactBooleanFlagRenderer renderer = new CompactBooleanFlagRenderer();
		List<String> values = renderer.render(NativeBuildArg.GEN_DEBUG_INFO, null);
		assertThat(values).isEmpty();
	}

	@Test
	void compactBooleanFlagFalse_rendersInvertedFlag() {
		CompactBooleanFlagRenderer renderer = new CompactBooleanFlagRenderer(true);
		List<String> values = renderer.render(NativeBuildArg.GEN_DEBUG_INFO, false);
		assertThat(values).containsExactly("-g");
	}

	@Test
	void compactBooleanFlag_true_rendersInvertedEmptyFlag() {
		CompactBooleanFlagRenderer renderer = new CompactBooleanFlagRenderer(true);
		List<String> values = renderer.render(NativeBuildArg.GEN_DEBUG_INFO, true);
		assertThat(values).isEmpty();
	}

	@Test
	void compactBooleanFlag_true_rendersFlag() {
		CompactBooleanFlagRenderer renderer = new CompactBooleanFlagRenderer();
		List<String> values = renderer.render(NativeBuildArg.GEN_DEBUG_INFO, true);
		assertThat(values).containsExactly("-g");
	}

	@Test
	void compactBooleanFlag_false_rendersEmptyFlag() {
		CompactBooleanFlagRenderer renderer = new CompactBooleanFlagRenderer();
		List<String> values = renderer.render(NativeBuildArg.GEN_DEBUG_INFO, false);
		assertThat(values).isEmpty();
	}

}
