
package mx.fei.sgpa.dao.avanceprogramatico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.sgpa.datasource.DataBase;
import mx.fei.sgpa.domain.EstadoDeDocumento;
import mx.fei.sgpa.domain.avanceprogramatico.AvanceProgramatico;
import mx.fei.sgpa.domain.avanceprogramatico.AvanceUnidadAvanceProgramatico;
import mx.fei.sgpa.domain.avanceprogramatico.UnidadPlaneacionAvanceProgramatico;

public class AvanceProgramaticoDAO implements IAvanceProgramaticoDAO{
    
    private AvanceProgramatico avanceProgramatico;
    private ArrayList<AvanceProgramatico> avancesProgramaticos;
    private ArrayList<UnidadPlaneacionAvanceProgramatico> unidadesPlaneacionAvanceProgramatico;
    private ArrayList<AvanceUnidadAvanceProgramatico> avancesUnidadAvanceProgramatico;
    private UnidadPlaneacionAvanceProgramatico unidadPlaneacion;
    
    private String consulta;
    private Connection conexionBD;

    @Override
    public boolean guardarAvanceProgramatico(AvanceProgramatico avanceProgramatico) {
        
        boolean guardado = false;
        
        try {
            guardarUnidadesPlaneacion(avanceProgramatico.getId(), avanceProgramatico.getUnidadesDePlaneacion());
            guardarAvancesUnidades(avanceProgramatico.getId(), avanceProgramatico.getAvancesDeUnidad());
            
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
    public boolean guardarUnidadesPlaneacion(String idAvanceProgramatico, ArrayList<UnidadPlaneacionAvanceProgramatico> unidadesPlaneacion) {
        boolean guardado = false;
        consulta = "INSERT INTO avance_programatico_unidad_planeacion VALUES (?,?,?,?,?,?)";
        conexionBD = DataBase.getDataBaseConnection();
        
        try {
            
            for (UnidadPlaneacionAvanceProgramatico unidadPlaneacion : unidadesPlaneacion){
                             
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
    public boolean guardarAvancesUnidades(String idAvanceProgramatico, ArrayList<AvanceUnidadAvanceProgramatico> avancesUnidad) {
        boolean guardado = false;
        consulta = "INSERT INTO avance_programatico_avance_unidad VALUES (?,?,?,?)";
        conexionBD = DataBase.getDataBaseConnection();
        
        try {
            
            for (AvanceUnidadAvanceProgramatico unidadAvance : avancesUnidad){
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
                avanceProgramatico.setUnidadesDePlaneacion(buscarUnidadesPlaneacionDeAvance(avanceProgramatico.getId()));
                avanceProgramatico.setAvancesDeUnidad(buscarAvancesUnidadDeAvance(avanceProgramatico.getId()));
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
                avanceProgramatico.setUnidadesDePlaneacion(buscarUnidadesPlaneacionDeAvance(avanceProgramatico.getId()));
                avanceProgramatico.setAvancesDeUnidad(buscarAvancesUnidadDeAvance(avanceProgramatico.getId()));
                
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
    public ArrayList<UnidadPlaneacionAvanceProgramatico> buscarUnidadesPlaneacionDeAvance(String idAvanceProgramatico) {
        unidadesPlaneacionAvanceProgramatico = new ArrayList<>();
        
        consulta = "SELECT * FROM avance_programatico_unidad_planeacion WHERE Id_Avance_Programatico=?";
        conexionBD = DataBase.getDataBaseConnection();
        
        try {
            
            PreparedStatement sentencia = conexionBD.prepareStatement(consulta);
            sentencia.setString(1, idAvanceProgramatico);
            ResultSet resultadoConsulta = sentencia.executeQuery();
            
            while (resultadoConsulta.next()){
                unidadPlaneacion = new UnidadPlaneacionAvanceProgramatico();
                
                
                
            }
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(AvanceProgramaticoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            DataBase.closeConnection();
        }
        
        return unidadesPlaneacionAvanceProgramatico;
    }

    @Override
    public ArrayList<AvanceUnidadAvanceProgramatico> buscarAvancesUnidadDeAvance(String idAvanceProgramatico) {
        avancesUnidadAvanceProgramatico = new ArrayList<>();
        
        return avancesUnidadAvanceProgramatico;
    }
    
}
