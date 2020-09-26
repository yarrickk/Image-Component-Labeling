import DataStructures.Queue;
import DataStructures.Stack;

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

    static int visitedIdCount_DFS = 1;
    static int visitedIdCount_BFS = 1;

    static boolean colorsOn = true;

    static Scanner userInput = new Scanner(System.in);


    static void init() {
        System.out.println("Welcome to the Image Component Labeling program!\n");

        if (getYesOrNo("Use default dimension and density? (y/n): ").equals("n"))
            getDimensions();

        printAcceptedValues();

        colorsOn = getYesOrNo("Do you want colors enabled (y/n)?").equals("y");

        generateImage();
    }


    static String getYesOrNo(String message) {
        while (true) {
            System.out.print(message);
            String temp = userInput.next();
            if (temp.equals("y") || temp.equals("n"))
                return temp;
        }
    }


    static void getDimensions() {
        getSide(5, 20);
        getDensity(0, 1);
    }


    static void getSide(int min, int max) {
        System.out.printf("Please enter a dimension between %d and %d: ", min, max);
        side = userInput.nextInt();
        while (side < min || side > max) {
            System.out.printf("Invalid! Please enter a dimension between %d and %d: ", min, max);
            side = userInput.nextInt();
        }
    }


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


    static void printImage(Pixel[][] table) {
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

                    depthFirstSearch(seed, groupId);
                    breadthFirstSearch(seed, groupId);
                    groupId++;
                }
        }
    }


    static void depthFirstSearch(Point initPos, int groupId) {
        Stack<Point> pointsGroup = new Stack<>();
        pointsGroup.push(initPos);

        while (!pointsGroup.isEmpty()) {
            Point curPos = pointsGroup.pop();
            imageTableDFS[curPos.row][curPos.col].group = groupId;
            imageTableDFS[curPos.row][curPos.col].visitedId = visitedIdCount_DFS++;

            // push all valid adjacent points
            for (Point adjacent : OFFSET) {
                Point tmpPos = curPos.add(adjacent);
                if (imageTableDFS[tmpPos.row][tmpPos.col].group == 1) {
                    imageTableDFS[tmpPos.row][tmpPos.col].group = groupId;
                    pointsGroup.push(tmpPos);
                }
            }
        }
    }


    static void breadthFirstSearch(Point initPos, int groupId) {
        Queue<Point> pointsGroup = new Queue<>();
        pointsGroup.enqueue(initPos);

        while (!pointsGroup.isEmpty()) {
            Point curPos = pointsGroup.dequeue();
            imageTableBFS[curPos.row][curPos.col].group = groupId;
            imageTableBFS[curPos.row][curPos.col].visitedId = visitedIdCount_BFS++;

            // enqueue all valid adjacent points
            for (Point adjacent : OFFSET) {
                Point tmpPos = curPos.add(adjacent);
                if (imageTableBFS[tmpPos.row][tmpPos.col].group == 1) {
                    imageTableBFS[tmpPos.row][tmpPos.col].group = groupId;
                    pointsGroup.enqueue(tmpPos);
                }
            }
        }
    }


    public static void main(String[] args) {
        init();

        System.out.println("\nBEFORE SEARCH...");
        printImage(imageTableBFS);

        labelGroups();

        System.out.println("AFTER DEPTH FIRST SEARCH...");
        printImage(imageTableDFS);

        System.out.println("AFTER BREADTH FIRST SEARCH...");
        printImage(imageTableBFS);
    }
}


