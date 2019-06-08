import java.util.HashMap;
import java.util.ArrayList;

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
    private HashMap<String, Room> salidas;
    private ArrayList<Item> objetos;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        salidas = new HashMap();
        objetos = new ArrayList<Item>();
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
        habitacion = salidas.get(direccion);
        return habitacion;
    }

    /**
     * Devuelve la información de las salidas existentes
     * Por ejemplo: "Exits: north east west"
     *
     * @return Una descripción de las salidas existentes.
     */
    public String getExitString() {
        return "Salidas: " + salidas.keySet();
    }

    /**
     * Define una salida para la habitacion.
     * @param direction El nombre de la direccion de la salida
     * @param neighbor La habitacion a la que se llega usando esa salida
     */
    public void setExit(String direccion, Room roomVecina) {
        salidas.put(direccion, roomVecina);
    }

    /**
     * Devuelve un texto con la descripcion larga de la habitacion del tipo:
     *     You are in the 'name of room'
     *     Exits: north west southwest
     * @return Una descripcion de la habitacion incluyendo sus salidas
     */
    public String getLongDescription() {
        String descripcionARetornar = "";
        descripcionARetornar += "Estas en: " + description + "\nSalidas: " + salidas.keySet();
        if (objetos.size() > 0) {
            for (Item objetoActual : objetos) {
                descripcionARetornar += "\n Objeto: " + objetoActual.getDescripcion() + ". Peso: " + objetoActual.getPeso() + "g." + "(" + objetoActual.getId() + ")" + "\n";
            }
        }
        else {
            descripcionARetornar += "\n Objeto: none.";
        }
        return descripcionARetornar;
    }
    
    public void addItem(Item objeto) {
        objetos.add(objeto);
    }
    
    public Item getItem(String id) {
        Item objetoADevolver = null;
        for (Item itemActual : objetos) {
            if (itemActual.getId().equals(id)) {
                objetoADevolver = itemActual;
            }
        }
        return objetoADevolver;
    }
    
    public void delItem(Item objeto) {
        objetos.remove(objeto);
    }
}
