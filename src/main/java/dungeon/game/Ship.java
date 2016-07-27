package dungeon.game;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.util.Collection;

import static dungeon.Main.SCALE;

class Ship extends Path implements Debris, Player {

    private final Body body;
    private final float mass;

    Ship(World world, Vec2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = position;

        float radius = 0.3f;  /* 10cm */
        Shape shape = new CircleShape();
        shape.m_radius = radius;
        //shape.set(new Vec2[]{ new Vec2(0f, 0f), new Vec2(0.5f, 1f), new Vec2(1f, 0f)}, 3);

        float density = 1f;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.restitution = 0f;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(this);
        //body.applyLinearImpulse(new Vec2(1, 1), new Vec2());

        mass = density * radius;

        drawSemiRing(position.x, position.y, radius * SCALE, (radius / 2) * SCALE, Color.LIGHTGREEN, Color.DARKGREEN);
    }

    private void drawSemiRing(double centerX, double centerY, double radius, double innerRadius, Color bgColor, Color strkColor) {
        setFill(bgColor);
        setStroke(strkColor);
        setFillRule(FillRule.EVEN_ODD);

        MoveTo moveTo = new MoveTo();
        moveTo.setX(centerX + innerRadius);
        moveTo.setY(centerY);

        ArcTo arcToInner = new ArcTo();
        arcToInner.setX(centerX - innerRadius);
        arcToInner.setY(centerY);
        arcToInner.setRadiusX(innerRadius);
        arcToInner.setRadiusY(innerRadius);

        MoveTo moveTo2 = new MoveTo();
        moveTo2.setX(centerX + innerRadius);
        moveTo2.setY(centerY);

        HLineTo hLineToRightLeg = new HLineTo();
        hLineToRightLeg.setX(centerX + radius);

        ArcTo arcTo = new ArcTo();
        arcTo.setX(centerX - radius);
        arcTo.setY(centerY);
        arcTo.setRadiusX(radius);
        arcTo.setRadiusY(radius);

        HLineTo hLineToLeftLeg = new HLineTo();
        hLineToLeftLeg.setX(centerX - innerRadius);

        getElements().add(moveTo);
        getElements().add(arcToInner);
        getElements().add(moveTo2);
        getElements().add(hLineToRightLeg);
        getElements().add(arcTo);
        getElements().add(hLineToLeftLeg);
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
        updateSpaceWrap();

        setTranslateX(body.getPosition().x * SCALE);
        setTranslateY(body.getPosition().y * SCALE);
    }

    @Override
    public void handle(KeyEvent event) {

        if (event.getCode().equals(KeyCode.LEFT)) {
            //body.setTransform(body.getPosition(), );
            this.setRotate(this.getRotate() + 2);
            event.consume();
        }
        else if (event.getCode().equals(KeyCode.RIGHT)) {
            this.setRotate(this.getRotate() - 2);
            event.consume();
        }
        else if (event.getCode().equals(KeyCode.UP)) {
            body.setLinearVelocity(body.getLinearVelocity().addLocal(1, 1));
            event.consume();
        }
    }
}
