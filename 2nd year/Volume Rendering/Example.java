import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;


public class Example extends Application {

    /**
     * Enum for 3 given orientation of the image
     */
    private enum Orientation{
        TOP, FRONT, SIDE;
    }

    short cthead[][][]; //store the 3D volume data set
    short min, max; //min/max value in the 3D volume data set
    int CT_x_axis = 256;
    int CT_y_axis = 256;
    int CT_z_axis = 113;

    @Override
    public void start(Stage stage) throws FileNotFoundException, IOException {
        stage.setTitle("CThead Viewer");

        ReadData();

        /* Limits axis for three different views */
        int Top_width = CT_x_axis;
        int Top_height = CT_y_axis;

        int Front_width = CT_x_axis;
        int Front_height = CT_z_axis;

        int Side_width = CT_y_axis;
        int Side_height = CT_z_axis;

        //1. We create an image we can write to
        WritableImage top_image = new WritableImage(Top_width, Top_height);
        WritableImage front_image = new WritableImage(Front_width, Front_height);
        WritableImage side_image = new WritableImage(Side_width, Side_height);

        //2. We create a view of that image
        ImageView topView = new ImageView(top_image);
        ImageView frontView = new ImageView(front_image);
        ImageView sideView = new ImageView(side_image);

        //Create Sliders for every functionality
        Slider topSlider = new Slider(0, CT_z_axis - 1, 0);
        Slider frontSlider = new Slider(0, CT_y_axis - 1, 0);
        Slider sideSlider = new Slider(0, CT_x_axis - 1, 0);
        Slider opacitySlider = new Slider(0, 100, 12);

        Button volumeRenderButton = new Button("volRender");
        Button showImages = new Button("images");

        topSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {
                        changeSlice(top_image, Orientation.TOP, newValue.intValue());
                    }
                });

        frontSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {
                        changeSlice(front_image, Orientation.FRONT, newValue.intValue());
                    }
                });

        sideSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {
                        changeSlice(side_image, Orientation.SIDE, newValue.intValue());
                    }
                });

        opacitySlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {
                        NewColor.skinOpacity = (double)newValue.intValue()/100.0;
                        volumeRendering(top_image, front_image, side_image);
                    }
                });

        volumeRenderButton.setOnAction(event -> {
            NewColor.skinOpacity = 0.12;
            volumeRendering(top_image, front_image, side_image);
        });

        showImages.setOnAction(event -> {
            changeSlice(top_image, Orientation.TOP, 20);
            changeSlice(front_image, Orientation.FRONT, 20);
            changeSlice(side_image, Orientation.SIDE, 20);
        });

        FlowPane root = new FlowPane();
        root.setVgap(8);
        root.setHgap(4);

        //Cosmetics
        VBox container = new VBox();
        HBox sliders = new HBox();
        HBox images = new HBox();
        HBox buttons = new HBox();
        images.setAlignment(Pos.CENTER);
        sliders.getChildren().addAll(topSlider, frontSlider, sideSlider, opacitySlider);
        images.getChildren().addAll(topView, frontView, sideView);
        buttons.getChildren().addAll(showImages, volumeRenderButton);
        container.getChildren().addAll(images, sliders, buttons);

        //3. (referring to the 3 things we need to display an image)
        //we need to add it to the flow pane
        root.getChildren().add(container);

        Scene scene = new Scene(root, 900, 480);
        stage.setScene(scene);
        stage.show();
    }

    //Function to read in the cthead data set
    public void ReadData() throws IOException {
        //File name is hardcoded here - much nicer to have a dialog to select it and capture the size from the user
        File file = new File("src\\CThead");
        //Read the data quickly via a buffer (in C++ you can just do a single fread - I couldn't find if there is an equivalent in Java)
        DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));

        int i, j, k; //loop through the 3D data set

        min = Short.MAX_VALUE;
        max = Short.MIN_VALUE; //set to extreme values
        short read; //value read in
        int b1, b2; //data is wrong Endian (check wikipedia) for Java so we need to swap the bytes around

        cthead = new short[CT_z_axis][CT_y_axis][CT_x_axis]; //allocate the memory - note this is fixed for this data set
        //loop through the data reading it in
        for (k = 0; k < CT_z_axis; k++) {
            for (j = 0; j < CT_y_axis; j++) {
                for (i = 0; i < CT_x_axis; i++) {
                    //because the Endianess is wrong, it needs to be read byte at a time and swapped
                    b1 = ((int) in.readByte()) & 0xff; //the 0xff is because Java does not have unsigned types
                    b2 = ((int) in.readByte()) & 0xff; //the 0xff is because Java does not have unsigned types
                    read = (short) ((b2 << 8) | b1); //and swizzle the bytes around
                    if (read < min) min = read; //update the minimum
                    if (read > max) max = read; //update the maximum
                    cthead[k][j][i] = read; //put the short into memory (in C++ you can replace all this code with one fread)
                }
            }
        }
        System.out.println(min + " " + max); //diagnostic - for CThead this should be -1117, 2248
        //(i.e. there are 3366 levels of grey (we are trying to display on 256 levels of grey)
        //therefore histogram equalization would be a good thing
        //maybe put your histogram equalization code here to set up the mapping array
    }

    /**
     * Method changes slices for given view
     * @param image image to change slice
     * @param orientation orientation of the image
     * @param slice num of slice
     */
    public void changeSlice(WritableImage image, Orientation orientation, int slice){
        int w = (int) image.getWidth(), h = (int) image.getHeight();
        PixelWriter image_writer = image.getPixelWriter();

        double col;
        short datum;

        //Switch slice based on orientation and show it
        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {

                switch (orientation){
                    case TOP:
                        datum = cthead[slice][j][i];
                        break;
                    case FRONT:
                        datum = cthead[j][slice][i];
                        break;
                    case SIDE:
                        datum = cthead[j][i][slice];
                        break;
                    default:
                        datum = -1;
                }
                col = (((float) datum - (float) min) / ((float) (max - min)));
                image_writer.setColor(i, j, Color.color(col, col, col, 1.0));
            }
        }
    }

    /**
     * Method performs volume rendering on 3 different views of the ct head
     * @param topImg top down view
     * @param frontImg front view
     * @param sideImg side view
     */
    public void volumeRendering(WritableImage topImg, WritableImage frontImg, WritableImage sideImg) {
        int w1 = (int) topImg.getWidth(), h1 = (int) topImg.getHeight();
        PixelWriter topWriter = topImg.getPixelWriter();
        int w2 = (int) frontImg.getWidth(), h2 = (int) frontImg.getHeight();
        PixelWriter frontWriter = frontImg.getPixelWriter();
        int w3 = (int) sideImg.getWidth(), h3 = (int) sideImg.getHeight();
        PixelWriter sideWriter = sideImg.getPixelWriter();

        short datum;

        //Top View
        for (int j = 0; j < h1; j++) {
            for (int i = 0; i < w1; i++) {
                double transAccum = 1;
                NewColor colorAccum = new NewColor(0, 0, 0, 1);
                for (int k = 0; k < 113; k++) {
                    datum = cthead[k][j][i];
                    NewColor ctColor = lookUp(datum);
                    ctColor.multiply(transAccum * ctColor.opacity);
                    transAccum = transAccum * (1 - ctColor.opacity);
                    colorAccum.sumColors(ctColor);
                }
                double red = clampValue(colorAccum.red);
                double green = clampValue(colorAccum.green);
                double blue = clampValue(colorAccum.blue);
                topWriter.setColor(i, j, Color.color(red, green, blue, 1));
            }
        }

        //Front View
        for (int j = 0; j < h2; j++) {
            for (int i = 0; i < w2; i++) {
                double transAccum = 1;
                NewColor colorAccum = new NewColor(0, 0, 0, 1);
                for (int k = 0; k < 256; k++) {
                    datum = cthead[j][k][i];
                    NewColor ctColor = lookUp(datum);
                    ctColor.multiply(transAccum * ctColor.opacity);
                    transAccum = transAccum * (1 - ctColor.opacity);
                    colorAccum.sumColors(ctColor);
                }
                double red = clampValue(colorAccum.red);
                double green = clampValue(colorAccum.green);
                double blue = clampValue(colorAccum.blue);
                frontWriter.setColor(i, j, Color.color(red, green, blue, 1));
            }
        }

        //Side view
        for (int j = 0; j < h3; j++) {
            for (int i = 0; i < w3; i++) {
                double transAccum = 1;
                NewColor colorAccum = new NewColor(0, 0, 0, 1);
                for (int k = 0; k < 256; k++) {
                    datum = cthead[j][i][k];
                    NewColor ctColor = lookUp(datum);
                    ctColor.multiply(transAccum * ctColor.opacity);
                    transAccum = transAccum * (1 - ctColor.opacity);
                    colorAccum.sumColors(ctColor);
                }
                double red = clampValue(colorAccum.red);
                double green = clampValue(colorAccum.green);
                double blue = clampValue(colorAccum.blue);
                sideWriter.setColor(i, j, Color.color(red, green, blue, 1));
            }
        }
    }

    /**
     * Method clamps value between 0 - 1
     * @param val value to be clamped
     * @return value from 0 - 1
     */
    private double clampValue(double val) {
        if (val > 1) {
            return 1;
        } else if (val < 0) {
            return 0;
        } else {
            return val;
        }
    }

    /**
     * Create color with values based on CThead value
     * @param CTValue CThead value
     * @return NewColor with predefined values
     */
    private NewColor lookUp(int CTValue) {
        return new NewColor(CTValue);
    }

    public static void main(String[] args) {
        launch();
    }

}