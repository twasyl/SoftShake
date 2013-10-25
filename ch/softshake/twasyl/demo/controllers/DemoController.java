package ch.softshake.twasyl.demo.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class DemoController implements Initializable {

    @FXML
    private Slider redSlider;
    @FXML
    private Slider greenSlider;
    @FXML
    private Slider blueSlider;
    @FXML
    private TextField fileLocationTF;
    @FXML
    private ImageView userImage;
    private ObjectProperty<Image> originalImage = new SimpleObjectProperty<>();
    private final StringProperty imageUrl = new SimpleStringProperty();

    @FXML
    public void browseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.png", "*.jpeg", "*.gif"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            this.fileLocationTF.setText(file.getAbsolutePath());
        }
    }

    @FXML
    public void loadImage(ActionEvent event) {
        if (this.imageUrl.isNotNull().get() && this.imageUrl.isNotEqualTo("").get()) {
            this.originalImage.set(new Image("file://".concat(this.imageUrl.get())));
        }
    }

    @FXML
    public void reset(ActionEvent event) {
        this.redSlider.setValue(0);
        this.greenSlider.setValue(0);
        this.blueSlider.setValue(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.imageUrl.bind(this.fileLocationTF.textProperty());

        this.originalImage.addListener(new ChangeListener<Image>() {
            @Override
            public void changed(ObservableValue<? extends Image> observableValue, Image image, Image image2) {
                DemoController.this.paintImage();
            }
        });

        final ChangeListener<Number> sliderValueChangeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number aDouble, Number aDouble2) {
                DemoController.this.paintImage();
            }
        };

        this.redSlider.valueProperty().addListener(sliderValueChangeListener);
        this.greenSlider.valueProperty().addListener(sliderValueChangeListener);
        this.blueSlider.valueProperty().addListener(sliderValueChangeListener);
    }

    private void paintImage() {
        if (this.originalImage.get() != null) {
            WritableImage wImage = new WritableImage(
                    (int) this.originalImage.get().getWidth(),
                    (int) this.originalImage.get().getHeight());

            final PixelReader pReader = this.originalImage.get().getPixelReader();
            final PixelWriter pWriter = wImage.getPixelWriter();
            Color pixelColor;
            double redColorComponent, greenColorComponent, blueColorComponent;

            for (int y = 0; y < (int) this.originalImage.get().getHeight(); y++) {
                for (int x = 0; x < (int) this.originalImage.get().getWidth(); x++) {

                    pixelColor = pReader.getColor(x, y);

                    if (pixelColor.getOpacity() != 0) {
                        redColorComponent = pixelColor.getRed() + this.redSlider.getValue();
                        if (redColorComponent > 1) {
                            redColorComponent = 1;
                        }

                        greenColorComponent = pixelColor.getGreen() + this.greenSlider.getValue();
                        if (greenColorComponent > 1) {
                            greenColorComponent = 1;
                        }

                        blueColorComponent = pixelColor.getBlue() + this.blueSlider.getValue();
                        if (blueColorComponent > 1) {
                            blueColorComponent = 1;
                        }

                        pWriter.setColor(x, y, Color.color(redColorComponent, greenColorComponent, blueColorComponent));
                    }
                }
            }

            this.userImage.setImage(wImage);
        } else {
            this.userImage.setImage(null);
        }
    }
}
