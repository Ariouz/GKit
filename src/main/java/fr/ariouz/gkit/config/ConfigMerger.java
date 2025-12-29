package fr.ariouz.gkit.config;

import fr.ariouz.gkit.util.FieldUtil;

import java.lang.reflect.Field;

public class ConfigMerger {

	@SuppressWarnings("unchecked")
	public static <T> T merge(T base, T profile) {
		if (base == null) return profile;
		if (profile == null) return base;

		try {
			Class<?> baseClass = base.getClass();
			T result =  (T) baseClass.getDeclaredConstructor().newInstance();

			for (Field field : baseClass.getDeclaredFields()) {
				field.setAccessible(true);

				Object b = field.get(base);
				Object p = field.get(profile);
				Object value;

				if (p == null) value = b;
				else if (FieldUtil.isSimple(field.getType())) value = p;
				else if (FieldUtil.isCollection(field.getType())) value = p;
				else value = merge(b, p);

				field.set(result, value);
			}

			return result;
		} catch (Exception e){
			throw new RuntimeException("Failed to merge config profile: " + e);
		}
	}



}
