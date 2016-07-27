package dungeon.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.util.Collection;
import java.util.Random;

import static dungeon.Main.HEIGHT;
import static dungeon.Main.SCALE;
import static dungeon.Main.WIDTH;

class Planet extends Circle implements Debris {

    private static final Random random = new Random();
    private final Body body;
    private final float mass;

    Planet(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;

        float x = random.nextFloat() * WIDTH;
        float y = random.nextFloat() * HEIGHT;
        bodyDef.position = new Vec2(x, y);

        float radius = 0.2f;  /* 10cm */
        Shape shape = new CircleShape();
        shape.m_radius = radius;

        float density = 1f;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.restitution = 0f;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(this);
        body.applyLinearImpulse(new Vec2(random.nextFloat(), random.nextFloat()), new Vec2());

        mass = density * radius;

        setRadius(radius * SCALE);
        setFill(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public float getMass() {
        return mass;
    }

    @Override
    public void updatePosition(Collection<Debris> debris) {
        updateForce(debris);
        updatePosition();

        setTranslateX(body.getPosition().x * SCALE);
        setTranslateY(body.getPosition().y * SCALE);
    }
}
