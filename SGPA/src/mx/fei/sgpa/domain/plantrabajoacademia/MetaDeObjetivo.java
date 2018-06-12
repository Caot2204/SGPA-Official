/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   24/04/2018				  */
/* Ultima modificación: 25/04/2018				  */
/* Descripción: Detalles de las Metas de un ObjetivoParticular del*/
/*              Formato de Academia PlanTrabajoAcademia.	  */
/****************************************************************/

package mx.fei.sgpa.domain.plantrabajoacademia;

import java.util.ArrayList;

public class MetaDeObjetivo {
    private String id;
    private String descripcion;
    private ArrayList<AccionDeMeta> accionesDeMeta;
    
    public MetaDeObjetivo(){
        
    }

    public String getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ArrayList<AccionDeMeta> getAccionesDeMeta() {
        return accionesDeMeta;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setAccionesDeMeta(ArrayList<AccionDeMeta> accionesDeMeta) {
        this.accionesDeMeta = accionesDeMeta;
    }
    
}
