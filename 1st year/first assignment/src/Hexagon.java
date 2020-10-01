import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Hexagon is a hexagon shape that can be drawn to
 * to the screen, either filled with colour or opaque.
 * Its position is determined by the upper left corner of the
 * hexagon bounding rectangle.
 */
public class Hexagon extends ClosedShape {

    /**
     * Length of the side of hexagon
     */
    private int side;

    /**
     * Coordinates of the points of hexagon
     */
    private double[] coordinatesX, coordinatesY;

    /**
     * Creates a hexagon.
     *
     * @param x           The display component's x position.
     * @param y           The display component's y position.
     * @param vx          The display component's x velocity.
     * @param vy          The display component's y velocity.
     * @param side        The side of the hexagon.
     * @param colour      The line colour or fill colour.
     * @param isFilled    True if the hexagon is filled with colour, false if opaque.
     * @param isFlashing  True if the hexagon is flashing
     * @param secondColor color to switch between if isFlashing
     */
    public Hexagon(int insertionTime, int x, int y, int vx, int vy, int side, Color colour, boolean isFilled,
                   boolean isFlashing, Color secondColor) {
        super(insertionTime, x, y, vx, vy, colour, isFilled, isFlashing, secondColor);
        this.side = side;
        coordinatesX = new double[]{2 * side, 1.5 * side, 0.5 * side, 0 * side, 0.5 * side, 1.5 * side};
        coordinatesY = new double[]{1 * side, (1.0 + (Math.sqrt(3) / 2.0)) * side, (1.0 + (Math.sqrt(3) / 2.0)) * side,
                1 * side, (1.0 - (Math.sqrt(3) / 2.0)) * side, (1.0 - (Math.sqrt(3) / 2.0)) * side};
    }

    /**
     * Method to convert a hexagon to a string.
     */
    public String toString() {
        String result = "This is a polygon\n";
        result += super.toString();
        result += "Its side is " + this.side + "\n";
        return result;
    }

    /**
     * Draw the hexagon on the screen.
     *
     * @param g The graphics object of the scene component.
     */
    public void draw(GraphicsContext g) {
        g.setFill(colour);
        g.setStroke(colour);
        if (isFilled) {
            g.fillPolygon(coordinatesX, coordinatesY, 6);
        } else {
            g.strokePolygon(coordinatesX, coordinatesY, 6);
        }
    }

    /**
     * Method takes in a direction and a velocity and moves the shape
     * in that direction on unit
     */
    @Override
    public void move() {
        for (int i = 0; i < coordinatesX.length; i++) {
            coordinatesX[i] += this.xVec;
        }
        for (int i = 0; i < coordinatesY.length; i++) {
            coordinatesY[i] += this.yVec;
        }
    }

    /**
     * Method returns true if shape went out of bounds in X axis
     *
     * @param winX size of the window
     * @return true if shape is out of bounds x
     */
    @Override
    public boolean outOfBoundsX(double winX) {
        for (int i = 0; i < coordinatesX.length; i++) {
            if (coordinatesX[i] > winX || coordinatesX[i] < 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method returns true if shape went out of bounds in Y axis
     *
     * @param winY size of the window
     * @return true if shape is out of bounds y
     */
    @Override
    public boolean outOfBoundsY(double winY) {
        for (int i = 0; i < coordinatesY.length; i++) {
            if (coordinatesY[i] > winY || coordinatesY[i] < 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the side of the hexagon
     */
    public int getWidth() {
        return side;
    }

    /**
     * @return the side of the hexagon
     */
    public int getHeight() {
        return side;
    }

    /**
     * @return the side of the hexagon
     */
    public int getSide() {
        return side;
    }

    /**
     * @param side Resets the side
     */
    public void setSide(int side) {
        this.side = side;
    }
}
