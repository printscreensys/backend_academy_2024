package backend.academy.fractal.cli;

import com.beust.jcommander.ParameterException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class PositiveNumberValidatorTest {
    private PositiveNumberValidator validator = new PositiveNumberValidator();

    @ParameterizedTest
    @ValueSource(strings = {"10", "0"})
    public void testValidate_PositiveNumberAndZero(String value) throws ParameterException {
        String name = "testParam";
        assertDoesNotThrow(() -> {
            validator.validate(name, value);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"-10", "abc"})
    public void testValidate_NegativeNumberAndNonNumericValue(String value) {
        String name = "testParam";
        assertThrows(Exception.class, () -> {
            validator.validate(name, value);
        });
    }

}
