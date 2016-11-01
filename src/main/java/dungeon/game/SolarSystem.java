package dungeon.game;

import javafx.geometry.Insets;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import java.util.ArrayList;
import java.util.List;

import static dungeon.Main.*;

public class SolarSystem implements Game, ContactListener {

    private final static int satellites = 5;

    private final List<Player> players = new ArrayList<>();
    private final List<Debris> debris = new ArrayList<>();
    private final List<Debris> markedForDestruction = new ArrayList<>(); // Cannot destroy bodies during world-step/collision-callback
    private World world;
    private Pane pane;
    private ShipDirection shipDirection;

    @Override
    public void load(World world, Pane pane) {
        this.world = world;
        this.pane = pane;

        pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setMinSize(WIDTH * SCALE, HEIGHT * SCALE);

        world.setContactListener(this);

        debris.clear();

        BlackHole blackHole1 = new BlackHole(world, new Vec2(WIDTH / 2, HEIGHT / 2));
        debris.add(blackHole1);
        pane.getChildren().add(blackHole1);

        Ship ship = new Ship(world, new Vec2(WIDTH / 8, HEIGHT / 8));
        debris.add(ship);
        players.add(ship);
        pane.getChildren().add(ship);

        shipDirection = new ShipDirection(ship);
        pane.getChildren().add(shipDirection);

        for (int i = 0; i < satellites; i++) {
            Planet planet = new Planet(world);
            debris.add(planet);
            pane.getChildren().add(planet);
        }

    }

    @Override
    public void updatePositions() {
        checkForDestruction();
        debris.forEach(debris -> debris.updatePosition(this.debris));
        shipDirection.updatePosition();
    }

    @Override
    public void handle(KeyEvent event) {
        players.forEach(player -> player.handle(event));
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fA = contact.getFixtureA();
        Fixture fB = contact.getFixtureB();

        if (fA == null || fB == null) return;
        if (fA.getUserData() == null || fB.getUserData() == null) return;

        if (fA.getUserData() instanceof BlackHole) {
            markedForDestruction.add((Debris) fB.getUserData());
        }
        if (fB.getUserData() instanceof BlackHole) {
            markedForDestruction.add((Debris) fA.getUserData());
        }
    }

    private synchronized void checkForDestruction() {
        if (markedForDestruction.isEmpty()) return;
        markedForDestruction.forEach(marked -> {
                    debris.remove(marked);
                    world.destroyBody(marked.getBody());
                    pane.getChildren().remove(marked);
                }
        );
        markedForDestruction.clear();
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold manifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {}
}