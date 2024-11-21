package backend.academy.functions;

import backend.academy.domain.FractalImage;

@FunctionalInterface
public
interface ImageProcessor {
    void process(FractalImage image);
}
