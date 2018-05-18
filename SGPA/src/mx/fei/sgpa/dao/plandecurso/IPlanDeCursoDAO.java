/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.fei.sgpa.dao.plandecurso;

import java.util.ArrayList;
import mx.fei.sgpa.domain.plandecurso.Bibliografia;
import mx.fei.sgpa.domain.plandecurso.EvaluacionDeUnidad;
import mx.fei.sgpa.domain.plandecurso.PlanDeCurso;
import mx.fei.sgpa.domain.plandecurso.UnidadDePlaneacion;

/**
 *
 * @author beto
 */
public class IPlanDeCursoDAO {
    
    boolean guardarPlanDeCurso(PlanDeCurso planDeCurso);
    boolean guardarBibliografia(String idPlanDeCurso, ArrayList<Bibliografia> referenciasBibliograficas);
    boolean guardarEvaluacionDeUnidad(String idPlanDeCurso, ArrayList<EvaluacionDeUnidad> evaluacionesDeUnidad);
    boolean guardarUnidadDePlaneacion(String idPlanDeCurso, ArrayList<UnidadDePlaneacion> unidadesDePlaneacion);
    PlanDeCurso buscarPlanDeCursoPorID(String idPlanDeCurso);
    ArrayList<PlanDeCurso> buscarPlanDeCursoPorCoordinador(String idCoordinador);
    ArrayList<Bibliografia> recuperarBibliografia(String idPlanDeCurso); 
    ArrayList<EvaluacionDeUnidad> recuperarEvaluacionDeUnidad(String idPlanDeCurso);
    ArrayList<UnidadDePlaneacion> recuperarUnidadDePlaneacion(String idPlanDeCurso);
    
}
