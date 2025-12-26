package fr.ariouz.gkit.cli;

import fr.ariouz.gkit.doctor.*;
import fr.ariouz.gkit.util.Colors;
import fr.ariouz.gkit.util.StatusPrefix;
import picocli.CommandLine.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

@Command(
		name = "doctor",
		description= "Check build prerequisites"
)
public class DoctorCommand implements Callable<Integer> {


	@Override
	public Integer call() {
		System.out.println(Colors.BLUE + "Performing prerequisites checks ...");

		List<DoctorStatus> results = Arrays.stream(DoctorChecks.values())
				.map(checkEntry -> {
				try {
					DoctorCheck check = checkEntry.getClazz().getDeclaredConstructor().newInstance();
					return check.check(true);
				} catch (Exception e) {
					throw new RuntimeException("Failed to instantiate check", e);
				}
		}).toList();

		if (results.stream().anyMatch(result -> result == DoctorStatus.WARNING)) {
			System.out.println("\n"+ StatusPrefix.WARN + "	Note: Some of the checks were not "+StatusPrefix.SUCCESS);
		}

		long errors = results.stream().filter(s -> s == DoctorStatus.ERROR).count();
		long warnings = results.stream().filter(s -> s == DoctorStatus.WARNING).count();
		long success = results.stream().filter(s -> s == DoctorStatus.SUCCESS).count();

		System.out.println();
		if (errors > 0) {
			System.out.println(Colors.RED + "Conclusion: BUILD ENVIRONMENT INCOMPLETE");
			System.out.println(Colors.RED + "Status: " + errors + " Error(s), " + warnings + " Warning(s)");
			System.out.println("Please fix the errors above before proceeding.");
		} else if (warnings > 0) {
			System.out.println(Colors.YELLOW + "Conclusion: ENVIRONMENT READY (with warnings)");
			System.out.println(Colors.YELLOW + "Status: " + success + "/" + results.size() + " checks passed.");
			System.out.println("Note: The build can proceed, but check warnings for potential runtime issues.");
		} else {
			System.out.println(Colors.GREEN + "Conclusion: ENVIRONMENT FULLY COMPLIANT");
			System.out.println(Colors.GREEN + "Status: All " + results.size() + " checks passed successfully.");
		}

		return 0;
	}
}
