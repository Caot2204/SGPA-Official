/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   10/09/2018				  */
/* Ultima modificación: 10/09/2018				  */
/* Descripción: Detalles de una AccionDeMeta en tableview.        */
/****************************************************************/
package mx.fei.sgpa.gui.adaptadorestablasclases;

import javafx.beans.property.SimpleStringProperty;

public class AdaptadorTablaAccionDeMeta {
    
    private SimpleStringProperty descripcionAccion = new SimpleStringProperty();
    private SimpleStringProperty fechaSemana = new SimpleStringProperty();
    private SimpleStringProperty formaOperar = new SimpleStringProperty();

    public String getDescripcionAccion() {
        return descripcionAccion.get();
    }

    public String getFechaSemana() {
        return fechaSemana.get();
    }

    public String getFormaOperar() {
        return formaOperar.get();
    }

    public void setDescripcionAccion(String descripcionAccion) {
        this.descripcionAccion.set(descripcionAccion);
    }

    public void setFechaSemana(String fechaSemana) {
        this.fechaSemana.set(fechaSemana);
    }

    public void setFormaOperar(String formaOperar) {
        this.formaOperar.set(formaOperar);
    }
    
}
