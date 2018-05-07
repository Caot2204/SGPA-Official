
package mx.fei.sgpa.domain.plantrabajoacademia;

import java.util.ArrayList;

public class ObjetivoParticular {
    private String id;
    private String descripcion;
    private ArrayList<MetaDeObjetivo> metasDeObjetivo;
    
    public ObjetivoParticular(){
        
    }

    public String getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ArrayList<MetaDeObjetivo> getMetasDeObjetivo() {
        return metasDeObjetivo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setMetasDeObjetivo(ArrayList<MetaDeObjetivo> metasDeObjetivo) {
        this.metasDeObjetivo = metasDeObjetivo;
    }
    
}
