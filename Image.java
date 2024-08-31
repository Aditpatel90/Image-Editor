import java.awt.Color;

public abstract class Image {

    private int width;
    private int height;
    private Color[][] colors;

    public Image(int width, int height) {
        // TODO.
        this.width = width;
        this.height = height;
        colors = new Color[height][width];
    }

    Image(){
        width = 0;
        height = 0;
    }
    public int getWidth(){
        return width;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public int getHeight(){
        return height;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public Color[][] getColors(){
        return colors;
    }

    public void setColors(Color[][] colors) {
        this.colors = colors;
    }

    public abstract void output(String filename);
}
