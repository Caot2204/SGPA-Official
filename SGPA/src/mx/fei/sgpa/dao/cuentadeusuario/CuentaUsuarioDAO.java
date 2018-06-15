/****************************************************************/
/* Nombre: Alberto Hernández Gómez				*/
/* Fecha de creación: 14/05/2018				*/
/* Ultima modificación: 14/05/2018				*/
/* Descripción:				                        */
/****************************************************************/
package mx.fei.sgpa.dao.cuentadeusuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.fei.sgpa.datasource.DataBase;
import mx.fei.sgpa.domain.cuentausuario.CuentaUsuario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Capa de acceso a la base de datos del sistema para almacenar y recuperar una CuentaUSuario
 */
public class CuentaUsuarioDAO implements ICuentaUsuarioDAO{
    
    private Connection conexionDB;
    private String consultaSQL;
    private PreparedStatement sentencia;
    private BarricadaCuentaUsuarioDAO barricada;
    private static final Logger loggerDelSistema = LogManager.getLogger(CuentaUsuarioDAO.class);

    public CuentaUsuarioDAO() {
    }
    
    /**
     * Almacena los datos de una CuentaUsuario en la base de datos del sistema
     * 
     * @param cuentaUsuario Datos de una cuenta de usuario para acceder al SGPA
     * @return true si los datos fueron guardados exitosamente, false si no fue así
     */
    @Override
    public boolean guardarCuentaDeUsuario(CuentaUsuario cuentaUsuario) {
        boolean guardadoExitoso = false;
        consultaSQL = "INSERT INTO cuenta_usuario VALUES (?,?,?)";
        conexionDB = DataBase.getDataBaseConnection();
        barricada = BarricadaCuentaUsuarioDAO.obtenerInstancia();
        
        if (barricada.validarDatosCuentaUsuario(cuentaUsuario)) {
            try {              
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setInt(1, cuentaUsuario.getNumeroDePersonal());
                sentencia.setString(2, cuentaUsuario.getNombreDeUsuario());
                sentencia.setString(3, cuentaUsuario.getContraseña());
                sentencia.execute();
                guardadoExitoso = true;
            } 
            catch (SQLException excepcionGuardadoDatos) {
                loggerDelSistema.error("guardarDatosCuentaUsuario: Ocurrió un problema en BD");
            }
            finally {
                DataBase.closeConnection();
            }    
        }
        return guardadoExitoso;
    }

    /**
     * Recupera los datos de una CuentaUsuario que tenga como nombre de usuario el valor del parámetro nombreUsuario
     * 
     * @param nombreUsuario Nombre de un usuario registrado en el SGPA
     * @return Datos de la cuenta de usuario que tenga como nombre el valor del parámetro nombreUsuario
     */
    @Override
    public CuentaUsuario obtenerDatosCuentaUsuario(String nombreUsuario) {
        CuentaUsuario cuentaUsuario = new CuentaUsuario();
        cuentaUsuario.setNumeroDePersonal(0);
        cuentaUsuario.setNombreDeUsuario("");
        cuentaUsuario.setContraseña("");
        consultaSQL = "SELECT * FROM cuenta_usuario WHERE Nombre_Usuario=?";
        conexionDB = DataBase.getDataBaseConnection();
        barricada = BarricadaCuentaUsuarioDAO.obtenerInstancia();
        
        if (barricada.validarNombreUsuario(nombreUsuario)) {
            try {
                sentencia = conexionDB.prepareStatement(consultaSQL);
                sentencia.setString(1, nombreUsuario);
                ResultSet resultadoConsulta = sentencia.executeQuery();
                while (resultadoConsulta.next()){
                    cuentaUsuario.setNumeroDePersonal(resultadoConsulta.getInt("Numero_Personal"));
                    cuentaUsuario.setContraseña(resultadoConsulta.getString("Password"));              
                } 
            } 
            catch (SQLException excepcionObtenerDatos) {
                loggerDelSistema.error("obtenerDatosCuentaUsuario: Ocurrió un problema en BD");
            } 
        }
        return cuentaUsuario;  
    }
    
    /**
     * Compara si los datos de la cuenta de usuario ingresada coincide con los registrados en el sistema
     * 
     * @param nombreUsuario Nombre del usuario ingresada en el loggin
     * @param password Contraseña del usuario ingresada en el loggin
     * @return numeroDePersonal vinculado al Nombre de usuario y Contraseña ingresados. 0 si la contraseña o el usuario no coinciden en alguna CuentaUsuario registrada
     */    
    public int iniciarSesion(String nombreUsuario, String password) {
        int numeroDePersonal = 0;
        barricada = BarricadaCuentaUsuarioDAO.obtenerInstancia();
        
        if (barricada.validarNombreUsuario(nombreUsuario) && barricada.validarContraseña(password)) {
            CuentaUsuario cuentaRecuperada = obtenerDatosCuentaUsuario(nombreUsuario);
            if (password.equals(cuentaRecuperada.getContraseña())) {
                numeroDePersonal = cuentaRecuperada.getNumeroDePersonal();
            }   
        }
        return numeroDePersonal;
    }   
    
}
