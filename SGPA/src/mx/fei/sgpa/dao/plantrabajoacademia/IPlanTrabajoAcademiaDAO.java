/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   16/04/2018				  */
/* Ultima modificación: 07/05/2018				  */
/* Descripción: Definir la interfaz de métodos para el DAO de     */
/*              PlanTrabajoAcademia.				  */
/****************************************************************/

package mx.fei.sgpa.dao.plantrabajoacademia;

import java.util.ArrayList;
import mx.fei.sgpa.domain.plantrabajoacademia.AccionDeMeta;
import mx.fei.sgpa.domain.plantrabajoacademia.EEConParcial;
import mx.fei.sgpa.domain.plantrabajoacademia.ExamenParcial;
import mx.fei.sgpa.domain.plantrabajoacademia.FirmaAutorizacion;
import mx.fei.sgpa.domain.plantrabajoacademia.FormaDeEvaluacion;
import mx.fei.sgpa.domain.plantrabajoacademia.MetaDeObjetivo;
import mx.fei.sgpa.domain.plantrabajoacademia.ObjetivoParticular;
import mx.fei.sgpa.domain.plantrabajoacademia.PlanTrabajoAcademia;
import mx.fei.sgpa.domain.plantrabajoacademia.Revision;

public interface IPlanTrabajoAcademiaDAO {
    boolean actualizarDatosPlan(PlanTrabajoAcademia planAcademia);
    boolean guardarPlanTrabajoAcademiaCompleto(PlanTrabajoAcademia planAcademia);
    boolean guardarPlanTrabajoAcademia(PlanTrabajoAcademia planAcademia);
    boolean guardarObjetivosParticulares(String idPlanTrabajoAcademia, ArrayList<ObjetivoParticular> objetivosParticulares);
    boolean guardarMetasDeObjetivoParticular(String idPlanTrabajoAcademia, String idObjetivoParticular, ArrayList<MetaDeObjetivo> metasDeObjetivo);
    boolean guardarAccionesDeMeta(String idPlanTrabajoAcademia, String idObjetivoParticular, String idMeta, ArrayList<AccionDeMeta> accionesDeMeta);
    boolean guardarExamenesParciales(String idPlanTrabajoAcademia, ArrayList<EEConParcial> eesConParciales);
    boolean guardarCantidadExamenesParcialesDeEE(String idPlanTrabajoAcademia, ArrayList<EEConParcial> eesConParciales);
    boolean guardarTemasDeParcialDeEE(String idPlanTrabajoAcademia, String experienciaEducativa, ArrayList<ExamenParcial> examenesParciales);
    boolean guardarFormasDeEvaluacion(String idPlanTrabajoAcademia, ArrayList<FormaDeEvaluacion> formasDeEvaluacion);
    boolean guardarHistoricoDeRevision(String idPlanTrabajoAcademia, ArrayList<Revision> historicoDeRevisiones);
    boolean guardarFirmaDeAutorizacion(String idPlanTrabajoAcademia, FirmaAutorizacion firmaDeAutorizacion);
    boolean eliminarDatosPlan(String idPlanAcademia);
    PlanTrabajoAcademia buscarPlanTrabajoByID(String idPlanTrabajoAcademia);
    ArrayList<PlanTrabajoAcademia> buscarPlanTrabajoByCoordinador(int idCoordinador);
    ArrayList<PlanTrabajoAcademia> obtenerPlanesEnEdicion(int numeroDePersonal);
    ArrayList<ObjetivoParticular> obtenerObjetivosParticulares(String idPlanTrabajoAcademia);
    ArrayList<MetaDeObjetivo> obtenerMetasDeObjetivo(String idPlanTrabajoAcademia, String idObjetivoParticular);
    ArrayList<AccionDeMeta> obtenerAccionesDeMeta(String idPlanTrabajoAcademia, String idObjetivoParticular, String idMeta);
    ArrayList<EEConParcial> obtenerEEsConExamenesParciales(String idPlanTrabajoAcademia);
    int obtenerCantidadExamenesParcialesDeEE(String idPlanTrabajoAcademia, String experienciaEducativa);
    ArrayList<String> obtenerTemasDeParcialDeEE(String idPlanTrabajoAcademia, String experienciaEducativa, int numeroParcial);
    ArrayList<FormaDeEvaluacion> obtenerFormasDeEvaluacion(String idPlanTrabajoAcademia);
    ArrayList<Revision> obtenerHistoricoDeRevision(String idPlanTrabajoAcademia);
    FirmaAutorizacion obtenerFirmaAutorizacion(String idPlanTrabajoAcademia);
}
