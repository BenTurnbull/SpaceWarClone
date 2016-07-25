package dungeon.game;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;

import java.util.Collection;

import static dungeon.Main.SCALE;

class BlackHole extends Circle implements Debris {

    private final Body body;
    private final float mass;

    BlackHole(World world, Vec2 position) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;
        bodyDef.position = position;

        float radius = 0.7f;
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
        setFill(Color.LIGHTGOLDENRODYELLOW);

        setTranslateX(body.getPosition().x * SCALE);
        setTranslateY(body.getPosition().y * SCALE);
    }

    @Override
    public void updatePosition(Collection<Debris> debris) {
        // do nothing
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public float getMass() {
        return mass;
    }
}
