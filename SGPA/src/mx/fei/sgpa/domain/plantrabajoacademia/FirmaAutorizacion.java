/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   24/04/2018				  */
/* Ultima modificación: 24/04/2018				  */
/* Descripción: Detalles del firma de Autorización del formato    */
/*              PlanTrabajoAcademia                               */
/****************************************************************/

package mx.fei.sgpa.domain.plantrabajoacademia;

import java.sql.Date;

/**
 * Detalles del firma de Autorización del formato PlanTrabajoAcademia
 */
public class FirmaAutorizacion {
    private String personaQueProponePlan;
    private String personaQueAutorizaPlan;
    private Date fechaAutorizacion;
    private Date fechaEntradaEnVigor;
    
    public FirmaAutorizacion(){
        
    }

    public String getPersonaQueProponePlan() {
        return personaQueProponePlan;
    }

    public String getPersonaQueAutorizaPlan() {
        return personaQueAutorizaPlan;
    }

    public Date getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public Date getFechaEntradaEnVigor() {
        return fechaEntradaEnVigor;
    }

    public void setPersonaQueProponePlan(String personaQueProponePlan) {
        this.personaQueProponePlan = personaQueProponePlan;
    }

    public void setPersonaQueAutorizaPlan(String personaQueAutorizaPlan) {
        this.personaQueAutorizaPlan = personaQueAutorizaPlan;
    }

    public void setFechaAutorizacion(Date fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public void setFechaEntradaEnVigor(Date fechaEntradaEnVigor) {
        this.fechaEntradaEnVigor = fechaEntradaEnVigor;
    }
    
}
