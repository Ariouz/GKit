package fr.ariouz.gkit.build;

import fr.ariouz.gkit.util.StatusPrefix;

import java.io.File;
import java.util.List;

public class ProcessRunner {

	public void run(
			String name,
			List<String> command,
			File workdir,
			boolean dryRun
	) throws BuildException {
		if (dryRun) {
			System.out.println(StatusPrefix.DRY_RUN +
					" Would run: " + String.join(" ", command));
			return;
		}

		ProcessBuilder pb = new ProcessBuilder(command);
		pb.inheritIO();
		pb.directory(workdir);

		try {
			Process process = pb.start();
			int exitValue = process.waitFor();

			if (exitValue != 0) {
				throw new BuildException(name + ": Build failed with exit code " + exitValue );
			}
		} catch (Exception e) {
			throw new BuildException(name + " : " + e.getMessage());
		}
	}

}
