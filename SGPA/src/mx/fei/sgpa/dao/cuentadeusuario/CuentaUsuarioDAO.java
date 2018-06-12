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
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.sgpa.datasource.DataBase;
import mx.fei.sgpa.domain.cuentausuario.CuentaUsuario;

public class CuentaUsuarioDAO implements ICuentaUsuarioDAO{
    
    private Connection conexionDB;
    private String consultaSQL;
    private PreparedStatement sentencia;

    public CuentaUsuarioDAO() {
    }
    

    @Override
    public boolean guardarCuentaDeUsuario(CuentaUsuario cuentaUsuario) {
        boolean guardadoExitoso = false;
        consultaSQL = "INSERT INTO cuenta_usuario VALUES (?,?)";
        conexionDB = DataBase.getDataBaseConnection();
         try {
            sentencia = conexionDB.prepareStatement(consultaSQL);
            sentencia.setString(1, cuentaUsuario.getNombreDeUsuario());
            sentencia.setString(2, cuentaUsuario.getContraseña());
            sentencia.execute();
            guardadoExitoso = true;
        } 
        catch (SQLException ex) {
            Logger.getLogger(CuentaUsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            DataBase.closeConnection();
        }
        return guardadoExitoso;
    }

    @Override
    public CuentaUsuario obtenerDatosCuentaUsuario(String nombreUsuario) {
        
        CuentaUsuario cuentaUsuario = new CuentaUsuario();
        consultaSQL = "SELECT * FROM cuenta_usuario WHERE Nombre_Usuario=?";
        conexionDB = DataBase.getDataBaseConnection();
        try {
            sentencia = conexionDB.prepareStatement(consultaSQL);
            sentencia.setString(1, nombreUsuario);
            ResultSet resultadoConsulta = sentencia.executeQuery();
            while (resultadoConsulta.next()){
                
                cuentaUsuario.setNombreDeUsuario(resultadoConsulta.getString("Password"));
              
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(CuentaUsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cuentaUsuario;  
    }
    
    
    
}
