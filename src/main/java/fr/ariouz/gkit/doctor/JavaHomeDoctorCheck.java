package fr.ariouz.gkit.doctor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JavaHomeDoctorCheck implements DoctorCheck {

	private DoctorStatus status = DoctorStatus.WARNING;

	@Override
	public DoctorStatus check(boolean print) {
		String home = System.getenv("JAVA_HOME");

		if (home == null) status = DoctorStatus.ERROR;
		if (isJavaHomeGraalVM(home)) status = DoctorStatus.SUCCESS;

		if (print) {
			StringBuilder builder=  new StringBuilder(status.getPrefix()+ "	Java home: ");
			switch (status) {
				case SUCCESS:
					builder.append("Is a GraalVM installation");
					break;
				case WARNING:
					builder.append("Is not a GraalVM installation");
					break;
				case ERROR:
					builder.append("Not set");
					break;
			}
			System.out.println(builder);
		}

		return status;
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
