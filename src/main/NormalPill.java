package main;

import java.awt.Color;

/**
 * <BODY>
 * <HTML>
 *      NormalPill class. <br>
 *     Sub class of Pill. Define a NormalPill object
 * </BODY>
 * </HTML>
 * @author Benoît & Yoann
 * @version 1.0
 * @see Pill
 */
class NormalPill extends Pill{

    /**
     * <BODY>
     * <HTML>
     * Constructor of the NormalPill class. <br>
     * This constructor is used to create a NormalPill object. <br>
     * In parameter the tile on which is the NormalPill. <br>
     * </BODY>
     * </HTML>
     * @param tile the tile on which is the NormalPill.
     */
    NormalPill(Tile tile) {

        super(tile,Color.WHITE,7);
    }
}