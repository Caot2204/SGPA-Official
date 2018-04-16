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
    String objetivo;
    int nrc;
    EstadoDeDocumento estado;
    String experienciaEducativa;
    String programaEducativo;
    String bloque;
    String seccion;
    String academico;
    String periodo;
    ArrayList<Bibliografia> listaDeReferenciasBibliograficas;
    ArrayList<EvaluacionDeUnidad> listaDeEvaluacionesDeUnidad;
    ArrayList<UnidadDePlaneacion> listaDePlaneacionesDeUnidad;
    
}
