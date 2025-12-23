package fr.ariouz.gkit;

import fr.ariouz.gkit.cli.GKitCommand;
import picocli.CommandLine;

public class Main {

	public static void main(String[] args) {
		int exitCode = new CommandLine(new GKitCommand()).execute(args);
		System.exit(exitCode);
	}

}