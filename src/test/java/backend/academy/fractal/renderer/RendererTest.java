package backend.academy.fractal.renderer;

import backend.academy.fractal.cli.Params;
import backend.academy.fractal.variation.Variation;
import backend.academy.fractal.variation.Variations;
import java.awt.Color;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RendererTest {
    private Renderer renderer;

    @Mock
    private Params params;

    @Mock
    private CountDownLatch latch;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        renderer = new Renderer();
        renderer.latch(latch);
        int width = 100;
        int height = 100;
        int samplingFactor = 2;
        double xMin = -2.0;
        double xMax = 2.0;
        double yMin = -2.0;
        double yMax = 2.0;
        int numVectors = 10;
        int samples = 1000;
        int nThreads = 4;
        int iterations = 100;
        int symmetry = 8;
        List<Variation> variations = List.of(Variations::linear);
        Color backgroundColor = Color.BLACK;

        when(params.width()).thenReturn(width);
        when(params.height()).thenReturn(height);
        when(params.samplingFactor()).thenReturn(samplingFactor);
        when(params.xMin()).thenReturn(xMin);
        when(params.xMax()).thenReturn(xMax);
        when(params.yMin()).thenReturn(yMin);
        when(params.yMax()).thenReturn(yMax);
        when(params.numVectors()).thenReturn(numVectors);
        when(params.samples()).thenReturn(samples);
        when(params.nThreads()).thenReturn(nThreads);
        when(params.iterations()).thenReturn(iterations);
        when(params.symmetry()).thenReturn(symmetry);
        when(params.variations()).thenReturn(variations);
        when(params.backgroundColor()).thenReturn(backgroundColor);

        renderer.provide(params);
    }

    @Test
    public void testRender_callsLatchCountDown() {
        renderer.latch(latch);
        renderer.render();
        verify(latch, times(1)).countDown();
    }
}
