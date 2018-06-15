/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   24/04/2018				  */
/* Ultima modificación: 03/05/2018				  */
/* Descripción: Detalles de las Formas de Evaluacion del Formato  */
/*              de Academia PlanTrabajoAcademia			  */
/****************************************************************/

package mx.fei.sgpa.domain.plantrabajoacademia;

/**
 * Detalles de las Formas de Evaluacion del Formato de Academia PlanTrabajoAcademia
 */
public class FormaDeEvaluacion {
    private String elemento;
    private float porcentaje;
    
    public FormaDeEvaluacion(){
        
    }

    public FormaDeEvaluacion(String elemento, float porcentaje) {
        this.elemento = elemento;
        this.porcentaje = porcentaje;
    }

    public String getElemento() {
        return elemento;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setElemento(String elemento) {
        this.elemento = elemento;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }
    
}
