import java.util.Stack;
import java.util.ArrayList;

/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
    // instance variables - replace the example below with your own
    private Room currentRoom;
    private Stack<Room> recorridoHabitaciones;
    private ArrayList<Item> mochila;
    private int capacidad;

    /**
     * Constructor for objects of class Player
     */
    public Player(Room currentRoom)
    {
        recorridoHabitaciones = new Stack<Room>();
        this.currentRoom = currentRoom;
        mochila = new ArrayList<Item>();
        capacidad = 15000;
    }

    public void goRoom(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("¿Ir a donde?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("¡No hay salida!");
        }
        else {
            recorridoHabitaciones.push(currentRoom);
            currentRoom = nextRoom;
            look();
            System.out.println();
        }
    }

    /**
    Este método imprime la descripción de la sala en la que nos encontramos
    y también muestra las salidas disponibles. (El comando aquí es "mirar"
    en vez de "look")
     */
    public void look() {   
        System.out.println(currentRoom.getLongDescription());
    }

    public void back() {
        if (!recorridoHabitaciones.empty()) {
            currentRoom = recorridoHabitaciones.pop();
            look();
            System.out.println();
        }
        else {
            System.out.println("¡No puedes volver atrás!");
        }
    }

    public void comer() {
        System.out.println("Has comido y ya no tienes hambre");
    }

    public void take(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take
            System.out.println("¿Coger qué?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to take the item
        String objeto = command.getSecondWord();
        Item objetoNuevo = null;
        objetoNuevo = currentRoom.getItem(objeto);
        if (objetoNuevo == null) {
            System.out.println("¡El objeto no existe!");
        }
        else {
            if (objetoNuevo.canTakeIt()) {
                if (capacidad > 0 && (capacidad - objetoNuevo.getPeso() >= 0)) {
                    mochila.add(objetoNuevo);
                    capacidad -= objetoNuevo.getPeso();
                    currentRoom.delItem(objetoNuevo);
                    System.out.println("Has cogido: " + objetoNuevo.getDescripcion());
                }
                else {
                    System.out.println("¡Has superado el peso máximo! Capacidad restante: " + capacidad);
                }
            }
            else {
                System.out.println("¡No puedes coger ese objeto!");
            }
        }
    }

    public void listItems() {
        if (mochila.isEmpty()) {
            System.out.println("No tienes objetos.");
        }
        else {
            for (Item itemActual : mochila) {
                System.out.println("Objeto: " + itemActual.getDescripcion() + " " + itemActual.getPeso() + "g" + ".\n"); 
            }
        }
        System.out.println("Capacidad actual: " + capacidad);
    }

    public void dropItem(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop
            System.out.println("¿Coger qué?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to take the item
        String objeto = command.getSecondWord();
        Item objetoNuevo = null;
        for (Item itemActual : mochila) {
            if (itemActual.getId().equals(objeto)) {
                objetoNuevo = itemActual;
            }
        }
        if (objetoNuevo == null) {
            System.out.println("¡El objeto no existe!");
        }
        else {
            currentRoom.addItem(objetoNuevo);
            mochila.remove(objetoNuevo);
            capacidad += objetoNuevo.getPeso();
            System.out.println("Has soltado: " + objetoNuevo.getDescripcion());
            System.out.println("Capacidad actual: " + capacidad);
        }
    }
}
