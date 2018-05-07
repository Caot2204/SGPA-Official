
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
    ArrayList<UnidadDePlaneacion> obtenerUnidadesPlaneacionDeAvance (String idAvanceProgramatico);
    ArrayList<AvancePorUnidad> obtenerAvancesUnidadDeAvance (String idAvanceProgramatico);
}
