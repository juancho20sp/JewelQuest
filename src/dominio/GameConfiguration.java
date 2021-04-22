package dominio;

import java.awt.*;

public class GameConfiguration {
    private Color backgroundColor;
    private int height;
    private int width;

    public GameConfiguration(Color backgroundColor, int height, int width) {
        this.backgroundColor = backgroundColor;
        this.height = height;
        this.width = width;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}

