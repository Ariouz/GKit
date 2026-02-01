package fr.ariouz.gkit.cli;

import fr.ariouz.gkit.doctor.*;
import fr.ariouz.gkit.util.Colors;
import fr.ariouz.gkit.util.StatusPrefix;
import picocli.CommandLine.*;

import java.util.ArrayList;
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

		List<DoctorStatus> results = new ArrayList<>();
		boolean earlyError = false;

		for (DoctorChecks checkEntry : DoctorChecks.values()) {
			try {
				ADoctorCheck check = checkEntry.create();
				DoctorStatus status = check.check(true);
				results.add(status);

				if (checkEntry.isFatalOnError() && status == DoctorStatus.ERROR) {
					earlyError = true;
					break ;
				}
			} catch (Exception e) {
				throw new RuntimeException("Failed to instantiate check: " + checkEntry, e);
			}
		}

		if (results.stream().anyMatch(result -> result == DoctorStatus.WARNING)) {
			System.out.println("\n"+ StatusPrefix.WARN + "	Note: Some of the checks were not "+StatusPrefix.SUCCESS);
		}

		int errors = (int) results.stream().filter(s -> s == DoctorStatus.ERROR).count();
		int warnings = (int) results.stream().filter(s -> s == DoctorStatus.WARNING).count();
		int success = (int) results.stream().filter(s -> s == DoctorStatus.SUCCESS).count();

		System.out.println();
		printCheckSummary(errors, warnings, success, (errors + warnings + success), earlyError);
		return 0;
	}

	private void printCheckSummary(int errors, int warnings, int success, int total, boolean fatal) {
		String color;
		String conclusion;
		String message;

		if (fatal) {
			color = Colors.RED;
			conclusion = "FATAL ERRORS DETECTED";
			message = "Action Required: Cannot run all checks until these errors are resolved!\n" +
					"Refer to the logs above for details on each fatal error.";
		} else if (errors > 0) {
			color = Colors.RED;
			conclusion = "BUILD ENVIRONMENT INCOMPLETE";
			message = "Please fix the errors above before proceeding.";
		} else if (warnings > 0) {
			color = Colors.YELLOW;
			conclusion = "ENVIRONMENT READY (with warnings)";
			message = "Note: The build can proceed, but check warnings for potential runtime issues.";
		} else {
			color = Colors.GREEN;
			conclusion = "ENVIRONMENT FULLY COMPLIANT";
			message = "All " + total + " checks passed successfully.";
		}

		if (fatal) System.out.println(color + "==================== CRITICAL FAILURE ====================" + Colors.RESET);
		System.out.println(color + "CONCLUSION: " + conclusion + Colors.RESET);
		System.out.println(color + "Status: " + success + " Success(es), " +  errors + " Error(s), " + warnings + " Warning(s)" + Colors.RESET);
		System.out.println(color + message + Colors.RESET);
		if (fatal) System.out.println(color + "==========================================================" + Colors.RESET);
	}


}
