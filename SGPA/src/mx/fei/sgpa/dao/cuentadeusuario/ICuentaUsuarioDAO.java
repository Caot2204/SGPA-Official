/****************************************************************/
/* Nombre: Alberto Hernández Gómez				*/
/* Fecha de creación: 14/05/2018				*/
/* Ultima modificación: 14/05/2018				*/
/* Descripción: Interfaz de métodoso para el DAO CuentaUsuario. */
/****************************************************************/
package mx.fei.sgpa.dao.cuentadeusuario;

import mx.fei.sgpa.domain.cuentausuario.CuentaUsuario;

public interface ICuentaUsuarioDAO{
    boolean guardarCuentaDeUsuario(CuentaUsuario cuentaUsuario);
    CuentaUsuario obtenerDatosCuentaUsuario(String nombreUsuario);
}
