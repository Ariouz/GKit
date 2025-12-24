package fr.ariouz.gkit.config;

import fr.ariouz.gkit.util.FieldUtil;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public class ConfigPrinter {

	public static void print(Object config) {
		print(config, 0);
	}

	private static void print(Object obj, int indent) {
		if (obj == null) {
			println("null", indent);
			return;
		}

		Class<?> clazz = obj.getClass();

		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			try {
				Object value = field.get(obj);

				if (value == null) continue;
				if (field.getName().equals("profiles")) continue;

				if (FieldUtil.isSimple(value.getClass())) {
					println(field.getName() + ": " + value, indent);
				} else if (value instanceof Collection<?> col) {
					println(field.getName() + ":", indent);
					for (Object v : col) {
						println("- " + v, indent + 1);
					}
				} else if (value instanceof Map<?, ?> map) {
					println(field.getName() + ":", indent);
					for (var e : map.entrySet()) {
						println(e.getKey() + ":", indent + 1);
						print(e.getValue(), indent + 2);
					}
				} else {
					println(field.getName() + ":", indent);
					print(value, indent + 1);
				}
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static void println(String s, int indent) {
		System.out.println("  ".repeat(indent) + s);
	}

}
