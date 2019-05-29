/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private Room northExit;
    private Room southExit;
    private Room eastExit;
    private Room westExit;
    private Room suresteExit;
    private Room noroesteExit;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(Room north, Room east, Room south, Room west, Room sureste, Room noroeste) 
    {
        if(north != null)
            northExit = north;
        if(east != null)
            eastExit = east;
        if(south != null)
            southExit = south;
        if(west != null)
            westExit = west;
        if (sureste != null) {
            suresteExit = sureste;
        }
        if (noroeste != null) {
            noroesteExit = noroeste;
        }
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public Room getExit(String direccion) {
        Room habitacion = null;
        if (direccion.equals("norte")) {
            habitacion = northExit;
        }
        else if (direccion.equals("este")) {
            habitacion = eastExit;
        }
        else if (direccion.equals("sur")) {
            habitacion = southExit;
        }
        else if (direccion.equals("oeste")) {
            habitacion = westExit;
        }
        else if (direccion.equals("sureste")) {
            habitacion = suresteExit;
        }
        else if (direccion.equals("noroeste")) {
            habitacion = noroesteExit;
        }
        return habitacion;
    }

    /**
     * Devuelve la información de las salidas existentes
     * Por ejemplo: "Exits: north east west"
     *
     * @return Una descripción de las salidas existentes.
     */
    public String getExitString() {
        String direccion = "Salidas: ";
        if(northExit != null) {
            direccion += "norte ";
        }
        if(eastExit != null) {
            direccion += "este ";
        }
        if(southExit != null) {
            direccion += "sur ";
        }
        if(westExit != null) {
            direccion += "oeste ";
        }
        if (suresteExit != null) {
            direccion += "sureste ";
        }
        if (noroesteExit != null) {
            direccion += "noroeste ";
        }
        return direccion;
    }
}
