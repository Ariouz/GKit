package fr.ariouz.gkit.doctor;

public class JavaVersionDoctorCheck extends AbstractDoctorCheck {

	private String vendor;

	@Override
	protected String getName() {
		return "Java Version";
	}

	@Override
	protected DoctorStatus performCheck() {
		vendor = System.getProperty("java.vendor.version");

		if (vendor == null) return DoctorStatus.ERROR;
		else if (vendor.contains("GraalVM")) return DoctorStatus.SUCCESS;
		return DoctorStatus.WARNING;
	}

	@Override
	protected String getStatusMessage(DoctorStatus status) {
		return switch (status) {
			case SUCCESS -> vendor + " " + Runtime.version().feature();
			case WARNING -> "Not using GraalVM. Add GRAALVM_HOME/bin to PATH";
			case ERROR-> "Couldn't fetch java vendor version";
		};
	}

}
