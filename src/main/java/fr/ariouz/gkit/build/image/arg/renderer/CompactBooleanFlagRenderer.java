package fr.ariouz.gkit.build.image.arg.renderer;

import fr.ariouz.gkit.build.image.arg.NativeBuildArg;

import java.util.List;

public class CompactBooleanFlagRenderer extends AbstractNativeBuildArgRenderer<Boolean> {

	private final boolean inverted;

	public CompactBooleanFlagRenderer() {
		super(Boolean.class);
		this.inverted = false;
	}

	public CompactBooleanFlagRenderer(boolean inverted) {
		super(Boolean.class);
		this.inverted = inverted;
	}

	@Override
	public List<String> renderValue(NativeBuildArg arg, Boolean value) {
		if (value == null) return List.of();

		return inverted != value
				? List.of("-" + arg.getCliKey())
				: List.of();
	}
}