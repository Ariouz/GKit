package fr.ariouz.gkit.test.build.image;

import fr.ariouz.gkit.build.image.arg.NativeBuildArg;
import fr.ariouz.gkit.build.image.arg.NativeBuildArgParser;
import fr.ariouz.gkit.config.models.NativeConfig;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class NativeBuildArgParserTest {


    @Test
    void parseBuildArgs_isValid() {
        NativeConfig nativeConfig = new NativeConfig();
        nativeConfig.setBuildArgs(List.of(
                Map.of(NativeBuildArg.FALLBACK_IMAGE.getConfigKey(), false),
                Map.of(NativeBuildArg.OPTIMIZATION_LEVEL.getConfigKey(), 2)
        ));

        List<String> args = new NativeBuildArgParser()
                .parseBuildArgs(nativeConfig);

        assertThat(args).containsExactly(
                "--no-fallback",
                "-O2"
        );
    }
}
