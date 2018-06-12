/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   16/04/2018				  */
/* Ultima modificación: 24/04/2018				  */
/* Descripción: Detalles del Formato de Academia Avance           */
/*              Programatico.                                     */
/****************************************************************/

package mx.fei.sgpa.domain.avanceprogramatico;

import java.util.ArrayList;
import mx.fei.sgpa.domain.EstadoDeDocumento;

public class AvanceProgramatico {
    private String id;
    private String idPlanDeCurso;
    private int nrc;
    private String nombreExperiencia;
    private String programaEducativo;
    private String bloque;
    private String seccion;
    private String academico;
    private String objetivoGeneral;
    private ArrayList<UnidadDePlaneacion> unidadesDePlaneacion;
    private ArrayList<AvancePorUnidad> avancesDeUnidad;
    private EstadoDeDocumento estado;
    
    public AvanceProgramatico(){
        
    }

    public String getId() {
        return id;
    }

    public String getIdPlanDeCurso() {
        return idPlanDeCurso;
    }

    public int getNrc() {
        return nrc;
    }

    public String getNombreExperiencia() {
        return nombreExperiencia;
    }

    public String getProgramaEducativo() {
        return programaEducativo;
    }

    public String getBloque() {
        return bloque;
    }

    public String getSeccion() {
        return seccion;
    }

    public String getAcademico() {
        return academico;
    }

    public String getObjetivoGeneral() {
        return objetivoGeneral;
    }

    public ArrayList<UnidadDePlaneacion> getUnidadesDePlaneacion() {
        return unidadesDePlaneacion;
    }

    public ArrayList<AvancePorUnidad> getAvancesDeUnidad() {
        return avancesDeUnidad;
    }

    public EstadoDeDocumento getEstado() {
        return estado;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIdPlanDeCurso(String idPlanDeCurso) {
        this.idPlanDeCurso = idPlanDeCurso;
    }

    public void setNrc(int nrc) {
        this.nrc = nrc;
    }

    public void setNombreExperiencia(String nombreExperiencia) {
        this.nombreExperiencia = nombreExperiencia;
    }

    public void setProgramaEducativo(String programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public void setAcademico(String academico) {
        this.academico = academico;
    }

    public void setObjetivoGeneral(String objetivoGeneral) {
        this.objetivoGeneral = objetivoGeneral;
    }

    public void setUnidadesDePlaneacion(ArrayList<UnidadDePlaneacion> unidadesDePlaneacion) {
        this.unidadesDePlaneacion = unidadesDePlaneacion;
    }

    public void setAvancesDeUnidad(ArrayList<AvancePorUnidad> avancesDeUnidad) {
        this.avancesDeUnidad = avancesDeUnidad;
    }

    public void setEstado(EstadoDeDocumento estado) {
        this.estado = estado;
    }
    
}
