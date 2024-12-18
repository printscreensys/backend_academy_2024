package backend.academy.fractal.renderer;

import backend.academy.fractal.cli.Params;
import backend.academy.fractal.domain.FractalImage;
import backend.academy.fractal.domain.Point;
import backend.academy.fractal.utils.ImageUtils;
import backend.academy.fractal.variation.Variation;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Renderer {
    private int xRes;
    private int yRes;
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    private int numVectors;
    private int samplesNumber;
    private int iterationsPerSample;
    private int symmetry;
    private List<Variation> variations;

    private List<Color> colorVectors;
    private List<List<Double>> coefficientVectors;

    @Getter
    private FractalImage image;
    @Setter
    private CountDownLatch latch;

    public void provide(Params params) {
        xRes = params.width() * params.samplingFactor();
        yRes = params.height() * params.samplingFactor();
        xMin = params.xMin();
        xMax = params.xMax();
        yMin = params.yMin();
        yMax = params.yMax();
        numVectors = params.numVectors();
        samplesNumber = params.samples() / params.nThreads();
        iterationsPerSample = params.iterations();
        symmetry = params.symmetry();
        variations = params.variations();
        colorVectors = params.colors().isEmpty() ? generateColors(numVectors) : params.colors();
        coefficientVectors = generateCoefficients(numVectors);
        image = FractalImage.create(xRes, yRes, params.backgroundColor());
    }

    public void render() {
        log.info("{} - вызвал render()", Thread.currentThread().getName());
        var random = ThreadLocalRandom.current();
        for (int sample = 0; sample < samplesNumber; sample++) {
            double newX = random.nextDouble(0, xMax- xMin) - xMax;
            double newY = random.nextDouble(0, yMax-yMin) - yMax;

            for (int iter = 0; iter < iterationsPerSample + 20; iter++) {
                var curFunc = variations.get(iter % variations.size());
                var vectorIndex = random.nextInt(numVectors);
                var vector = coefficientVectors.get(vectorIndex);

                var a = vector.get(0);
                var b = vector.get(1);
                var c = vector.get(2);
                var d = vector.get(3);
                var e = vector.get(4);
                var f = vector.get(5);

                var x = a * newX + b * newY + c;
                var y = d * newX + e * newY + f;

                var oldPoint = new Point(x, y);
                var newPoint = curFunc.apply(oldPoint);

                newX = newPoint.x();
                newY = newPoint.y();

                var ranX = xMax - xMin;
                var ranY = yMax - yMin;

                if (iter > 20) {

                    var theta2 = 0.0;
                    for (int s = 0; s < symmetry; s++) {
                        theta2 += (2 * Math.PI / symmetry);
                        var xRot = newX * Math.cos(theta2) - newY * Math.sin(theta2);
                        var yRot = newX * Math.sin(theta2) - newY * Math.cos(theta2);
                        var pointRot = new Point(xRot, yRot);

                        if (pointRot.isInArea(xMin, xMax, yMin, yMax)) {
                            var x1 = (int) ((xMax - xRot) / ranX) * xRes;
                            var y1 = (int) ((yMax - yRot) / ranY) * yRes;
                            if (image.contains(x1, y1)) {
                                var color = colorVectors.get(vectorIndex % colorVectors.size());
                                updateColor(x1, y1, color);
                            }
                        }
                    }
                }
            }
        }
        latch.countDown();
    }

    private List<List<Double>> generateCoefficients(int numVectors) {
        List<List<Double>> coefficientVectors = new ArrayList<>();
        for (int i = 0; i < numVectors; i++) {
            var vector = new ArrayList<Double>();
            for (int j = 0; j < 6; j++) {
                vector.add(2 * ThreadLocalRandom.current().nextDouble() - 1);
            }
            coefficientVectors.add(vector);
        }
        return coefficientVectors;
    }

    private List<Color> generateColors(int numVectors) {
        List<Color> colorVectors = new ArrayList<>();
        for (int i = 0; i < numVectors; i++) {
            var randomColor = new Color(
                ThreadLocalRandom.current().nextInt(256),
                ThreadLocalRandom.current().nextInt(256),
                ThreadLocalRandom.current().nextInt(256)
            );
            colorVectors.add(randomColor);
        }
        return colorVectors;
    }

    @SneakyThrows
    private void updateColor(int x, int y, Color color) {
        var pixel = image.pixel(x, y);
        synchronized (pixel) {
            pixel.hit();
            var newColor = pixel.hitCount() > 0 ? ImageUtils.averageColor(color, pixel.color()) : color;
            pixel.color(newColor);
        }
    }
}
