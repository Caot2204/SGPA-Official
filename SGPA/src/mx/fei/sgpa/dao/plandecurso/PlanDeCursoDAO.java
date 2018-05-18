package mx.fei.sgpa.dao.plandecurso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.sgpa.datasource.DataBase;
import mx.fei.sgpa.domain.EstadoDeDocumento;
import mx.fei.sgpa.domain.plandecurso.Bibliografia;
import mx.fei.sgpa.domain.plandecurso.EvaluacionDeUnidad;
import mx.fei.sgpa.domain.plandecurso.PlanDeCurso;
import mx.fei.sgpa.domain.plandecurso.UnidadDePlaneacion;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author beto
 */
public class PlanDeCursoDAO implements IPlanDeCursoDAO{
    
    private String query;
    private Connection conexion;
    private PreparedStatement sentencia;

    public PlanDeCursoDAO() {
        
    }

    @Override
    public boolean guardarPlanDeCurso(PlanDeCurso planDeCurso) {
        
        boolean guardadoRealizado;
        String idPlanDeCurso = planDeCurso.getIdPlanDeCurso();
        query = "INSERT INTO plan_curso VALUES (?,?,?,?,?)";
        conexion = DataBase.getDataBaseConnection();
       
        try {
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1, idPlanDeCurso);
            sentencia.setInt(2, planDeCurso.getNrc());
            sentencia.setString(3, planDeCurso.getAcademico());
            sentencia.setString(4, planDeCurso.getObjetivoGeneral());
            sentencia.setString(5, planDeCurso.getEstado().name());
            sentencia.execute();
            
            guardarBibliografia(idPlanDeCurso, planDeCurso.getListaDeReferenciasBibliograficas());
            guardarEvaluacionDeUnidad(idPlanDeCurso, planDeCurso.getListaDeEvaluacionesDeUnidad());
            guardarUnidadDePlaneacion(idPlanDeCurso, planDeCurso.getListaDePlaneacionesDeUnidad());
        } 
        catch (SQLException ex) {
            Logger.getLogger(PlanDeCursoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            DataBase.closeConnection();
            guardadoRealizado = true;
        }
        return guardadoRealizado;
    }

    @Override
    public boolean guardarBibliografia(String idPlanDeCurso, ArrayList<Bibliografia> referenciasBibliograficas) {
        
        boolean guardado = false;
        
        query = "INSERT INTO plan_curso_bibliografia VALUES (?,?,?,?,?)";
        conexion = DataBase.getDataBaseConnection();
        
        try{
            
            for(Bibliografia bibliografia : referenciasBibliograficas){
                
                    sentencia = conexion.prepareStatement(query);
                    sentencia.setString(1, idPlanDeCurso);
                    sentencia.setString(2, bibliografia.getAutor());
                    sentencia.setString(3,bibliografia.getTituloDelLibro());
                    sentencia.setString(4,bibliografia.getEditorial());
                    sentencia.setInt(5,bibliografia.getAño());
                    sentencia.execute();
            }
            
            guardado = true;

        }
        catch(SQLException ex){
            
            Logger.getLogger(PlanDeCursoDAO.class.getName()).log(Level.SEVERE, null, ex);
        
        }
        finally{
            
            DataBase.closeConnection();
            
        }
        
        return guardado;
        
    }

    @Override
    public boolean guardarEvaluacionDeUnidad(String idPlanDeCurso, ArrayList<EvaluacionDeUnidad> evaluacionesDeUnidad) {
        
          boolean guardado = false;
        
        query = "INSERT INTO plan_curso_calendario_evaluacion_unidad VALUES (?,?,?,?,?,?)";
        conexion = DataBase.getDataBaseConnection();
        
         try{
            
            for(EvaluacionDeUnidad evaluacionDeUnidad : evaluacionesDeUnidad){
                
                    sentencia = conexion.prepareStatement(query);
                    sentencia.setString(1, idPlanDeCurso);
                    sentencia.setInt(2, evaluacionDeUnidad.getUnidad());
                    sentencia.setDate(3, evaluacionDeUnidad.getFecha());
                    sentencia.setString(4, evaluacionDeUnidad.getCriterioDeEvaluacion());
                    sentencia.setString(5, evaluacionDeUnidad.getInstrumento());
                    sentencia.setDouble(6, evaluacionDeUnidad.getPorcentaje());
                    sentencia.execute();
            }
            
            return guardado = true;     

        }
        catch(SQLException ex){
            
            Logger.getLogger(PlanDeCursoDAO.class.getName()).log(Level.SEVERE, null, ex);
        
        }
        finally{
            
            DataBase.closeConnection();
            
        }
        
        return guardado;
    }

    @Override
    public boolean guardarUnidadDePlaneacion(String idPlanDeCurso, ArrayList<UnidadDePlaneacion> unidadesDePlaneacion) {
       
        boolean guardado = false;
        
        query = "INSERT INTO plan_curso_unidad_planeacion VALUES (?,?,?,?,?,?)";
        conexion = DataBase.getDataBaseConnection();
        
         try{
            
            for(UnidadDePlaneacion unidadDePlaneacion : unidadesDePlaneacion){
                
                    sentencia = conexion.prepareStatement(query);
                    sentencia.setString(1, idPlanDeCurso);
                    sentencia.setInt(2, unidadDePlaneacion.getUnidad());
                    sentencia.setString(3,unidadDePlaneacion.getTema());
                    sentencia.setDate(4,unidadDePlaneacion.getFecha());
                    sentencia.setString(5,unidadDePlaneacion.getTareaPractica());
                    sentencia.setString(6,unidadDePlaneacion.getTecnicaDidactica());
                    sentencia.execute();
            }
            
            return guardado = true;

        }
        catch(SQLException ex){
            
            Logger.getLogger(PlanDeCursoDAO.class.getName()).log(Level.SEVERE, null, ex);
        
        }
        finally{
            
            DataBase.closeConnection();
            
        }
        
        return guardado;
    }

    @Override
    public PlanDeCurso buscarPlanDeCursoPorID(String idPlanDeCurso) {
       
        PlanDeCurso planDeCurso = new PlanDeCurso();
        query = "SELECT * from plan_trabajo_academia where Id=?";
        conexion = DataBase.getDataBaseConnection();
        try {
            sentencia = conexion.prepareStatement(query);
            sentencia.setString(1, idPlanDeCurso);
            ResultSet resultadoSentencia = sentencia.executeQuery();
            
            while (resultadoSentencia.next()){
                planDeCurso.setIdPlanDeCurso(idPlanDeCurso);
                planDeCurso.setNrc(resultadoSentencia.getInt("NRC"));
                planDeCurso.setAcademico(resultadoSentencia.getString("Id_Docente"));
                planDeCurso.setObjetivoGeneral(resultadoSentencia.getString("Objetivo_General"));
                planDeCurso.setEstado(resultadoSentencia.getObject("Estado", EstadoDeDocumento.class));
            }
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(PlanDeCursoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            DataBase.closeConnection();
        }
        return planDeCurso;
    }

    @Override
    public ArrayList<PlanDeCurso> buscarPlanDeCursoPorCoordinador(String idCoordinador) {
      
       ArrayList<PlanDeCurso> planesDeCurso = new ArrayList<>();
       query = "SELECT * from plan_trabajo_academia where Id=?";
       conexion = DataBase.getDataBaseConnection();
       
       return planesDeCurso;
    }

    @Override
    public ArrayList<Bibliografia> recuperarBibliografia(String idPlanDeCurso) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<EvaluacionDeUnidad> recuperarEvaluacionDeUnidad(String idPlanDeCurso) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<UnidadDePlaneacion> recuperarUnidadDePlaneacion(String idPlanDeCurso) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
