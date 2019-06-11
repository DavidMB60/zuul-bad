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
    private Player jugador;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        parser = new Parser();
        jugador = new Player(createRooms());
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private Room createRooms()
    {
        Room spawn, seguridad, contencion, anexo, lobby, recepcion;
        Room laboratorio, serverRoom, almacen, easterEgg, armeria;
        Item keycard, phone, hdd, glock9, armarioMuniciones;

        // Crear los objetos (id, descripcion, peso, loPuedesCoger?)
        keycard = new Item("keycard", "Tarjeta de seguridad de nivel 3", 100, true);
        phone = new Item("phone", "Un Samsung Galaxy Note 7; útil para explotar paredes", 150, true);
        hdd = new Item("hdd", "Disco duro de 10TB, contiene información relacionada con el proyecto CK17", 500, true);
        glock9 = new Item("glock9", "Glock 9MM cargada", 900, true);
        armarioMuniciones = new Item("armario", "Armario con municiones y armamento", 25000, false);

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
        armeria.addItem(armarioMuniciones);
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
        
        return spawn;
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
        jugador.look();
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
            jugador.goRoom(command);
        }
        else if (commandWord.equals("salir")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("mirar")) {
            jugador.look();
        }
        else if (commandWord.equals("comer")) {
            jugador.comer();
        }
        else if (commandWord.equals("volver")) {
            jugador.back();
        }
        else if (commandWord.equals("coger")) {
            jugador.take(command);
        }
        else if (commandWord.equals("items")) {
            jugador.listItems();
        }
        else if (commandWord.equals("soltar")) {
            jugador.dropItem(command);
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
}
