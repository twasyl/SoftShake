/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.softshake.twasyl.demo.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author twasyl
 */
public class Demo extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        final Parent root = FXMLLoader.load(Demo.class.getResource("/ch/softshake/twasyl/demo/resources/fxml/Demo.fxml"));

        final Scene scene = new Scene(root);

        stage.setTitle("Demo SoftShake");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}