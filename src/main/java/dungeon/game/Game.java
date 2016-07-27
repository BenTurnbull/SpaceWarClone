package dungeon.game;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.jbox2d.dynamics.World;

public interface Game extends EventHandler<KeyEvent> {
    /*
     * Load the game into the given world.
     */
    void load(World world, Pane pane);
    
    /*
     * Updates the position of all the nodes in the game based on their physics state.
     */
    void updatePositions();

    @Override
    void handle(KeyEvent event);
}
