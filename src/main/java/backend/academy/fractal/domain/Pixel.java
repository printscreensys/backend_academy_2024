package backend.academy.fractal.domain;

import java.awt.Color;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Pixel {
    private Integer x;
    private Integer y;
    private int hitCount = 0;
    private Color color = Color.BLACK;

    public void hit() {
        hitCount++;
    }

    public void transform(Consumer<Pixel> function) {
        function.accept(this);
    }
}
