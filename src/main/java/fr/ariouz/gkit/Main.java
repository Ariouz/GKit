package fr.ariouz.gkit;

import fr.ariouz.gkit.cli.GKitCommand;
import fr.ariouz.gkit.util.OSHelper;
import picocli.CommandLine;

public class Main {

	public static void main(String[] args) {
		enableAnsi();

		int exitCode = new CommandLine(new GKitCommand()).execute(args);
		System.exit(exitCode);
	}

	private static void enableAnsi() {
		System.setProperty("jdk.console.allowAnsi", "true");

		if (OSHelper.isWindows()) {
			try {
				new ProcessBuilder("cmd", "/c", "").inheritIO().start().waitFor();
			} catch (Exception ignored) {}
		}
	}


}