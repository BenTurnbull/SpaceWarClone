package dungeon.loop;

import javafx.animation.AnimationTimer;

/*
 * Abstract superclass for all GameLoop subclasses. Holds the common maximumStep property.
 */
public abstract class GameLoop extends AnimationTimer
{
    float getMaximumStep()
    {
        return Float.MAX_VALUE;
    }
}
