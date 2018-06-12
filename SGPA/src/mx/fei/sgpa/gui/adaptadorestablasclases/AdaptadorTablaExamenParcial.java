/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   10/06/2018				  */
/* Ultima modificación: 10/06/2018				  */
/* Descripción: Detalles de un ExamenParcial en tableview.       */
/****************************************************************/
package mx.fei.sgpa.gui.adaptadorestablasclases;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AdaptadorTablaExamenParcial {
    
    private SimpleIntegerProperty numeroParcial = new SimpleIntegerProperty();
    private SimpleStringProperty temaDeParcial = new SimpleStringProperty();

    public int getNumeroParcial() {
        return numeroParcial.get();
    }

    public String getTemaDeParcial() {
        return temaDeParcial.get();
    }
    
    public void setNumeroParcial(int numeroParcial) {
        this.numeroParcial.set(numeroParcial);
    }
    
    public void setTemaDeParcial(String tema) {
        this.temaDeParcial.set(tema);
    }
}
