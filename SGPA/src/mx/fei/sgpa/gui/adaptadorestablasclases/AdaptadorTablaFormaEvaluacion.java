/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   10/06/2018				  */
/* Ultima modificación: 10/06/2018				  */
/* Descripción: Detalles de una FormaEvaluacion en tableview.     */
/****************************************************************/
package mx.fei.sgpa.gui.adaptadorestablasclases;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Detalles de una FormaEvaluacion en tableview
 */
public class AdaptadorTablaFormaEvaluacion {
    
    private final SimpleStringProperty elemento = new SimpleStringProperty();
    private final SimpleFloatProperty porcentaje = new SimpleFloatProperty();

    public String getElemento() {
        return elemento.get();
    }

    public float getPorcentaje() {
        return porcentaje.floatValue();
    }

    public void setElemento(String elemento) {
        this.elemento.set(elemento);
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje.set(porcentaje);
    }
    
}
