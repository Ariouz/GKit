package fr.ariouz.gkit.config.variables;

import java.util.Optional;

public class EnvVariableResolver implements VariableResolver {

	@Override
	public String namespace() {
		return "env";
	}

	@Override
	public Optional<String> resolve(String key) {
		return Optional.ofNullable(System.getenv(key));
	}
}
