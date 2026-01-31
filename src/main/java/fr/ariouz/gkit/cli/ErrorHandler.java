package fr.ariouz.gkit.cli;

import fr.ariouz.gkit.build.BuildException;
import fr.ariouz.gkit.build.image.arg.BuildArgException;
import fr.ariouz.gkit.config.ConfigException;
import fr.ariouz.gkit.util.Colors;

import java.util.concurrent.Callable;

public class ErrorHandler {

	public static Integer runWithErrorHandling(Callable<Integer> action) {
		try {
			return action.call();
		} catch (ConfigException e) {
			System.err.println(Colors.RED + "Configuration Error: " + e.getMessage() + Colors.RESET);
			return 1;
		} catch (BuildException e) {
			System.err.println(Colors.RED + "Build Failed: " + e.getMessage() + Colors.RESET);
			return 1;
		} catch (BuildArgException e) {
			System.err.println(Colors.RED + "Invalid build arg: " + e.getMessage() + Colors.RESET);
			return 1;
		} catch (Exception e) {
			System.err.println(Colors.RED + "An unexpected error occurred:" + Colors.RESET);
			e.printStackTrace();
			return 2;
		}
	}

}
