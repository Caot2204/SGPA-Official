/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   07/05/2018				  */
/* Ultima modificación: 07/05/2018				  */
/* Descripción: Detalles de una Academia.                         */
/****************************************************************/
package mx.fei.sgpa.domain;

import java.util.ArrayList;
import mx.fei.sgpa.dao.academia.AcademiaDAO;
import mx.fei.sgpa.dao.academico.AcademicoDAO;

/**
 * Detalles de una Academia de la Universidad Veracruzana
 */
public class Academia {
    private String idAcademia;
    private String nombreAcademia;
    private int coordinadorAcademia;
    
    public Academia() {
        
    }
    
    /**
     * Obtiene el Nombre del Academico en base al número de personal del mismo
     * @return Nombre del Coordinador de la Academia
     */
    public String obtenerNombreDeCoordinador() {
        AcademicoDAO academicoDAO = new AcademicoDAO();
        Academico coordinador = academicoDAO.obtenerAcademico(coordinadorAcademia);
        return coordinador.getNombreAcademico();
    }
    
    /**
     * Obtiene los Académicos pertenecientes a esta Academia
     * @return Lista de Academico
     */
    public ArrayList<Academico> obtenerIntegrantes() {
        AcademiaDAO academiaDAO = new AcademiaDAO();
        return academiaDAO.obtenerIntegrantesAcademia(idAcademia);
    }

    public String getIdAcademia() {
        return idAcademia;
    }

    public String getNombreAcademia() {
        return nombreAcademia;
    }

    public int getCoordinadorAcademia() {
        return coordinadorAcademia;
    }

    public void setIdAcademia(String idAcademia) {
        this.idAcademia = idAcademia;
    }

    public void setNombreAcademia(String nombreAcademia) {
        this.nombreAcademia = nombreAcademia;
    }

    public void setCoordinadorAcademia(int coordinadorAcademia) {
        this.coordinadorAcademia = coordinadorAcademia;
    }
    
    @Override
    public String toString(){
        return this.nombreAcademia;
    }
    
}
