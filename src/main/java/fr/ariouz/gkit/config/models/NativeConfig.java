package fr.ariouz.gkit.config.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ConfigObject
public class NativeConfig {

	private String output;
	private List<Map<String, Object>> buildArgs = new ArrayList<>();

	public String getOutput() {
		return output;
	}

	public List<Map<String, Object>> getBuildArgs() {
		return buildArgs;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public void setBuildArgs(List<Map<String, Object>> buildArgs) {
		this.buildArgs = buildArgs;
	}
}
