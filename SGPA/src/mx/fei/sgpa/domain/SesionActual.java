/** ************************************************************* */
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   14/06/2018				  */
/* Ultima modificación: 14/06/2018				  */
/* Descripción: Datos de la sesión actual iniciada por el         */
/*              Académico.                 		          */
/** ************************************************************* */
package mx.fei.sgpa.domain;

/**
 * Datos de la sesión iniciada en el sistema por un Académico
 */
public class SesionActual {
    
    private int numeroPersonal;
    private String nombreAcademico;
    private RolAcademico rolAcademico;
    private static SesionActual sesionActual;
    
    /**
     * Obtiene la instancia de la SesionActual. Si existe la regresa, sino, crea una nueva
     * @return Objeto de tipo SesionActual
     */
    public static SesionActual obtenerSesionActual() {
        if (sesionActual == null) {
            sesionActual = new SesionActual();
        }
        return sesionActual;
    }

    public int getNumeroPersonal() {
        return numeroPersonal;
    }

    public String getNombreAcademico() {
        return nombreAcademico;
    }

    public RolAcademico getRolAcademico() {
        return rolAcademico;
    }

    public void setNumeroPersonal(int numeroPersonal) {
        this.numeroPersonal = numeroPersonal;
    }

    public void setNombreAcademico(String nombreAcademico) {
        this.nombreAcademico = nombreAcademico;
    }

    public void setRolAcademico(RolAcademico rolAcademico) {
        this.rolAcademico = rolAcademico;
    }
        
}
