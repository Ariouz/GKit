package fr.ariouz.gkit.util;

public class OSHelper {

	public static boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().contains("win");
	}

}
