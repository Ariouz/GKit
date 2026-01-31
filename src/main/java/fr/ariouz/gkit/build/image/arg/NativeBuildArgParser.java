package fr.ariouz.gkit.build.image.arg;

import fr.ariouz.gkit.config.models.NativeConfig;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class NativeBuildArgParser {

	public NativeBuildArgParser() {}

	public List<String> parseBuildArgs(NativeConfig config) {
		List<NormalizedArg> normalizedArgs = normalize(config.getBuildArgs());
		normalizedArgs.sort(Comparator.comparing(NormalizedArg::arg));
		return renderArgs(normalizedArgs);
	}

	List<NormalizedArg> normalize(List<Map<String, Object>> buildArgs) {
		List<NormalizedArg> result = new ArrayList<>();

		for (Map<String, Object> map : buildArgs) {
			if (map.size() != 1) throw new BuildArgException("BuildArg must contain one entry: " + map);

			Map.Entry<String, Object> entry =
					map.entrySet()
					.iterator()
					.next();

			String key = entry.getKey();
			Object value = entry.getValue();

			NativeBuildArg arg = NativeBuildArg.fromConfigKey(key);
			result.add(new NormalizedArg(arg, value));
		}

		return result;
	}

	List<String> renderArgs(List<NormalizedArg> args) {
		List<String> cli = new ArrayList<>();

		for (NormalizedArg arg : args) {
			cli.addAll(arg.arg().render(arg.value()));
		}

		return cli;
	}

}
