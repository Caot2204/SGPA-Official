/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   24/04/2018				  */
/* Ultima modificación: 25/05/2018				  */
/* Descripción: Detalles de un Accion de una Meta.                */
/****************************************************************/

package mx.fei.sgpa.domain.plantrabajoacademia;

public class AccionDeMeta {
    private String descripcionAccion;
    private String fechaSemana;
    private String formaOperar;
    
    public void AccionMetaPlanTrabajoAcademia(){
        
    }

    public String getDescripcionAccion() {
        return descripcionAccion;
    }

    public String getFechaSemana() {
        return fechaSemana;
    }

    public String getFormaOperar() {
        return formaOperar;
    }

    public void setDescripcionAccion(String descripcionAccion) {
        this.descripcionAccion = descripcionAccion;
    }

    public void setFechaSemana(String fechaSemana) {
        this.fechaSemana = fechaSemana;
    }

    public void setFormaOperar(String formaOperar) {
        this.formaOperar = formaOperar;
    }
    
}
