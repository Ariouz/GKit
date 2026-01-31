package fr.ariouz.gkit.build.image.arg.renderer;

import fr.ariouz.gkit.build.image.arg.NativeBuildArg;

import java.util.List;

public class BooleanFlagRenderer implements NativeBuildArgRenderer<Boolean> {

	private final boolean inverted;

	public BooleanFlagRenderer(boolean inverted) {
		this.inverted = inverted;
	}

	public List<String> render(NativeBuildArg arg, Boolean value) {
		if (value == null) return List.of();

		return inverted != value
				? List.of("--" + arg.getCliKey())
				: List.of();
	}
}