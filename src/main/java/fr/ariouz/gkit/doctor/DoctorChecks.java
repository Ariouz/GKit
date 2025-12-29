package fr.ariouz.gkit.doctor;

public enum DoctorChecks {

	JAVA_VERSION(true, JavaVersionDoctorCheck.class),
	JAVA_HOME(false, JavaHomeDoctorCheck.class),
	NATIVE_IMAGE(true, NativeImageDoctorCheck.class),
	MAVEN_PROJECT(false, MavenProjectDoctorCheck.class),

	;

	private final boolean fatalOnError;
	private final  Class<? extends ADoctorCheck> clazz;

	DoctorChecks(boolean fatalOnError, Class<? extends ADoctorCheck> clazz) {
		this.fatalOnError = fatalOnError;
		this.clazz = clazz;
	}

	public boolean isFatalOnError() {
		return fatalOnError;
	}

	public Class<? extends ADoctorCheck> getClazz() {
		return clazz;
	}
}
