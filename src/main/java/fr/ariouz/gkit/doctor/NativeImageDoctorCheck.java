package fr.ariouz.gkit.doctor;

import java.nio.file.Files;
import java.nio.file.Path;

public class NativeImageDoctorCheck extends AbstractDoctorCheck {

	@Override
	protected String getName() {
		return "native-image";
	}

	@Override
	protected DoctorStatus performCheck() {
		String home = System.getenv("JAVA_HOME");

		if (home == null) return DoctorStatus.ERROR;
		if (doesNativeImageExists(home)) return DoctorStatus.SUCCESS;
		return DoctorStatus.WARNING;
	}

	@Override
	protected String getStatusMessage(DoctorStatus status) {
		return switch (status) {
			case SUCCESS -> "Found";
			case WARNING -> "Not found";
			case ERROR   -> "JAVA_HOME not set";
		};
	}

	private boolean doesNativeImageExists(String javaHome) {
		if (javaHome == null) return false;

		String os = System.getProperty("os.name").toLowerCase();
		String executableName = os.contains("win") ? "native-image.cmd" : "native-image";

		Path niPath = Path.of(javaHome, "bin", executableName);

		return Files.exists(niPath) && Files.isExecutable(niPath);
	}

}
