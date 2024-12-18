package backend.academy.fractal;

import backend.academy.fractal.cli.Params;
import backend.academy.fractal.renderer.Renderer;
import backend.academy.fractal.utils.ImageUtils;
import com.beust.jcommander.JCommander;
import java.util.concurrent.CountDownLatch;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@Log4j2
@UtilityClass
public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        var params = new Params();
        var jcommander = JCommander.newBuilder()
            .addObject(params)
            .build();

        jcommander.parse(args);

        if (params.help()) {
            jcommander.usage();
            return;
        }

        log.info(params);

        var latch = new CountDownLatch(params.nThreads());
        var renderer = new Renderer();
        renderer.latch(latch).provide(params);
        for (int i = 0; i < params.nThreads(); i++) {
            Thread.ofVirtual()
                .name("RendererThread" + i)
                .start(renderer::render);
        }
        latch.await();

        var img = renderer.image();
        ImageUtils.gammaCorrect(img, params.gamma(), params.backgroundColor());
        img = ImageUtils.reduced(img, params.samplingFactor());
        ImageUtils.save(img, params.path());
    }
}
