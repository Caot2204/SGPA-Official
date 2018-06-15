/*****************************************************************/
/* Nombre: Alberto Hernández Gómez			         */
/* Fecha de creación: 14/05/2018			         */
/* Ultima modificación: 12/06/2018			         */
/* Descripción:	Cuenta de usuario utilizada para poder acceder   */
/*              al SGPA, vinculada a un único Académico          */
/*****************************************************************/
package mx.fei.sgpa.domain.cuentausuario;

/**
 * Cuenta de usuario utilizada para poder acceder al SGPA, vinculada a un
 * único Académico.
 */
public class CuentaUsuario {
    
    private int numeroDePersonal;
    private String nombreDeUsuario;
    private String contraseña;

    public CuentaUsuario() {
    }

    public int getNumeroDePersonal() {
        return numeroDePersonal;
    }

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setNumeroDePersonal(int numeroDePersonal) {
        this.numeroDePersonal = numeroDePersonal;
    }

    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    
    
}
