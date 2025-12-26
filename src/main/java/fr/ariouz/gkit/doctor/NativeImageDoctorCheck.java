package fr.ariouz.gkit.doctor;

import java.nio.file.Files;
import java.nio.file.Path;

public class NativeImageDoctorCheck extends AbstractDoctorCheck {

	private boolean imageFound;

	@Override
	protected String getName() {
		return "native-image";
	}

	@Override
	protected DoctorStatus performCheck() {
		String home = System.getenv("JAVA_HOME");

		if (home == null) return DoctorStatus.ERROR;
		imageFound = doesNativeImageExists(home);
		if (imageFound) return DoctorStatus.SUCCESS;
		return DoctorStatus.ERROR;
	}

	@Override
	protected String getStatusMessage(DoctorStatus status) {
		if (!imageFound && status == DoctorStatus.ERROR) return "Not found in JAVA_HOME/bin";
		return switch (status) {
			case SUCCESS -> "Found";
			case WARNING -> "no-op";
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
