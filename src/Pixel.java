import java.time.Year;
import java.util.*;

public class Pixel {

    public int group;
    public int visitedId;

    public Pixel(int group) {
        this.group = group;
        this.visitedId = 0;
    }

    public Pixel(int group, int visitedId) {
        this.group = group;
        this.visitedId = visitedId;
    }


    public static final String CYAN = "\033[0;36m";
    public static final String YELLOW = "\033[0;33m";
    public static final String RESET = "\033[0m";

    @Override
    public String toString() {
        String color;
        if (group == 0) color = CYAN;
        else color = YELLOW;

        return color + "(" + group + " " + visitedId + ')' + RESET;
    }
}
