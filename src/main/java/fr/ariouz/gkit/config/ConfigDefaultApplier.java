package fr.ariouz.gkit.config;

import fr.ariouz.gkit.config.models.ConfigDefault;
import fr.ariouz.gkit.config.models.ConfigObject;
import fr.ariouz.gkit.util.FieldUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ConfigDefaultApplier {

	public static void apply(Object config) {
		if (config == null) return ;

		Class<?> clazz = config.getClass();
		if (!clazz.isAnnotationPresent(ConfigObject.class)) return;

		for (Field field : clazz.getDeclaredFields()) {
			if (Modifier.isStatic(field.getModifiers())) continue;
			field.setAccessible(true);

			try {
				Object value = field.get(config);

				if (value != null && !FieldUtil.isPrimitiveLike(field.getType())) {
					apply(value);
					continue;
				}

				if (value == null && field.isAnnotationPresent(ConfigDefault.class)) {
					ConfigDefault def = field.getAnnotation(ConfigDefault.class);
					Object converted = FieldUtil.convert(def.value(), field.getType());
					field.set(config, converted);
				}

			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
