package fr.ariouz.gkit.build.image.arg.renderer;

import fr.ariouz.gkit.build.image.arg.NativeBuildArg;

import java.util.List;

public interface NativeBuildArgRenderer<T> {

	List<String> render(NativeBuildArg arg, T value);





}

