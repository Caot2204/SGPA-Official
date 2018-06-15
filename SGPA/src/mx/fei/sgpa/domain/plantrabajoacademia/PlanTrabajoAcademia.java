/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   16/04/2018				  */
/* Ultima modificación: 04/05/2018				  */
/* Descripción: Detalles del Formato de Academia                  */
/*              PlanTrabajoAcademia.				  */
/****************************************************************/

package mx.fei.sgpa.domain.plantrabajoacademia;

import java.sql.Date;
import java.util.ArrayList;
import mx.fei.sgpa.domain.EstadoDeDocumento;

/**
 * Detalles del Formato de Academia PlanTrabajoAcademia
 */
public class PlanTrabajoAcademia {
    private String id;
    private Date fechaAprobacion;
    private String programaEducativo;
    private String periodoEscolar;
    private String nombreAcademia;
    private String nombreCoordinador;
    private String objetivoGeneral;
    private ArrayList<ObjetivoParticular> objetivosParticulares;
    private ArrayList<ExperienciaEducativaConParciales> examenesParciales;
    private ArrayList<FormaDeEvaluacion> formasDeEvaluacion;
    private ArrayList<Revision> historicoDeRevisiones;
    private FirmaAutorizacion autorizacion;
    private EstadoDeDocumento estado;  
    
    public PlanTrabajoAcademia(){
        
    }

    public String getId() {
        return id;
    }

    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }

    public String getProgramaEducativo() {
        return programaEducativo;
    }

    public String getPeriodoEscolar() {
        return periodoEscolar;
    }

    public String getNombreAcademia() {
        return nombreAcademia;
    }

    public String getNombreCoordinador() {
        return nombreCoordinador;
    }

    public String getObjetivoGeneral() {
        return objetivoGeneral;
    }

    public ArrayList<ObjetivoParticular> getObjetivosParticulares() {
        return objetivosParticulares;
    }

    public ArrayList<ExperienciaEducativaConParciales> getExamenesParciales() {
        return examenesParciales;
    }

    public ArrayList<FormaDeEvaluacion> getFormasDeEvaluacion() {
        return formasDeEvaluacion;
    }

    public ArrayList<Revision> getHistoricoDeRevisiones() {
        return historicoDeRevisiones;
    }

    public FirmaAutorizacion getAutorizacion() {
        return autorizacion;
    }

    public EstadoDeDocumento getEstado() {
        return estado;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public void setProgramaEducativo(String programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    public void setPeriodoEscolar(String periodoEscolar) {
        this.periodoEscolar = periodoEscolar;
    }

    public void setNombreAcademia(String nombreAcademia) {
        this.nombreAcademia = nombreAcademia;
    }

    public void setNombreCoordinador(String nombreCoordinador) {
        this.nombreCoordinador = nombreCoordinador;
    }

    public void setObjetivoGeneral(String objetivoGeneral) {
        this.objetivoGeneral = objetivoGeneral;
    }

    public void setObjetivosParticulares(ArrayList<ObjetivoParticular> objetivosParticulares) {
        this.objetivosParticulares = objetivosParticulares;
    }

    public void setExamenesParciales(ArrayList<ExperienciaEducativaConParciales> examenesParciales) {
        this.examenesParciales = examenesParciales;
    }

    public void setFormasDeEvaluacion(ArrayList<FormaDeEvaluacion> formasDeEvaluacion) {
        this.formasDeEvaluacion = formasDeEvaluacion;
    }

    public void setHistoricoDeRevisiones(ArrayList<Revision> historicoDeRevisiones) {
        this.historicoDeRevisiones = historicoDeRevisiones;
    }

    public void setAutorizacion(FirmaAutorizacion autorizacion) {
        this.autorizacion = autorizacion;
    }

    public void setEstado(EstadoDeDocumento estado) {
        this.estado = estado;
    }
    
}
