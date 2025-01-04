package backend.academy.fractal.cli;

import com.beust.jcommander.IParametersValidator;
import com.beust.jcommander.ParameterException;
import lombok.SneakyThrows;
import java.util.Map;

public class MutualParametersValidator implements IParametersValidator {
    @Override
    @SneakyThrows
    public void validate(Map<String, Object> parameters) {
        var path = (String) parameters.get("-path");
        var duration = (int) parameters.get("-duration");
        var fps = (int) parameters.get("-fps");

        if (path.endsWith(".mp4")) {
            if (duration <= 0 || 121 <= duration) {
                throw new ParameterException("Длительность видео должна быть от 1 до 120");
            }
            if (fps <= 0 || 121 <= fps) {
                throw new ParameterException("Частота кадров видео должна быть от 1 до 120");
            }
        }
    }
}
