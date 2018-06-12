/****************************************************************/
/* Nombre: Alberto Hernández Gómez				*/
/* Fecha de creación: 14/05/2018				*/
/* Ultima modificación: 14/05/2018				*/
/* Descripción:				                        */
/****************************************************************/
package mx.fei.sgpa.dao.cuentadeusuario;

import mx.fei.sgpa.domain.cuentausuario.CuentaUsuario;

/**
 *
 * @author beto
 */
public interface ICuentaUsuarioDAO{
    
    boolean guardarCuentaDeUsuario(CuentaUsuario cuentaUsuario);
    CuentaUsuario obtenerDatosCuentaUsuario(String nombreUsuario);
    
}
