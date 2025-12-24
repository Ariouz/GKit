package fr.ariouz.gkit.cli;

import picocli.CommandLine.Command;

@Command(
		name = "gkit",
		description = "GKit - GraalVM native builds, simplified.",
		mixinStandardHelpOptions = true,
		version = "GKit v0.0.1",
		subcommands = {
			CheckConfigCommand.class
		}
)
public class GKitCommand implements Runnable {

	@Override
	public void run() {
		System.out.println("Use `gkit --help` to see available commands.");
	}
}
