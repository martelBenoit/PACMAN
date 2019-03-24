package main;

import java.awt.Color;

/**
 * PowerPill class. <br>
 * Sub class of Pill. Define a PowerPill object
 * @author Benoît Martel
 * @author Yoann Le Dréan
 * @version 1.0
 * @see Pill
 */
class PowerPill extends Pill {

    /**
     * Constructor of the PowerPill class. <br>
     * This constructor is used to create a PowerPill object. <br>
     * In parameter the tile on which is the PowerPill and also its color. <br>
     * @param tile the tile on which is the PowerPill.
     * @param color the color of the PowerPill.
     */
     PowerPill(Tile tile, Color color) {

        // on appelle le constructeur de la classe Pill en spécifiant en plus dans les paramètres, le facteur taille
        // de la PowerPill
        super(tile,color,3);

    }
}