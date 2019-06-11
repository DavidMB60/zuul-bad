
/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String id;
    private String descripcion;
    private int peso;
    private boolean canTakeIt;

    /**
     * Constructor for objects of class Item
     */
    public Item(String id, String descripcion, int peso, boolean canTakeIt)
    {
        this.id = id;
        this.descripcion = descripcion;
        this.peso = peso;
        this.canTakeIt = canTakeIt;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPeso() {
        return peso;
    }

    public String getId() {
        return id;
    }

    public boolean canTakeIt() {
        return canTakeIt;
    }
}
