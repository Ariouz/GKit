package fr.ariouz.gkit.config.variables;

import java.util.Optional;

public interface VariableResolver {

	String namespace();
	Optional<String> resolve(String key);

}
