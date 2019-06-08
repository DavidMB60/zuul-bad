
/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String descripcion;
    private int peso;
    private String id;
    private boolean canTakeIt;

    /**
     * Constructor for objects of class Item
     */
    public Item(String descripcion, int peso, String id, boolean canTakeIt)
    {
        this.descripcion = descripcion;
        this.peso = peso;
        this.id = id;
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
    
    public boolean getCanTakeIt() {
        return canTakeIt;
    }
}
