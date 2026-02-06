package fr.ariouz.gkit.test.build.image.arg.renderer;

import fr.ariouz.gkit.build.image.arg.BuildArgException;
import fr.ariouz.gkit.build.image.arg.NativeBuildArg;
import fr.ariouz.gkit.build.image.arg.NativeBuildArgParser;
import fr.ariouz.gkit.build.image.arg.renderer.KeyValueListFlagRenderer;
import fr.ariouz.gkit.config.models.NativeConfig;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class KeyValueListFlagRendererTest {

	@Test
	void nullValue_isEmpty() {
		KeyValueListFlagRenderer renderer = new KeyValueListFlagRenderer();
		List<String> values = renderer.render(NativeBuildArg.INITIALIZE_AT_BUILD_TIME, null);
		assertThat(values).isEmpty();
	}

	@Test
	void emptyValue_hasFiltered() {
		KeyValueListFlagRenderer renderer = new KeyValueListFlagRenderer();
		List<String> values = renderer.render(NativeBuildArg.INITIALIZE_AT_BUILD_TIME, List.of("foo", ""));
		assertThat(values).containsExactly("--initialize-at-build-time=foo");
	}

	@Test
	void nonStringValue_hasFiltered() {
		KeyValueListFlagRenderer renderer = new KeyValueListFlagRenderer();

		assertThatThrownBy(() ->
				renderer.render(NativeBuildArg.INITIALIZE_AT_BUILD_TIME, List.of("foo", 2))
		).isInstanceOf(BuildArgException.class);
	}

	@Test
	void listKeyValue_isValid() {
		KeyValueListFlagRenderer renderer = new KeyValueListFlagRenderer();
		List<String> values = renderer.render(NativeBuildArg.INITIALIZE_AT_BUILD_TIME, List.of("foo", "bar"));
		assertThat(values).containsExactly("--initialize-at-build-time=foo", "--initialize-at-build-time=bar");
	}

	@Test
	void listKeyValue_isEmpty() {
		KeyValueListFlagRenderer renderer = new KeyValueListFlagRenderer();
		List<String> values = renderer.render(NativeBuildArg.INITIALIZE_AT_BUILD_TIME, List.of());
		assertThat(values).isEmpty();
	}

}
