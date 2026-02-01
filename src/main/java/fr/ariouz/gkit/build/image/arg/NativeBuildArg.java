package fr.ariouz.gkit.build.image.arg;

import fr.ariouz.gkit.build.image.arg.renderer.*;

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
	),
	INITIALIZE_AT_RUN_TIME(
			"initializeAtRunTime",
			"initialize-at-run-time",
			new KeyValueListFlagRenderer()
	),
	SHARED_LIBRARY(
			"buildSharedLibrary",
			"shared",
			new BooleanFlagRenderer()
	),
	SILENT_OUTPUT(
			"silentBuild",
			"silent",
			new BooleanFlagRenderer()
	),
	VERBOSE_OUTPUT(
			"verboseBuild",
			"verbose",
			new BooleanFlagRenderer()
	),
	GEN_DEBUG_INFO(
			"debugInfo",
			"g",
			new CompactBooleanFlagRenderer()
	),

	ENABLE_HTTP(
			"enableHttp",
			"enable-http",
			new BooleanFlagRenderer()
	),
	ENABLE_HTTPS(
			"enableHttps",
			"enable-https",
			new BooleanFlagRenderer()
	),
	ENABLE_ALL_SECURITY_SERVICES(
			"enableAllSecurityServices",
			"enable-all-security-services",
			new BooleanFlagRenderer()
	),
	REPORT_UNSUPPORTED_AT_RUNTIME(
			"reportUnsupportedAtRuntime",
			"report-unsupported-elements-at-runtime",
			new BooleanFlagRenderer()
	),
	ALLOW_INCOMPLETE_CLASSPATH(
			"allowIncompleteClasspath",
			"allow-incomplete-classpath",
			new BooleanFlagRenderer()
	),
	LIBC(
			"libc",
			"libc",
			new KeyValueFlagRenderer()
	),
	OPTIMIZATION_LEVEL(
			"optimizationLevel",
			"O",
			new CompactKeyValueFlagRenderer()
	),

	RAW_ARGS(
			"rawArgs",
			"",
			new RawArgsRenderer()
	),


	;


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
				.orElseThrow(() -> new BuildArgException("Unknown native image build arg: '" + key+"'") );
	}

	public NativeBuildArgRenderer<?> getRenderer() {
		return renderer;
	}
}
