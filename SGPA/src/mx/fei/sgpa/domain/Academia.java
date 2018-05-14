
package mx.fei.sgpa.domain;

import java.util.ArrayList;

public class Academia {
    String idAcademia;
    String nombreAcademia;
    String coordinadorAcademia;
    ArrayList<Academico> integrantes;
    
    public Academia() {
        
    }

    public String getIdAcademia() {
        return idAcademia;
    }

    public String getNombreAcademia() {
        return nombreAcademia;
    }

    public String getCoordinadorAcademia() {
        return coordinadorAcademia;
    }

    public ArrayList<Academico> getIntegrantes() {
        return integrantes;
    }

    public void setIdAcademia(String idAcademia) {
        this.idAcademia = idAcademia;
    }

    public void setNombreAcademia(String nombreAcademia) {
        this.nombreAcademia = nombreAcademia;
    }

    public void setCoordinadorAcademia(String coordinadorAcademia) {
        this.coordinadorAcademia = coordinadorAcademia;
    }

    public void setIntegrantes(ArrayList<Academico> integrantes) {
        this.integrantes = integrantes;
    }
    
}
