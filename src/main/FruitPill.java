package main;

import java.awt.Color;

/**
 * <BODY>
 * <HTML>
 *     FruitPill class. <br>
 *     Sub class of Pill. Define a FruitPill object
 * </BODY>
 * </HTML>
 * @author Benoît & Yoann
 * @version 1.0
 * @see Pill
 */
public class FruitPill extends Pill{

    /**
     * <BODY>
     * <HTML>
     * Constructor of the FruitPill class. <br>
     * This constructor is used to create a FruitPill object. <br>
     * In parameter the tile on which is the FruitPill and also its color. <br>
     * </BODY>
     * </HTML>
     * @param tile the tile on which is the FruitPill.
     * @param color the color of the FruitPill.
     */
    public FruitPill(Tile tile, Color color) {

        super(tile,color,3);
    }


}