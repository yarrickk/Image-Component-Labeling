import java.util.Scanner;

public class ImgCompLabeler {

    static Pixel[][] imageTable;

    static int side = 8; // change to 15 later
    static double density = 0.3;

    static void welcome() {
        System.out.println("Welcome to the Image Component Labeling program!\n");
    }

    static void getDimensions() {
        Scanner userInput = new Scanner(System.in);

        System.out.print("Please enter a dimension between 5 and 20: ");
        side = userInput.nextInt();

        System.out.print("Please enter a density between 0 and 1: ");
        density = userInput.nextDouble();
    }

    static void printAcceptedValues() {
        System.out.println("\nAccepted Values");
        System.out.println("Dimension: " + side);
        System.out.println("Density: " + density + "\n");
        System.out.println("Grids successfully created!");
    }

    static void generateImage(int len, double density) {
        imageTable = new Pixel[len][len];
        int size = len * len;

        int full = (int) (size * density);
        int empty = size - full;

        for (int row = 0; row < len; row++)
            for (int col = 0; col < len; col++) {
                int newPixel = choosePixel(empty, full);

                if (newPixel == 0) empty--;
                else full--;

                imageTable[row][col] = new Pixel(newPixel);
            }
    }


    static int choosePixel(double empty, double full) {
        double chance = Math.random();
        double hundredPercent = empty + full;

        if (chance < (empty / hundredPercent)) {
            return (empty > 0) ? 0 : 1;
        } else {
            return (full > 0) ? 1 : 0;
        }
    }


    static void printImage() {
        for (Pixel[] pixels : imageTable) {
            System.out.print("| ");
            for (int col = 0; col < imageTable.length; col++)
                System.out.print(pixels[col] + " | ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        welcome();
        getDimensions();
        printAcceptedValues();
        generateImage(side, density);
        printImage();
    }
}
