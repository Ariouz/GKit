package fr.ariouz.gkit.util;

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

}
