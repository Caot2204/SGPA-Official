/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   24/04/2018				  */
/* Ultima modificación: 04/05/2018				  */
/* Descripción: Detalles del Examen Parcial con sus respectivos   */
/*              temas.                                            */
/****************************************************************/

package mx.fei.sgpa.domain.plantrabajoacademia;

import java.util.ArrayList;

/**
 * Detalles del Examen Parcial con sus respectivos temas
 */
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
