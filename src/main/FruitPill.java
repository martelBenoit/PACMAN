package main;

import java.awt.Color;

/**
 * FruitPill class. <br>
 * Sub class of Pill. Define a FruitPill object
 * @author Benoît Martel
 * @author Yoann Le Dréan
 * @version 1.0
 * @see Pill
 */
class FruitPill extends Pill{

    /**
     * Constructor of the FruitPill class. <br>
     * This constructor is used to create a FruitPill object. <br>
     * In parameter the tile on which is the FruitPill and also its color. <br>
     * @param tile the tile on which is the FruitPill.
     * @param color the color of the FruitPill.
     */
    FruitPill(Tile tile, Color color) {

        super(tile,color,3);
    }

}