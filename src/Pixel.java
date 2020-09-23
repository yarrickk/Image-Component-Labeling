public class Pixel {

    public int group;
    public int visitedId;

    public static final String CYAN = "\033[0;36m";
    public static final String YELLOW = "\033[0;33m";
    public static final String GREEN = "\033[0;32m";
    public static final String RESET = "\033[0m";




    public Pixel(int group) {
        this.group = group;
        this.visitedId = 0;
    }

    public Pixel(int group, int visitedId) {
        this.group = group;
        this.visitedId = visitedId;
    }


    public String center(String str, int length) {
        while (str.length() + 1 < length) {
            str = " " + str + " ";
        }
        if (str.length() < length) {
            return " " + str;
        }

        return str;

    }

//for i in range(0, 16):
//    for j in range(0, 16):
//        code = str(i * 16 + j)
//        print(u"\u001b[38;5;" + code + "m " + code.ljust(4), end=" ")
//    print(u"\u001b[0m")

    @Override
    public String toString() {
        String color;
//        if (group == 0) color = CYAN;
//        else if (group == 1) color = YELLOW;
//        else color = GREEN;

        int code = group + 150;
        color = "\u001b[38;5;" + code + "m";
        if (group == 0) {
            color = "\u001b[38;5;" + 242 + "m";
        }

        String pixelRepr = "(" + group + " " + visitedId + ")";
        return color + center(pixelRepr, 8) + RESET;
    }
}
