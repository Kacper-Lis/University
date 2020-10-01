import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Square is a square shape that can be drawn to
 * to the screen, either filled with colour or opaque.
 * Its position is determined by the upper left corner of the
 * square bounding rectangle.
 */
public class Square extends ClosedShape {
    //The side of a square
    private int side;

    /**
     * Creates a Square.
     *
     * @param x        The display component's x position.
     * @param y        The display component's y position.
     * @param vx       The display component's x velocity.
     * @param vy       The display component's y velocity.
     * @param side     The side of the Square.
     * @param colour   The line colour or fill colour.
     * @param isFilled True if the Square is filled with colour, false if opaque.
     */
    public Square(int insertionTime, int x, int y, int vx, int vy, int side, Color colour, boolean isFilled,
                  boolean isFlashing, Color secondColor) {
        super(insertionTime, x, y, vx, vy, colour, isFilled, isFlashing, secondColor);
        this.side = side;
    }

    /**
     * Method to convert a square to a string.
     */
    public String toString() {
        String result = "This is a square\n";
        result += super.toString();
        result += "Its side is " + this.side + "\n";
        return result;
    }

    /**
     * Draw the square on the screen.
     *
     * @param g The graphics object of the scene component.
     */
    public void draw(GraphicsContext g) {
        g.setFill(colour);
        g.setStroke(colour);
        if (isFilled) {
            g.fillRect(x, y, side, side);
        } else {
            g.strokeRect(x, y, side, side);
        }
    }

    /**
     * @return the side of the square
     */
    public int getWidth() {
        return side;
    }

    /**
     * @return the side of the square
     */
    public int getHeight() {
        return side;
    }

    /**
     * @return the side of the square
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
