
package mx.fei.sgpa.domain.avanceprogramatico;

import java.util.ArrayList;
import mx.fei.sgpa.domain.EstadoDeDocumento;

public class AvanceProgramatico {
    String id;
    int nrc;
    String nombreExperiencia;
    String programaEducativo;
    String bloque;
    String seccion;
    String academico;
    String objetivoGeneral;
    ArrayList<UnidadPlaneacionAvanceProgramatico> unidadesDeEvaluacion;
    ArrayList<AvanceUnidadAvanceProgramatico> avancesDeUnidad;
    EstadoDeDocumento estado;
    
}
