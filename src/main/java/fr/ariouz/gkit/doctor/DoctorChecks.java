package fr.ariouz.gkit.doctor;

import java.util.function.Supplier;

public enum DoctorChecks {

	JAVA_VERSION(true, JavaVersionDoctorCheck::new),
	JAVA_HOME(false, JavaHomeDoctorCheck::new),
	NATIVE_IMAGE(true, NativeImageDoctorCheck::new),
	MAVEN_PROJECT(false, MavenProjectDoctorCheck::new),

	;

	private final boolean fatalOnError;
	private final Supplier<ADoctorCheck> supplier;

	DoctorChecks(boolean fatalOnError, Supplier<ADoctorCheck> supplier) {
		this.fatalOnError = fatalOnError;
		this.supplier = supplier;
	}

	public boolean isFatalOnError() {
		return fatalOnError;
	}

	public ADoctorCheck create() {
		return supplier.get();
	}
}
