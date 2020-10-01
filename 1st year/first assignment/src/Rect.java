import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Rect is a rectangular shape that can be drawn to
 * to the screen, either filled with colour or opaque.
 * Its position is determined by the upper left corner of the
 * rectangular bounding rectangle.
 */
public class Rect extends ClosedShape {
    //The width and height of a rectangle
    private int width, height;

    /**
     * Creates a rectangle.
     *
     * @param x        The display component's x position.
     * @param y        The display component's y position.
     * @param vx       The display component's x velocity.
     * @param vy       The display component's y velocity.
     * @param width    The width of the rectangle (in pixels).
     * @param height   The height of the rectangle (in pixels).
     * @param colour   The line colour or fill colour.
     * @param isFilled True if the rectangle is filled with colour, false if opaque.
     */
    public Rect(int insertionTime, int x, int y, int vx, int vy, int width, int height, Color colour, boolean isFilled,
                boolean isFlashing, Color secondColor) {
        super(insertionTime, x, y, vx, vy, colour, isFilled, isFlashing, secondColor);
        this.width = width;
        this.height = height;
    }

    /**
     * Method to convert a rectangle to a string.
     */
    public String toString() {
        String result = "This is a rectangle\n";
        result += super.toString();
        result += "Its width is " + this.width + "\n";
        result += "Its height is " + this.height + "\n";
        return result;
    }

    /**
     * Draw the rectangle on the screen.
     *
     * @param g The graphics object of the scene component.
     */
    public void draw(GraphicsContext g) {
        g.setFill(colour);
        g.setStroke(colour);
        if (isFilled) {
            g.fillRect(x, y, width, height);
        } else {
            g.strokeRect(x, y, width, height);
        }
    }

    /**
     * @return The width of the rectangle.
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return The height of the rectangle.
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param width Resets the width.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @param height Resets the height.
     */
    public void setHeight(int height) {
        this.height = height;
    }
}
