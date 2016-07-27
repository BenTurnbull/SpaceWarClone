package dungeon;

import dungeon.game.Game;
import dungeon.game.SolarSystem;
import dungeon.loop.FixedSteps;
import dungeon.loop.GameLoop;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.io.IOException;
import java.util.function.Consumer;

public class Main extends Application {

    public static final float SCALE = 100f; /* pixels per meter */
    public static final float WIDTH = 12f; /* 12m */
    public static final float HEIGHT = 8f; /* 8m */

    private static final int velocityIterations = 5;
    private static final int positionIterations = 3;

    @Override
    public void start(Stage stage) throws IOException {

        final AnchorPane root = new AnchorPane();
        final Label fpsLabel = new Label();

        AnchorPane.setTopAnchor(fpsLabel, 10.0);
        AnchorPane.setRightAnchor(fpsLabel, 10.0);
        root.getChildren().addAll(fpsLabel);

        final Game game = new SolarSystem();
        final World world = new World(new Vec2(0f, 0f));
        Runnable renderer = game::updatePositions;
        Consumer<Float> updater = secondsElapsed -> world.step(secondsElapsed, velocityIterations, positionIterations);
        Consumer<Integer> fpsReporter = fps -> fpsLabel.setText(String.format("FPS: %d", fps));

        Pane gamePane = new Pane();
        game.load(world, gamePane);
        root.getChildren().add(0, gamePane); /* Add the gamePane at the bottom, underneath the controls. */

        GameLoop gameLoop = new FixedSteps(updater, renderer, fpsReporter);
        gameLoop.start();

        Scene scene = new Scene(root, WIDTH * SCALE, HEIGHT * SCALE);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, game);

        stage.setScene(scene);
        stage.setTitle("Solar system");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String... args) {
        Application.launch(Main.class, args);
    }
}
