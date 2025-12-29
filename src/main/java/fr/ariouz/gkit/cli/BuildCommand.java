package fr.ariouz.gkit.cli;

import fr.ariouz.gkit.cli.options.ProfileOption;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command (
		name="build",
		description = "Build artifact"
)
public class BuildCommand implements Callable<Integer> {

	@Mixin
	private ProfileOption profileOption;

	@Option(
			names = {"--native"},
			description = "Build native image after artifact build"
	)
	private boolean buildNative;


	@Override
	public Integer call() {
		return 0;
	}
}
