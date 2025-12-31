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

		if (FieldUtil.isSimple(obj.getClass())) {
			println(obj.toString(), indent);
			return;
		}

		if (obj instanceof Collection<?> col) {
			for (Object v : col) {
				print("- ", indent);
				print(v, indent);
			}
			return;
		}

		if (obj instanceof Map<?, ?> map) {
			for (var e : map.entrySet()) {
				print(e.getKey() + ":", indent);
				print(e.getValue(), indent);
			}
			return;
		}

		Package pkg = obj.getClass().getPackage();
		if (pkg != null && pkg.getName().startsWith("java.")) {
			println(obj.toString(), indent);
			return;
		}

		for (Field field : obj.getClass().getDeclaredFields()) {
			if (field.getName().equals("profiles")) continue;

			field.setAccessible(true);
			try {
				Object value = field.get(obj);
				if (value == null) continue;

				println(field.getName() + ":", indent);
				print(value, indent + 1);

			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}


	private static void println(String s, int indent) {
		System.out.println("  ".repeat(indent) + s);
	}

	private static void print(String s, int indent) {
		System.out.print("  ".repeat(indent) + s);
	}

}
