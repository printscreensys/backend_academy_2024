package backend.academy.fractal.cli;

import backend.academy.fractal.domain.Point;
import backend.academy.fractal.variation.Variation;
import backend.academy.fractal.variation.Variations;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import lombok.SneakyThrows;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Properties;

public class VariationConverter implements IStringConverter<Variation> {
    @Override
    @SneakyThrows
    public Variation convert(String value) {
        var contextClassLoader = Thread.currentThread().getContextClassLoader();
        var rootPath = Objects.requireNonNull(contextClassLoader.getResource("")).getPath();
        var variationsProperties = new Properties();
        variationsProperties.load(new FileInputStream(rootPath + "variations"));
        if (!variationsProperties.containsKey(value)) {
            throw new ParameterException("Невалидный список вариаций, используйте вариации из списка");
        }
        var method = Variations.class.getMethod(value, Point.class);
        return p -> {
            try {
                return (Point) method.invoke(null, p);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        };
    }
}
