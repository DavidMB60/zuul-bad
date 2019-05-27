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
        Room laboratorio, serverRoom, almacen, easterEgg;
      
        // create the rooms
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
        
        // initialise room exits (norte, este, sur, oeste, sureste)
        spawn.setExits(null, seguridad, null, null, null);
        seguridad.setExits(laboratorio, contencion, null, spawn, null);
        contencion.setExits(null, anexo, null, seguridad, null);
        anexo.setExits(null, lobby, null, contencion, easterEgg);
        lobby.setExits(null, recepcion, null, anexo, null);
        recepcion.setExits(null, null, null, lobby, null);
        almacen.setExits(null, laboratorio, null, null, null);
        laboratorio.setExits(null, serverRoom, seguridad, almacen, null);
        serverRoom.setExits(null, null, null, laboratorio, null);
        
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
        System.out.println();
        System.out.println("Estás en: " + currentRoom.getDescription());
        System.out.print("Salidas: ");
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
        System.out.println("   ir salir ayuda");
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
        if(direction.equals("norte")) {
            nextRoom = currentRoom.northExit;
        }
        if(direction.equals("este")) {
            nextRoom = currentRoom.eastExit;
        }
        if(direction.equals("sur")) {
            nextRoom = currentRoom.southExit;
        }
        if(direction.equals("oeste")) {
            nextRoom = currentRoom.westExit;
        }
        if (direction.equals("sureste")) {
            nextRoom = currentRoom.suresteExit;
        }

        if (nextRoom == null) {
            System.out.println("¿No hay salida!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println("Estas en: " + currentRoom.getDescription());
            System.out.print("Salidas: ");
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
        if(currentRoom.northExit != null) {
            System.out.print("norte ");
        }
        if(currentRoom.eastExit != null) {
            System.out.print("este ");
        }
        if(currentRoom.southExit != null) {
            System.out.print("sur ");
        }
        if(currentRoom.westExit != null) {
            System.out.print("oeste ");
        }
        if (currentRoom.suresteExit != null) {
            System.out.print("sureste ");
        }
    }
}
