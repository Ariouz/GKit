package fr.ariouz.gkit.config;

import java.util.List;

public class NativeConfig {

	private String output;
	private List<String> buildArgs;

	public String getOutput() {
		return output;
	}

	public List<String> getBuildArgs() {
		return buildArgs;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public void setBuildArgs(List<String> buildArgs) {
		this.buildArgs = buildArgs;
	}
}
