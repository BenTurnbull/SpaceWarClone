package dungeon.game;

import dungeon.Util;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.util.Collection;

import static dungeon.Main.SCALE;

class Ship extends Circle implements Debris, Player {

    private final static float turnRate = 0.1f;

    private final Body body;
    private final float mass;

    private float angle = 180;

    Ship(World world, Vec2 position) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = position;
        bodyDef.angle = angle * Util.degToRad;

        float radius = 0.2f;
        Shape shape = new CircleShape();
        shape.m_radius = radius;

        float density = 1f;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.restitution = 0f;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(this);

        mass = density * radius;

        setRadius(radius * SCALE);
        setFill(Color.BLUEVIOLET);
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public float getMass() {
        return mass;
    }

    public float getAngle() {
        return angle;
    }

    @Override
    public void updatePosition(Collection<Debris> debris) {
        updateForce(debris);
        updateSpaceWrap();

        setTranslateX(body.getPosition().x * SCALE);
        setTranslateY(body.getPosition().y * SCALE);
        float angle = (body.getAngle() * Util.radToDeg) % 360;
        setRotate(angle);
    }

    @Override
    public void handle(KeyEvent event) {

        if (event.getCode().equals(KeyCode.LEFT)) {
            body.setTransform(body.getPosition(), body.getAngle() + turnRate);
            event.consume();
        }
        else if (event.getCode().equals(KeyCode.RIGHT)) {
            body.setTransform(body.getPosition(), body.getAngle() - turnRate);
            event.consume();
        }
        else if (event.getCode().equals(KeyCode.UP)) {
            Vec2 force = computeForce(body);
            body.applyForceToCenter(force);
            event.consume();
        }
        else if (event.getCode().equals(KeyCode.DOWN)) {
            Vec2 force = computeForce(body);
            body.applyForceToCenter(force.negate());
            event.consume();
        }
    }

    private Vec2 computeForce(Body body) {
        final float angle = (body.getAngle() * Util.radToDeg) % 360;
        final double sinRatio = Math.sin(angle); // opp
        final double cosRatio = Math.cos(angle); // adj

        //int x = angle < 180 ? 1 : -1;
        return new Vec2((float) cosRatio, (float) sinRatio);
    }
}
