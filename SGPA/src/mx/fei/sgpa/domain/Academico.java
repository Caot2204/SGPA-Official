/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   07/05/2018				  */
/* Ultima modificación: 12/06/2018				  */
/* Descripción: Clase que representa a un Académico en el sistema */
/****************************************************************/
package mx.fei.sgpa.domain;

/**
 * Clase que representa a un Académico en el sistema
 */
public class Academico {
    private int numeroPersonal;
    private String nombreAcademico;
    private String gradoEstudios;
    private RolAcademico rolAcademico;
    
    public Academico() {
        
    }

    public int getNumeroPersonal() {
        return numeroPersonal;
    }

    public String getNombreAcademico() {
        return nombreAcademico;
    }

    public String getGradoEstudios() {
        return gradoEstudios;
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

    public void setGradoEstudios(String gradoEstudios) {
        this.gradoEstudios = gradoEstudios;
    }

    public void setRolAcademico(RolAcademico rolAcademico) {
        this.rolAcademico = rolAcademico;
    }
    
}
