/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   17/04/2018				  */
/* Ultima modificación: 17/05/2018				  */
/* Descripción: Implementacion de los métodos para el DAO de      */
/*              PlanTrabajoAcademia.				  */
/****************************************************************/

package mx.fei.sgpa.dao.plantrabajoacademia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.fei.sgpa.dao.academia.AcademiaDAO;
import mx.fei.sgpa.dao.academico.AcademicoDAO;
import mx.fei.sgpa.datasource.DataBase;
import mx.fei.sgpa.domain.Academia;
import mx.fei.sgpa.domain.Academico;
import mx.fei.sgpa.domain.EstadoDeDocumento;
import mx.fei.sgpa.domain.plantrabajoacademia.AccionDeMeta;
import mx.fei.sgpa.domain.plantrabajoacademia.ExperienciaEducativaConParciales;
import mx.fei.sgpa.domain.plantrabajoacademia.ExamenParcial;
import mx.fei.sgpa.domain.plantrabajoacademia.FirmaAutorizacion;
import mx.fei.sgpa.domain.plantrabajoacademia.FormaDeEvaluacion;
import mx.fei.sgpa.domain.plantrabajoacademia.MetaDeObjetivo;
import mx.fei.sgpa.domain.plantrabajoacademia.ObjetivoParticular;
import mx.fei.sgpa.domain.plantrabajoacademia.PlanTrabajoAcademia;
import mx.fei.sgpa.domain.plantrabajoacademia.Revision;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Capa de acceso a la base de datos del sistema para almacenar y recuperar un PlanTrabajoAcademia
 */
public class PlanTrabajoAcademiaDAO implements IPlanTrabajoAcademiaDAO{
       
    private Connection conexionDB;
    private String consultaSQL;
    private PreparedStatement sentencia;
    private BarricadaPlanTrabajoAcademiaDAO barricada;
    private static final Logger loggerDelSistema = LogManager.getLogger(PlanTrabajoAcademiaDAO.class);

    /**
     * Almacena los datos completos de un Plan de Trabajo de Academia, 
     * además de las firmas de autorización y el histórico de revisiones.
     * Si ocurre algun error al guardar un dato, todos los datos de este
     * Plan de Trabajo de Academia serán eliminados, con el fin de 
     * salvaguardar la integridad de los datos.
     * 
     * @param planAcademia Datos de un PlanTrabajoAcademia
     * @return true si todos los datos fueron guardados exitosamente, false si no fue asi
     */
    @Override
    public boolean guardarPlanTrabajoAcademiaCompleto(PlanTrabajoAcademia planAcademia) {
        boolean guardadoRealizado = false;
        barricada = BarricadaPlanTrabajoAcademiaDAO.obtenerInstancia();
        
        if (barricada.validarDatosPlanAcademiaCompleto(planAcademia)) {
            AcademicoDAO academicoDAO = new AcademicoDAO();
            Academico coordinador = academicoDAO.obtenerAcademico(planAcademia.getNombreCoordinador());
            String idPlan = planAcademia.getId();
            consultaSQL = "INSERT INTO plan_trabajo_academia values (?,?,?,?,?,?,?,?)";
            conexionDB = DataBase.getDataBaseConnection();
            try {
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setString(1, idPlan);
                sentencia.setDate(2, planAcademia.getFechaAprobacion());
                sentencia.setString(3, planAcademia.getProgramaEducativo());
                sentencia.setString(4, planAcademia.getPeriodoEscolar());
                sentencia.setString(5, planAcademia.getNombreAcademia());
                sentencia.setInt(6, coordinador.getNumeroPersonal());
                sentencia.setString(7, planAcademia.getObjetivoGeneral());
                sentencia.setString(8, planAcademia.getEstado().name());
                sentencia.execute();

                guardarObjetivosParticulares(idPlan, planAcademia.getObjetivosParticulares());            
                guardarExamenesParciales(idPlan, planAcademia.getExamenesParciales());
                guardarFormasDeEvaluacion(idPlan, planAcademia.getFormasDeEvaluacion());
                guardarHistoricoDeRevision(idPlan, planAcademia.getHistoricoDeRevisiones());
                guardarFirmaDeAutorizacion(idPlan, planAcademia.getAutorizacion());
                guardadoRealizado = true;
            } 
            catch (SQLException excepcionBaseDatos) {
                loggerDelSistema.error("guardarPlanTrabajoAcademiaCompleto: Ocurrió un problema en BD");
            }
            catch (NullPointerException excepcionNullPointer) {
                loggerDelSistema.error("guardarPlanTrabajoAcademiaCompleto: Valor nulo");
                eliminarDatosPlan(idPlan);
                guardadoRealizado = false;
            }
            finally{
                DataBase.closeConnection();
            } 
        }
        return guardadoRealizado;
    }
    
    /**
     * Almacena solo los datos ingresados por el Coordinador al Plan de Trabajo 
     * de Academia (Detalles general del Plan, Objetivos particulares, Metas, Acciones, 
     * Temas de Parciales y Formas de evaluación).
     * Si ocurre algun error al guardar un dato, todos los datos de este
     * Plan de Trabajo de Academia serán eliminados, con el fin de 
     * salvaguardar la integridad de los datos.
     * 
     * @param planAcademia Datos de un PlanTrabajoAcademia
     * @return true si todos los datos fueron guardados exitosamente, false si no fue asi
     */    
    @Override
    public boolean guardarPlanTrabajoAcademia(PlanTrabajoAcademia planAcademia) {
        boolean guardadoRealizado = false;
        barricada = BarricadaPlanTrabajoAcademiaDAO.obtenerInstancia();
        
        if (barricada.validarDatosPlanAcademia(planAcademia)) {
            AcademicoDAO academicoDAO = new AcademicoDAO();
            AcademiaDAO academiaDAO = new AcademiaDAO();
            Academico coordinador = academicoDAO.obtenerAcademico(planAcademia.getNombreCoordinador());
            Academia academia = academiaDAO.obtenerAcademiaPorNombre(planAcademia.getNombreAcademia());
            String idPlan = planAcademia.getId();
            consultaSQL = "INSERT INTO plan_trabajo_academia values (?,?,?,?,?,?,?,?)";
            conexionDB = DataBase.getDataBaseConnection();
            try {
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setString(1, idPlan);
                sentencia.setDate(2, planAcademia.getFechaAprobacion());
                sentencia.setString(3, planAcademia.getProgramaEducativo());
                sentencia.setString(4, planAcademia.getPeriodoEscolar());
                sentencia.setString(5, academia.getIdAcademia());
                sentencia.setInt(6, coordinador.getNumeroPersonal());
                sentencia.setString(7, planAcademia.getObjetivoGeneral());
                sentencia.setString(8, planAcademia.getEstado().name());
                sentencia.execute();

                guardarObjetivosParticulares(idPlan, planAcademia.getObjetivosParticulares());            
                guardarExamenesParciales(idPlan, planAcademia.getExamenesParciales());
                guardarFormasDeEvaluacion(idPlan, planAcademia.getFormasDeEvaluacion());
                guardadoRealizado = true;
            } 
            catch (SQLException excepcionBaseDatos) {
                loggerDelSistema.error("guardarPlanTrabajoAcademia: Ocurrió un problema en BD");
            }
            catch (NullPointerException excepcionNullPointer) {
                loggerDelSistema.error("guardarPlanTrabajoAcademia: Valor nulo");
                eliminarDatosPlan(idPlan);
                guardadoRealizado = false;
            }
            finally{
                DataBase.closeConnection();
            }   
        }
        return guardadoRealizado;
    }
    
    /**
     * Actualiza los datos de un PlanTrabajoAcademia que fué almacenado con el 
     * estado EN_EDICION.
     * 
     * @param planAcademia Datos del PlanTrabajoAcademia actualizados
     * @return true si los datos fueron actualizados con éxito, false si no fue así
     */    
    @Override
    public boolean actualizarDatosPlan(PlanTrabajoAcademia planAcademia) {
        boolean confirmacionGuardado = false;
        if (planAcademia != null) {
            if (eliminarDatosPlan(planAcademia.getId())) {
                if (guardarPlanTrabajoAcademia(planAcademia)) {
                    confirmacionGuardado = true;
                }
            }  
        }
        return confirmacionGuardado;
    }
    
    /**
     * Almacena los Objetivos Particulares del PlanTrabajoAcademia
     * 
     * @param idPlanTrabajoAcademia Identificador del PlanTrabajoAcademia al cual estarán relacionados los Objetivos Particulares
     * @param objetivosParticulares Lista de Objetivos Particulares a guardar
     * @return true si los datos fueron guardados con exito, false si no fue así
     */
    @Override
    public boolean guardarObjetivosParticulares(String idPlanTrabajoAcademia, ArrayList<ObjetivoParticular> objetivosParticulares){
        boolean guardadoRealizado = false;
        consultaSQL = "INSERT INTO plan_trabajo_academia_objetivo_particular values (?,?,?)";
        conexionDB = DataBase.getDataBaseConnection();
        barricada = BarricadaPlanTrabajoAcademiaDAO.obtenerInstancia();
        
        if (barricada.validarObjetivosParticulares(objetivosParticulares)) {
            try {
                for (ObjetivoParticular objetivo : objetivosParticulares){
                    sentencia = conexionDB.prepareStatement(consultaSQL);
                    sentencia.setString(1, idPlanTrabajoAcademia);
                    sentencia.setString(2, objetivo.getId());
                    sentencia.setString(3, objetivo.getDescripcion());
                    sentencia.execute();
                    guardarMetasDeObjetivoParticular(idPlanTrabajoAcademia, objetivo.getId(), objetivo.getMetasDeObjetivo());                
                }
                guardadoRealizado = true;
            } 
            catch (SQLException excepcionBaseDatos) {
                loggerDelSistema.error("guardarObjetivosParticulares: Ocurrió un problema en BD");
            }
            catch (NullPointerException excepcionNullPointer) {
                loggerDelSistema.error("guardarObjetivosParticulares: Valor nulo");
                eliminarDatosPlan(idPlanTrabajoAcademia);
                guardadoRealizado = false;            
            }
            finally{
                DataBase.closeConnection();
            }            
        }
        return guardadoRealizado;
    }
    
    /**
     * Almacena las Metas de un Objetivo Particular específico
     * 
     * @param idPlanTrabajoAcademia Identificador del PlanTrabajoAcademia al cual estarán relacionadas las Metas de Objetivo Particular
     * @param idObjetivoParticular Identificador del Objetivo Particular al cual estarán relacionadas las Metas de Objetivo Particular 
     * @param metasDeObjetivo Lista de Metas de un Objetivo Particular específico
     * @return true si los datos fueron guardados exitosamente, false si no fue así
     */
    @Override
    public boolean guardarMetasDeObjetivoParticular(String idPlanTrabajoAcademia, String idObjetivoParticular, ArrayList<MetaDeObjetivo> metasDeObjetivo){
        boolean guardadoRealizado = false;
        consultaSQL = "INSERT INTO plan_trabajo_academia_objetivo_particular_meta values (?,?,?,?)";
        conexionDB = DataBase.getDataBaseConnection();
        try {
            for (MetaDeObjetivo meta : metasDeObjetivo){
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setString(1, idPlanTrabajoAcademia);
                sentencia.setString(2, idObjetivoParticular);
                sentencia.setString(3, meta.getId());
                sentencia.setString(4, meta.getDescripcion());
                sentencia.execute();
                guardarAccionesDeMeta(idPlanTrabajoAcademia, idObjetivoParticular, meta.getId(), meta.getAccionesDeMeta());
            }
            guardadoRealizado = true;
        } 
        catch (SQLException excepcionBaseDatos) {
            loggerDelSistema.error("guardarMetasDeObjetivoParticular: Ocurrió un problema en BD");
        }
        catch (NullPointerException excepcionNullPointer) {
            loggerDelSistema.error("guardarMetasDeObjetivoParticular: Valor nulo");
            eliminarDatosPlan(idPlanTrabajoAcademia);
            guardadoRealizado = false;            
        }
        finally{
            DataBase.closeConnection();
        }
        return guardadoRealizado;
    }
    
    /**
     * Almancena las Acciones de una Meta específica
     * 
     * @param idPlanTrabajoAcademia Identificador del PlanTrabajoAcademia al cual estarán relacionadas las Acciones de Meta
     * @param idObjetivoParticular Identificador del Objetivo Particular al cual estarán relacionadas las Acciones de Meta
     * @param idMeta Identificador de la Meta a la cual estarán relacionadas las Acciones de Meta
     * @param accionesDeMeta Lista de Acciones para una Meta específica
     * @return true si los datos fueron guardados exitosamente, false si no fue así
     */
    @Override
    public boolean guardarAccionesDeMeta(String idPlanTrabajoAcademia, String idObjetivoParticular, String idMeta, ArrayList<AccionDeMeta> accionesDeMeta){
        boolean guardadoRealizado = false;
        consultaSQL = "INSERT INTO plan_trabajo_academia_objetivo_particular_meta_accion values (?,?,?,?,?,?)";
        conexionDB = DataBase.getDataBaseConnection();
        try {
            for (int i = 0; i < accionesDeMeta.size(); i++){
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setString(1, idPlanTrabajoAcademia);
                sentencia.setString(2, idObjetivoParticular);
                sentencia.setString(3, idMeta);
                sentencia.setString(4, accionesDeMeta.get(i).getDescripcionAccion());
                sentencia.setString(5, accionesDeMeta.get(i).getFechaSemana());
                sentencia.setString(6, accionesDeMeta.get(i).getFormaOperar());
                sentencia.execute();
            }
            guardadoRealizado = true;
        } 
        catch (SQLException excepcionBaseDatos) {
            loggerDelSistema.error("guardarAccionesDeMeta: Ocurrió un problema en BD");
        }
        catch (NullPointerException excepcionNullPointer) {
            loggerDelSistema.error("guardarAccionesDeMeta: Valor nulo");
            eliminarDatosPlan(idPlanTrabajoAcademia);
            guardadoRealizado = false;            
        }
        finally{
            DataBase.closeConnection();
        }
        return guardadoRealizado;
    }
    
    /**
     * Almacena los Examenes Parciales de las Experiencias Educativas del PlanTrabajoAcademia
     * 
     * @param idPlanTrabajoAcademia Identificador del PlanTrabajoAcademia al cual estarán realacionados las Experiencias Educativas con sus parciales
     * @param experienciasConParciales Lista de ExperienciaEducativaConParciales
     * @return true si los datos fueron guardados exitosamente, false si no fue así
     */
    @Override
    public boolean guardarExamenesParciales(String idPlanTrabajoAcademia, ArrayList<ExperienciaEducativaConParciales> experienciasConParciales){
        boolean guardadoRealizado = false;
        barricada = BarricadaPlanTrabajoAcademiaDAO.obtenerInstancia();
        
        if (barricada.validarExamenesParciales(experienciasConParciales)) {
            guardarCantidadExamenesExperienciaEducativa(idPlanTrabajoAcademia, experienciasConParciales);        
            for (ExperienciaEducativaConParciales eeParcial : experienciasConParciales){
                if (guardarTemasParcialExperienciaEducativa(idPlanTrabajoAcademia, eeParcial.getExperienciaEducativa(), eeParcial.getExamenesParciales())){
                    guardadoRealizado = true;
                }
            }  
        }
        return guardadoRealizado;
    }
    
    /**
     * Almacena la cantidad de ExamenParcial por Experiencia Educativa.
     * 
     * @param idPlanTrabajoAcademia Identificador del PlanTrabajoAcademia al cual estará realacionada la cantidad de examenes parciales por Experiencia Educativa
     * @param experienciasConParciales Lista de ExperienciaEducativaConParciales
     * @return true si los datos fueron guardados exitosamente, false si no fue así
     */
    @Override
    public boolean guardarCantidadExamenesExperienciaEducativa(String idPlanTrabajoAcademia, ArrayList<ExperienciaEducativaConParciales> experienciasConParciales) {
        boolean guardadoRealizado = false;
        consultaSQL = "INSERT INTO plan_trabajo_academia_examen_parcial_ee values (?,?,?)";
        conexionDB = DataBase.getDataBaseConnection();
        try {
            for (ExperienciaEducativaConParciales eeParcial : experienciasConParciales){
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setString(1, idPlanTrabajoAcademia);
                sentencia.setString(2, eeParcial.getExperienciaEducativa());
                sentencia.setInt(3, eeParcial.getExamenesParciales().size());
                sentencia.execute();   
            }
            guardadoRealizado = true;
        } 
        catch (SQLException excepcionBaseDatos) {
            loggerDelSistema.error("guardarCantidadExamenesExperienciaEducativa: Ocurrió un problema en BD");
        }
        catch (NullPointerException excepcionNullPointer) {
            loggerDelSistema.error("guardarCantidadExamenesExperienciaEducativa: Valor nulo");
            eliminarDatosPlan(idPlanTrabajoAcademia);
            guardadoRealizado = false;            
        }
        finally{
            DataBase.closeConnection();
        }
        return guardadoRealizado;
    }
    
    /**
     * Almacena los temas para un Exámen Parcial de una Experiencia Educativa
     * @param idPlanTrabajoAcademia Identificador del PlanTrabajoAcademia al cual estarán relacionados los temas de un parcial para una Experiencia Educativa
     * @param experienciaEducativa Nombre de Experiencia Educativa a la cual estarán relacionados los temas de cada parcial
     * @param examenesParciales Lista de Temas para Exámenes Parciales de una Experiencia Educativa
     * @return true si los datos fueron guardados exitosamente, false si no fue así
     */
    @Override
    public boolean guardarTemasParcialExperienciaEducativa(String idPlanTrabajoAcademia, String experienciaEducativa, ArrayList<ExamenParcial> examenesParciales) {
        boolean guardadoRealizado = false;
        consultaSQL = "INSERT INTO plan_trabajo_academia_examen_parcial_tema values (?,?,?,?)";
        conexionDB = DataBase.getDataBaseConnection();
        try {
            for (ExamenParcial examen: examenesParciales){
                ArrayList<String> temasParcial = examen.getTemasDeParcial();
                
                for (int i = 0; i < temasParcial.size(); i++){
                    sentencia = conexionDB.prepareStatement(consultaSQL);
                    sentencia.setString(1, idPlanTrabajoAcademia);
                    sentencia.setString(2, experienciaEducativa);
                    sentencia.setInt(3, examen.getId());
                    sentencia.setString(4, temasParcial.get(i));
                    sentencia.execute();                    
                }
            }
            guardadoRealizado = true;
        } 
        catch (SQLException excepcionBaseDatos) {
            loggerDelSistema.error("guardarTemasParcialExperienciaEducativa: Ocurrió un problema en BD");
        }
        catch (NullPointerException excepcionNullPointer) {
            loggerDelSistema.error("guardarTemasParcialExperienciaEducativa: Valor nulo");
            eliminarDatosPlan(idPlanTrabajoAcademia);
            guardadoRealizado = false;            
        }
        finally{
            DataBase.closeConnection();
        }
        return guardadoRealizado;
    }
    
    /**
     * Almancena las Formas de Evaluación para una Experiencia Educativa del PlanTrabajoAcademia
     * 
     * @param idPlanTrabajoAcademia Identificador del PlanTrabajoAcademia al cual estarán relacionadas las Formas de Evaluación
     * @param formasDeEvaluacion Lista de FormaDeEvaluación
     * @return true si los datos fueron almacenados exitosamente, false si no fue así
     */
    @Override
    public boolean guardarFormasDeEvaluacion(String idPlanTrabajoAcademia, ArrayList<FormaDeEvaluacion> formasDeEvaluacion) {
        boolean guardadoRealizado = false;
        consultaSQL = "INSERT INTO plan_trabajo_academia_forma_evaluacion values (?,?,?)";
        conexionDB = DataBase.getDataBaseConnection();
        barricada = BarricadaPlanTrabajoAcademiaDAO.obtenerInstancia();
        
        if (barricada.validarFormasDeEvaluacion(formasDeEvaluacion)) {
            try {
                for (int i = 0; i < formasDeEvaluacion.size(); i++){
                    sentencia = conexionDB.prepareStatement(consultaSQL);
                    sentencia.setString(1, idPlanTrabajoAcademia);
                    sentencia.setString(2, formasDeEvaluacion.get(i).getElemento());
                    sentencia.setFloat(3, formasDeEvaluacion.get(i).getPorcentaje());
                    sentencia.execute();
                }
                guardadoRealizado = true;
            } 
            catch (SQLException excepcionBaseDatos) {
                loggerDelSistema.error("guardarFormasDeEvaluacion: Ocurrió un problema en BD");
            }
            catch (NullPointerException excepcionNullPointer) {
                loggerDelSistema.error("guardarFormasDeEvaluacion: Valor nulo");
                eliminarDatosPlan(idPlanTrabajoAcademia);
                guardadoRealizado = false;            
            }
            finally{
                DataBase.closeConnection();
            }  
        }
        return guardadoRealizado;
    }
    
    /**
     * Almacena todas las revisiones del PlanTrabajoAcademia
     * @param idPlanTrabajoAcademia Identificador del PlanTrabajoAcademia al cual estarán realacionadas las Revisiones de mismo
     * @param historicoDeRevisiones Lista de Revision realizadas al PlanTrabajoAcademia
     * @return true si los datos fueron almacenados exitosamente, false si no fue así
     */
    @Override
    public boolean guardarHistoricoDeRevision(String idPlanTrabajoAcademia, ArrayList<Revision> historicoDeRevisiones) {
        boolean guardadoRealizado = false;
        consultaSQL = "INSERT INTO plan_trabajo_academia_historico_de_revision values (?,?,?,?,?)";
        conexionDB = DataBase.getDataBaseConnection();
        barricada = BarricadaPlanTrabajoAcademiaDAO.obtenerInstancia();
        
        if (barricada.validarHistoricoDeRevision(historicoDeRevisiones)) {
            try {
                for (int i = 0; i < historicoDeRevisiones.size(); i++){
                    sentencia = conexionDB.prepareStatement(consultaSQL);
                    sentencia.setString(1, idPlanTrabajoAcademia);
                    sentencia.setInt(2, historicoDeRevisiones.get(i).getNumeroRevision());
                    sentencia.setDate(3, historicoDeRevisiones.get(i).getFecha());
                    sentencia.setString(4, historicoDeRevisiones.get(i).getSeccionPaginaModificada());
                    sentencia.setString(5, historicoDeRevisiones.get(i).getDescripcionDeModificacion());
                    sentencia.execute();
                }
                guardadoRealizado = true;
            } 
            catch (SQLException excepcionBaseDatos) {
                loggerDelSistema.error("guardarHistoricoDeRevision: Ocurrió un problema en BD");
            }
            catch (NullPointerException excepcionNullPointer) {
                loggerDelSistema.error("guardarHistoricoDeRevision: Valor nulo");
                eliminarDatosPlan(idPlanTrabajoAcademia);
                guardadoRealizado = false;
            }
            finally{
                DataBase.closeConnection();
            }   
        }
                
        return guardadoRealizado;
    }
    
    /**
     * Almacena la firma de autorización del PlanTrabajoAcademia
     * 
     * @param idPlanTrabajoAcademia Identificador del PlanTrabajoAcademia al cual estará relacionada la FirmaDeAutorizacion del mismo
     * @param firmaDeAutorizacion Firma de Autorización del PlanTrabajoAcademia
     * @return true si los datos fueron almacenados exitosamente, false si no fue así
     */
    @Override
    public boolean guardarFirmaDeAutorizacion(String idPlanTrabajoAcademia, FirmaAutorizacion firmaDeAutorizacion) {
        boolean guardadoRealizado = false;
        consultaSQL = "INSERT INTO plan_trabajo_academia_autorizacion values (?,?,?,?,?)";
        conexionDB = DataBase.getDataBaseConnection();
        barricada = BarricadaPlanTrabajoAcademiaDAO.obtenerInstancia();
        
        //if (barricada.validarFirmaAutorizacion(firmaDeAutorizacion)) {
            try {
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setString(1, idPlanTrabajoAcademia);
                sentencia.setString(2, firmaDeAutorizacion.getPersonaQueProponePlan());
                sentencia.setString(3, firmaDeAutorizacion.getPersonaQueAutorizaPlan());
                sentencia.setDate(4, firmaDeAutorizacion.getFechaAutorizacion());
                sentencia.setDate(5, firmaDeAutorizacion.getFechaEntradaEnVigor());
                sentencia.execute();

                guardadoRealizado = true;
            } 
            catch (SQLException excepcionBaseDatos) {
                loggerDelSistema.error("guardarFirmaDeAutorizacion: Ocurrió un problema en BD");
            }
            catch (NullPointerException excepcionNullPointer) {
                loggerDelSistema.error("guardarFirmaDeAutorizacion: Valor nulo");
                eliminarDatosPlan(idPlanTrabajoAcademia);
                guardadoRealizado = false;            
            }
            finally{
                DataBase.closeConnection();
            }  
        //}
        return guardadoRealizado;
    }

    /**
     * Recupera los datos de un PlanTrabajoAcademia específico
     * 
     * @param idPlanTrabajoAcademia Identificador del PlanTrabajoAcademia a recuperar desde la Base de Datos
     * @return PlanTrabajoAcademia que tenga como Id el valor del parámetro idPlanTrabajoAcademia
     */
    @Override
    public PlanTrabajoAcademia buscarPlanTrabajoByID(String idPlanTrabajoAcademia) {
        PlanTrabajoAcademia planAcademia = new PlanTrabajoAcademia();
        AcademiaDAO academiaDAO = new AcademiaDAO();
        consultaSQL = "SELECT * from plan_trabajo_academia where Id=?";
        conexionDB = DataBase.getDataBaseConnection();
        barricada = BarricadaPlanTrabajoAcademiaDAO.obtenerInstancia();
        
        if (barricada.validarIdPlanAcademia(idPlanTrabajoAcademia)) {
            try {
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setString(1, idPlanTrabajoAcademia);
                ResultSet resultadoSentencia = sentencia.executeQuery();

                while (resultadoSentencia.next()){
                    planAcademia.setId(idPlanTrabajoAcademia);
                    planAcademia.setFechaAprobacion(resultadoSentencia.getDate("Fecha_Aprobacion"));
                    planAcademia.setProgramaEducativo(resultadoSentencia.getString("Programa_Educativo"));
                    planAcademia.setPeriodoEscolar(resultadoSentencia.getString("Periodo_Escolar"));
                    Academia academia = academiaDAO.obtenerAcademia(resultadoSentencia.getString("Id_Academia"));
                    planAcademia.setNombreAcademia(academia.getNombreAcademia());
                    planAcademia.setNombreCoordinador(resultadoSentencia.getString("Id_Coordinador"));
                    planAcademia.setObjetivoGeneral(resultadoSentencia.getString("Objetivo_General"));
                    planAcademia.setEstado(EstadoDeDocumento.valueOf(resultadoSentencia.getString("Estado")));

                    planAcademia.setObjetivosParticulares(obtenerObjetivosParticulares(planAcademia.getId()));
                    planAcademia.setExamenesParciales(obtenerExamenesPorExperiencia(planAcademia.getId()));
                    planAcademia.setFormasDeEvaluacion(obtenerFormasDeEvaluacion(planAcademia.getId()));
                    planAcademia.setHistoricoDeRevisiones(obtenerHistoricoDeRevision(planAcademia.getId()));
                    planAcademia.setAutorizacion(obtenerFirmaAutorizacion(planAcademia.getId()));
                }

            } 
            catch (SQLException excepcionBaseDatos) {
                loggerDelSistema.error("buscarPlanTrabajoByID: Ocurrió un problema en BD");
            }
            catch (NullPointerException excepcionNullPointer) {
                loggerDelSistema.error("buscarPlanTrabajoByID: Valor nulo");
            }
            finally {
                DataBase.closeConnection();
            }            
        }
        return planAcademia;
    }

    /**
     * Obtiene todos los PlanTrabajoAcademia con estado CONCLUIDO e idCoordinador
     * registrado como su autor.
     * 
     * @param idCoordinador Número de Personal del Académico Coordinador de un Academia
     * @return Lista de PlanTrabajoAcademia concluidos por el Académico
     */
    @Override
    public ArrayList<PlanTrabajoAcademia> buscarPlanTrabajoByCoordinador(int idCoordinador) {
        ArrayList<PlanTrabajoAcademia> planesAcademiaDeCoordinador = new ArrayList<>();
        PlanTrabajoAcademia planAcademia;
        AcademicoDAO academicoDAO;
        AcademiaDAO academiaDAO;
        consultaSQL = "SELECT * from plan_trabajo_academia where Id_Coordinador=? and Estado=?";
        conexionDB = DataBase.getDataBaseConnection();
        
        if (idCoordinador < Integer.MAX_VALUE) {
            try {
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setInt(1, idCoordinador);
                sentencia.setString(2, "CONCLUIDO");
                ResultSet resultadoSentencia = sentencia.executeQuery();

                while (resultadoSentencia.next()){
                    planAcademia = new PlanTrabajoAcademia();
                    academicoDAO = new AcademicoDAO();
                    academiaDAO = new AcademiaDAO();
                    Academico coordinador = academicoDAO.obtenerAcademico(idCoordinador);
                    Academia academia = academiaDAO.obtenerAcademia(resultadoSentencia.getString("Id_Academia"));

                    planAcademia.setId(resultadoSentencia.getString("Id"));
                    planAcademia.setFechaAprobacion(resultadoSentencia.getDate("Fecha_Aprobacion"));
                    planAcademia.setProgramaEducativo(resultadoSentencia.getString("Programa_Educativo"));
                    planAcademia.setPeriodoEscolar(resultadoSentencia.getString("Periodo_Escolar"));
                    planAcademia.setNombreAcademia(academia.getNombreAcademia());
                    planAcademia.setNombreCoordinador(coordinador.getNombreAcademico());
                    planAcademia.setObjetivoGeneral(resultadoSentencia.getString("Objetivo_General"));
                    planAcademia.setEstado(EstadoDeDocumento.valueOf(resultadoSentencia.getString("Estado")));

                    planAcademia.setObjetivosParticulares(obtenerObjetivosParticulares(planAcademia.getId()));
                    planAcademia.setExamenesParciales(obtenerExamenesPorExperiencia(planAcademia.getId()));
                    planAcademia.setFormasDeEvaluacion(obtenerFormasDeEvaluacion(planAcademia.getId()));
                    planAcademia.setHistoricoDeRevisiones(obtenerHistoricoDeRevision(planAcademia.getId()));
                    planAcademia.setAutorizacion(obtenerFirmaAutorizacion(planAcademia.getId()));

                    planesAcademiaDeCoordinador.add(planAcademia);
                }

            } 
            catch (SQLException excepcionBaseDatos) {
                loggerDelSistema.error("buscarPlanTrabajoByCoordinador: Ocurrió un problema en BD");
            }
            catch (NullPointerException excepcionNullPointer) {
                loggerDelSistema.error("buscarPlanTrabajoByCoordinador: Valor nulo");
            }
            finally{
                DataBase.closeConnection();
            } 
        }
        return planesAcademiaDeCoordinador;
    }
    
    /**
     * Obtiene los PlanTrabajoAcademia con estado EN_EDICION e idCoordinador
     * registrado como su autor.
     * 
     * @param idCoordinador Número de Personal del Académico Coordinador de un Academia
     * @return Lista de PlanTrabajoAcademia disponibles para editar
     */    
    @Override
    public ArrayList<PlanTrabajoAcademia> obtenerPlanesEnEdicion(int idCoordinador) {
        ArrayList<PlanTrabajoAcademia> planesAcademiaDeCoordinador = new ArrayList<>();
        PlanTrabajoAcademia planAcademia;
        AcademicoDAO academicoDAO;
        AcademiaDAO academiaDAO;
        consultaSQL = "SELECT * from plan_trabajo_academia where Id_Coordinador=? and Estado=?";
        conexionDB = DataBase.getDataBaseConnection();
        
        if (idCoordinador < Integer.MAX_VALUE) {
            try {
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setInt(1, idCoordinador);
                sentencia.setString(2, "EN_EDICION");
                ResultSet resultadoSentencia = sentencia.executeQuery();

                while (resultadoSentencia.next()){
                    planAcademia = new PlanTrabajoAcademia();
                    academicoDAO = new AcademicoDAO();
                    academiaDAO = new AcademiaDAO();
                    Academico coordinador = academicoDAO.obtenerAcademico(idCoordinador);
                    Academia academia = academiaDAO.obtenerAcademia(resultadoSentencia.getString("Id_Academia"));

                    planAcademia.setId(resultadoSentencia.getString("Id"));
                    planAcademia.setFechaAprobacion(resultadoSentencia.getDate("Fecha_Aprobacion"));
                    planAcademia.setProgramaEducativo(resultadoSentencia.getString("Programa_Educativo"));
                    planAcademia.setPeriodoEscolar(resultadoSentencia.getString("Periodo_Escolar"));
                    planAcademia.setNombreAcademia(academia.getNombreAcademia());
                    planAcademia.setNombreCoordinador(coordinador.getNombreAcademico());
                    planAcademia.setObjetivoGeneral(resultadoSentencia.getString("Objetivo_General"));
                    planAcademia.setEstado(EstadoDeDocumento.valueOf(resultadoSentencia.getString("Estado")));

                    planAcademia.setObjetivosParticulares(obtenerObjetivosParticulares(planAcademia.getId()));
                    planAcademia.setExamenesParciales(obtenerExamenesPorExperiencia(planAcademia.getId()));
                    planAcademia.setFormasDeEvaluacion(obtenerFormasDeEvaluacion(planAcademia.getId()));
                    planAcademia.setHistoricoDeRevisiones(obtenerHistoricoDeRevision(planAcademia.getId()));
                    planAcademia.setAutorizacion(obtenerFirmaAutorizacion(planAcademia.getId()));

                    planesAcademiaDeCoordinador.add(planAcademia);
                }

            } 
            catch (SQLException excepcionBaseDatos) {
                loggerDelSistema.error("obtenerPlanesEnEdicion: Ocurrió un problema en BD");
            }
            catch (NullPointerException excepcionNullPointer) {
                loggerDelSistema.error("obtenerPlanesEnEdicion: Valor nulo");
            }
            finally{
                DataBase.closeConnection();
            }
        }
        return planesAcademiaDeCoordinador;
    }
    
    /**
     * Recupera los Objetivos Particulares relacionados a un PlanTrabajoAcademia específico
     * 
     * @param idPlanTrabajoAcademia Identificador del PlanTrabajoAcademia dell que se recuperarán sus ObjetivoParticular
     * @return Lista de ObjetivoParticular
     */
    @Override
    public ArrayList<ObjetivoParticular> obtenerObjetivosParticulares(String idPlanTrabajoAcademia) {
        ArrayList<ObjetivoParticular> objetivosParticulares = new ArrayList<>();
        ObjetivoParticular objetivoParticular;
        consultaSQL = "SELECT Id_Objetivo_Particular, Descripcion from plan_trabajo_academia_objetivo_particular where Id_Plan_Academia=?";
        conexionDB = DataBase.getDataBaseConnection();
        barricada = BarricadaPlanTrabajoAcademiaDAO.obtenerInstancia();
        
        if  (barricada.validarIdPlanAcademia(idPlanTrabajoAcademia)) {
            try {
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setString(1, idPlanTrabajoAcademia);
                ResultSet resultadoSentencia = sentencia.executeQuery();

                while (resultadoSentencia.next()){
                    objetivoParticular = new ObjetivoParticular();
                    objetivoParticular.setId(resultadoSentencia.getString("Id_Objetivo_Particular"));
                    objetivoParticular.setDescripcion(resultadoSentencia.getString("Descripcion"));
                    objetivoParticular.setMetasDeObjetivo(obtenerMetasDeObjetivo(idPlanTrabajoAcademia, objetivoParticular.getId()));
                    objetivosParticulares.add(objetivoParticular);
                }

            } 
            catch (SQLException excepcionBaseDatos) {
                loggerDelSistema.error("obtenerObjetivosParticulares: Ocurrió un problema en BD");
            }
            catch (NullPointerException excepcionNullPointer) {
                loggerDelSistema.error("obtenerObjetivosParticulares: Valor nulo");
            }
            finally{
                DataBase.closeConnection();
            }  
        }
        return objetivosParticulares;
    }
    
    /**
     * Recupera las Metas de un Objetivo Particular específico de un PlanTrabajoAcademia
     * 
     * @param idPlanTrabajoAcademia Identificador del PlanTrabajoAcademia del cual se recuperará las MetaDeObjetivo de un ObjetivoParticular
     * @param idObjetivoParticular Identificador del ObjetivoParticular del cual se recuperará sus MetaDeObjetivo
     * @return Lista de MetaDeObjetivo asociadas al idObjetivoParticular
     */
    @Override
    public ArrayList<MetaDeObjetivo> obtenerMetasDeObjetivo(String idPlanTrabajoAcademia, String idObjetivoParticular) {
        ArrayList<MetaDeObjetivo> metasDeObjetivo = new ArrayList<>();
        MetaDeObjetivo metaDeObjetivo;
        consultaSQL = "SELECT Id_Meta, Descripcion from plan_trabajo_academia_objetivo_particular_meta where Id_Plan_Academia=? and Id_Objetivo_Particular=?";
        conexionDB = DataBase.getDataBaseConnection();
        barricada = BarricadaPlanTrabajoAcademiaDAO.obtenerInstancia();
        
        if (barricada.validarIdPlanAcademia(idPlanTrabajoAcademia) && barricada.validarIdObjetivoParticular(idObjetivoParticular)) {
            try {
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setString(1, idPlanTrabajoAcademia);
                sentencia.setString(2, idObjetivoParticular);
                ResultSet resultadoSentencia = sentencia.executeQuery();

                while (resultadoSentencia.next()){
                    metaDeObjetivo = new MetaDeObjetivo();
                    metaDeObjetivo.setId(resultadoSentencia.getString("Id_Meta"));
                    metaDeObjetivo.setDescripcion(resultadoSentencia.getString("Descripcion"));
                    metaDeObjetivo.setAccionesDeMeta(obtenerAccionesDeMeta(idPlanTrabajoAcademia, idObjetivoParticular, metaDeObjetivo.getId()));
                    metasDeObjetivo.add(metaDeObjetivo);
                }

            } 
            catch (SQLException excepcionBaseDatos) {
                loggerDelSistema.error("obtenerMetasDeObjetivo: Ocurrió un problema en BD");
            }
            catch (NullPointerException excepcionNullPointer) {
                loggerDelSistema.error("obtenerMetasDeObjetivo: Valor nulo");
            }
            finally{
                DataBase.closeConnection();
            }
        }
        return metasDeObjetivo;
    }

    /**
     * Recupera las Acciones de una Meta expecífica
     * 
     * @param idPlanTrabajoAcademia Identificador del PlanTrabajoAcademia del cual se recuperarán las AccionDeMeta de uno de sus ObjetivoParticular
     * @param idObjetivoParticular Identificador del ObjetivoParticular del cual se recuperarán las AccionDeMeta de una de sus MetaDeObjetivo
     * @param idMeta Identificador de la MetaDeObjetivo de la cual se recuperarán sus AccionDeMeta
     * @return Lista de AccionDeMeta asociadas al idMeta
     */
    @Override
    public ArrayList<AccionDeMeta> obtenerAccionesDeMeta(String idPlanTrabajoAcademia, String idObjetivoParticular, String idMeta) {
        ArrayList<AccionDeMeta> accionesDeMeta = new ArrayList<>();
        AccionDeMeta accionDeMeta;
        consultaSQL = "SELECT Descripcion_Accion, Fecha_Semana, Forma_Operar from plan_trabajo_academia_objetivo_particular_meta_accion where Id_Plan_Academia=? and Id_Objetivo_Particular=? and Id_Meta=?";
        conexionDB = DataBase.getDataBaseConnection();
        barricada = BarricadaPlanTrabajoAcademiaDAO.obtenerInstancia();
        
        if (barricada.validarIdPlanAcademia(idPlanTrabajoAcademia) && barricada.validarIdObjetivoParticular(idObjetivoParticular) && barricada.validarIdMeta(idMeta)) {
            try {
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setString(1, idPlanTrabajoAcademia);
                sentencia.setString(2, idObjetivoParticular);
                sentencia.setString(3, idMeta);
                ResultSet resultadoSentencia = sentencia.executeQuery();

                while (resultadoSentencia.next()){
                    accionDeMeta = new AccionDeMeta();
                    accionDeMeta.setDescripcionAccion(resultadoSentencia.getString("Descripcion_Accion"));
                    accionDeMeta.setFechaSemana(resultadoSentencia.getString("Fecha_Semana"));
                    accionDeMeta.setFormaOperar(resultadoSentencia.getString("Forma_Operar"));
                    accionesDeMeta.add(accionDeMeta);
                }

            } 
            catch (SQLException excepcionBaseDatos) {
                loggerDelSistema.error("obtenerAccionesDeMeta: Ocurrió un problema en BD");
            }
            catch (NullPointerException excepcionNullPointer) {
                loggerDelSistema.error("obtenerAccionesDeMeta: Valor nulo");
            }
            finally{
                DataBase.closeConnection();
            }
            
        }
        return accionesDeMeta;
    }

    /**
     * Recupera las Experiencias Educativa con los temas de sus Exámenes Parciales
     * 
     * @param idPlanTrabajoAcademia Identificador del PlanTrabajoAcademia del cual se recuperarán sus ExperienciaEducativaConParciales
     * @return Lista de ExperienciaEducativaConParcial asociadas al idPlanTrabajoAcademia
     */
    @Override
    public ArrayList<ExperienciaEducativaConParciales> obtenerExamenesPorExperiencia(String idPlanTrabajoAcademia) {
        ArrayList<ExperienciaEducativaConParciales> eesConParciales = new ArrayList<>();
        ArrayList<ExamenParcial> examenesDeEE;
        consultaSQL = "SELECT EE, Cantidad_Examenes_Parciales from plan_trabajo_academia_examen_parcial_ee where Id_Plan_Academia=?";
        conexionDB = DataBase.getDataBaseConnection();
        barricada = BarricadaPlanTrabajoAcademiaDAO.obtenerInstancia();
        
        if (barricada.validarIdPlanAcademia(idPlanTrabajoAcademia)) {
            try {
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setString(1, idPlanTrabajoAcademia);
                ResultSet resultadoConsulta = sentencia.executeQuery();

                while (resultadoConsulta.next()){
                    ExperienciaEducativaConParciales eeConParcial = new ExperienciaEducativaConParciales();
                    examenesDeEE = new ArrayList<>();

                    eeConParcial.setExperienciaEducativa(resultadoConsulta.getString("EE"));

                    int cantidadParciales = obtenerCantidadExamenesPorExperiencia(idPlanTrabajoAcademia, eeConParcial.getExperienciaEducativa());

                    for (int a = 1; a <= cantidadParciales; a++){
                        ExamenParcial examen = new ExamenParcial();
                        examen.setId(a);
                        examen.setTemasDeParcial(obtenerTemasDeParcial(idPlanTrabajoAcademia, eeConParcial.getExperienciaEducativa(), a));
                        examenesDeEE.add(examen);
                    }

                    eeConParcial.setExamenesParciales(examenesDeEE);

                    eesConParciales.add(eeConParcial);
                }

            } 
            catch (SQLException excepcionBaseDatos) {
                loggerDelSistema.error("obtenerExamenesPorExperiencia: Ocurrió un problema en BD");
            }
            catch (NullPointerException excepcionNullPointer) {
                loggerDelSistema.error("obtenerExamenesPorExperiencia: Valor nulo");
            }
            finally{
                DataBase.closeConnection();
            } 
        }                
        return eesConParciales;
    }
    
    /**
     * Recupera la cantidad de los Exámenes Parciales por Experiencia Educativa
     * 
     * @param idPlanTrabajoAcademia Identificador del PlanTrabajoAcademia del cual se recuperará la cantidad de ExamenParcial para una Experiencia Educativa
     * @param experienciaEducativa Nombre de Experiencia Educativa de la cual se recuperará la cantidad de ExamenParcial asociados a ella
     * @return Cantidad de ExamenParcial asociados a la experienciaEducativa
     */
    @Override
    public int obtenerCantidadExamenesPorExperiencia(String idPlanTrabajoAcademia, String experienciaEducativa) {
        int cantidadExamenes = 0;
        consultaSQL = "SELECT Cantidad_Examenes_Parciales from plan_trabajo_academia_examen_parcial_ee where Id_Plan_Academia=? and EE=?";
        conexionDB = DataBase.getDataBaseConnection();
        barricada = BarricadaPlanTrabajoAcademiaDAO.obtenerInstancia();
        
        if (barricada.validarIdPlanAcademia(idPlanTrabajoAcademia) && barricada.validarCadena(experienciaEducativa)) {
            try {
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setString(1, idPlanTrabajoAcademia);
                sentencia.setString(2, experienciaEducativa);
                ResultSet resultadoSentencia = sentencia.executeQuery();
                while (resultadoSentencia.next()){
                    cantidadExamenes = resultadoSentencia.getInt("Cantidad_Examenes_Parciales");
                }
            } 
            catch (SQLException excepcionBaseDatos) {
                loggerDelSistema.error("obtenerCantidadExamenesPorExperiencia: Ocurrió un problema en BD");
            }
            catch (NullPointerException excepcionNullPointer) {
                loggerDelSistema.error("obtenerCantidadExamenesPorExperiencia: Valor nulo");
            }
            finally{
                DataBase.closeConnection();
            } 
        }        
        return cantidadExamenes;
    }

    /**
     * Recupera los temas de un Exámen Parcial específico de una Experiencia Educativa
     * 
     * @param idPlanTrabajoAcademia Identificador del PlanTrabajoAcademia del cual se recuperarán los temas de los ExamenParcial de sus Experiencias Educativas
     * @param experienciaEducativa Nombre de Experiencia Educativa a la cual se recuperarán los temas de uno de sus ExamenParcial
     * @param numeroParcial Número de ExamenParcial del que se recuperarán sus Temas
     * @return Lista de Temas asociadas al numeroParcial, experienciaEducativa e idPlanTrabajoAcademia
     */
    @Override
    public ArrayList<String> obtenerTemasDeParcial(String idPlanTrabajoAcademia, String experienciaEducativa, int numeroParcial) {
        ArrayList<String> temasDeParcial = new ArrayList<>();
        consultaSQL = "SELECT Tema_De_Parcial from plan_trabajo_academia_examen_parcial_tema where Id_Plan_Academia=? and EE=? and Numero_Parcial=?";
        conexionDB = DataBase.getDataBaseConnection();
        barricada = BarricadaPlanTrabajoAcademiaDAO.obtenerInstancia();
        
        if (barricada.validarIdPlanAcademia(idPlanTrabajoAcademia) && barricada.validarCadena(experienciaEducativa) && (numeroParcial < 3 && numeroParcial > 0)) {
            try {
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setString(1, idPlanTrabajoAcademia);
                sentencia.setString(2, experienciaEducativa);
                sentencia.setInt(3, numeroParcial);
                ResultSet resultadoSentencia = sentencia.executeQuery();

                while (resultadoSentencia.next()){
                    temasDeParcial.add(resultadoSentencia.getString("Tema_De_Parcial"));
                }

            } 
            catch (SQLException excepcionBaseDatos) {
                loggerDelSistema.error("obtenerTemasDeParcial: Ocurrió un problema en BD");
            }
            catch (NullPointerException excepcionNullPointer) {
                loggerDelSistema.error("obtenerTemasDeParcial: Valor nulo");
            }
            finally{
                DataBase.closeConnection();
            }  
        }
        return temasDeParcial;
    }

    /**
     * Recupera las Formas de Evaluación para cada Experiencia Educativa del PlanTrabajoAcademia
     * 
     * @param idPlanTrabajoAcademia Identificador del PlanTrabajoAcademia del cual se recuperarán sus FormaDeEvaluacion
     * @return Lista de FormaDeEvaluacion asociadas al idPlanTrabajoAcademia
     */
    @Override
    public ArrayList<FormaDeEvaluacion> obtenerFormasDeEvaluacion(String idPlanTrabajoAcademia) {
        ArrayList<FormaDeEvaluacion> formasDeEvaluacion = new ArrayList<>();
        FormaDeEvaluacion formaDeEvaluacion;
        consultaSQL = "SELECT Elemento,Porcentaje from plan_trabajo_academia_forma_evaluacion where Id_Plan_Academia=?";
        conexionDB = DataBase.getDataBaseConnection();
        barricada = BarricadaPlanTrabajoAcademiaDAO.obtenerInstancia();
        
        if (barricada.validarIdPlanAcademia(idPlanTrabajoAcademia)) {
            try {
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setString(1, idPlanTrabajoAcademia);
                ResultSet resultadoSentencia = sentencia.executeQuery();

                while (resultadoSentencia.next()){
                    formaDeEvaluacion = new FormaDeEvaluacion();
                    formaDeEvaluacion.setElemento(resultadoSentencia.getString("Elemento"));
                    formaDeEvaluacion.setPorcentaje(resultadoSentencia.getFloat("Porcentaje"));
                    formasDeEvaluacion.add(formaDeEvaluacion);
                }

            } 
            catch (SQLException excepcionBaseDatos) {
                loggerDelSistema.error("obtenerFormasDeEvaluacion: Ocurrió un problema en BD");
            }
            catch (NullPointerException excepcionNullPointer) {
                loggerDelSistema.error("obtenerFormasDeEvaluacion: Valor nulo");
            }
            finally{
                DataBase.closeConnection();
            }   
        }
        return formasDeEvaluacion;
    }

    /**
     * Recupera las Revisiones de un PlanTrabajoAcademia
     * 
     * @param idPlanTrabajoAcademia Identificador del PlanTrabajoAcademia del cual se recuperarán sus Revision
     * @return Lista de Revision asociadas al idPlanTrabajoAcademia
     */
    @Override
    public ArrayList<Revision> obtenerHistoricoDeRevision(String idPlanTrabajoAcademia) {
        ArrayList<Revision> historicoDeRevisiones = new ArrayList<>();
        Revision revision;
        consultaSQL = "SELECT * from plan_trabajo_academia_historico_de_revision where Id_Plan_Academia=?";
        conexionDB = DataBase.getDataBaseConnection();
        barricada = BarricadaPlanTrabajoAcademiaDAO.obtenerInstancia();
        
        if (barricada.validarIdPlanAcademia(idPlanTrabajoAcademia)) {
            try {
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setString(1, idPlanTrabajoAcademia);
                ResultSet resultadoSentencia = sentencia.executeQuery();
                int a = 1;

                while (resultadoSentencia.next()){
                    revision = new Revision();
                    revision.setNumeroRevision(a);
                    revision.setFecha(resultadoSentencia.getDate("Fecha"));
                    revision.setSeccionPaginaModificada(resultadoSentencia.getString("Seccion"));
                    revision.setDescripcionDeModificacion(resultadoSentencia.getString("Descripcion"));
                    a++;
                    historicoDeRevisiones.add(revision);
                }

            } 
            catch (SQLException excepcionBaseDatos) {
                loggerDelSistema.error("obtenerHistoricoDeRevision: Ocurrió un problema en BD");
            }
            catch (NullPointerException excepcionNullPointer) {
                loggerDelSistema.error("obtenerHistoricoDeRevision: Valor nulo");
            }
            finally{
                DataBase.closeConnection();
            }   
        }
        return historicoDeRevisiones;
    }

    /**
     * Recupera la Firma de Autorización de un PlanTrabajoAcademia
     * 
     * @param idPlanTrabajoAcademia Identificador del PlanTrabajoAcademia del cual se recuperará su FirmaDeAutorizacion
     * @return FirmaDeAutorizacion asociada al idPlanTrabajoAcademia
     */
    @Override
    public FirmaAutorizacion obtenerFirmaAutorizacion(String idPlanTrabajoAcademia) {
        FirmaAutorizacion firmaDeAutorizacion = new FirmaAutorizacion();
        consultaSQL = "SELECT * from plan_trabajo_academia_autorizacion where Id_Plan_Academia=?";
        conexionDB = DataBase.getDataBaseConnection();
        barricada = BarricadaPlanTrabajoAcademiaDAO.obtenerInstancia();
        
        if (barricada.validarIdPlanAcademia(idPlanTrabajoAcademia)) {
            try {
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setString(1, idPlanTrabajoAcademia);
                ResultSet resultadoSentencia = sentencia.executeQuery();

                while (resultadoSentencia.next()){
                    firmaDeAutorizacion.setPersonaQueProponePlan(resultadoSentencia.getString("Nombre_Propone"));
                    firmaDeAutorizacion.setPersonaQueAutorizaPlan(resultadoSentencia.getString("Nombre_Autoriza"));
                    firmaDeAutorizacion.setFechaAutorizacion(resultadoSentencia.getDate("Fecha_Autorizacion"));
                    firmaDeAutorizacion.setFechaEntradaEnVigor(resultadoSentencia.getDate("Fecha_Entrada_Vigor"));
                }

            } 
            catch (SQLException excepcionBaseDatos) {
                loggerDelSistema.error("obtenerFirmaAutorizacion: Ocurrió un problema en BD");
            }
            catch (NullPointerException excepcionNullPointer) {
                loggerDelSistema.error("obtenerFirmaAutorizacion: Valor nulo");
            }
            finally{
                DataBase.closeConnection();
            } 
        }
        return firmaDeAutorizacion;
    }
    
    /**
     * Elimina todos los datos de un PlanTrabajoAcademia
     * 
     * @param idPlanAcademia Identificador del PlanTrabajoAcademia del cual se eliminarán sus datos
     * @return  true si los datos fueron eliminados correctamente, false si no fue así
     */
    @Override
    public boolean eliminarDatosPlan(String idPlanAcademia) {
        boolean confirmacionEliminado = false;
        conexionDB = DataBase.getDataBaseConnection();
        barricada = BarricadaPlanTrabajoAcademiaDAO.obtenerInstancia();
        
        if (barricada.validarIdPlanAcademia(idPlanAcademia)) {
            ArrayList<String> consultasSQL = new ArrayList<>();
            consultasSQL.add("DELETE FROM plan_trabajo_academia_objetivo_particular where Id_Plan_Academia=?");        
            consultasSQL.add("DELETE FROM plan_trabajo_academia_objetivo_particular_meta where Id_Plan_Academia=?");
            consultasSQL.add("DELETE FROM plan_trabajo_academia_objetivo_particular_meta_accion where Id_Plan_Academia=?");
            consultasSQL.add("DELETE FROM plan_trabajo_academia_examen_parcial_ee where Id_Plan_Academia=?");
            consultasSQL.add("DELETE FROM plan_trabajo_academia_examen_parcial_tema where Id_Plan_Academia=?");
            consultasSQL.add("DELETE FROM plan_trabajo_academia_forma_evaluacion where Id_Plan_Academia=?");
            consultasSQL.add("DELETE FROM plan_trabajo_academia_historico_de_revision where Id_Plan_Academia=?");
            consultasSQL.add("DELETE FROM plan_trabajo_academia_autorizacion where Id_Plan_Academia=?");
            consultasSQL.add("DELETE FROM plan_trabajo_academia where Id=?");

            for (int a = 0; a < consultasSQL.size(); a++){
                try {
                    PreparedStatement sentenciaSQL = conexionDB.prepareStatement(consultasSQL.get(a));
                    sentenciaSQL.setString(1, idPlanAcademia);
                    sentenciaSQL.execute();
                    confirmacionEliminado = true;
                } 
                catch (SQLException excepcionBaseDatos) {
                    loggerDelSistema.error("eliminarDatosPlan: Ocurrió un problema en BD");
                }           
            }            
        }
        DataBase.closeConnection();
        return confirmacionEliminado;
    }
    
}
