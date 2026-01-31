package fr.ariouz.gkit.build.image.arg.renderer;

import fr.ariouz.gkit.build.image.arg.NativeBuildArg;

import java.util.List;

public class KeyValueListFlagRenderer implements NativeBuildArgRenderer<List<String>> {

	public List<String> render(NativeBuildArg arg, List<String> values) {
		if (values == null) return List.of();

		return values.stream()
				.map(String::trim)
				.filter(s -> !s.isEmpty())
				.map(v -> "--" + arg.getCliKey() + "=" + v)
				.toList();
	}

}