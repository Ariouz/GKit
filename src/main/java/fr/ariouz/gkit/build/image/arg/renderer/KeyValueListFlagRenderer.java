package fr.ariouz.gkit.build.image.arg.renderer;

import fr.ariouz.gkit.build.BuildException;
import fr.ariouz.gkit.build.image.arg.BuildArgException;
import fr.ariouz.gkit.build.image.arg.NativeBuildArg;

import java.util.List;

public class KeyValueListFlagRenderer extends AbstractNativeBuildArgRenderer<List<?>> {

	@SuppressWarnings("unchecked")
	public KeyValueListFlagRenderer() {
		super((Class<List<?>>)(Class<?>) List.class);
	}

	@Override
	public List<String> renderValue(NativeBuildArg arg, List<?> values) {
		if (values == null) return List.of();
		validateElements(values);

		return values.stream()
				.map(v -> ((String) v).trim())
				.filter(s -> !s.isEmpty())
				.map(v -> "--" + arg.getCliKey() + "=" + v)
				.toList();
	}

	private void validateElements(List<?> value) {
		for (Object v : value) {
			if (!(v instanceof String s)) {
				throw new BuildArgException(
						"Expected list of strings, got " + v.getClass().getSimpleName()
				);
			}
		}
	}

}