
public class NewColor {

    public double red, green, blue, opacity;

    public static double skinOpacity = 0.12;

    /**
     * Create new color based on ct head value
     * @param CTValue ct head value
     */
    public NewColor(int CTValue) {
        calculateValues(CTValue);
    }

    /**
     * Create new color with RBH and opacity values
     * @param red red value
     * @param green green value
     * @param blue blue value
     * @param opacity opacity value
     */
    public NewColor(double red, double green, double blue, double opacity){
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.opacity = opacity;
    }

    /**
     * Based on ct head value assign predefined color values
     * @param CTValue ct head value
     */
    private void calculateValues(int CTValue) {
        if (CTValue < -300) {
            red = 1;
            green = 1;
            blue = 1;
            opacity = 0;
        } else if (CTValue < 50) {
            red = 1.0;
            green = 0.79;
            blue = 0.6;
            opacity = skinOpacity;
        } else if (CTValue < 300) {
            red = 1;
            green = 1;
            blue = 1;
            opacity = 0;
        } else {
            red = 1;
            green = 1;
            blue = 1;
            opacity = 0.8;
        }
    }

    /**
     * Sum the RGB values of two colors
     * @param c1 color to sum with
     */
    public void sumColors(NewColor c1){
        this.red += c1.red;
        this.green += c1.green;
        this.blue += c1.blue;
    }

    /**
     * Multiply RGB values of a color
     * @param val to multiply by
     */
    public void multiply(double val){
        this.red *= val;
        this.green *= val;
        this.blue *= val;
    }
}
