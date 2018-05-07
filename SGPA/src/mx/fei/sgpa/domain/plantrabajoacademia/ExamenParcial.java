
package mx.fei.sgpa.domain.plantrabajoacademia;

import java.util.ArrayList;

public class ExamenParcial {
    private int id;
    private ArrayList<String> temasDeParcial;
    
    public ExamenParcial(){
        
    }

    public ExamenParcial(int id, ArrayList<String> temasDeParcial) {
        this.id = id;
        this.temasDeParcial = temasDeParcial;
    }

    public int getId() {
        return id;
    }

    public ArrayList<String> getTemasDeParcial() {
        return temasDeParcial;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTemasDeParcial(ArrayList<String> temasDeParcial) {
        this.temasDeParcial = temasDeParcial;
    } 
    
}
