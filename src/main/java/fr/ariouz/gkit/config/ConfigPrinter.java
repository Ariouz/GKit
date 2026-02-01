package fr.ariouz.gkit.config;

import fr.ariouz.gkit.util.FieldUtil;
import fr.ariouz.gkit.util.Colors;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public class ConfigPrinter {

	public static void print(Object config) {
		print(config, 0);
	}

	private static void print(Object obj, int indent) {
		if (obj == null) {
			println(Colors.RED + "null" + Colors.RESET, indent);
			return;
		}

		if (FieldUtil.isSimple(obj.getClass())) {
			println(Colors.GREEN + obj.toString() + Colors.RESET, indent);
			return;
		}

		if (obj instanceof Collection<?> col) {
			for (Object v : col) {
				print("- ", indent);
				print(v, indent + 1);
			}
			return;
		}

		if (obj instanceof Map<?, ?> map) {
			for (var e : map.entrySet()) {
				Object value = e.getValue();
				if (value == null || FieldUtil.isSimple(value.getClass())) {
					println(e.getKey() + ": " + value, indent);
				} else {
					println(e.getKey() + ":", indent);
					print(value, indent + 1);
				}
			}
			return;
		}


		Package pkg = obj.getClass().getPackage();
		if (pkg != null && pkg.getName().startsWith("java.")) {
			println(Colors.GREEN + obj.toString() + Colors.RESET, indent);
			return;
		}

		for (Field field : obj.getClass().getDeclaredFields()) {
			if (field.getName().equals("profiles")) continue; // skip profiles

			field.setAccessible(true);
			try {
				Object value = field.get(obj);
				if (value == null) continue;

				println(Colors.CYAN + field.getName() + ":" + Colors.RESET, indent);
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
