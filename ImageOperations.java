import java.awt.*;
import java.io.FileNotFoundException;

class ImageOperations {

    public static void main(String[] args) throws FileNotFoundException {
        // TODO.
        if (args.length < 3) {
            System.out.println("Usage java ImageOperations <operation> <input-file>");
            return;
        }

        String operation = args[0];
        String inputFile = args[1];
        String outputFile = args[2];

        PpmImage image = new PpmImage();
        image.readImage(inputFile);

        switch (operation) {
            case "--zerored":
                Image zeroRedImg = zeroRed(image);
                zeroRedImg.output(outputFile);
                break;
            case "--grayscale":
                Image grayscaleImg = grayscale(image);
                grayscaleImg.output(outputFile);
                break;
            case "--invert":
                Image invertImg = invert(image);
                invertImg.output(outputFile);
                break;
            case "--crop":
                if (args.length != 7) {
                    System.out.println("Usage: java ImageOperations --crop x y w h <input-file> <output-file>");
                    return;
                }
                int x = Integer.parseInt(args[3]);
                int y = Integer.parseInt(args[4]);
                int w = Integer.parseInt(args[5]);
                int h = Integer.parseInt(args[6]);
                Image croppedImg = crop(image, x, y, w, h);
                croppedImg.output(outputFile);
                break;
            case "--mirror":
                if (args.length != 4) {
                    System.out.println("Usage: java ImageOperations --mirror [H|V] <input-file> <output-file>");
                    return;
                }
                String mode = args[3];
                Image mirroredImg = mirror(image, mode);
                mirroredImg.output(outputFile);
                break;
            case "--repeat":
                if (args.length != 5) {
                    System.out.println("Usage: java ImageOperations --repeat [H|V] n <input-file> <output-file>");
                    return;
                }
                String dir = args[3];
                int n = Integer.parseInt(args[4]);
                Image repeatedImg = repeat(image, n, dir);
                repeatedImg.output(outputFile);
                break;
            default:
                System.out.println("Invalid operation. Supported operations are: --zerored, --grayscale, --invert, --crop, --mirror, --repeat");
        }
    }

    public static Image zeroRed(Image img) {
        int width = img.getWidth();
        int height = img.getHeight();
        Color[][] colors = img.getColors();
        Color[][] newColors = new Color[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color = colors[i][j];
                newColors[i][j] = new Color(0, color.getGreen(), color.getBlue());
            }
        }

        Image newImg = new PpmImage();
        newImg.setWidth(width);
        newImg.setHeight(height);
        newImg.setColors(newColors);

        return newImg;
    }

    public static Image grayscale(Image img) {
        int width = img.getWidth();
        int height = img.getHeight();
        Color[][] colors = img.getColors();
        Color[][] newColors = new Color[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color = colors[i][j];
                int gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                newColors[i][j] = new Color(gray, gray, gray);
            }
        }

        Image newImg = new PpmImage();
        newImg.setWidth(width);
        newImg.setHeight(height);
        newImg.setColors(newColors);

        return newImg;
    }

    public static Image invert(Image img) {
        int width = img.getWidth();
        int height = img.getHeight();
        Color[][] colors = img.getColors();
        Color[][] newColors = new Color[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color = colors[i][j];
                newColors[i][j] = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
            }
        }

        Image newImg = new PpmImage();
        newImg.setWidth(width);
        newImg.setHeight(height);
        newImg.setColors(newColors);

        return newImg;
    }

    public static Image crop(Image img, int x1, int y1, int w, int h) {
        int width = img.getWidth();
        int height = img.getHeight();
        Color[][] colors = img.getColors();
        Color[][] newColors = new Color[h][w];

        for (int i = y1; i < y1 + h; i++) {
            for (int j = x1; j < x1 + w; j++) {
                newColors[i - y1][j - x1] = colors[i][j];
            }
        }

        Image newImg = new PpmImage();
        newImg.setWidth(w);
        newImg.setHeight(h);
        newImg.setColors(newColors);

        return newImg;
    }

    public static Image mirror(Image img, String mode) {
        int width = img.getWidth();
        int height = img.getHeight();
        Color[][] colors = img.getColors();
        Color[][] newColors = new Color[height][width];

        if (mode.equals("V")) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    newColors[i][j] = colors[height - 1 - i][j];
                }
            }
        } else if (mode.equals("H")) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    newColors[i][j] = colors[i][width - 1 - j];
                }
            }
        }

        Image newImg = new PpmImage();
        newImg.setWidth(width);
        newImg.setHeight(height);
        newImg.setColors(newColors);

        return newImg;
    }

    public static Image repeat(Image img, int n, String dir) {
        int width = img.getWidth();
        int height = img.getHeight();
        Color[][] colors = img.getColors();

        if (dir.equals("H")) {
            int newWidth = width * n;
            Color[][] newColors = new Color[height][newWidth];

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < newWidth; j++) {
                    newColors[i][j] = colors[i][j % width];
                }
            }

            Image newImg = new PpmImage();
            newImg.setWidth(newWidth);
            newImg.setHeight(height);
            newImg.setColors(newColors);

            return newImg;
        } else if (dir.equals("V")) {
            int newHeight = height * n;
            Color[][] newColors = new Color[newHeight][width];

            for (int i = 0; i < newHeight; i++) {
                for (int j = 0; j < width; j++) {
                    newColors[i][j] = colors[i % height][j];
                }
            }

            Image newImg = new PpmImage();
            newImg.setWidth(width);
            newImg.setHeight(newHeight);
            newImg.setColors(newColors);
        }
        return img;
    }
}
