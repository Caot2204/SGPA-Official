/****************************************************************/
/* Nombre: Alberto Hernández Gómez				*/
/* Fecha de creación:   13/06/2018				*/
/* Ultima modificación: 13/06/2018				*/
/* Descripción:	Pruebas a la clase CuentaUsuarioDAO.            */
/****************************************************************/
package mx.fei.sgpa.dao.cuentadeusuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.sgpa.datasource.DataBase;
import mx.fei.sgpa.domain.cuentausuario.CuentaUsuario;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CuentaUsuarioDAOTest {
    
    private CuentaUsuario cuentaDePrueba;
    private CuentaUsuarioDAO cuentaUsuarioDAO;
    
    public CuentaUsuarioDAOTest() {
        cuentaDePrueba = new CuentaUsuario();
        cuentaUsuarioDAO = new CuentaUsuarioDAO();
        cuentaDePrueba.setNumeroDePersonal(12345);
        cuentaDePrueba.setNombreDeUsuario("UsuarioPrueba");
        cuentaDePrueba.setContraseña("passwordPrueba");
    }
    
    @Test
    public void guardarCuentaUsuario() {
        boolean valorEsperado = true;
        boolean valorObtenido = cuentaUsuarioDAO.guardarCuentaDeUsuario(cuentaDePrueba);
        
        assertEquals("Prueba de guardar una cuenta de usuario", valorEsperado, valorObtenido);
    }

    @Test
    public void iniciarSesion() {
        int valorEsperado = 12345;
        int valorObtenido = cuentaUsuarioDAO.iniciarSesion("UsuarioPrueba", "passwordPrueba");
        
        assertEquals("Prueba de iniciar sesion", valorEsperado, valorObtenido);
    }
    
    @Test
    public void iniciarSesionFallida() {
        int valorEsperado = 0;
        int valorObtenido = cuentaUsuarioDAO.iniciarSesion("usuarioInexistente", "passwordPrueba");
        
        assertEquals("Prueba de iniciar sesion con usuario no registrado", valorEsperado, valorObtenido);
    }
    
    @AfterClass
    public static void limpiarDatosDePrueba() {
        Connection conexionDB = DataBase.getDataBaseConnection();
        String consultaSQL = "DELETE FROM cuenta_usuario where Numero_Personal=?";
        try {
            PreparedStatement sentencia = conexionDB.prepareStatement(consultaSQL);
            sentencia.setInt(1, 12345);
            sentencia.execute();
        } 
        catch (SQLException ex) {
            Logger.getLogger(CuentaUsuarioDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
