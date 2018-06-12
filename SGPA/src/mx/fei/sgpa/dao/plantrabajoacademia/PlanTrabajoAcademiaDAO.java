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
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.sgpa.dao.academia.AcademiaDAO;
import mx.fei.sgpa.dao.academico.AcademicoDAO;
import mx.fei.sgpa.datasource.DataBase;
import mx.fei.sgpa.domain.Academia;
import mx.fei.sgpa.domain.Academico;
import mx.fei.sgpa.domain.EstadoDeDocumento;
import mx.fei.sgpa.domain.plantrabajoacademia.AccionDeMeta;
import mx.fei.sgpa.domain.plantrabajoacademia.EEConParcial;
import mx.fei.sgpa.domain.plantrabajoacademia.ExamenParcial;
import mx.fei.sgpa.domain.plantrabajoacademia.FirmaAutorizacion;
import mx.fei.sgpa.domain.plantrabajoacademia.FormaDeEvaluacion;
import mx.fei.sgpa.domain.plantrabajoacademia.MetaDeObjetivo;
import mx.fei.sgpa.domain.plantrabajoacademia.ObjetivoParticular;
import mx.fei.sgpa.domain.plantrabajoacademia.PlanTrabajoAcademia;
import mx.fei.sgpa.domain.plantrabajoacademia.Revision;

public class PlanTrabajoAcademiaDAO implements IPlanTrabajoAcademiaDAO{
       
    private Connection conexionDB;
    private String consultaSQL;
    private PreparedStatement sentencia;

    @Override
    public boolean guardarPlanTrabajoAcademiaCompleto(PlanTrabajoAcademia planAcademia) {
        AcademicoDAO academicoDAO = new AcademicoDAO();
        Academico coordinador = academicoDAO.obtenerAcademico(planAcademia.getNombreCoordinador());
        boolean guardadoRealizado = false;
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
        catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException excepcion) {
            eliminarDatosPlan(idPlan);
            guardadoRealizado = false;
        }
        finally{
            DataBase.closeConnection();
        }
        return guardadoRealizado;
    }
    
    @Override
    public boolean guardarPlanTrabajoAcademia(PlanTrabajoAcademia planAcademia) {
        AcademicoDAO academicoDAO = new AcademicoDAO();
        AcademiaDAO academiaDAO = new AcademiaDAO();
        Academico coordinador = academicoDAO.obtenerAcademico(planAcademia.getNombreCoordinador());
        Academia academia = academiaDAO.obtenerAcademiaPorNombre(planAcademia.getNombreAcademia());
        
        boolean guardadoRealizado = false;
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
        catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException excepcion) {
            eliminarDatosPlan(idPlan);
            guardadoRealizado = false;
        }
        finally{
            DataBase.closeConnection();
        }
        return guardadoRealizado;
    }
    
    @Override
    public boolean actualizarDatosPlan(PlanTrabajoAcademia planAcademia) {
        boolean confirmacionGuardado = true;
        if (planAcademia != null) {
            if (eliminarDatosPlan(planAcademia.getId())) {
                guardarPlanTrabajoAcademia(planAcademia);
            }  
        }
        else {
            confirmacionGuardado = false;
        }
        return confirmacionGuardado;
    }
    
    @Override
    public boolean guardarObjetivosParticulares(String idPlanTrabajoAcademia, ArrayList<ObjetivoParticular> objetivosParticulares){
        boolean guardadoRealizado = false;
        consultaSQL = "INSERT INTO plan_trabajo_academia_objetivo_particular values (?,?,?)";
        conexionDB = DataBase.getDataBaseConnection();
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
        catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException excepcion) {
            eliminarDatosPlan(idPlanTrabajoAcademia);
            guardadoRealizado = false;            
        }
        finally{
            DataBase.closeConnection();
        }
        return guardadoRealizado;
    }
    
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
        catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException excepcion) {
            eliminarDatosPlan(idPlanTrabajoAcademia);
            guardadoRealizado = false;            
        }
        finally{
            DataBase.closeConnection();
        }
        return guardadoRealizado;
    }
    
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
        catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException excepcion) {
            eliminarDatosPlan(idPlanTrabajoAcademia);
            guardadoRealizado = false;            
        }
        finally{
            DataBase.closeConnection();
        }
        return guardadoRealizado;
    }
    
    @Override
    public boolean guardarExamenesParciales(String idPlanTrabajoAcademia, ArrayList<EEConParcial> eesConParciales){
        boolean guardadoRealizado = false;
        guardarCantidadExamenesParcialesDeEE(idPlanTrabajoAcademia, eesConParciales);        
        for (EEConParcial eeParcial : eesConParciales){
            if (guardarTemasDeParcialDeEE(idPlanTrabajoAcademia, eeParcial.getExperienciaEducativa(), eeParcial.getExamenesParciales())){
                guardadoRealizado = true;
            }
            else{
                guardadoRealizado = false;                    
            }
        }
        return guardadoRealizado;
    }
    
    @Override
    public boolean guardarCantidadExamenesParcialesDeEE(String idPlanTrabajoAcademia, ArrayList<EEConParcial> eesConParciales) {
        boolean guardadoRealizado = false;
        consultaSQL = "INSERT INTO plan_trabajo_academia_examen_parcial_ee values (?,?,?)";
        conexionDB = DataBase.getDataBaseConnection();
        try {
            for (EEConParcial eeParcial : eesConParciales){
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setString(1, idPlanTrabajoAcademia);
                sentencia.setString(2, eeParcial.getExperienciaEducativa());
                sentencia.setInt(3, eeParcial.getExamenesParciales().size());
                sentencia.execute();   
            }
            guardadoRealizado = true;
        } 
        catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException excepcion) {
            eliminarDatosPlan(idPlanTrabajoAcademia);
            guardadoRealizado = false;            
        }
        finally{
            DataBase.closeConnection();
        }
        return guardadoRealizado;
    }

    @Override
    public boolean guardarTemasDeParcialDeEE(String idPlanTrabajoAcademia, String experienciaEducativa, ArrayList<ExamenParcial> examenesParciales) {
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
        catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException excepcion) {
            eliminarDatosPlan(idPlanTrabajoAcademia);
            guardadoRealizado = false;            
        }
        finally{
            DataBase.closeConnection();
        }
        return guardadoRealizado;
    }
    
    @Override
    public boolean guardarFormasDeEvaluacion(String idPlanTrabajoAcademia, ArrayList<FormaDeEvaluacion> formasDeEvaluacion) {
        boolean guardadoRealizado = false;
        consultaSQL = "INSERT INTO plan_trabajo_academia_forma_evaluacion values (?,?,?)";
        conexionDB = DataBase.getDataBaseConnection();
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
        catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException excepcion) {
            eliminarDatosPlan(idPlanTrabajoAcademia);
            guardadoRealizado = false;            
        }
        finally{
            DataBase.closeConnection();
        }
        return guardadoRealizado;
    }
    
    @Override
    public boolean guardarHistoricoDeRevision(String idPlanTrabajoAcademia, ArrayList<Revision> historicoDeRevisiones) {
        boolean guardadoRealizado = false;
        consultaSQL = "INSERT INTO plan_trabajo_academia_historico_de_revision values (?,?,?,?,?)";
        conexionDB = DataBase.getDataBaseConnection();
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
        catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException excepcion) {
            eliminarDatosPlan(idPlanTrabajoAcademia);
            guardadoRealizado = false;
        }
        finally{
            DataBase.closeConnection();
        }        
        return guardadoRealizado;
    }
    
    @Override
    public boolean guardarFirmaDeAutorizacion(String idPlanTrabajoAcademia, FirmaAutorizacion firmaDeAutorizacion) {
        boolean guardadoRealizado = false;
        consultaSQL = "INSERT INTO plan_trabajo_academia_autorizacion values (?,?,?,?,?)";
        conexionDB = DataBase.getDataBaseConnection();
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
        catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException excepcion) {
            eliminarDatosPlan(idPlanTrabajoAcademia);
            guardadoRealizado = false;            
        }
        finally{
            DataBase.closeConnection();
        }
        return guardadoRealizado;
    }

    @Override
    public PlanTrabajoAcademia buscarPlanTrabajoByID(String idPlanTrabajoAcademia) {
        PlanTrabajoAcademia planAcademia = new PlanTrabajoAcademia();
        AcademiaDAO academiaDAO = new AcademiaDAO();
        consultaSQL = "SELECT * from plan_trabajo_academia where Id=?";
        conexionDB = DataBase.getDataBaseConnection();
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
                planAcademia.setExamenesParciales(obtenerEEsConExamenesParciales(planAcademia.getId()));
                planAcademia.setFormasDeEvaluacion(obtenerFormasDeEvaluacion(planAcademia.getId()));
                planAcademia.setHistoricoDeRevisiones(obtenerHistoricoDeRevision(planAcademia.getId()));
                planAcademia.setAutorizacion(obtenerFirmaAutorizacion(planAcademia.getId()));
            }
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException excepcion) {
            
        }
        finally{
            DataBase.closeConnection();
        }
        return planAcademia;
    }

    @Override
    public ArrayList<PlanTrabajoAcademia> buscarPlanTrabajoByCoordinador(int idCoordinador) {
        ArrayList<PlanTrabajoAcademia> planesAcademiaDeCoordinador = new ArrayList<>();
        PlanTrabajoAcademia planAcademia;
        AcademicoDAO academicoDAO;
        AcademiaDAO academiaDAO = new AcademiaDAO();
        consultaSQL = "SELECT * from plan_trabajo_academia where Id_Coordinador=? and Estado=?";
        conexionDB = DataBase.getDataBaseConnection();
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
                planAcademia.setExamenesParciales(obtenerEEsConExamenesParciales(planAcademia.getId()));
                planAcademia.setFormasDeEvaluacion(obtenerFormasDeEvaluacion(planAcademia.getId()));
                planAcademia.setHistoricoDeRevisiones(obtenerHistoricoDeRevision(planAcademia.getId()));
                planAcademia.setAutorizacion(obtenerFirmaAutorizacion(planAcademia.getId()));
                
                planesAcademiaDeCoordinador.add(planAcademia);
            }
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException excepcion) {
            
        }
        finally{
            DataBase.closeConnection();
        }
        return planesAcademiaDeCoordinador;
    }
    
    @Override
    public ArrayList<PlanTrabajoAcademia> obtenerPlanesEnEdicion(int idCoordinador) {
        ArrayList<PlanTrabajoAcademia> planesAcademiaDeCoordinador = new ArrayList<>();
        PlanTrabajoAcademia planAcademia;
        AcademicoDAO academicoDAO;
        AcademiaDAO academiaDAO;
        consultaSQL = "SELECT * from plan_trabajo_academia where Id_Coordinador=? and Estado=?";
        conexionDB = DataBase.getDataBaseConnection();
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
                planAcademia.setExamenesParciales(obtenerEEsConExamenesParciales(planAcademia.getId()));
                planAcademia.setFormasDeEvaluacion(obtenerFormasDeEvaluacion(planAcademia.getId()));
                planAcademia.setHistoricoDeRevisiones(obtenerHistoricoDeRevision(planAcademia.getId()));
                planAcademia.setAutorizacion(obtenerFirmaAutorizacion(planAcademia.getId()));
                
                planesAcademiaDeCoordinador.add(planAcademia);
            }
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException excepcion) {
            
        }
        finally{
            DataBase.closeConnection();
        }
        return planesAcademiaDeCoordinador;
    }
    
    @Override
    public ArrayList<ObjetivoParticular> obtenerObjetivosParticulares(String idPlanTrabajoAcademia) {
        ArrayList<ObjetivoParticular> objetivosParticulares = new ArrayList<>();
        ObjetivoParticular objetivoParticular;
        consultaSQL = "SELECT Id_Objetivo_Particular, Descripcion from plan_trabajo_academia_objetivo_particular where Id_Plan_Academia=?";
        conexionDB = DataBase.getDataBaseConnection();
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
        catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException excepcion) {
            
        }
        finally{
            DataBase.closeConnection();
        }
        return objetivosParticulares;
    }
    
    @Override
    public ArrayList<MetaDeObjetivo> obtenerMetasDeObjetivo(String idPlanTrabajoAcademia, String idObjetivoParticular) {
        ArrayList<MetaDeObjetivo> metasDeObjetivo = new ArrayList<>();
        MetaDeObjetivo metaDeObjetivo;
        consultaSQL = "SELECT Id_Meta, Descripcion from plan_trabajo_academia_objetivo_particular_meta where Id_Plan_Academia=? and Id_Objetivo_Particular=?";
        conexionDB = DataBase.getDataBaseConnection();
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
        catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException excepcion) {
            
        }
        finally{
            DataBase.closeConnection();
        }
        return metasDeObjetivo;
    }

    @Override
    public ArrayList<AccionDeMeta> obtenerAccionesDeMeta(String idPlanTrabajoAcademia, String idObjetivoParticular, String idMeta) {
        ArrayList<AccionDeMeta> accionesDeMeta = new ArrayList<>();
        AccionDeMeta accionDeMeta;
        consultaSQL = "SELECT Descripcion_Accion, Fecha_Semana, Forma_Operar from plan_trabajo_academia_objetivo_particular_meta_accion where Id_Plan_Academia=? and Id_Objetivo_Particular=? and Id_Meta=?";
        conexionDB = DataBase.getDataBaseConnection();
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
        catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException excepcion) {
            
        }
        finally{
            DataBase.closeConnection();
        }
        return accionesDeMeta;
    }

    @Override
    public ArrayList<EEConParcial> obtenerEEsConExamenesParciales(String idPlanTrabajoAcademia) {
        ArrayList<EEConParcial> eesConParciales = new ArrayList<>();
        ArrayList<ExamenParcial> examenesDeEE;
        consultaSQL = "SELECT EE, Cantidad_Examenes_Parciales from plan_trabajo_academia_examen_parcial_ee where Id_Plan_Academia=?";
        conexionDB = DataBase.getDataBaseConnection();
        try {
            sentencia = conexionDB.prepareStatement(consultaSQL);
            sentencia.setString(1, idPlanTrabajoAcademia);
            ResultSet resultadoConsulta = sentencia.executeQuery();
            
            while (resultadoConsulta.next()){
                EEConParcial eeConParcial = new EEConParcial();
                examenesDeEE = new ArrayList<>();
                
                eeConParcial.setExperienciaEducativa(resultadoConsulta.getString("EE"));
                
                int cantidadParciales = obtenerCantidadExamenesParcialesDeEE(idPlanTrabajoAcademia, eeConParcial.getExperienciaEducativa());
                
                for (int a = 1; a <= cantidadParciales; a++){
                    ExamenParcial examen = new ExamenParcial();
                    examen.setId(a);
                    examen.setTemasDeParcial(obtenerTemasDeParcialDeEE(idPlanTrabajoAcademia, eeConParcial.getExperienciaEducativa(), a));
                    examenesDeEE.add(examen);
                }
                
                eeConParcial.setExamenesParciales(examenesDeEE);
                
                eesConParciales.add(eeConParcial);
            }
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException excepcion) {
            
        }
        finally{
            DataBase.closeConnection();
        }                
        return eesConParciales;
    }
    
    @Override
    public int obtenerCantidadExamenesParcialesDeEE(String idPlanTrabajoAcademia, String experienciaEducativa) {
        int cantidadExamenes = 0;
        consultaSQL = "SELECT Cantidad_Examenes_Parciales from plan_trabajo_academia_examen_parcial_ee where Id_Plan_Academia=? and EE=?";
        conexionDB = DataBase.getDataBaseConnection();
        try {
            sentencia = conexionDB.prepareStatement(consultaSQL);
            sentencia.setString(1, idPlanTrabajoAcademia);
            sentencia.setString(2, experienciaEducativa);
            ResultSet resultadoSentencia = sentencia.executeQuery();
            while (resultadoSentencia.next()){
                cantidadExamenes = resultadoSentencia.getInt("Cantidad_Examenes_Parciales");
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException excepcion) {
            
        }
        finally{
            DataBase.closeConnection();
        }
        return cantidadExamenes;
    }

    @Override
    public ArrayList<String> obtenerTemasDeParcialDeEE(String idPlanTrabajoAcademia, String experienciaEducativa, int numeroParcial) {
        ArrayList<String> temasDeParcial = new ArrayList<>();
        consultaSQL = "SELECT Tema_De_Parcial from plan_trabajo_academia_examen_parcial_tema where Id_Plan_Academia=? and EE=? and Numero_Parcial=?";
        conexionDB = DataBase.getDataBaseConnection();
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
        catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException excepcion) {
            
        }
        finally{
            DataBase.closeConnection();
        }
        return temasDeParcial;
    }

    @Override
    public ArrayList<FormaDeEvaluacion> obtenerFormasDeEvaluacion(String idPlanTrabajoAcademia) {
        ArrayList<FormaDeEvaluacion> formasDeEvaluacion = new ArrayList<>();
        FormaDeEvaluacion formaDeEvaluacion;
        consultaSQL = "SELECT Elemento,Porcentaje from plan_trabajo_academia_forma_evaluacion where Id_Plan_Academia=?";
        conexionDB = DataBase.getDataBaseConnection();
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
        catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException excepcion) {
            
        }
        finally{
            DataBase.closeConnection();
        }
        return formasDeEvaluacion;
    }

    @Override
    public ArrayList<Revision> obtenerHistoricoDeRevision(String idPlanTrabajoAcademia) {
        ArrayList<Revision> historicoDeRevisiones = new ArrayList<>();
        Revision revision;
        consultaSQL = "SELECT * from plan_trabajo_academia_historico_de_revision where Id_Plan_Academia=?";
        conexionDB = DataBase.getDataBaseConnection();
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
        catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException excepcion) {
            
        }
        finally{
            DataBase.closeConnection();
        }
        return historicoDeRevisiones;
    }

    @Override
    public FirmaAutorizacion obtenerFirmaAutorizacion(String idPlanTrabajoAcademia) {
        FirmaAutorizacion firmaDeAutorizacion = new FirmaAutorizacion();
        consultaSQL = "SELECT * from plan_trabajo_academia_autorizacion where Id_Plan_Academia=?";
        conexionDB = DataBase.getDataBaseConnection();
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
        catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException excepcion) {
                        
        }
        finally{
            DataBase.closeConnection();
        }
        return firmaDeAutorizacion;
    }
    
    @Override
    public boolean eliminarDatosPlan(String idPlanAcademia) {
        boolean confirmacionEliminado = false;
        conexionDB = DataBase.getDataBaseConnection();
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
            catch (SQLException ex) {
                Logger.getLogger(PlanTrabajoAcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        DataBase.closeConnection();
        return confirmacionEliminado;
    }
    
}
