package fr.ariouz.gkit.build.image.arg.renderer;

import fr.ariouz.gkit.build.image.arg.NativeBuildArg;

import java.util.List;

public abstract class AbstractNativeBuildArgRenderer<T>
		implements NativeBuildArgRenderer<Object> {

	private final Class<T> expectedType;

	protected AbstractNativeBuildArgRenderer(Class<T> expectedType) {
		this.expectedType = expectedType;
	}

	@Override
	@SuppressWarnings("unchecked")
	public final List<String> render(NativeBuildArg arg, Object value) {
		if (value == null) return renderValue(arg, null);
		if (value instanceof Integer && expectedType == String.class) return renderValue(arg, (T) String.valueOf(value));

		if (!expectedType.isInstance(value)) {
			throw new IllegalArgumentException(
					"expected " + expectedType.getSimpleName() + ", got " + value.getClass().getSimpleName()
			);
		}

		return renderValue(arg, expectedType.cast(value));
	}

	protected abstract List<String> renderValue(NativeBuildArg arg, T value);
}

