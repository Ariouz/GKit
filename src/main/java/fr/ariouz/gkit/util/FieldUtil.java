package fr.ariouz.gkit.util;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class FieldUtil {

	public static boolean isSimple(Class<?> type) {
		return type.isPrimitive()
				|| type == String.class
				|| Number.class.isAssignableFrom(type)
				|| type == Boolean.class
				|| type.isEnum();
	}

	public static boolean isCollection(Class<?> type) {
		return List.class.isAssignableFrom(type)
				|| Map.class.isAssignableFrom(type);
	}

	public static boolean isPrimitiveLike(Class<?> type) {
		return isSimple(type) || isCollection(type);
	}

	public static Object convert(String value, Class<?> type) {
		if (type == String.class) return value;
		if (type == Integer.class || type == int.class) return Integer.parseInt(value);
		if (type == Boolean.class || type == boolean.class) return Boolean.parseBoolean(value);
		if (type == Path.class) return Path.of(value);

		throw new IllegalArgumentException("Unsupported type: " + type);
	}



}
