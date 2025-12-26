package fr.ariouz.gkit.cli;

import fr.ariouz.gkit.doctor.JavaHomeDoctorCheck;
import fr.ariouz.gkit.doctor.JavaVersionDoctorCheck;
import fr.ariouz.gkit.doctor.NativeImageDoctorCheck;
import picocli.CommandLine.*;

import java.util.concurrent.Callable;

@Command(
		name = "doctor",
		description= "Check build prerequisites"
)
public class DoctorCommand implements Callable<Integer> {


	@Override
	public Integer call() throws Exception {

		new JavaVersionDoctorCheck().check(true);
		new JavaHomeDoctorCheck().check(true);
		new NativeImageDoctorCheck().check(true);

		return 0;
	}
}
