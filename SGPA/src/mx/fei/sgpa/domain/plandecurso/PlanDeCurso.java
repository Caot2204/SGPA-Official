/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.fei.sgpa.domain.plandecurso;

import java.util.ArrayList;
import mx.fei.sgpa.domain.EstadoDeDocumento;

/**
 *
 * @author beto
 */
public class PlanDeCurso {
    
    String idPlanDeCurso;
    int nrc;
    String academico;
    String objetivoGeneral;
    EstadoDeDocumento estado;
    ArrayList<Bibliografia> listaDeReferenciasBibliograficas;
    ArrayList<EvaluacionDeUnidad> listaDeEvaluacionesDeUnidad;
    ArrayList<UnidadDePlaneacion> listaDePlaneacionesDeUnidad;

    public PlanDeCurso() {
    }

    public String getIdPlanDeCurso() {
        return idPlanDeCurso;
    }

    public String getObjetivoGeneral() {
        return objetivoGeneral;
    }

    public int getNrc() {
        return nrc;
    }

    public EstadoDeDocumento getEstado() {
        return estado;
    }
    
    public String getAcademico(){
        return academico;
    }

    public ArrayList<Bibliografia> getListaDeReferenciasBibliograficas() {
        return listaDeReferenciasBibliograficas;
    }

    public ArrayList<EvaluacionDeUnidad> getListaDeEvaluacionesDeUnidad() {
        return listaDeEvaluacionesDeUnidad;
    }

    public ArrayList<UnidadDePlaneacion> getListaDePlaneacionesDeUnidad() {
        return listaDePlaneacionesDeUnidad;
    }

    public void setIdPlanDeCurso(String idPlanDeCurso) {
        this.idPlanDeCurso = idPlanDeCurso;
    }

    public void setObjetivoGeneral(String objetivo) {
        this.objetivoGeneral = objetivo;
    }

    public void setNrc(int nrc) {
        this.nrc = nrc;
    }

    public void setEstado(EstadoDeDocumento estado) {
        this.estado = estado;
    }
    
    public void setAcademico(String academico){
        this.academico = academico;
    }

    public void setListaDeReferenciasBibliograficas(ArrayList<Bibliografia> listaDeReferenciasBibliograficas) {
        this.listaDeReferenciasBibliograficas = listaDeReferenciasBibliograficas;
    }

    public void setListaDeEvaluacionesDeUnidad(ArrayList<EvaluacionDeUnidad> listaDeEvaluacionesDeUnidad) {
        this.listaDeEvaluacionesDeUnidad = listaDeEvaluacionesDeUnidad;
    }

    public void setListaDePlaneacionesDeUnidad(ArrayList<UnidadDePlaneacion> listaDePlaneacionesDeUnidad) {
        this.listaDePlaneacionesDeUnidad = listaDePlaneacionesDeUnidad;
    }
}
