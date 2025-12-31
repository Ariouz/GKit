package fr.ariouz.gkit.config.variables;

import java.util.Map;
import java.util.Optional;

public class GKitVariableResolver implements VariableResolver {

	private final Map<String, String> variables;

	public GKitVariableResolver(Map<String, String> variables) {
		this.variables = variables;
	}

	@Override
	public String namespace() {
		return "gkit";
	}

	@Override
	public Optional<String> resolve(String key) {
		return Optional.ofNullable(variables.get(key));
	}

}
