package fr.ariouz.gkit.doctor;

import fr.ariouz.gkit.config.ConfigProvider;

import java.nio.file.Files;
import java.nio.file.Path;

public class MavenProjectDoctorCheck extends ADoctorCheck {

	boolean isMavenProject = false;

	@Override
	protected String getName() {
		return "Maven Project";
	}

	@Override
	protected DoctorStatus performCheck() {
		if (!doesPomExists()) return DoctorStatus.SUCCESS;

		isMavenProject = true;
		String buildCommand = ConfigProvider.getConfig().getBuildConfig().getArtifact().getCommand();
		if (doesPomExists() && buildCommand == null) return DoctorStatus.WARNING;
		return DoctorStatus.SUCCESS;
	}

	@Override
	protected String getStatusMessage(DoctorStatus status) {
		if (!isMavenProject) return "Not a maven project";
		if (status == DoctorStatus.WARNING) return "No artifact build command defined, assuming the jar artifact will already be built";
		return "pom.xml found and build command defined: \n\t\t\t" + ConfigProvider.getConfig().getBuildConfig().getArtifact().getCommand();
	}

	private boolean doesPomExists() {
		Path pomPath = Path.of(ConfigProvider.getConfig().getBuildConfig().getProjectDir(),"pom.xml");
		return Files.exists(pomPath) && Files.isExecutable(pomPath);
	}

}
