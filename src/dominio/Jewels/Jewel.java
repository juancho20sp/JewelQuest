package dominio.Jewels;

import javax.swing.*;
import java.awt.*;

public abstract class Jewel extends JLabel{
    private int points;
    private int id;

    public Jewel(){
        new JLabel();

        setBorder(BorderFactory.createLineBorder(Color.black));
        setHorizontalAlignment(SwingConstants.CENTER);
        setOpaque(true);
        setVisible(true);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
