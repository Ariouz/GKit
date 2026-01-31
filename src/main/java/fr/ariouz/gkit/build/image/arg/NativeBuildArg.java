package fr.ariouz.gkit.build.image.arg;

import fr.ariouz.gkit.build.image.arg.renderer.BooleanFlagRenderer;
import fr.ariouz.gkit.build.image.arg.renderer.KeyValueListFlagRenderer;
import fr.ariouz.gkit.build.image.arg.renderer.NativeBuildArgRenderer;

import java.util.Arrays;
import java.util.List;

public enum NativeBuildArg {

	FALLBACK_IMAGE(
			"fallbackImage",
			"no-fallback",
			new BooleanFlagRenderer(true)
	),
	INITIALIZE_AT_BUILD_TIME(
			"initializeAtBuildTime",
			"initialize-at-build-time",
			new KeyValueListFlagRenderer()
	);

	// TODO AdditionalArgsListFlagRenderer: List<String>


	private final String configKey;
	private final String cliKey;
	private final NativeBuildArgRenderer<?> renderer;

	NativeBuildArg(String configKey, String cliKey, NativeBuildArgRenderer<?> renderer) {
		this.configKey = configKey;
		this.cliKey = cliKey;
		this.renderer = renderer;
	}

	public String getConfigKey() {
		return configKey;
	}

	public String getCliKey() {
		return cliKey;
	}

	@SuppressWarnings("unchecked")
	public List<String> render(Object value) {
		return ((NativeBuildArgRenderer<Object>) renderer)
				.render(this, value);
	}

	public static NativeBuildArg fromConfigKey(String key) {
		return Arrays.stream(values())
				.filter(a -> a.configKey.equals(key))
				.findFirst()
				.orElseThrow(() -> new BuildArgException("Unknown native image build arg: " + key) );
	}

}
