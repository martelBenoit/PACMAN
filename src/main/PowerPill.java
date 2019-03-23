package main;

import main.view.GameFrame;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

/**
 * <BODY>
 * <HTML>
 *     PowerPill class. <br>
 *     Sub class of Pill. Define a PowerPill object
 * </BODY>
 * </HTML>
 * @author Benoît & Yoann
 * @version 1.0
 * @see Pill
 */
public class PowerPill extends Pill {

    private boolean boBig = false;

    /**
     * <BODY>
     * <HTML>
     * Constructor of the PowerPill class. <br>
     * This constructor is used to create a PowerPill object. <br>
     * In parameter the tile on which is the PowerPill and also its color. <br>
     * </BODY>
     * </HTML>
     * @param tile the tile on which is the PowerPill.
     * @param color the color of the PowerPill.
     */
    public PowerPill(Tile tile, Color color) {

        // on appelle le constructeur de la classe Pill en spécifiant en plus dans les paramètres, le facteur taille
        // de la PowerPill
        super(tile,color,3);

    }

}
