package fr.ariouz.gkit.cli;

import picocli.CommandLine.Command;

@Command(
		name = "gkit",
		description = "GKit - Unlock full GraalVM potential on your project",
		mixinStandardHelpOptions = true,
		version = "GKit v0.0.1",
		subcommands = {

		}
)
public class GKitCommand implements Runnable {

	@Override
	public void run() {
		System.out.println("Use `gkit --help` to see available commands.");
	}
}
