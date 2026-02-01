package fr.ariouz.gkit.build.image.arg.renderer;

import fr.ariouz.gkit.build.image.arg.NativeBuildArg;

import java.util.List;

public class CompactKeyValueFlagRenderer extends AbstractNativeBuildArgRenderer<String> {

	public CompactKeyValueFlagRenderer() {
		super(String.class);
	}

	@Override
	public List<String> renderValue(NativeBuildArg arg, String value) {
		if (value == null) return List.of();

		return List.of("-" + arg.getCliKey() + "=" + value);
	}
}