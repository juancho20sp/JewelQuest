package dominio.Jewels;

import java.awt.*;

/**
 * Class for the Empty Jewel
 * @author Juan David Murillo
 * @version 1.0
 */
public class EmptyJewel extends Jewel {

    /**
     * Constructor for the EmptyJewel class
     * @param color The background color
     */
    public EmptyJewel(Color color){
        super.setId(-2);
        setBackground(color);
    }
}
