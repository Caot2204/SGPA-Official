/** ************************************************************* */
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   14/06/2018				  */
/* Ultima modificación: 14/06/2018				  */
/* Descripción: Controlador del Inicio de Sesión por un           */
/*              Académico.                 		          */
/** ************************************************************* */
package mx.fei.sgpa.gui;

import mx.fei.sgpa.dao.academico.AcademicoDAO;
import mx.fei.sgpa.dao.cuentadeusuario.CuentaUsuarioDAO;
import mx.fei.sgpa.domain.Academico;
import mx.fei.sgpa.domain.SesionActual;

/**
 * Capa lógica para el manejo del inicio de sesión de un Académico en el sistema.
 */
public class AdministradorInicioSesion {
    
    /**
     * Valida que el Nombre usuario y contraseña coincidan con alguna cuenta
     * de usuario registrada en el sistema mediante CuentaUsuarioDAO
     * 
     * @param nombreUsuario Nombre de usuario a buscar en el sistema
     * @param contraseña Contraseña de la cuenta de usuario ingresado
     * @return true si inicio sesion correctamente, false si no existe la cuenta de usuario ingresada
     */
    public boolean iniciarSesion(String nombreUsuario, String contraseña) {
        CuentaUsuarioDAO cuentaUsuarioDAO;
        AcademicoDAO academicoDAO;
        Academico academico;
        SesionActual sesionActual;
        int numeroDePersonal;
        boolean inicioCorrecto = false;
        
        if (validarDatos(nombreUsuario, contraseña)) {
            cuentaUsuarioDAO = new CuentaUsuarioDAO();
            numeroDePersonal = cuentaUsuarioDAO.iniciarSesion(nombreUsuario, contraseña);
            if (numeroDePersonal != 0) {
                academicoDAO = new AcademicoDAO();
                academico = academicoDAO.obtenerAcademico(numeroDePersonal);
                sesionActual = SesionActual.obtenerSesionActual();
                sesionActual.setNumeroPersonal(academico.getNumeroPersonal());
                sesionActual.setNombreAcademico(academico.getNombreAcademico());
                sesionActual.setRolAcademico(academico.getRolAcademico());
                inicioCorrecto = true;
            }
        }
        return inicioCorrecto;
    }
    
    /**
     * Valida que los datos ingresados por el usuario cumplan con las restricciones
     * necesarias (No Nulos y sin espacios en blanco)
     * 
     * @param nombreUsuario Nombre de usuario a validar
     * @param contraseña Contraseña a validar
     * @return true si los datos cumplen con las restricciones, false si no fue así
     */
    public boolean validarDatos(String nombreUsuario, String contraseña) {
        boolean datosValidos = false;
        if (nombreUsuario != null && !nombreUsuario.equals("")) {
            if (!String.valueOf(nombreUsuario.charAt(0)).equals(" ") && nombreUsuario.length() < 100) {
                if (contraseña != null && !contraseña.equals("")) {
                    if (!String.valueOf(contraseña.charAt(0)).equals(" ") && contraseña.length() < 100) {
                        datosValidos = true;
                    }
                }
            }            
        }
        return datosValidos;
    }
    
}
