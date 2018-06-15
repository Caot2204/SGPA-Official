/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   15/06/2018				  */
/* Ultima modificación: 15/06/2018				  */
/* Descripción: Capa encargada de validar lo datos a utilizar en  */
/*              CuentaUsuarioDAO                                  */
/****************************************************************/
package mx.fei.sgpa.dao.cuentadeusuario;

import mx.fei.sgpa.domain.cuentausuario.CuentaUsuario;

/**
 * Capa encargada de validar lo datos a utilizar en CuentaUsuarioDAO
 */
public class BarricadaCuentaUsuarioDAO {
    
    private static BarricadaCuentaUsuarioDAO barricadaCuentaUsuarioDAO;
    
    /**
     * Devuelve una instancia de BarricadaCuentaUsuarioDAO. Si existe una, la devuelve
     * sino, crea una nueva
     * 
     * @return Objeto de tipo BarricadaCuentaUsuarioDAO
     */
    public static BarricadaCuentaUsuarioDAO obtenerInstancia() {
        if (barricadaCuentaUsuarioDAO == null) {
            barricadaCuentaUsuarioDAO = new BarricadaCuentaUsuarioDAO();
        }
        return barricadaCuentaUsuarioDAO;
    }
    
    /**
     * Valida que los datos de la CuentaUsuario cumplan con las restricciones para ser
     * almacenados (No Nulos, no empezar con espacio y menor al valor máximo entero)
     * 
     * @param cuentaAValidar Datos de la CuentaUsuario a validar
     * @return true si los datos cumplen con las restricciones, false si no es así
     */
    public boolean validarDatosCuentaUsuario(CuentaUsuario cuentaAValidar) {
        boolean datosValidos = false;
        if (validarNumeroDePersonal(cuentaAValidar.getNumeroDePersonal())) {
            if (validarNombreUsuario(cuentaAValidar.getNombreDeUsuario())) {
                if (validarContraseña(cuentaAValidar.getContraseña())) {
                    datosValidos = true;
                }
            }
        }
        return datosValidos;
    }
    
    /**
     * Valida que el Identificador del Academico no exceda el valor entero máximo permitido
     * 
     * @param numeroPersonal Número de Personal de un Academico a validar
     * @return true si el dato cumple con las restricciones, false si no fue así
     */
    public boolean validarNumeroDePersonal(int numeroPersonal) {
        boolean datoValido = false;
        if (numeroPersonal < Integer.MAX_VALUE) {
            datoValido = true;
        }
        return datoValido;
    }
    
    /**
     * Valida que el Nombre de Usuario no sea nulo, contenga un 
     * espacio en blanco al principio o exceda la longitud permitida
     * 
     * @param nombreUsuario Nombre de Usuario a validar
     * @return true si el dato cumple con las restricciones, false si no fue así
     */
    public boolean validarNombreUsuario(String nombreUsuario) {
        boolean datoValido = false;
        if (nombreUsuario != null) {
            if (!nombreUsuario.equals("") && !String.valueOf(nombreUsuario.charAt(0)).equals(" ")) {
                if (nombreUsuario.length() < 100) {
                    datoValido = true;                    
                }
            }
        }
        return datoValido;
    }
    
    /**
     * Valida que la Contraseña no sea nula, contenga un 
     * espacio en blanco al principio o exceda la longitud permitida
     * 
     * @param contraseña Contraeña a validar
     * @return true si el dato cumple con las restricciones, false si no fue así
     */
    public boolean validarContraseña(String contraseña) {
        boolean datoValido = false;
        if (contraseña != null) {
            if (!contraseña.equals("") && !String.valueOf(contraseña.charAt(0)).equals(" ")) {
                if (contraseña.length() < 100) {
                    datoValido = true;                    
                }
            }
        }
        return datoValido;
    }
    
}
