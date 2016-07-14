    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.bc.digdig;

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
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Random random = new Random();
        final Group group = new Group();
        final int spacing = 1;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                final Rectangle rectangle = new Rectangle(20,20);
                rectangle.setTranslateX((i * 20 ) + spacing );
                rectangle.setTranslateY(spacing + (j * 20));
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
                rectangle.setOnMouseExited(e -> {
                    rectangle.setStrokeWidth(0);
                });
                group.getChildren().add(rectangle);
                
            }
        }
        
        final Scene scene = new Scene(group, 800, 600);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
