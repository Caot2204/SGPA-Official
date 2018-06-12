/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   16/04/2018				  */
/* Ultima modificación: 08/05/2018				  */
/* Descripción: Definir la interfaz de métodos para el DAO de     */
/*              AvanceProgramatico.				  */
/****************************************************************/

package mx.fei.sgpa.dao.avanceprogramatico;

import java.util.ArrayList;
import mx.fei.sgpa.domain.avanceprogramatico.AvanceProgramatico;
import mx.fei.sgpa.domain.avanceprogramatico.AvancePorUnidad;
import mx.fei.sgpa.domain.avanceprogramatico.UnidadDePlaneacion;

public interface IAvanceProgramaticoDAO {
    boolean guardarAvanceProgramatico(AvanceProgramatico avanceProgramatico);
    boolean guardarUnidadesPlaneacion(String idAvanceProgramatico, ArrayList<UnidadDePlaneacion> unidadesPlaneacion);
    boolean guardarDetallesUnidadesPlaneacion(String idAvanceProgramatico, ArrayList<UnidadDePlaneacion> unidadesPlaneacion);
    boolean guardarAvancesPorUnidades(String idAvanceProgramatico, ArrayList<AvancePorUnidad> avancesUnidad);
    AvanceProgramatico buscarAvanceProgramaticoById(String id);
    ArrayList<AvanceProgramatico> buscarAvanceProgramaticoByDocente(String idDocente);
    ArrayList<UnidadDePlaneacion> obtenerUnidadesDePlaneacion (String idAvanceProgramatico);
    ArrayList<AvancePorUnidad> obtenerAvancesPorUnidad (String idAvanceProgramatico);
}
