import java.util.Stack;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> recorridoHabitaciones;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room spawn, seguridad, contencion, anexo, lobby, recepcion;
        Room laboratorio, serverRoom, almacen, easterEgg, armeria;
        Item keycard, phone, hdd, glock9;

        // Crear los objetos
        keycard = new Item("Tarjeta de seguridad de nivel 3", 100);
        phone = new Item("Un Samsung Galaxy Note 7; útil para explotar paredes", 150);
        hdd = new Item("Disco duro de 10TB, contiene información relacionada con el proyecto CK17", 500);
        glock9 = new Item("Glock 9MM cargada", 900);

        // create the rooms (descripcion, objeto)

        spawn = new Room("en el interior de una oficina");
        seguridad = new Room("el pasillo de seguridad");
        contencion = new Room("el sistema de contención");
        anexo = new Room("el pasillo central del anexo");
        lobby = new Room("la lobby principal del complejo Lambda");
        recepcion = new Room("la recepcion al edificion principal");
        laboratorio = new Room("el laboratorio de investigación principal del complejo Lambda");
        serverRoom = new Room("la localización de los servidores del complejo");
        almacen = new Room("el almacén del nivel 3");
        easterEgg = new Room("no deberías estar aquí...");
        armeria = new Room("armería de seguridad");

        // Agregar los objetos

        armeria.addItem(glock9);
        contencion.addItem(keycard);
        laboratorio.addItem(hdd);
        spawn.addItem(phone);

        // initialise room exits (norte, este, sur, oeste, sureste, noroeste)
        spawn.setExit("este", seguridad);
        seguridad.setExit("norte", laboratorio);
        seguridad.setExit("este", contencion);
        seguridad.setExit("oeste", spawn);
        contencion.setExit("este", anexo);
        contencion.setExit("oeste", seguridad);
        anexo.setExit("este", lobby);
        anexo.setExit("oeste", contencion);
        anexo.setExit("sureste", easterEgg);
        anexo.setExit("noroeste", armeria);
        lobby.setExit("este", recepcion);
        lobby.setExit("oeste", anexo);
        recepcion.setExit("oeste", recepcion);
        almacen.setExit("este", laboratorio);
        laboratorio.setExit("oeste", almacen);
        laboratorio.setExit("sur", seguridad);
        laboratorio.setExit("este", serverRoom);
        serverRoom.setExit("oeste", laboratorio);
        armeria.setExit("sureste", anexo);

        currentRoom = spawn;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Gracias por jugar. Adiós.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Bienvenido a World of Zuul!");
        System.out.println("World of Zuul es un nuevo e increíblemente aburrido juego de aventuras.");
        System.out.println("Escribe 'ayuda' si necesitas asistencia.");
        recorridoHabitaciones = new Stack<Room>();
        printLocationInfo();
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("No te entiendo...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("ayuda")) {
            printHelp();
        }
        else if (commandWord.equals("ir")) {
            goRoom(command);
        }
        else if (commandWord.equals("salir")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("mirar")) {
            look();
        }
        else if (commandWord.equals("comer")) {
            System.out.println("Has comido y ya no tienes hambre");
        }
        else if (commandWord.equals("volver")) {
            back();
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Estás perdido. Estás sangrando. Pero estás callado.");
        System.out.println("Deambulas por las instalaciones (aunque llevas trabajando aquí 3 años).");
        System.out.println();
        System.out.println("Los comandos son: ");
        System.out.println(parser.showCommands());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
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
            printLocationInfo();
            System.out.println();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("¿Salir de que?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     *  Este método imprime las salidad disponibles en cada habitación que nos
     *  encontremos.
     */
    private void printLocationInfo() {
        System.out.print(currentRoom.getLongDescription());
    }

    /**
    Este método imprime la descripción de la sala en la que nos encontramos
    y también muestra las salidas disponibles. (El comando aquí es "mirar"
    en vez de "look")
     */
    private void look() {   
        System.out.println(currentRoom.getLongDescription());
    }

    private void back() {
        if (!recorridoHabitaciones.empty()) {
            currentRoom = recorridoHabitaciones.pop();
            printLocationInfo();
            System.out.println();
        }
        else {
            System.out.println("¡No puedes volver atrás!");
        }
    }
}
