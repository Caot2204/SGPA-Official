/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   15/06/2018				  */
/* Ultima modificación: 15/06/2018				  */
/* Descripción: Capa encargada de validar lo datos a utilizar en  */
/*              AcademiaDAO                                      */
/****************************************************************/
package mx.fei.sgpa.dao.academia;

import mx.fei.sgpa.domain.Academia;

/**
 * Capa encargada de validar lo datos a utilizar en AcademiaDAO
 */
public class BarricadaAcademiaDAO {
    
    private static BarricadaAcademiaDAO barricadaAcademiaDAO;
    
    /**
     * Devuelve una instancia de BarricadaAcademiaDAO. Si existe una, la devuelve
     * sino, crea una nueva
     * 
     * @return Objeto de tipo BarricadaAcademiaDAO
     */
    public static BarricadaAcademiaDAO obtenerInstancia() {
        if (barricadaAcademiaDAO == null) {
            barricadaAcademiaDAO = new BarricadaAcademiaDAO();
        }
        return barricadaAcademiaDAO;
    }
    
    /**
     * Valida que los datos de una Academia cumplan con las restricciones para ser
     * almacenados (No Nulos, no empezar con espacio y menor al valor máximo entero)
     * 
     * @param academiaAValidar Datos de la Academia a validar
     * @return true si los datos cumplen con las restricciones, false si no es así
     */
    public boolean validarDatos(Academia academiaAValidar) {
        boolean datosValidos = false;        
        if (validarIdAcademia(academiaAValidar.getIdAcademia())) {
            if (validarNombreAcademia(academiaAValidar.getNombreAcademia())) {
                if (validarIdCoordinador(academiaAValidar.getCoordinadorAcademia())) {
                    datosValidos = true;                    
                }
            }
        }       
        return datosValidos;
    }
    
    /**
     * Valida que el Identificador de la Academia no sea nulo, contenga un 
     * espacio en blanco al principio o exceda la longitud permitida
     * 
     * @param idAcademia Identificador de una Academia a validar
     * @return true si el dato cumple con las restricciones, false si no fue así
     */
    public boolean validarIdAcademia(String idAcademia) {
        boolean datoValido = false;
        if (idAcademia != null) {
            if (!idAcademia.equals("") && !String.valueOf(idAcademia.charAt(0)).equals(" ")) {
                if (idAcademia.length() < 50) {
                    datoValido = true;                    
                }
            }
        }
        return datoValido;        
    }
    
    /**
     * Valida que el Nombre de la Academia no sea nulo, contenga un 
     * espacio en blanco al principio o exceda la longitud permitida
     * 
     * @param nombreAcademia Nombre de una academia a validar
     * @return true si el dato cumple con las restricciones, false si no fue así
     */
    public boolean validarNombreAcademia(String nombreAcademia) {
        boolean datoValido = false;
        if (nombreAcademia != null) {
            if (!nombreAcademia.equals("") && !String.valueOf(nombreAcademia.charAt(0)).equals(" ")) {
                if (nombreAcademia.length() < 500) {
                    datoValido = true;                    
                }
            }
        }
        return datoValido;        
    }
    
    /**
     * Valida que el Número de personal del Académico COORDINADOR 
     * no exceda el máximo valor permitido
     * 
     * @param idCoordinador Número de Personal de un Académico a validar
     * @return true si el dato cumple con las restricciones, false si no fue así
     */
    public boolean validarIdCoordinador(int idCoordinador) {
        boolean datoValido = false;
        if (idCoordinador < Integer.MAX_VALUE) {
            datoValido = true;
        }
        return datoValido;        
    }
}
