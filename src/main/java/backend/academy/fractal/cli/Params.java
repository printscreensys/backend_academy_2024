package backend.academy.fractal.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import java.awt.Color;
import backend.academy.fractal.variation.Variations;
import backend.academy.fractal.variation.Variation;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
@Parameters
@SuppressWarnings({"MagicNumber", "ImportOrder"})
public class Params {
    @Parameter(
        names = {"-p", "-path"},
        description = "Путь для сохранения изображения. Допустимые форматы '.png', '.jpeg', '.bmp'",
        required = true,
        order = 0,
        validateWith = PathValidator.class
    )
    private String path;

    @Parameter(
        names = {"-nt", "-nThreads"},
        description = "Количество потоков",
        order = 1,
        validateWith = PositiveNumberValidator.class
    )
    private int nThreads = 1;

    @Parameter(
        names = {"-w", "-width"},
        description = "Ширина изображения в пикелях",
        order = 2,
        validateWith = PositiveNumberValidator.class
    )
    private int width = 1920;

    @Parameter(
        names = {"-h", "-height"},
        description = "Высота изображения в пикелях",
        order = 3,
        validateWith = PositiveNumberValidator.class
    )
    private int height = 1080;

    @Parameter(
        names = {"-xmin", "-xMin"},
        description = "Левый край рабочей области",
        order = 4
    )
    private double xMin = -1.777;

    @Parameter(
        names = {"-xmax", "-xMax"},
        description = "Правый край рабочей области",
        order = 5
    )
    private double xMax = 1.777;

    @Parameter(
        names = {"-ymin", "-yMin"},
        description = "Нижний край рабочей области",
        order = 6
    )
    private double yMin = -1;

    @Parameter(
        names = {"-ymax", "-yMax"},
        description = "Верхний край рабочей области",
        order = 7
    )
    private double yMax = 1;

    @Parameter(
        names = {"-nv", "-numVectors"},
        description = "Количество векторов со случайными коэффициентами a, b, c, d, e, f",
        order = 8,
        validateWith = PositiveNumberValidator.class
    )
    private int numVectors = 16;

    @Parameter(
        names = {"-s", "-samples"},
        description = "Количество сэмплов",
        order = 9,
        validateWith = PositiveNumberValidator.class
    )
    private int samples = 20_000;

    @Parameter(
        names = {"-i", "-iterations"},
        description = "Количество итераций на сэмпл",
        order = 10,
        validateWith = PositiveNumberValidator.class
    )
    private int iterations = 1_000;

    @Parameter(
        names = {"-gamma"},
        description = "Коэффициент гамма-коррекции",
        order = 11
    )
    private double gamma = 2.2;

    @Parameter(
        names = {"-sf", "-samplingFactor"},
        description = "Множитель для суперсэмплинга",
        order = 12,
        validateWith = PositiveNumberValidator.class
    )
    private int samplingFactor = 1;

    @Parameter(
        names = {"-sym", "-symmetry"},
        description = "Количество поворотов вокруг своей оси",
        order = 13,
        validateWith = PositiveNumberValidator.class
    )
    private int symmetry = 1;

    @Parameter(
        names = {"-c", "-colors"},
        description = """
            Не более <nv> hex-кодов (с решеткой) используемых цветов через пробел
            Например: -с #abcdef #123456""",
        order = 14,
        variableArity = true,
        listConverter = ColorConverter.class,
        defaultValueDescription = "Используется <nv> случайных цветов"
    )
    private List<Color> colors = new ArrayList<>();

    @Parameter(
        names = {"-bc", "-background"},
        description = "Единственный hex-код цвета фона",
        order = 15,
        defaultValueDescription = "#000000",
        converter = ColorConverter.class
    )
    private Color backgroundColor = Color.BLACK;

    @Parameter(
        names = {"-v", "-variations"},
        description = """
            Названия вариаций через пробел
                    Допустимые значения
                    'linear'
                    'sinusoidal'
                    'spherical'
                    'swirl'
                    'horseshoe'
                    'spiral'
                    'blur'
                    'gaussian'
            """,
        listConverter = VariationConverter.class,
        variableArity = true,
        order = 16
    )
    private List<Variation> variations = List.of(Variations::linear);

    @Parameter(names = {"--help", "--h"}, description = "Помощь", help = true)
    private boolean help;
}
