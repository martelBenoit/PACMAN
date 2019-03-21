package main;

import java.awt.Color;

/**
 * PowerPill class. Sub class of Pill. Define a PowerPill object
 */
public class PowerPill extends Pill {

    /**
     * Constructor of the PowerPill class.
     * This constructor is used to create a PowerPill object.
     * In parameter the tile on which is the PowerPill and also its color.
     * @param tile the tile on which is the PowerPill
     * @param color the color of the PowerPill
     */
    public PowerPill(Tile tile, Color color) {

        // on appelle le constructeur de la classe Pill en spécifiant en plus dans les paramètres, le facteur taille
        // de la PowerPill
        super(tile,color,3);

    }
}
