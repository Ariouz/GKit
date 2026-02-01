package fr.ariouz.gkit.doctor;

import fr.ariouz.gkit.build.image.arg.BuildArgException;
import fr.ariouz.gkit.build.image.arg.NativeBuildArgParser;
import fr.ariouz.gkit.config.ConfigException;
import fr.ariouz.gkit.config.ConfigProvider;
import fr.ariouz.gkit.config.models.GKitConfig;

public class ConfigDoctorCheck extends ADoctorCheck {

	@Override
	protected String getName() {
		return "Base config";
	}

	@Override
	protected DoctorStatus performCheck() {
		try {
			GKitConfig config = ConfigProvider.getConfig();
			new NativeBuildArgParser().parseBuildArgs(config.getNativeImage());
		} catch (BuildArgException e) {
			return DoctorStatus.WARNING;
		} catch (ConfigException e) {
			return DoctorStatus.ERROR;
		}
		return DoctorStatus.SUCCESS;
	}

	@Override
	protected String getStatusMessage(DoctorStatus status) {
		return switch (status) {
			case ERROR -> "Failed to load config. Consider using gkit check-config [-p PROFILE] for details";
			case WARNING -> "One or more native build args are invalid. Consider using gkit check-config [-p PROFILE] for details";
			case SUCCESS -> "No error on base profile found. Consider using gkit check-config [-p PROFILE] for profile validation";
		};
	}
}
