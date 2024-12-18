package backend.academy.fractal.cli;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class PositiveNumberValidator implements IParameterValidator {
    @Override
    public void validate(String name, String value) throws ParameterException {
        int n = Integer.parseInt(value);
        if (n < 0) {
            throw new ParameterException("Параметр " + name + " должен быть больше 0");
        }
    }
}
