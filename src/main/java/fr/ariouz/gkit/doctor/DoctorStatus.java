package fr.ariouz.gkit.doctor;

import fr.ariouz.gkit.util.StatusPrefix;

public enum DoctorStatus {

	SUCCESS(StatusPrefix.SUCCESS),
	WARNING(StatusPrefix.WARN),
	ERROR(StatusPrefix.ERROR),

	;

	private final String prefix;

	DoctorStatus(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}
}
