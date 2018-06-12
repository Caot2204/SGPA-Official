/** ************************************************************* */
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   09/06/2018				  */
/* Ultima modificación: 09/06/2018				  */
/* Descripción: Interfaz para los métodos del AcademicoDAO        */
/** ************************************************************* */
package mx.fei.sgpa.dao.academico;

import mx.fei.sgpa.domain.Academico;

public interface IAcademicoDAO {
    boolean guardarAcademico(Academico academico);
    Academico obtenerAcademico(int numeroDePersonal);
    Academico obtenerAcademico(String nombreAcademico);
}
