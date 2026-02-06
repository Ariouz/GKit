package fr.ariouz.gkit.test.build.image.arg.renderer;

import fr.ariouz.gkit.build.image.arg.NativeBuildArg;
import fr.ariouz.gkit.build.image.arg.renderer.KeyValueFlagRenderer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class KeyValueFlagRendererTest {

	@Test
	void nullValue_isEmpty() {
		KeyValueFlagRenderer renderer = new KeyValueFlagRenderer();
		List<String> values = renderer.render(NativeBuildArg.LIBC, null);
		assertThat(values).isEmpty();
	}

	@Test
	void keyValue_isEmpty() {
		KeyValueFlagRenderer renderer = new KeyValueFlagRenderer();
		List<String> values = renderer.render(NativeBuildArg.LIBC, "");
		assertThat(values).isEmpty();
	}

	@Test
	void keyValue_isValid() {
		KeyValueFlagRenderer renderer = new KeyValueFlagRenderer();
		List<String> values = renderer.render(NativeBuildArg.LIBC, "test");
		assertThat(values).containsExactly("--libc=test");
	}

}
