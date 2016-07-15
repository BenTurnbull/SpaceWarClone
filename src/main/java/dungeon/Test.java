package dungeon;

import com.google.common.eventbus.EventBus;
import dungeon.events.EventLoop;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Test extends Application {
    private static final int NUMBER_OF_BLOCKS = 10;
    private static final int BLOCKSIZE = 40;
    
    private static final int SPACING = 2;
    private static final int WIDTH = (SPACING + BLOCKSIZE) * NUMBER_OF_BLOCKS;
    private static final int HEIGHT = (SPACING + BLOCKSIZE) * NUMBER_OF_BLOCKS;

    public static void main(String[] args) {
        // backend
        final EventBus eventBus = new EventBus();
        final DungeonModel dungeonModel = new DungeonModel(eventBus, NUMBER_OF_BLOCKS);
        final Thread eventThread = new Thread(new EventLoop(eventBus));
        eventThread.start();

        // frontend
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Group group = new Group();
        for (int iy = 0; iy < NUMBER_OF_BLOCKS; iy++) {
            for (int ix = 0; ix < NUMBER_OF_BLOCKS; ix++) {

                final Rectangle rectangle = new Rectangle(BLOCKSIZE, BLOCKSIZE);
                rectangle.setTranslateX(ix*(BLOCKSIZE + SPACING));
                rectangle.setTranslateY(iy*(SPACING + BLOCKSIZE));
                rectangle.setFill(Color.SANDYBROWN);
                rectangle.setOnMouseEntered(e -> {
                    rectangle.setStroke(Color.DARKGRAY);
                    rectangle.setStrokeWidth(3);
                });
                rectangle.setOnMouseClicked(e -> rectangle.setFill(Color.DARKGRAY));
                rectangle.setOnMouseExited(e -> rectangle.setStrokeWidth(0));
                group.getChildren().add(rectangle);
            }
        }

        final Scene scene = new Scene(group, WIDTH, HEIGHT);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
