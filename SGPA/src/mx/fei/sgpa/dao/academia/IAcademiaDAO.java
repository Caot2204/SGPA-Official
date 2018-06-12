/** ************************************************************* */
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   08/06/2018				  */
/* Ultima modificación: 08/06/2018				  */
/* Descripción: Detalles de una Academia.                         */
/** ************************************************************* */
package mx.fei.sgpa.dao.academia;

import java.util.ArrayList;
import mx.fei.sgpa.domain.Academia;
import mx.fei.sgpa.domain.Academico;

public interface IAcademiaDAO {
    boolean guardarAcademia(Academia academia);
    Academia obtenerAcademia(String idAcademia);
    Academia obtenerAcademiaPorNombre(String nombreAcademia);
    ArrayList<Academia> obtenerAcademias(int numeroPersonalCoordinador);
    ArrayList<Academico> obtenerIntegrantesAcademia(String idAcademia);
}
