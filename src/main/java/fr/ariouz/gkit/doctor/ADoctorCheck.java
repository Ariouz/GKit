package fr.ariouz.gkit.doctor;

public abstract class ADoctorCheck {

	protected DoctorStatus status = DoctorStatus.WARNING;

	protected abstract String getName();
	protected abstract DoctorStatus performCheck();
	protected abstract String getStatusMessage(DoctorStatus status);

	public DoctorStatus check(boolean print) {
		this.status = performCheck();

		if (print) {
			String message = String.format("%s		%s: %s",
					status.getPrefix(),
					getName(),
					getStatusMessage(status)
			);
			System.out.println(message);
		}

		return status;
	}

}
