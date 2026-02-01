package fr.ariouz.gkit.config;

import fr.ariouz.gkit.util.FieldUtil;

import java.lang.reflect.Field;
import java.util.*;

public class ConfigMerger {

	@SuppressWarnings("unchecked")
	public static <T> T merge(T base, T profile) {
		if (base == null) return profile;
		if (profile == null) return base;

		try {
			Class<?> clazz = base.getClass();
			T result = (T) clazz.getDeclaredConstructor().newInstance();

			for (Field field : clazz.getDeclaredFields()) {
				field.setAccessible(true);
				Object mergedValue = mergeField(field, field.get(base), field.get(profile));
				field.set(result, mergedValue);
			}

			return result;
		} catch (Exception e) {
			throw new ConfigException("Failed to merge config profile: " + e);
		}
	}

	@SuppressWarnings("unchecked")
	private static Object mergeField(Field field, Object baseValue, Object profileValue) {
		if (profileValue == null) return copy(baseValue);
		if (FieldUtil.isSimple(field.getType())) return profileValue;

		if (Map.class.isAssignableFrom(field.getType())) {
			return mergeMap((Map<Object,Object>) baseValue, (Map<Object,Object>) profileValue);
		}

		if (Collection.class.isAssignableFrom(field.getType())) {
			return mergeCollection((Collection<Object>) baseValue, (Collection<Object>) profileValue);
		}

		return merge(baseValue, profileValue);
	}

	private static Map<Object,Object> mergeMap(Map<Object,Object> base, Map<Object,Object> profile) {
		Map<Object,Object> merged = new LinkedHashMap<>();
		if (base != null) merged.putAll(base);
		if (profile != null) merged.putAll(profile);
		return merged;
	}

	private static Collection<Object> mergeCollection(Collection<Object> base, Collection<Object> profile) {
		Collection<Object> merged = new ArrayList<>();
		if (base != null) {
			for (Object bItem : base) {
				if (bItem instanceof Map<?, ?> map) merged.add(new LinkedHashMap<>(map));
				else merged.add(bItem);
			}
		}

		if (profile != null) {
			for (Object pItem : profile) {
				if (pItem instanceof Map<?, ?> pMap) mergeMapItem(merged, pMap);
				else merged.add(pItem);
			}
		}

		return merged;
	}

	@SuppressWarnings("unchecked")
	private static void mergeMapItem(Collection<Object> merged, Map<?, ?> profileMap) {
		String pKey = profileMap.keySet().iterator().next().toString();
		boolean mergedFlag = false;

		for (Object bItem : merged) {
			if (bItem instanceof Map<?, ?> bMapRaw) {
				Map<Object,Object> bMap = (Map<Object,Object>) bMapRaw;
				if (bMap.containsKey(pKey)) {
					Object bValue = bMap.get(pKey);
					Object pValue = profileMap.get(pKey);

					if (bValue instanceof List<?> bList && pValue instanceof List<?> pList) {
						List<Object> newList = new ArrayList<>(bList);
						newList.addAll(pList);
						bMap.put(pKey, newList);
					} else {
						bMap.put(pKey, pValue);
					}

					mergedFlag = true;
					break;
				}
			}
		}

		if (!mergedFlag) {
			merged.add(new LinkedHashMap<>(profileMap));
		}
	}

	private static Object copy(Object value) {
		if (value == null) return null;
		if (value instanceof List<?> list) return new ArrayList<>(list);
		if (value instanceof Set<?> set) new HashSet<>(set);
		if (value instanceof Map<?, ?> map) return new LinkedHashMap<>(map);

		return value;
	}


}
