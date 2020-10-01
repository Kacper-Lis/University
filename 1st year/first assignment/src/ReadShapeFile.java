
/**
 * This class reads a shape file.  For the format of this shape file, see the assignment description.
 * Also, please see the shape files ExampleShapes.txt, ExampleShapesStill.txt, and TwoRedCircles.txt
 *
 * @author you
 */

import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadShapeFile {

    /**
     * delimiter which describes how the input is separated in a file
     */
    private static String delimiter = " ";

    /**
     * Reads the data file used by the program and returns the constructed queue
     *
     * @param in the scanner of the file
     * @return the queue represented by the data file
     */
    private static Queue<ClosedShape> readDataFile(Scanner in) {
        Queue<ClosedShape> shapeQueue = new Queue<ClosedShape>();

        ArrayList<String[]> dataList = new ArrayList<>();
        while (in.hasNextLine()) {
            dataList.add(lineSplit(in.nextLine()));
        }

        for (int i = 0; i < dataList.size(); i++) {
            shapeQueue.enqueue(createShape(dataList.get(i)));
        }

        in.close();
        return shapeQueue;
    }


    /**
     * Method to read the file and return a queue of shapes from this file. The
     * program should handle the file not found exception here and shut down the
     * program gracefully.
     *
     * @param filename the name of the file
     * @return the queue of shapes from the file
     */
    public static Queue<ClosedShape> readDataFile(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner in = new Scanner(file);

        return ReadShapeFile.readDataFile(in);
    }

    /**
     * Method splits line of data into individual tokens and returns them as an array of strings
     *
     * @param input string to split
     * @return array of individual tokens
     */
    private static String[] lineSplit(String input) {
        String[] tokens = input.split(delimiter);
        return tokens;
    }

    /**
     * Method takes a line from an input file and chooses which shape needs to be created and creates it
     *
     * @param input line of input
     * @return new closed shape
     */
    private static ClosedShape createShape(String[] input) {
        String shapeName = input[0];

        switch (shapeName) {
            case "oval":
                return createOval(input);
            case "circle":
                return createCircle(input);
            case "square":
                return createSquare(input);
            case "rect":
                return createRect(input);
            case "hexagon":
                return createHexagon(input);
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Method creates oval
     *
     * @param input line of input describing oval
     * @return new oval
     */
    private static Oval createOval(String[] input) {
        int px = Integer.parseInt(input[1]);
        int py = Integer.parseInt(input[2]);
        int vx = Integer.parseInt(input[3]);
        int vy = Integer.parseInt(input[4]);
        boolean isFilled = Boolean.parseBoolean(input[5]);
        int width = Integer.parseInt(input[6]);
        int height = Integer.parseInt(input[7]);
        float r = (float) Integer.parseInt(input[8]) / 255.0f;
        float g = (float) Integer.parseInt(input[9]) / 255.0f;
        float b = (float) Integer.parseInt(input[10]) / 255.0f;
        int insertionTime = Integer.parseInt(input[11]);
        boolean isFlashing = Boolean.parseBoolean(input[12]);
        float r1 = (float) Integer.parseInt(input[13]) / 255.0f;
        float g1 = (float) Integer.parseInt(input[14]) / 255.0f;
        float b1 = (float) Integer.parseInt(input[15]) / 255.0f;
        return new Oval(insertionTime, px, py, vx, vy, width, height, new Color(r, g, b, 1), isFilled,
                isFlashing, new Color(r1, g1, b1, 1));
    }

    /**
     * Method creates a circle
     *
     * @param input line of input describing circle
     * @return new circle
     */
    private static Circle createCircle(String[] input) {
        int px = Integer.parseInt(input[1]);
        int py = Integer.parseInt(input[2]);
        int vx = Integer.parseInt(input[3]);
        int vy = Integer.parseInt(input[4]);
        boolean isFilled = Boolean.parseBoolean(input[5]);
        int diameter = Integer.parseInt(input[6]);
        float r = (float) Integer.parseInt(input[7]) / 255.0f;
        float g = (float) Integer.parseInt(input[8]) / 255.0f;
        float b = (float) Integer.parseInt(input[9]) / 255.0f;
        int insertionTime = Integer.parseInt(input[10]);
        boolean isFlashing = Boolean.parseBoolean(input[11]);
        float r1 = (float) Integer.parseInt(input[12]) / 255.0f;
        float g1 = (float) Integer.parseInt(input[13]) / 255.0f;
        float b1 = (float) Integer.parseInt(input[14]) / 255.0f;
        return new Circle(insertionTime, px, py, vx, vy, diameter, new Color(r, g, b, 1), isFilled,
                isFlashing, new Color(r1, g1, b1, 1));
    }

    /**
     * Method creates a square
     *
     * @param input line of input describing square
     * @return new square
     */
    private static Square createSquare(String[] input) {
        int px = Integer.parseInt(input[1]);
        int py = Integer.parseInt(input[2]);
        int vx = Integer.parseInt(input[3]);
        int vy = Integer.parseInt(input[4]);
        boolean isFilled = Boolean.parseBoolean(input[5]);
        int side = Integer.parseInt(input[6]);
        float r = (float) Integer.parseInt(input[7]) / 255.0f;
        float g = (float) Integer.parseInt(input[8]) / 255.0f;
        float b = (float) Integer.parseInt(input[9]) / 255.0f;
        int insertionTime = Integer.parseInt(input[10]);
        boolean isFlashing = Boolean.parseBoolean(input[11]);
        float r1 = (float) Integer.parseInt(input[12]) / 255.0f;
        float g1 = (float) Integer.parseInt(input[13]) / 255.0f;
        float b1 = (float) Integer.parseInt(input[14]) / 255.0f;
        return new Square(insertionTime, px, py, vx, vy, side, new Color(r, g, b, 1), isFilled,
                isFlashing, new Color(r1, g1, b1, 1));
    }

    /**
     * Method creates new hexagon
     *
     * @param input line of input describing hexagon
     * @return new hexagon
     */
    private static Hexagon createHexagon(String[] input) {
        int px = Integer.parseInt(input[1]);
        int py = Integer.parseInt(input[2]);
        int vx = Integer.parseInt(input[3]);
        int vy = Integer.parseInt(input[4]);
        boolean isFilled = Boolean.parseBoolean(input[5]);
        int side = Integer.parseInt(input[6]);
        float r = (float) Integer.parseInt(input[7]) / 255.0f;
        float g = (float) Integer.parseInt(input[8]) / 255.0f;
        float b = (float) Integer.parseInt(input[9]) / 255.0f;
        int insertionTime = Integer.parseInt(input[10]);
        boolean isFlashing = Boolean.parseBoolean(input[11]);
        float r1 = (float) Integer.parseInt(input[12]) / 255.0f;
        float g1 = (float) Integer.parseInt(input[13]) / 255.0f;
        float b1 = (float) Integer.parseInt(input[14]) / 255.0f;
        return new Hexagon(insertionTime, px, py, vx, vy, side, new Color(r, g, b, 1), isFilled,
                isFlashing, new Color(r1, g1, b1, 1));
    }

    /**
     * Method creates a rectangle
     *
     * @param input line of input describing rectangle
     * @return new rectangle
     */
    private static Rect createRect(String[] input) {
        int px = Integer.parseInt(input[1]);
        int py = Integer.parseInt(input[2]);
        int vx = Integer.parseInt(input[3]);
        int vy = Integer.parseInt(input[4]);
        boolean isFilled = Boolean.parseBoolean(input[5]);
        int width = Integer.parseInt(input[6]);
        int height = Integer.parseInt(input[7]);
        float r = (float) Integer.parseInt(input[8]) / 255.0f;
        float g = (float) Integer.parseInt(input[9]) / 255.0f;
        float b = (float) Integer.parseInt(input[10]) / 255.0f;
        int insertionTime = Integer.parseInt(input[11]);
        boolean isFlashing = Boolean.parseBoolean(input[12]);
        float r1 = (float) Integer.parseInt(input[13]) / 255.0f;
        float g1 = (float) Integer.parseInt(input[14]) / 255.0f;
        float b1 = (float) Integer.parseInt(input[15]) / 255.0f;
        return new Rect(insertionTime, px, py, vx, vy, width, height, new Color(r, g, b, 1), isFilled,
                isFlashing, new Color(r1, g1, b1, 1));
    }
}
