
package mx.fei.sgpa.domain;

public class Academico {
    int numeroPersonal;
    String nombreAcademico;
    String gradoEstudios;
    RolAcademico rolAcademico;
    
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
