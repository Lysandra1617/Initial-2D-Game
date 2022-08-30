package Markers;

import java.io.Serial;
import java.io.Serializable;

public interface Respond extends Serializable {
    default void mouseClick() {}
    default void mouseMove() {}
    default void mouseDrag() {}
    default void mousePress() {}
    default void mouseRelease(){}
    default void screenResize() {}
    default void keyPress() {}
    default void keyRelease() {}
    default void keyType() {}
    default void update() {}
}
