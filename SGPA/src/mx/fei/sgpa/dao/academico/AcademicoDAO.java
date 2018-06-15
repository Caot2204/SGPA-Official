/** ************************************************************* */
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   08/06/2018				  */
/* Ultima modificación: 08/06/2018				  */
/* Descripción: Implementación de los métodos de acceso a la DB   */
/*              para Academico.                    		  */
/** ************************************************************* */
package mx.fei.sgpa.dao.academico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.fei.sgpa.datasource.DataBase;
import mx.fei.sgpa.domain.Academico;
import mx.fei.sgpa.domain.RolAcademico;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Capa de acceso a la base de datos del sistema para almacenar y recuperar un Academico
 */
public class AcademicoDAO implements IAcademicoDAO {
    
    private Connection conexionDB;
    private String consultaSQL;
    private PreparedStatement sentencia;
    private BarricadaAcademicoDAO barricadaAcademico;
    private static final Logger loggerDelSistema = LogManager.getLogger(AcademicoDAO.class);

    /**
     * Almacena los datos de un Academico en la base de datos del sistema
     * 
     * @param academico Datos de un Académico de la Universidad Veracruzana
     * @return true si los datos fueron guardados con éxito, false si no fue así
     */
    @Override
    public boolean guardarAcademico(Academico academico) {
        boolean guardadoExitoso = false;
        consultaSQL = "INSERT INTO academico VALUES (?,?,?,?)";
        conexionDB = DataBase.getDataBaseConnection();
        barricadaAcademico = BarricadaAcademicoDAO.obtenerInstancia();
        
        if (barricadaAcademico.validarDatosAcademico(academico)) {
            try {
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setInt(1, academico.getNumeroPersonal());
                sentencia.setString(2, academico.getNombreAcademico());
                sentencia.setString(3, academico.getGradoEstudios());
                sentencia.setString(4, academico.getRolAcademico().name());
                sentencia.execute();
                guardadoExitoso = true;
            } 
            catch (SQLException ex) {
                loggerDelSistema.error("guardarAcademico: Ocurrió un problema en BD");
            }
            finally {
                DataBase.closeConnection();
            }  
        }        
        return guardadoExitoso;        
    }

    /**
     * Recupera los datos del Académico que tenga como Número de personal el valor del parámetro numeroDePersonal
     * 
     * @param numeroDePersonal Identificador único para un Académico dentro de la Universidad Veracruzana
     * @return Datos del Académico que tenga como Número de Personal el valor del parámetro numeroDePersonal
     */
    @Override
    public Academico obtenerAcademico(int numeroDePersonal) {
        Academico academico = new Academico();
        consultaSQL = "SELECT * FROM academico WHERE Numero_Personal=?";
        conexionDB = DataBase.getDataBaseConnection();
        barricadaAcademico = BarricadaAcademicoDAO.obtenerInstancia();
        
        if (barricadaAcademico.validarNumeroDePersonal(numeroDePersonal)) {
            try {
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setInt(1, numeroDePersonal);
                ResultSet resultadoConsulta = sentencia.executeQuery();
                while (resultadoConsulta.next()) {
                    academico.setNumeroPersonal(numeroDePersonal);
                    academico.setNombreAcademico(resultadoConsulta.getString("Nombre"));
                    academico.setGradoEstudios(resultadoConsulta.getString("Grado_Estudios"));
                    academico.setRolAcademico(RolAcademico.valueOf(resultadoConsulta.getString("Rol")));
                }
            } 
            catch (SQLException ex) {
                loggerDelSistema.error("obtenerAcademico por numero de personal: Ocurrió un problema en BD");
            }
            finally {
                DataBase.closeConnection();
            } 
        }
        return academico;
    }
    
    /**
     * Recupera los datos del Académico que tenga como nombre el valor del parámetro nombreAcademico
     * 
     * @param nombreAcademico Nombre de un Académico dentro de la Universidad Veracruzana
     * @return Datos del Académico que tenga como nombre el valor del parámetro nombreAcademico
     */
    @Override
    public Academico obtenerAcademico(String nombreAcademico) {
        Academico academico = new Academico();
        consultaSQL = "SELECT * FROM academico WHERE Nombre=?";
        conexionDB = DataBase.getDataBaseConnection();
        barricadaAcademico = BarricadaAcademicoDAO.obtenerInstancia();
        
        if (barricadaAcademico.validarNombreAcademico(nombreAcademico)) {
            try {
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setString(1, nombreAcademico);
                ResultSet resultadoConsulta = sentencia.executeQuery();
                while (resultadoConsulta.next()) {
                    academico.setNumeroPersonal(resultadoConsulta.getInt("Numero_Personal"));
                    academico.setNombreAcademico(nombreAcademico);
                    academico.setGradoEstudios(resultadoConsulta.getString("Grado_Estudios"));
                    academico.setRolAcademico(RolAcademico.valueOf(resultadoConsulta.getString("Rol")));
                }
            } 
            catch (SQLException ex) {
                loggerDelSistema.error("obtenerAcademico por nombre: Ocurrió un problema en BD");
            }
            finally {
                DataBase.closeConnection();
            }   
        }
        return academico;
    }
        
}
