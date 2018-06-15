/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   15/06/2018				  */
/* Ultima modificación: 15/06/2018				  */
/* Descripción: Capa encargada de validar lo datos a utilizar en  */
/*              AcademicoDAO                                      */
/****************************************************************/
package mx.fei.sgpa.dao.academico;

import mx.fei.sgpa.domain.Academico;
import mx.fei.sgpa.domain.RolAcademico;

/**
 * Capa encargada de validar lo datos a utilizar en AcademicoDAO
 */
public class BarricadaAcademicoDAO {
    
    private static BarricadaAcademicoDAO barricadaAcademicoDAO;
    
    /**
     * Devuelve una instancia de BarricadaAcademicoDAO. Si existe una, la devuelve
     * sino, crea una nueva
     * 
     * @return Objeto de tipo BarricadaAcademicoDAO
     */
    public static BarricadaAcademicoDAO obtenerInstancia() {
        if (barricadaAcademicoDAO == null) {
            barricadaAcademicoDAO = new BarricadaAcademicoDAO();
        }
        return barricadaAcademicoDAO;
    }
    
    /**
     * Valida que los datos de un Academico cumplan con las restricciones para ser
     * almacenados (No Nulos, no empezar con espacio y menor al valor máximo entero)
     * 
     * @param academicoAValidar Datos del Academico a validar
     * @return true si los datos cumplen con las restricciones, false si no es así
     */
    public boolean validarDatosAcademico(Academico academicoAValidar) {
        boolean datosValidos = false;
        if (validarNumeroDePersonal(academicoAValidar.getNumeroPersonal())) {
            if (validarNombreAcademico(academicoAValidar.getNombreAcademico())) {
                if (validarGradoEstudios(academicoAValidar.getGradoEstudios())) {
                    if (validarRolAcademico(academicoAValidar.getRolAcademico())) {
                        datosValidos = true;
                    }
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
     * Valida que el Nombre del Academico no sea nulo, contenga un 
     * espacio en blanco al principio o exceda la longitud permitida
     * 
     * @param nombreAcademico Nombre de un Academico a validar
     * @return true si el dato cumple con las restricciones, false si no fue así
     */
    public boolean validarNombreAcademico(String nombreAcademico) {
        boolean datoValido = false;
        if (nombreAcademico != null) {
            if (!nombreAcademico.equals("") && !String.valueOf(nombreAcademico.charAt(0)).equals(" ")) {
                if (nombreAcademico.length() < 100) {
                    datoValido = true;                    
                }
            }
        }
        return datoValido;
    }
    
    /**
     * Valida que el Grado de Estudios del Academico no sea nulo, contenga un 
     * espacio en blanco al principio o exceda la longitud permitida
     * 
     * @param gradoEstudios Grado de estudios de un Academico a validar
     * @return true si el dato cumple con las restricciones, false si no fue así
     */
    public boolean validarGradoEstudios(String gradoEstudios) {
        boolean datoValido = false;
        if (gradoEstudios != null) {
            if (!gradoEstudios.equals("") && !String.valueOf(gradoEstudios.charAt(0)).equals(" ")) {
                if (gradoEstudios.length() < 150) {
                    datoValido = true;                    
                }
            }
        }
        return datoValido;
    }
    
    /**
     * Valida que el Rol del Academico sea DOCENTE, COORDINADOR o COORDINADOR_GENERAL
     * 
     * @param rol Rol de un Academico a validar
     * @return true si el dato cumple con las restricciones, false si no fue así
     */
    public boolean validarRolAcademico(RolAcademico rol) {
        boolean datoValido = false;
        if (rol.name().equals(RolAcademico.COORDINADOR.name()) || rol.name().equals(RolAcademico.DOCENTE.name()) || rol.name().equals(RolAcademico.COORDINADOR_GENERAL.name())) {
            datoValido = true;
        }
        return datoValido;
    }
    
}
