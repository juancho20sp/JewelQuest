package dominio;

import java.awt.*;

public class GameConfiguration {
    private Color backgroundColor;

    public GameConfiguration(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}

