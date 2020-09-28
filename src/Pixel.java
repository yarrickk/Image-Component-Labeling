import java.util.Random;

public class Pixel {

    // ANSI codes for console colors
    static final String COLOR_PREFIX = "\u001b[38;5;";
    static final String BRIGHT_WHITE = COLOR_PREFIX + "231m";
    static final String DIM_GREY = COLOR_PREFIX + "239m";
    static final String RESET = "\033[0m";

    public int group;
    public int visitedId = 0;
    public boolean colorsOn = false;

    public Pixel(int group, boolean withColors) {
        this.group = group;
        colorsOn = withColors;
    }


    /**
     * Return a centered String of length width.
     * Padding is done using the space.
     */
    public String center(String str, int width) {
        while (str.length() + 1 < width)
            str = " " + str + " ";
        if (str.length() < width)
            return " " + str;
        return str;
    }


    /**
     * pseudo random generator in range (100 - 230),
     * to choose from the brightest colors.
     * There could be Pixel groups with the same color, but
     * they won't likely to be near each other.
     */
    private int getColorCode() {
        Random random = new Random(group);
        return random.nextInt(130) + 100;
    }


    @Override
    public String toString() {
        String rawPixelRepr = "(" + group + " " + visitedId + ")";
        rawPixelRepr = center(rawPixelRepr, 8);

        if (!colorsOn) return rawPixelRepr;

        String color;
        if (group == 0) color = DIM_GREY;               // empty pixel
        else if (group == 1) color = BRIGHT_WHITE;      // filled pixel
        else color = COLOR_PREFIX + getColorCode() + "m";

        return color + rawPixelRepr + RESET;
    }
}