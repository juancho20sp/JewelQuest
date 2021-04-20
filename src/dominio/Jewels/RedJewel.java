package dominio.Jewels;

import dominio.Jewels.Jewel;

import java.awt.*;

/**
 * Class for the Red Jewel
 * @author Juan David Murillo
 * @version 1.0
 */
public class RedJewel extends Jewel {

    /**
     * Constructor for the RedJewel class
     */
    public RedJewel(){
        super.setId(3);
        setBackground(Color.RED);
    }
}
