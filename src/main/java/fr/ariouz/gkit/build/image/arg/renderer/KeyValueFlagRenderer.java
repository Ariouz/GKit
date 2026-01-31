package fr.ariouz.gkit.build.image.arg.renderer;

import fr.ariouz.gkit.build.image.arg.NativeBuildArg;

import java.util.List;

public class KeyValueFlagRenderer implements NativeBuildArgRenderer<String> {

	public List<String> render(NativeBuildArg arg, String value) {
		if (value == null) return List.of();

		return List.of("--" + arg.getCliKey() + "=" + value);
	}

}