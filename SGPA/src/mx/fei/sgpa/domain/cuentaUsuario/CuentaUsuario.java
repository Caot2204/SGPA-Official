/*******************************************************************************************************/
/* Nombre: Alberto Hernández Gómez				                                       */
/* Fecha de creación: 14/05/2018				                                       */
/* Ultima modificación: 14/05/2018				                                       */
/* Descripción:	Clase que se relaciona con la base de datos para la creacion de una cueta en el sistema*/
/*******************************************************************************************************/
package mx.fei.sgpa.domain.cuentausuario;

/**
 *
 * @author beto
 */
public class CuentaUsuario {
    
    private String nombreDeUsuario;
    private String contraseña;

    public CuentaUsuario() {
    }

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    
    
}
