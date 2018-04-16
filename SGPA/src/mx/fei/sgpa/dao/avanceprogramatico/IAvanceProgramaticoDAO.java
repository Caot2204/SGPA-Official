
package mx.fei.sgpa.dao.avanceprogramatico;

import java.util.ArrayList;
import mx.fei.sgpa.domain.avanceprogramatico.AvanceProgramatico;

public interface IAvanceProgramaticoDAO {
    boolean guardarAvanceProgramatico();
    AvanceProgramatico buscarAvanceProgramaticoById(String id);
    ArrayList<AvanceProgramatico> buscarAvanceProgramaticoByDocente(String idDocente);
}
