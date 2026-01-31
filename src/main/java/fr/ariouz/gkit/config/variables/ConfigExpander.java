package fr.ariouz.gkit.config.variables;

import fr.ariouz.gkit.config.ConfigException;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ConfigExpander {

	private static final Pattern VAR_PATTERN = Pattern.compile("\\$\\{([a-zA-Z0-9_.-]+)}");
	private final Map<String, VariableResolver> resolvers;

	public ConfigExpander(List<VariableResolver> resolvers) {
		this.resolvers = resolvers.stream()
				.collect(Collectors.toMap(
						VariableResolver::namespace,
						Function.identity()
				));
	}

	public void expandConfig(Object config) {
		if (config == null) return;

		if (isTerminalType(config)) return ;

		if (config instanceof Collection<?> collection) {
			expandCollection(collection);
			return ;
		}

		if (config instanceof Map<?, ?> map) {
			expandMap(map);
			return ;
		}

		Class<?> clazz = config.getClass();
		for (Field field :  clazz.getDeclaredFields()) {
			field.setAccessible(true);
			try {
				Object value = field.get(config);
				if (value instanceof String s) field.set(config, expand(s));
				else expandConfig(value);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void expandCollection(Collection<?> col) {
		if (col.isEmpty()) return;

		if (col.iterator().next() instanceof String) {
			Collection<String> strings = (Collection<String>) col;

			List<String> expanded = strings.stream()
					.map(this::expand)
					.toList();

			strings.clear();
			strings.addAll(expanded);
		} else {
			col.forEach(this::expandConfig);
		}
	}

	@SuppressWarnings("unchecked")
	private void expandMap(Map<?, ?> map) {
		for (Map.Entry<?, ?> entry : map.entrySet()) {
			Object value = entry.getValue();

			if (value instanceof String s) {
				((Map<Object, Object>) map)
						.put(entry.getKey(), expand(s));
			} else {
				expandConfig(value);
			}
		}
	}

	public String expand(String value) {
		Matcher matcher = VAR_PATTERN.matcher(value);
		StringBuilder sb = new StringBuilder();

		while (matcher.find()) {
			String expression = matcher.group(1);
			int index = expression.indexOf('.');

			if (index < 0) throw new ConfigException("Invalid variable syntax: ${"+expression+"}");

			String namespace = expression.substring(0, index);
			String key =  expression.substring(index + 1);
			VariableResolver resolver = resolvers.get(namespace);

			if (resolver == null) throw new ConfigException("Unknown variable namespace: "+namespace);

			String resolved = resolver.resolve(key)
					.orElseThrow(() ->
							new ConfigException("Undefined variable: "+namespace+"."+key)
					);
			matcher.appendReplacement(sb, Matcher.quoteReplacement(resolved));
		}

		matcher.appendTail(sb);
		return sb.toString();
	}

	private boolean isTerminalType(Object o) {
		return o instanceof String
				|| o instanceof Number
				|| o instanceof Boolean
				|| o.getClass().isEnum();
	}

}
