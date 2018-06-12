/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   04/05/2018				  */
/* Ultima modificación: 04/05/2018				  */
/* Descripción: Detalles de una Experiencia Educativa con         */
/*              Exámenes parciales ingresados por el Coordianador.*/
/****************************************************************/

package mx.fei.sgpa.domain.plantrabajoacademia;

import java.util.ArrayList;

public class EEConParcial {
    String experienciaEducativa;
    ArrayList<ExamenParcial> examenesParciales;

    public EEConParcial(String experienciaEducativa, ArrayList<ExamenParcial> examenesParciales) {
        this.experienciaEducativa = experienciaEducativa;
        this.examenesParciales = examenesParciales;
    }

    public EEConParcial() {
        
    }

    public String getExperienciaEducativa() {
        return experienciaEducativa;
    }

    public ArrayList<ExamenParcial> getExamenesParciales() {
        return examenesParciales;
    }

    public void setExperienciaEducativa(String experienciaEducativa) {
        this.experienciaEducativa = experienciaEducativa;
    }

    public void setExamenesParciales(ArrayList<ExamenParcial> examenesParciales) {
        this.examenesParciales = examenesParciales;
    }
    
}
