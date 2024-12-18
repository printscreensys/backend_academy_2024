package backend.academy.fractal.cli;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.SneakyThrows;

public class PathValidator implements IParameterValidator {
    @Override
    @SneakyThrows
    public void validate(String name, String value) throws ParameterException {
        String pathStr = value;
        try {
            Paths.get(pathStr);
        } catch (Exception e) {
            throw new ParameterException("Введен невалидный путь");
        }

        var path = Paths.get(pathStr).toAbsolutePath().normalize();
        var parentDir = new File(path.getParent().toString());
        var fileNamePath = path.getFileName();

        if (fileNamePath == null || !fileNamePath.toString().matches(".*\\.png|.*\\.jpg|.*\\.bmp")) {
            throw new ParameterException("Путь не содержит имени файла или имеет неподходящее расширение");
        }

        if (!parentDir.exists() && !parentDir.mkdirs()) {
            throw new ParameterException("Не удалось создать родительскую директорию");
        }
        Files.deleteIfExists(path);
        if (Files.notExists(path)) {
            Files.createFile(path);
        }
    }
}
