package backend.academy.fractal.cli;

import com.beust.jcommander.ParameterException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class PathValidatorTest {
    private final PathValidator validator = new PathValidator();

    @Test
    @SneakyThrows
    public void testValidate_givenValidPath_doesNotThrowException(@TempDir Path tempDir) {
        String name = "testParam";
        String value = tempDir.resolve("test.png").toString();
        validator.validate(name, value);
        assertTrue(Files.exists(Path.of(value)));
    }

    @Test
    public void testValidate_givenInvalidPath_throwsParameterException() {
        String name = "testParam";
        String value = "invalid\\path";
        assertThrows(ParameterException.class, () -> {
            validator.validate(name, value);
        });
    }

    @Test
    public void testValidate_givenPathWithoutFileName_throwsParameterException() {
        String name = "testParam";
        String value = "/path/to/directory/";
        assertThrows(ParameterException.class, () -> {
            validator.validate(name, value);
        });
    }

    @Test
    public void testValidate_givenPathWithInvalidExtension_throwsParameterException() {
        String name = "testParam";
        String value = "/path/to/file.txt";
        assertThrows(ParameterException.class, () -> {
            validator.validate(name, value);
        });
    }

    @Test
    @SneakyThrows
    public void testValidate_givenPathWithNonexistentParentDirectory_createsParentDirectory(@TempDir Path tempDir){
        String name = "testParam";
        String value = tempDir.resolve("nonexistent/directory/test.png").toString();
        validator.validate(name, value);
        assertTrue(new File(tempDir.resolve("nonexistent/directory").toString()).exists());
    }
}
