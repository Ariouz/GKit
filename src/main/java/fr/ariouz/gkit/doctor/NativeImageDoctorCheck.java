package fr.ariouz.gkit.doctor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class NativeImageDoctorCheck implements DoctorCheck {

	private DoctorStatus status = DoctorStatus.WARNING;

	@Override
	public DoctorStatus check(boolean print) {
		String home = System.getenv("JAVA_HOME");

		if (home == null) status = DoctorStatus.ERROR;
		if (doesNativeImageExists(home)) status = DoctorStatus.SUCCESS;

		if (print) {
			StringBuilder builder=  new StringBuilder(status.getPrefix()+ "	native-image: ");
			switch (status) {
				case SUCCESS:
					builder.append("Found");
					break;
				case WARNING:
					builder.append("Not found");
					break;
				case ERROR:
					builder.append("JAVA_HOME not set");
					break;
			}
			System.out.println(builder);
		}

		return status;
	}

	private boolean doesNativeImageExists(String javaHome) {
		if (javaHome == null) return false;

		String os = System.getProperty("os.name").toLowerCase();
		String executableName = os.contains("win") ? "native-image.cmd" : "native-image";

		Path niPath = Path.of(javaHome, "bin", executableName);

		return Files.exists(niPath) && Files.isExecutable(niPath);
	}

}
