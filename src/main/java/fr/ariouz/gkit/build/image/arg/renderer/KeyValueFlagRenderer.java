package fr.ariouz.gkit.build.image.arg.renderer;

import fr.ariouz.gkit.build.image.arg.NativeBuildArg;

import java.util.List;

public class KeyValueFlagRenderer extends AbstractNativeBuildArgRenderer<String> {

	public KeyValueFlagRenderer() {
		super(String.class);
	}

	@Override
	public List<String> renderValue(NativeBuildArg arg, String value) {
		if (value == null || value.isEmpty()) return List.of();

		return List.of("--" + arg.getCliKey() + "=" + value);
	}

}