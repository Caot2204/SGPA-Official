/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   24/04/2018				  */
/* Ultima modificación: 25/04/2018				  */
/* Descripción: Detalles de un ObjetivoParticular del Formato de  */
/*              Academia PlanTrabajoAcademia.			  */
/****************************************************************/

package mx.fei.sgpa.domain.plantrabajoacademia;

import java.util.ArrayList;

/**
 * Detalles de un ObjetivoParticular del Formato de Academia PlanTrabajoAcademia
 */
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
