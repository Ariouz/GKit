package fr.ariouz.gkit.build;

public class BuildException extends RuntimeException {
	public BuildException(String message) {
		super(message);
	}

	public BuildException(String message, Throwable cause) {
		super(message, cause);
	}

}
