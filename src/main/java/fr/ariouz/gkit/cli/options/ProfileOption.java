package fr.ariouz.gkit.cli.options;

import picocli.CommandLine;

public class ProfileOption {

	@CommandLine.Option(
			names = {"-p", "--profile"},
			description = "Profile to use, defined in gkit.yml"
	)
	public String profile;

}
