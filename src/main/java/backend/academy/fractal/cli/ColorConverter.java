package backend.academy.fractal.cli;

import com.beust.jcommander.IStringConverter;
import java.awt.Color;

public class ColorConverter implements IStringConverter<Color> {
    @Override
    public Color convert(String value) {
        try {
            return Color.decode(value);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Не удалось конвертировать %s в цвет", value));
        }
    }
}
