package backend.academy.fractal.cli;

import backend.academy.fractal.domain.Point;
import backend.academy.fractal.variation.Variation;
import backend.academy.fractal.variation.Variations;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import lombok.SneakyThrows;

public class VariationConverter implements IStringConverter<Variation> {
    @Override
    @SneakyThrows
    public Variation convert(String value) {
        Method method;
        try {
            method = Variations.class.getMethod(value, Point.class);
        } catch (Exception e) {
            throw new ParameterException("Невалидный список вариаций, используйте вариации из списка");
        }
        return p -> {
            try {
                return (Point) method.invoke(null, p);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        };
    }
}
