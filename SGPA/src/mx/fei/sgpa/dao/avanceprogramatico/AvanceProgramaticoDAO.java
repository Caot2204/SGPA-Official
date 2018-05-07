
package mx.fei.sgpa.dao.avanceprogramatico;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.sgpa.datasource.DataBase;
import mx.fei.sgpa.domain.EstadoDeDocumento;
import mx.fei.sgpa.domain.avanceprogramatico.AvanceProgramatico;
import mx.fei.sgpa.domain.avanceprogramatico.AvancePorUnidad;
import mx.fei.sgpa.domain.avanceprogramatico.UnidadDePlaneacion;

public class AvanceProgramaticoDAO implements IAvanceProgramaticoDAO{
    
    private AvanceProgramatico avanceProgramatico;
    private ArrayList<AvanceProgramatico> avancesProgramaticos;
    private ArrayList<UnidadDePlaneacion> unidadesPlaneacionAvanceProgramatico;
    private ArrayList<AvancePorUnidad> avancesUnidadAvanceProgramatico;
    private UnidadDePlaneacion unidadPlaneacion;
    private AvancePorUnidad unidadAvance;
    
    private String consulta;
    private Connection conexionBD;
    
    public AvanceProgramaticoDAO(){  
    }

    @Override
    public boolean guardarAvanceProgramatico(AvanceProgramatico avanceProgramatico) {
        
        boolean guardado = false;
        
        try {
            guardarDetallesUnidadesPlaneacion(avanceProgramatico.getId(), avanceProgramatico.getUnidadesDePlaneacion());
            guardarAvancesPorUnidades(avanceProgramatico.getId(), avanceProgramatico.getAvancesDeUnidad());
            
            consulta = "INSERT INTO avance_programatico VALUES (?,?,?,?,?,?)";
            conexionBD = DataBase.getDataBaseConnection();
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, avanceProgramatico.getId());
            sentencia.setInt(2, avanceProgramatico.getNrc());
            sentencia.setString(3, avanceProgramatico.getAcademico());
            sentencia.setString(4, avanceProgramatico.getObjetivoGeneral());
            sentencia.setString(5, avanceProgramatico.getEstado().toString());
            sentencia.setString(6, avanceProgramatico.getIdPlanDeCurso());
            
            guardado = true;
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(AvanceProgramaticoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            DataBase.closeConnection();
        }
        
        return guardado;
    }
    
    @Override
    public boolean guardarUnidadesPlaneacion(String idAvanceProgramatico, ArrayList<UnidadDePlaneacion> unidadesPlaneacion){
        boolean guardado = false;
        consulta = "INSERT INTO avance_programatico_unidad_planeacion VALUES (?,?)";
        conexionBD = DataBase.getDataBaseConnection();
        
        try {
            
            for (UnidadDePlaneacion unidadPlaneacion : unidadesPlaneacion){             
                PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, idAvanceProgramatico);
                sentencia.setInt(2, unidadPlaneacion.getUnidad());
                sentencia.execute();
            }
            
            guardado = true;
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(AvanceProgramaticoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            DataBase.closeConnection();
        }
        
        return guardado;
    }
    
    @Override
    public boolean guardarDetallesUnidadesPlaneacion(String idAvanceProgramatico, ArrayList<UnidadDePlaneacion> unidadesPlaneacion) {
        boolean guardado = false;
        consulta = "INSERT INTO avance_programatico_detalle_unidad VALUES (?,?,?,?,?,?)";
        conexionBD = DataBase.getDataBaseConnection();
        
        try {
            
            for (UnidadDePlaneacion unidadPlaneacion : unidadesPlaneacion){
                             
                for (int b = 0; b < unidadPlaneacion.getTemasDesarrollados().size(); b++){
                    PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
                    sentencia.setString(1, idAvanceProgramatico);
                    sentencia.setInt(2, unidadPlaneacion.getUnidad());
                    sentencia.setString(3, unidadPlaneacion.getTemasDesarrollados().get(b));
                    sentencia.setDate(4, unidadPlaneacion.getFechas().get(b));
                    sentencia.setString(5, unidadPlaneacion.getTareasPracticas().get(b));
                    sentencia.setString(6, unidadPlaneacion.getTecnicasDidacticas().get(b));
                    sentencia.execute();   
                }
                
            }
            
            guardado = true;
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(AvanceProgramaticoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            DataBase.closeConnection();
        }
        
        return guardado;
    }

    @Override
    public boolean guardarAvancesPorUnidades(String idAvanceProgramatico, ArrayList<AvancePorUnidad> avancesUnidad) {
        boolean guardado = false;
        consulta = "INSERT INTO avance_programatico_avance_unidad VALUES (?,?,?,?)";
        conexionBD = DataBase.getDataBaseConnection();
        
        try {           
            for (AvancePorUnidad unidadAvance : avancesUnidad){
                PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, idAvanceProgramatico);
                sentencia.setInt(2, unidadAvance.getUnidad());
                sentencia.setFloat(3, unidadAvance.getPorcentaje());
                sentencia.setString(4, unidadAvance.getObservacion());
                sentencia.execute();
            }
            guardado = true; 
        } 
        catch (SQLException ex) {
            Logger.getLogger(AvanceProgramaticoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            DataBase.closeConnection();
        }
        
        return guardado;
    }

    @Override
    public AvanceProgramatico buscarAvanceProgramaticoById(String id) {
        
        avanceProgramatico = new AvanceProgramatico();
        consulta = "SELECT * FROM avance_programatico WHERE Id=?";
        conexionBD = DataBase.getDataBaseConnection();
        
        try {
            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, id);
            ResultSet resultadoConsulta = sentencia.executeQuery();
            
            while (resultadoConsulta.next()){
                avanceProgramatico.setId(resultadoConsulta.getString("Id"));
                avanceProgramatico.setNrc(resultadoConsulta.getInt("NRC"));
                avanceProgramatico.setObjetivoGeneral(resultadoConsulta.getString("Objetivo_General"));
                avanceProgramatico.setEstado(EstadoDeDocumento.valueOf(resultadoConsulta.getString("Estado")));
                avanceProgramatico.setUnidadesDePlaneacion(obtenerUnidadesPlaneacionDeAvance(avanceProgramatico.getId()));
                avanceProgramatico.setAvancesDeUnidad(obtenerAvancesUnidadDeAvance(avanceProgramatico.getId()));
            }
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(AvanceProgramaticoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            DataBase.closeConnection();
        }
        
        return avanceProgramatico;
    }

    @Override
    public ArrayList<AvanceProgramatico> buscarAvanceProgramaticoByDocente(String idDocente) {
        
        avancesProgramaticos = new ArrayList<>();
        consulta = "SELECT * FROM avance_programatico WHERE Id_Docente=?";
        conexionBD = DataBase.getDataBaseConnection();
        
        try {
            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, idDocente);
            ResultSet resultadoConsulta = sentencia.executeQuery();
            
            while (resultadoConsulta.next()){
                avanceProgramatico = new AvanceProgramatico();
                
                avanceProgramatico.setId(resultadoConsulta.getString("Id"));
                avanceProgramatico.setNrc(resultadoConsulta.getInt("NRC"));
                avanceProgramatico.setObjetivoGeneral(resultadoConsulta.getString("Objetivo_General"));
                avanceProgramatico.setEstado(EstadoDeDocumento.valueOf(resultadoConsulta.getString("Estado")));
                avanceProgramatico.setUnidadesDePlaneacion(obtenerUnidadesPlaneacionDeAvance(avanceProgramatico.getId()));
                avanceProgramatico.setAvancesDeUnidad(obtenerAvancesUnidadDeAvance(avanceProgramatico.getId()));
                
                avancesProgramaticos.add(avanceProgramatico);
            }
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(AvanceProgramaticoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            DataBase.closeConnection();
        }
    
        return avancesProgramaticos;
    }

    @Override
    public ArrayList<UnidadDePlaneacion> obtenerUnidadesPlaneacionDeAvance(String idAvanceProgramatico) {
        unidadesPlaneacionAvanceProgramatico = new ArrayList<>();
        
        ArrayList<Integer> numerosUnidad = buscarUnidadesPlaneacion(idAvanceProgramatico);
        consulta = "SELECT * FROM avance_programatico_detalle_unidad WHERE Id_Avance_Programatico=? and Unidad=?";
        conexionBD = DataBase.getDataBaseConnection();
        
        for (int a = 0; a < numerosUnidad.size(); a++){
            try {
                PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
                sentencia.setString(1, idAvanceProgramatico);
                sentencia.setInt(2, numerosUnidad.get(a));
                ResultSet resultadoConsulta = sentencia.executeQuery();
                
                unidadPlaneacion = new UnidadDePlaneacion();
                ArrayList<String> temasDesarrollados = new ArrayList<>();
                ArrayList<Date> fechas = new ArrayList<>();
                ArrayList<String> tareasPracticas = new ArrayList<>();
                ArrayList<String> tecnicasDidacticas = new ArrayList<>();
                
                unidadPlaneacion.setUnidad(numerosUnidad.get(a));

                while (resultadoConsulta.next()){
                    temasDesarrollados.add(resultadoConsulta.getString("Tema_Desarrollado"));
                    fechas.add(resultadoConsulta.getDate("Fecha"));
                    tareasPracticas.add(resultadoConsulta.getString("Tarea_Practica_Realizada"));
                    tecnicasDidacticas.add(resultadoConsulta.getString("Tecnica_Didactica"));
                }
                
                unidadPlaneacion.setTemasDesarrollados(temasDesarrollados);
                unidadPlaneacion.setFechas(fechas);
                unidadPlaneacion.setTareasPracticas(tareasPracticas);
                unidadPlaneacion.setTecnicasDidacticas(tecnicasDidacticas);
                
                unidadesPlaneacionAvanceProgramatico.add(unidadPlaneacion);

            } 
            catch (SQLException ex) {
                Logger.getLogger(AvanceProgramaticoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally{
                DataBase.closeConnection();
            }
            
        }
        
        return unidadesPlaneacionAvanceProgramatico;
    }
    
    public ArrayList<Integer> buscarUnidadesPlaneacion(String idAvanceProgramatico){
        ArrayList<Integer> unidades = new ArrayList<>();
        
        consulta = "SELECT Unidad FROM avance_programatico_unidad_planeacion WHERE Id_Avance_Programatico=?";
        conexionBD = DataBase.getDataBaseConnection();
        
        try {
            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, idAvanceProgramatico);
            ResultSet resultadoConsulta = sentencia.executeQuery();
            
            while (resultadoConsulta.next()){
                unidades.add(resultadoConsulta.getInt("Unidad"));                                
            }
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(AvanceProgramaticoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            DataBase.closeConnection();
        }
        
        return unidades;
    }

    @Override
    public ArrayList<AvancePorUnidad> obtenerAvancesUnidadDeAvance(String idAvanceProgramatico) {
        avancesUnidadAvanceProgramatico = new ArrayList<>();
        
        consulta = "SELECT * FROM avance_programatico_avance_unidad WHERE Id_Avance_Programatico=?";
        conexionBD = DataBase.getDataBaseConnection();
        
        try {
            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, idAvanceProgramatico);
            ResultSet resultadoConsulta = sentencia.executeQuery();
            
            while (resultadoConsulta.next()){
                unidadAvance = new AvancePorUnidad();
                unidadAvance.setUnidad(resultadoConsulta.getInt("Unidad"));
                unidadAvance.setPorcentaje(resultadoConsulta.getFloat("Porcentaje_Avance"));
                unidadAvance.setObservacion(resultadoConsulta.getString("Observacion"));
                avancesUnidadAvanceProgramatico.add(unidadAvance);
            }
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(AvanceProgramaticoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            DataBase.closeConnection();
        }
        
        return avancesUnidadAvanceProgramatico;
    }
    
}
