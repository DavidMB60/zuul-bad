import java.util.Stack;

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

    /**
     * Constructor for objects of class Player
     */
    public Player(Room currentRoom)
    {
        recorridoHabitaciones = new Stack<Room>();
        this.currentRoom = currentRoom;
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
}
