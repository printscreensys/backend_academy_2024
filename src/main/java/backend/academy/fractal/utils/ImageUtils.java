package backend.academy.fractal.utils;

import backend.academy.fractal.domain.FractalImage;
import backend.academy.fractal.domain.Pixel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;
import java.util.Arrays;
import javax.imageio.ImageIO;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

/**
 * Класс для работы с изображением
 */
@Log4j2
@UtilityClass
public final class ImageUtils {
    /**
     * Сохраняет FractalImage в файл по заданным пути и формату
     * @param image фрактал
     * @param pathToFile путь до файла в виде строки
     */
    @SneakyThrows
    public void save(FractalImage image, String pathToFile) {
        log.info("Сохранение изображения - {}", pathToFile);
        var outputFile = Paths.get(pathToFile).toFile();
        var extensionName = outputFile.getName()
            .substring(outputFile.getName().lastIndexOf(".") + 1)
            .toUpperCase();
        ImageIO.write(buffer(image), extensionName, outputFile);
    }

    /**
     * Преобразует FractalImage в BufferedImage для записи в файл
     * @param image FractalImage
     * @return BufferedImage
     */
    private BufferedImage buffer(FractalImage image) {
        var bufferedImage = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                var rgb = image.pixel(x, y).color().getRGB();
                bufferedImage.setRGB(x, y, rgb);
            }
        }
        return bufferedImage;
    }

    /**
     * Вычисляет средние значаения каналов RGB по двум цветам
     * @param color1 первый цвет
     * @param color2 второй цвет
     * @return новый цвет
     */
    public Color averageColor(Color color1, Color color2) {
        int newR = (color1.getRed() + color2.getRed()) / 2;
        int newG = (color1.getGreen() + color2.getGreen()) / 2;
        int newB = (color1.getBlue() + color2.getBlue()) / 2;

        return new Color(newR, newG, newB);
    }

    /**
     * Делает in-place гамма-коррекцию изображения
     * @param image FractalImage
     * @param backgroundColor - цвет фона
     * @param gamma коэффициент коррекции
     */
    public void gammaCorrect(FractalImage image, double gamma, Color backgroundColor) {
        log.info("{} выполняет гамма-коррекцию с коэффициентом {}", Thread.currentThread().getName(), gamma);
        var maxLogHitCount = Arrays.stream(image.data())
            .map(Pixel::hitCount)
            .filter(c -> c > 0)
            .mapToDouble(Math::log10)
            .max()
            .orElse(1);

        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                var currentPixel = image.pixel(x, y);
                var logHitCount = Math.log10(Math.max(currentPixel.hitCount(), 1)) / maxLogHitCount;

                var newRed = (int) (backgroundColor.getRed() + Math.pow(logHitCount, (1d / gamma))
                    * (currentPixel.color().getRed() - backgroundColor.getRed()));

                var newGreen = (int) (backgroundColor.getGreen() + Math.pow(logHitCount, (1d / gamma))
                    * (currentPixel.color().getGreen() - backgroundColor.getGreen()));

                var newBlue = (int) (backgroundColor.getBlue() + Math.pow(logHitCount, (1d / gamma))
                    * (currentPixel.color().getBlue() - backgroundColor.getBlue()));

                var newColor = new Color(newRed, newGreen, newBlue);
                currentPixel.color(newColor);
            }
        }
    }

    /**
     * Делает сжимает изображение и делает сглаживание (Supersample anti-aliasing)
     * @param image FractalImage
     * @param samplingFactor множитель для сжатия
     * @return сглаженное изображение
     */
    @SuppressWarnings({"checkstyle:MultipleVariableDeclarations", "checkstyle:NestedForDepth"})
    public FractalImage reduced(FractalImage image, int samplingFactor) {
        log.info("{} сжимает изображение в {} раз", Thread.currentThread().getName(), samplingFactor);
        log.info("Размер до сжатия - {} x {}", image.width(), image.height());
        var newHeight = image.height() / samplingFactor;
        var newWidth = image.width() / samplingFactor;
        log.info("Размер после сжатия - {} x {}", newWidth, newHeight);
        var reducedImageData = new Pixel[newHeight * newWidth];

        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                int red = 0, green = 0, blue = 0;
                var totalHit = 0;
                for (int sampleY = 0; sampleY < samplingFactor; sampleY++) {
                    for (int sampleX = 0; sampleX < samplingFactor; sampleX++) {
                        var curPixel = image.pixel(x * samplingFactor + sampleX, y * samplingFactor + sampleY);
                        red += curPixel.color().getRed();
                        green += curPixel.color().getGreen();
                        blue += curPixel.color().getBlue();
                        totalHit += curPixel.hitCount();
                    }
                }
                red = red / (samplingFactor * samplingFactor);
                green = green / (samplingFactor * samplingFactor);
                blue = blue / (samplingFactor * samplingFactor);
                reducedImageData[y * newWidth + x] = new Pixel()
                    .x(x)
                    .y(y)
                    .color(new Color(red, green, blue))
                    .hitCount(totalHit);
            }
        }
        return new FractalImage(reducedImageData, newWidth, newHeight);
    }
}
