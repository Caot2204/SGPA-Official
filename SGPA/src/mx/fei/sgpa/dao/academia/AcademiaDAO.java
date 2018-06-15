/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   08/06/2018				  */
/* Ultima modificación: 08/06/2018				  */
/* Descripción: Implementación del acceso a BD para recuperar y   */
/*              guardar Academias                                 */
/****************************************************************/
package mx.fei.sgpa.dao.academia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.fei.sgpa.datasource.DataBase;
import mx.fei.sgpa.domain.Academia;
import mx.fei.sgpa.domain.Academico;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Capa de acceso a la base de datos del sistema para almacenar y recuperar una Academia
 */
public class AcademiaDAO implements IAcademiaDAO {
    
    private Connection conexionDB;
    private String consultaSQL;
    private PreparedStatement sentencia;
    private BarricadaAcademiaDAO barricada;
    private static final Logger loggerDelSistema = LogManager.getLogger(AcademiaDAO.class);

    /**
     * @param academia Datos de una Academia a almacenar
     * @return true si los datos fueron guardados con éxito, false si no fue así
     */
    @Override
    public boolean guardarAcademia(Academia academia) {
        boolean guardadoExitoso = false;
        consultaSQL = "INSERT INTO academia VALUES (?,?,?)";
        conexionDB = DataBase.getDataBaseConnection();
        barricada = BarricadaAcademiaDAO.obtenerInstancia();
        
        if (barricada.validarDatos(academia)) {
            try {
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setString(1, academia.getIdAcademia());
                sentencia.setString(2, academia.getNombreAcademia());
                sentencia.setInt(3, academia.getCoordinadorAcademia());
                sentencia.execute();
                guardadoExitoso = true;
            } 
            catch (SQLException ex) {
                loggerDelSistema.error("guardarAcademia: Ocurrió un problema en BD");            
            }
            finally {
                DataBase.closeConnection();
            }  
        }
        
        return guardadoExitoso;        
    }
    
    /**
     * @param numeroPersonalCoordinador Identificador único del Académico en la Universidad Veracruzana
     * @return Academias que están bajo cargo del Académico
     */
    @Override
    public ArrayList<Academia> obtenerAcademias(int numeroPersonalCoordinador) {
        ArrayList<Academia> academiasDeCoordinador = new ArrayList<>();
        consultaSQL = "SELECT * FROM academia WHERE Coordinador=?";
        conexionDB = DataBase.getDataBaseConnection();
        barricada = BarricadaAcademiaDAO.obtenerInstancia();
        
        if (barricada.validarIdCoordinador(numeroPersonalCoordinador)) {
            try {
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setInt(1, numeroPersonalCoordinador);
                ResultSet resultadoConsulta = sentencia.executeQuery();
                while (resultadoConsulta.next()){
                    Academia academia = new Academia();
                    academia.setIdAcademia(resultadoConsulta.getString("Id"));
                    academia.setNombreAcademia(resultadoConsulta.getString("Nombre"));
                    academia.setCoordinadorAcademia(resultadoConsulta.getInt("Coordinador"));
                    academiasDeCoordinador.add(academia);
                }
            } 
            catch (SQLException ex) {
                loggerDelSistema.error("obtenerAcademias: Ocurrió un problema en BD");
            }   
        }
        
        return academiasDeCoordinador;        
    }

    /**
     * @param idAcademia Identificador único para una Academia dentro de la Universidad Veracruzana
     * @return Datos de la Academia que su Id coincida con el parámetro idAcademia
     */
    @Override
    public Academia obtenerAcademia(String idAcademia) {
        Academia academia = new Academia();
        consultaSQL = "SELECT * FROM academia WHERE Id=?";
        conexionDB = DataBase.getDataBaseConnection();
        barricada = BarricadaAcademiaDAO.obtenerInstancia();
        
        if (barricada.validarIdAcademia(idAcademia)) {
            try {
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setString(1, idAcademia);
                ResultSet resultadoConsulta = sentencia.executeQuery();
                while (resultadoConsulta.next()){
                    academia.setIdAcademia(resultadoConsulta.getString("Id"));
                    academia.setNombreAcademia(resultadoConsulta.getString("Nombre"));
                    academia.setCoordinadorAcademia(resultadoConsulta.getInt("Coordinador"));
                }
            } 
            catch (SQLException ex) {
                loggerDelSistema.error("obtenerAcademia: Ocurrió un problema en BD");
            } 
        }
        return academia;        
    }
    
    /**
     * @param nombreAcademia Nombre de la Academia dentro de la Universidad Veracruzana
     * @return Datos de la Academia que tenga como nombre el valor del parámetro nombreAcademia
     */
    @Override
    public Academia obtenerAcademiaPorNombre(String nombreAcademia) {
        Academia academia = new Academia();
        consultaSQL = "SELECT * FROM academia WHERE Nombre=?";
        conexionDB = DataBase.getDataBaseConnection();
        barricada = BarricadaAcademiaDAO.obtenerInstancia();
        
        if (barricada.validarNombreAcademia(nombreAcademia)) {
            try {
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setString(1, nombreAcademia);
                ResultSet resultadoConsulta = sentencia.executeQuery();
                while (resultadoConsulta.next()){
                    academia.setIdAcademia(resultadoConsulta.getString("Id"));
                    academia.setNombreAcademia(resultadoConsulta.getString("Nombre"));
                    academia.setCoordinadorAcademia(resultadoConsulta.getInt("Coordinador"));
                }
            } 
            catch (SQLException ex) {
                loggerDelSistema.error("obtenerAcademiaPorNombre: Ocurrió un problema en BD");
            }  
        }
        return academia;        
    }

    /**
     * @param idAcademia Identificador único para una Academia dentro de la Universidad Veracruzana
     * @return Integrantes de la Academia
     */
    @Override
    public ArrayList<Academico> obtenerIntegrantesAcademia(String idAcademia) {
        ArrayList<Academico> integrantes = new ArrayList<>();
        
        return integrantes;
    }
        
}
