
package mx.fei.sgpa.dao.avanceprogramatico;

import java.util.ArrayList;
import mx.fei.sgpa.domain.avanceprogramatico.AvanceProgramatico;
import mx.fei.sgpa.domain.avanceprogramatico.AvanceUnidadAvanceProgramatico;
import mx.fei.sgpa.domain.avanceprogramatico.UnidadPlaneacionAvanceProgramatico;

public interface IAvanceProgramaticoDAO {
    boolean guardarAvanceProgramatico(AvanceProgramatico avanceProgramatico);
    boolean guardarUnidadesPlaneacion(String idAvanceProgramatico, ArrayList<UnidadPlaneacionAvanceProgramatico> unidadesPlaneacion);
    boolean guardarAvancesUnidades(String idAvanceProgramatico, ArrayList<AvanceUnidadAvanceProgramatico> avancesUnidad);
    AvanceProgramatico buscarAvanceProgramaticoById(String id);
    ArrayList<AvanceProgramatico> buscarAvanceProgramaticoByDocente(String idDocente);
    ArrayList<UnidadPlaneacionAvanceProgramatico> buscarUnidadesPlaneacionDeAvance (String idAvanceProgramatico);
    ArrayList<AvanceUnidadAvanceProgramatico> buscarAvancesUnidadDeAvance (String idAvanceProgramatico);
}
