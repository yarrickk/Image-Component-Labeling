package project.main;

import project.datastructures.Queue;
import project.datastructures.Stack;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class ImgCompLabeler {

    static Point[] OFFSET = {
            new Point(0, 1),    // right
            new Point(1, 0),    // down
            new Point(0, -1),   // left
            new Point(-1, 0)    // up
    };

    static Pixel[][] imageTableDFS, imageTableBFS;

    static int side = 15;        // default values
    static double density = 0.3;

    static int DFS_visitedIdCount = 1;
    static int BFS_visitedIdCount = 1;

    static boolean colorsOn = true;
    static Scanner userInput = new Scanner(System.in);


    static void init() {
        System.out.println("Welcome to the Image Component Labeling program!\n");

        if (getYesOrNo("Use default dimension and density? (y/n): ").equals("n"))
            getDimensions();

        printAcceptedValues();

        colorsOn = getYesOrNo("Do you want colors enabled (y/n)?").equals("y");
    }


    /**
     * Prints question and asks for input until provided "y" or "n" answer
     */
    static String getYesOrNo(String question) {
        while (true) {
            System.out.print(question);
            String temp = userInput.next().toLowerCase();
            if (temp.equals("y") || temp.equals("n"))
                return temp;
        }
    }


    /**
     * Ask user to input dimensions, without accepting wrong values.
     */
    static void getDimensions() {
        getSide(5, 20);
        getDensity(0, 1);
    }


    /**
     * Ask user to enter a side (int) between specified range
     */
    static void getSide(int min, int max) {
        System.out.printf("Please enter a dimension between %d and %d: ", min, max);
        side = userInput.nextInt();
        while (side < min || side > max) {
            System.out.printf("Invalid! Please enter a dimension between %d and %d: ", min, max);
            side = userInput.nextInt();
        }
    }


    /**
     * Ask user to enter a density (double) between specified range
     */
    static void getDensity(double min, double max) {
        System.out.printf("Please enter a density between %.1f and %.1f: ", min, max);
        density = userInput.nextDouble();
        while (density < min || density > max) {
            System.out.printf("Invalid! Please enter a density between %.1f and %.1f: ", min, max);
            density = userInput.nextDouble();
        }
    }


    static void printAcceptedValues() {
        System.out.println("\nAccepted Values");

        System.out.println("Dimension: " + side);
        System.out.println("Density: " + density + "\n");

        System.out.println("Grid is successfully created!\n");
    }


    /**
     * Generate imageTableDFS and imageTableBFS with pixels according to provided density
     */
    static void generateImage() {
        imageTableDFS = new Pixel[side + 2][side + 2];
        imageTableBFS = new Pixel[side + 2][side + 2];

        for (int row = 0; row < side + 2; row++)
            // initialize surrounding wall
            for (int col = 0; col < side + 2; col++) {
                if (row == 0 || col == 0 || col == side + 1 || row == side + 1) {
                    imageTableDFS[row][col] = new Pixel(0, colorsOn);
                    imageTableBFS[row][col] = new Pixel(0, colorsOn);
                } else {
                    int newColor = density > Math.random() ? 1 : 0;

                    imageTableDFS[row][col] = new Pixel(newColor, colorsOn);
                    imageTableBFS[row][col] = new Pixel(newColor, colorsOn);
                }
            }
    }


    static void printTable(Pixel[][] table) {
        System.out.println();
        for (int row = 1; row <= side; row++) {
            System.out.print("|");
            for (int col = 1; col <= side; col++)
                System.out.print(table[row][col] + "|");
            System.out.println();
        }
        System.out.println();
    }


    static void labelGroups() {
        int groupId = 2;
        for (int row = 1; row <= side + 1; row++) {
            for (int col = 1; col <= side + 1; col++)
                if (imageTableDFS[row][col].group == 1) {
                    Point seed = new Point(row, col);

                    labelWithDFS(seed, groupId, imageTableDFS);
                    labelWithBFS(seed, groupId, imageTableBFS);
                    groupId++;
                }
        }
    }


    static void labelWithDFS(Point initPos, int groupId, Pixel[][] image) {
        Stack<Point> pointsGroup = new Stack<>();
        pointsGroup.push(initPos);

        while (!pointsGroup.isEmpty()) {
            Point curPos = pointsGroup.pop();
            image[curPos.row][curPos.col].group = groupId;
            image[curPos.row][curPos.col].visitedId = DFS_visitedIdCount++;

            // push all valid adjacent points
            for (Point adjacent : OFFSET) {
                Point tmpPos = curPos.add(adjacent);
                if (image[tmpPos.row][tmpPos.col].group == 1) {
                    image[tmpPos.row][tmpPos.col].group = groupId;
                    pointsGroup.push(tmpPos);
                }
            }
        }
    }


    static void labelWithBFS(Point initPos, int groupId, Pixel[][] image) {
        Queue<Point> pointsGroup = new Queue<>();
        pointsGroup.enqueue(initPos);

        while (!pointsGroup.isEmpty()) {
            Point curPos = pointsGroup.dequeue();
            image[curPos.row][curPos.col].group = groupId;
            image[curPos.row][curPos.col].visitedId = BFS_visitedIdCount++;

            // enqueue all valid adjacent points
            for (Point adjacent : OFFSET) {
                Point tmpPos = curPos.add(adjacent);
                if (image[tmpPos.row][tmpPos.col].group == 1) {
                    image[tmpPos.row][tmpPos.col].group = groupId;
                    pointsGroup.enqueue(tmpPos);
                }
            }
        }
    }


    /**
     * Saves imageTableBFS into a png image.
     * ( Since imageTableBFS groups are identical to imageTableDFS, it doesn't really matter which one)
     *
     * @param filePath     where the image will be stored
     * @param withLabeling if true, label each cell with its group number
     */
    static void generateOutputImagePNG(String filePath, boolean withLabeling) {
        int scalingCoef = 1200 / side;        // scale table to be 1200 x 1200 pixels
        int imageSide = side * scalingCoef;

        BufferedImage img = new BufferedImage(imageSide, imageSide, BufferedImage.TYPE_INT_RGB);
        fillWithGroupColors(img, scalingCoef);

        if (withLabeling)
            fillCellsWithGroupLabels(img, scalingCoef);

        saveImagePNG(img, filePath);
    }


    static void saveImagePNG(BufferedImage img, String filePath) {
        try {
            ImageIO.write(img, "png", new File(filePath));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }


    static void fillWithGroupColors(BufferedImage img, int coef) {
        for (int row = 0; row < img.getWidth(); row++)
            for (int col = 0; col < img.getHeight(); col++) {
                // add 1 to index because of the empty surrounding wall
                int group = imageTableBFS[row / coef + 1][col / coef + 1].group;
                img.setRGB(col, row, getColor(group));
            }
    }


    static void fillCellsWithGroupLabels(BufferedImage img, int coef) {
        Graphics graphics = img.getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Serif", Font.BOLD, coef - coef / 2));

        FontMetrics metrics = graphics.getFontMetrics();

        for (int row = 0; row < side; row++)
            for (int col = 0; col < side; col++) {
                int group = imageTableBFS[row + 1][col + 1].group;
                if (group > 1) {
                    String groupStr = String.valueOf(group);
                    // center text
                    int x = col * coef + (coef - metrics.stringWidth(groupStr)) / 2;
                    int y = row * coef + (coef - metrics.getHeight() / 2);
                    graphics.drawString(groupStr, x, y);
                }
            }
    }


    /**
     * pseudo random color generator with groupId as seed.
     * 8-bit RGB color components packed into integer pixel.
     */
    static int getColor(int group) {
        if (group == 0) return 16777215; // white
        if (group == 1) return 0;        // black

        Random rand = new Random(group);  // randomize seed
        rand = new Random(rand.nextInt());

        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);

        return (r << 16) | (g << 8) | b; // pixel color
    }


    public static void main(String[] args) {
        init();
        generateImage();

        String generateImageAnswer = getYesOrNo("Do you want to also generate PNG images? (y/n) ");
        if (generateImageAnswer.equals("y"))
            generateOutputImagePNG("before_search.png", false);

        System.out.println("\nBEFORE SEARCH...");
        printTable(imageTableBFS);

        labelGroups();

        System.out.println("AFTER DEPTH FIRST SEARCH...");
        printTable(imageTableDFS);

        System.out.println("AFTER BREADTH FIRST SEARCH...");
        printTable(imageTableBFS);

        if (generateImageAnswer.equals("y"))
            generateOutputImagePNG("after_search.png", true);
    }
}


