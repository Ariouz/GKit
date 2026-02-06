package fr.ariouz.gkit.test.build.image.arg.renderer;

import fr.ariouz.gkit.build.image.arg.NativeBuildArg;
import fr.ariouz.gkit.build.image.arg.renderer.CompactKeyValueFlagRenderer;
import org.junit.jupiter.api.Test;

import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;

public class CompactKeyValueFlagRendererTest {

	@Test
	void compactNullValue_isEmpty() {
		CompactKeyValueFlagRenderer renderer = new CompactKeyValueFlagRenderer();
		List<String> args = renderer.render(NativeBuildArg.OPTIMIZATION_LEVEL, null);
		assertThat(args).isEmpty();
	}

	@Test
	void compactEmptyValue_isEmpty() {
		CompactKeyValueFlagRenderer renderer = new CompactKeyValueFlagRenderer();
		List<String> args = renderer.render(NativeBuildArg.OPTIMIZATION_LEVEL, "");
		assertThat(args).isEmpty();
	}

	@Test
	void compactKeyValue_acceptsInteger() {
		CompactKeyValueFlagRenderer renderer = new CompactKeyValueFlagRenderer();
		List<String> args = renderer.render(NativeBuildArg.OPTIMIZATION_LEVEL, "2");

		assertThat(args).containsExactly("-O2");
	}

}
