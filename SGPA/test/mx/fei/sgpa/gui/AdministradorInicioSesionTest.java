/** ************************************************************* */
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   14/06/2018				  */
/* Ultima modificación: 14/06/2018				  */
/* Descripción: Prueba a los métodos del AdministradorInicioSesion*/
/** ************************************************************* */
package mx.fei.sgpa.gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.sgpa.dao.cuentadeusuario.CuentaUsuarioDAO;
import mx.fei.sgpa.datasource.DataBase;
import mx.fei.sgpa.domain.cuentausuario.CuentaUsuario;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AdministradorInicioSesionTest {
    
    private CuentaUsuario cuentaDePrueba;
    private CuentaUsuarioDAO cuentaUsuarioDAO;
    private AdministradorInicioSesion administradorSesion;
    
    public AdministradorInicioSesionTest() {
        cuentaDePrueba = new CuentaUsuario();
        cuentaUsuarioDAO = new CuentaUsuarioDAO();
        cuentaDePrueba.setNumeroDePersonal(12345);
        cuentaDePrueba.setNombreDeUsuario("UsuarioPrueba");
        cuentaDePrueba.setContraseña("passwordPrueba");
        cuentaUsuarioDAO.guardarCuentaDeUsuario(cuentaDePrueba);
        administradorSesion = new AdministradorInicioSesion();
    }
    
    @Test
    public void probarInicioSesion() {
        boolean valorEsperado = true;
        boolean valorObtenido = administradorSesion.iniciarSesion("UsuarioPrueba", "passwordPrueba");
        
        assertEquals("Prueba de inicio de sesion correcto", valorEsperado, valorObtenido);
    }
    
    @Test
    public void probarInicioSesionFallidoContraseña() {
        boolean valorEsperado = false;
        boolean valorObtenido = administradorSesion.iniciarSesion("UsuarioPrueba", "hola");
        
        assertEquals("Prueba de inicio de sesion con contraseña erronea", valorEsperado, valorObtenido);
    }
    
    @Test
    public void probarInicioSesionFallidoNombreUsuario() {
        boolean valorEsperado = false;
        boolean valorObtenido = administradorSesion.iniciarSesion("Otro usuario", "hola");
        
        assertEquals("Prueba de inicio de sesion con usuario erroneo", valorEsperado, valorObtenido);
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
            Logger.getLogger(AdministradorInicioSesionTest.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }

        
}
