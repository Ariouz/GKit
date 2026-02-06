package fr.ariouz.gkit.build.image.arg.renderer;

import fr.ariouz.gkit.build.image.arg.BuildArgException;
import fr.ariouz.gkit.build.image.arg.NativeBuildArg;

import java.util.List;

public class RawArgsRenderer extends AbstractNativeBuildArgRenderer<List<?>> {

	@SuppressWarnings("unchecked")
	public RawArgsRenderer() {
		super((Class<List<?>>)(Class<?>) List.class);
	}

	@Override
	public List<String> renderValue(NativeBuildArg arg, List<?> values) {
		if (values == null) return List.of();
		validateElements(values);

		return values.stream()
				.map(v -> ((String) v).trim())
				.filter(s -> !s.isEmpty())
				.toList();
	}

	private void validateElements(List<?> value) {
		for (Object v : value) {
			if (!(v instanceof String s)) {
				throw new IllegalArgumentException(
						"Expected list of strings, got " + v.getClass().getSimpleName()
				);
			}
		}
	}

}
