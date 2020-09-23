import DataStructures.Stack;

import java.util.Scanner;

public class ImgCompLabeler {

    static Pixel[][] imageTable;

    static int side = 20; // change to 15 later
    static double density = 0.5;
    static int count = 1;

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

    static void generateImage() {
        imageTable = new Pixel[side + 2][side + 2];
        int size = side * side;

        int full = (int) (size * density);
        int empty = size - full;

        for (int row = 0; row < side + 2; row++)
            for (int col = 0; col < side + 2; col++) {
                if (row == 0 || col == 0 || col == side + 1 || row == side + 1) {
                    imageTable[row][col] = new Pixel(0);
                } else {
                    int newPixel = choosePixel(empty, full);

                    if (newPixel == 0) empty--;
                    else full--;
                    imageTable[row][col] = new Pixel(newPixel);
                }
            }
    }

    static void labelGroups() {
        int id = 2;
        for (int row = 1; row <= side + 1; row++) {
            for (int col = 1; col <= side + 1; col++) {
                if (imageTable[row][col].group == 1) {
//                    imageTable[row][col].group = 2;
                    depthFirstSearch(row, col, id++);
                }
            }
        }
    }

    static void depthFirstSearch(int row, int col, int id) {
        Point[] offset = new Point[4];
        offset[0] = new Point(0, 1); // right
        offset[1] = new Point(1, 0); // down
        offset[2] = new Point(0, -1); // left
        offset[3] = new Point(-1, 0); // up

        Stack<Point> points = new Stack<>();

        points.push(new Point(row, col));


        Point curPos = new Point(row, col);
        int option = 0;
        int lastOption = 3;


        while (!points.isEmpty()) {
            curPos = points.pop();
            imageTable[curPos.row][curPos.col].group = id;
            imageTable[curPos.row][curPos.col].visitedId = count++;

            // push all adjacent
            System.out.println(curPos);
            for (Point point : offset) {
                Point tmpPos = curPos.add(point);
                if (imageTable[tmpPos.row][tmpPos.col].group == 1) {
                    imageTable[tmpPos.row][tmpPos.col].group = id;
                    points.push(tmpPos);
                }
            }

            for (Point x : points) System.out.print(x + " ");
            System.out.println();

            //            while (option <= lastOption) {
//                Point newPosition = curPos.add(offset[option]);
//                System.out.println(newPosition);
//                if (imageTable[curPos.row][curPos.col].group == 1) {
//                    imageTable[curPos.row][curPos.col].group = id;
//                    points.push(newPosition);
//                }
//                option++;
//            }
//            if (option == lastOption) option = 0;

//            option = 0;
            // pop a node
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
        for (int row = 1; row <= side; row++) {
            System.out.print("|");
            for (int col = 1; col <= side; col++)
                System.out.print(imageTable[row][col] + "|");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        welcome();
//        getDimensions();
        printAcceptedValues();
        generateImage();
        labelGroups();
        printImage();
//        printImage();
    }
}


