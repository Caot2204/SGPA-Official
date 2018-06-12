/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   10/09/2018				  */
/* Ultima modificación: 10/09/2018				  */
/* Descripción: Detalles de un PlanTrabajoAcademia en tableview   */
/****************************************************************/
package mx.fei.sgpa.gui.adaptadorestablasclases;

import javafx.beans.property.SimpleStringProperty;

public class AdaptadorTablaPlanTrabajoAcademia {
    
    private String idPlan;
    private SimpleStringProperty academia = new SimpleStringProperty();
    private SimpleStringProperty periodo = new SimpleStringProperty();

    public String getId() {
        return idPlan;
    }
    
    public String getAcademia() {
        return academia.get();
    }

    public String getPeriodo() {
        return periodo.get();
    }
    
    public void setId(String idPlan) {
        this.idPlan = idPlan;
    }

    public void setAcademia(String academia) {
        this.academia.set(academia);
    }

    public void setPeriodo(String periodo) {
        this.periodo.set(periodo);
    }
    
}
