package fr.ariouz.gkit.util;

import org.yaml.snakeyaml.error.MarkedYAMLException;

public class YamlErrorFormatter {

	public static RuntimeException formatYamlError(MarkedYAMLException e) {
		StringBuilder sb = new StringBuilder();

		sb.append("Invalid gkit.yml\n\n");

		if (e.getProblem() != null) {
			sb.append("Problem: ").append(e.getProblem()).append("\n");
		}

		if (e.getContext() != null) {
			sb.append("Context: ").append(e.getContext()).append("\n");
		}

		if (e.getProblemMark() != null) {
			sb.append("\nLocation:\n");
			sb.append("  line ").append(e.getProblemMark().getLine())
					.append(", column ").append(e.getProblemMark().getColumn())
					.append("\n");
		}

		if (e.getProblem() != null && e.getContext().contains("Cannot create property")) {
			sb.append("\nHint: Check for typos or unsupported fields in gkit.yml\n");
		}

		return new RuntimeException(sb.toString());
	}

}
