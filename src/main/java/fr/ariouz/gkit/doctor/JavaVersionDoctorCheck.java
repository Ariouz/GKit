package fr.ariouz.gkit.doctor;

public class JavaVersionDoctorCheck implements DoctorCheck {

	private DoctorStatus status = DoctorStatus.WARNING;

	@Override
	public DoctorStatus check(boolean print) {
		String vendor = System.getProperty("java.vendor.version");

		if (vendor == null) status = DoctorStatus.ERROR;
		else if (vendor.contains("GraalVM")) status = DoctorStatus.SUCCESS;

		if (print) {
			StringBuilder builder=  new StringBuilder(status.getPrefix()+ "	Java version: ");
			switch (status) {
				case SUCCESS:
					builder.append(vendor).append(" ").append(Runtime.version().feature());
					break;
				case WARNING:
					builder.append("Not using GraalVM. Add GRAALVM_HOME/bin to PATH");
					break;
				case ERROR:
					builder.append("Couldn't fetch java vendor version");
					break;
			}
			System.out.println(builder);
		}

		return status;
	}

}
