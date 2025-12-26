package fr.ariouz.gkit.doctor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JavaHomeDoctorCheck extends  AbstractDoctorCheck{

	@Override
	protected String getName() {
		return "JAVA_HOME";
	}

	@Override
	protected DoctorStatus performCheck() {
		String home = System.getenv("JAVA_HOME");

		if (home == null) return DoctorStatus.ERROR;
		if (isJavaHomeGraalVM(home)) return DoctorStatus.SUCCESS;
		return  DoctorStatus.WARNING;
	}

	@Override
	protected String getStatusMessage(DoctorStatus status) {
		return switch (status) {
			case SUCCESS 	-> "Is a GraalVM installation";
			case WARNING 	-> "Not a GraalVM installation";
			case ERROR 		-> "Not set";
		};
	}

	private boolean isJavaHomeGraalVM(String javaHome) {
		if (javaHome == null) return false;

		Path releaseFile = Path.of(javaHome, "release");
		if (!Files.exists(releaseFile)) return false;

		try {
			List<String> lines = Files.readAllLines(releaseFile);
			return lines.stream() .anyMatch(line -> line.toUpperCase().contains("GRAALVM"));
		} catch (IOException e) {
			return false;
		}
	}

}
