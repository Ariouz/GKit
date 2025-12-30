package fr.ariouz.gkit.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class NativeUtil {

	public static boolean doesNativeImageExists() {
		File image = getNativeImage();
		if (image == null) return false;
		return image.exists() && Files.isExecutable(image.toPath());
	}

	public static File getNativeImage() {
		String javaHome = System.getenv("JAVA_HOME");
		if (javaHome == null) return null;

		String os = System.getProperty("os.name").toLowerCase();
		String executableName = os.contains("win") ? "native-image.cmd" : "native-image";

		Path niPath = Path.of(javaHome, "bin", executableName);
		return niPath.toFile();
	}

}
