/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   08/05/2018				  */
/* Ultima modificación: 08/05/2018				  */
/* Descripción: Definir los roles que puede tener un Académico.   */
/****************************************************************/

package mx.fei.sgpa.domain;

/**
 * Rol que juega un Académico dentro de la Universidad Veracruzana. El Académico
 * puede ser un Docente que imparte Cursos, un Coordinador de una Academia o
 * Coordinador General de todas las Academias de la Facultad de Estadística e 
 * Informática.
 */
public enum RolAcademico {
    DOCENTE, COORDINADOR, COORDINADOR_GENERAL;    
}
