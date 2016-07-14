package dungeon;

import java.util.Random;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author anton
 */
public class Test extends Application {

    private int width = 800;
    private int height = 600;
    private int blockSize = 20;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Random random = new Random();
        final Group group = new Group();
        final int spacing = 1;
        int numberOfBlocks = height / blockSize;
        for (int i = 0; i < numberOfBlocks; i++) {
            for (int j = 0; j < numberOfBlocks; j++) {

                final Rectangle rectangle = new Rectangle(blockSize, blockSize);
                rectangle.setTranslateX((i * blockSize ) + spacing );
                rectangle.setTranslateY(spacing + (j * blockSize));
                int r = random.nextInt(255),
                    g = random.nextInt(255),
                    b = random.nextInt(255);
                System.out.format("(%d, %d, %d)%n", r, g, b);
                rectangle.setFill(Color.rgb(r,g,b));
                rectangle.setOnMouseEntered(e -> {
                    System.out.println("Entered " + e.getX());
                    rectangle.setStroke(Color.AQUAMARINE);
                    rectangle.setStrokeWidth(3);
                });
                rectangle.setOnMouseExited(e -> rectangle.setStrokeWidth(0));
                group.getChildren().add(rectangle);
            }
        }

        final Scene scene = new Scene(group, width, height);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
