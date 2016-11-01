package dungeon.game;

import dungeon.Util;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.jbox2d.common.Vec2;

import static dungeon.Main.SCALE;

class ShipDirection extends Line {

    private final Ship ship;

    ShipDirection(Ship ship) {
        this.ship = ship;
        setFill(Color.CORAL);
        updatePosition();
    }

    void updatePosition() {
        Vec2 position = ship.getBody().getPosition();
        setStartX(position.x * SCALE);
        setStartY(position.y * SCALE);

            /*
            for a circle with origin (j, k) and radius r:
            x(t) = r cos(t) + j       y(t) = r sin(t) + k
            */
        float angle = ship.getBody().getAngle() * Util.radToDeg;
        setEndX((0.2 * Math.cos(angle) + position.x) * SCALE);
        setEndY((0.2 * Math.sin(angle) + position.y) * SCALE);
    }
}
