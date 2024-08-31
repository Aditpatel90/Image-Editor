import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class PpmImage extends Image {

    public PpmImage(int width, int height) {

        super(width, height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                getColors()[i][j] = Color.BLACK;
            }
        }
    }

    public PpmImage(String filename) throws FileNotFoundException {
        super();
    }

    public PpmImage() {
        super();
    }

    void readImage(String filename) throws FileNotFoundException {
        try {
            Scanner sc = new Scanner(new File(filename));
            sc.nextLine(); // Skip the P3 line
            int width = sc.nextInt();
            int height = sc.nextInt();
            setWidth(width);
            setHeight(height);
            setColors(new Color[height][width]);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int red = sc.nextInt();
                    int green = sc.nextInt();
                    int blue = sc.nextInt();
                    getColors()[y][x] = new Color(red, green, blue);
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void output(String filename){
        try{
            java.io.PrintWriter scanner = new java.io.PrintWriter(filename);
            scanner.println("P3");
            scanner.println(getWidth() + " " + getHeight());
            scanner.println("255");

            for (int i = 0; i < getHeight(); i++) {
                for (int j = 0; j < getWidth(); j++) {
                    Color color = getColors()[i][j];
                    scanner.print(color.getRed() + " " + color.getGreen() + " " + color.getBlue() + " ");
                }
                scanner.println();
            }

            scanner.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
