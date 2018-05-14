
package mx.fei.sgpa.dao.avanceprogramatico.suitepruebas;

import java.util.ArrayList;
import mx.fei.sgpa.dao.avanceprogramatico.AvanceProgramaticoDAO;
import mx.fei.sgpa.domain.avanceprogramatico.AvancePorUnidad;
import mx.fei.sgpa.domain.avanceprogramatico.AvanceProgramatico;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SuiteRecuperarAvanceProgramatico {
    
    public AvanceProgramaticoDAO avanceDAO;
    public AvanceProgramatico avance;
    
    public SuiteRecuperarAvanceProgramatico() {
        ArrayList<AvancePorUnidad> avancesPorUnidad = new ArrayList<>();
        
        avancesPorUnidad.add(new AvancePorUnidad(4, 1, "Se termino en la primera semana de abril"));
        avancesPorUnidad.add(new AvancePorUnidad(5, 1, "Terminada, sin embargo para primer parcial se omitió el tema, dejándolo para el segundo; por aplicar examen departamental y dado que el otro bloque y sección de la tarde de Administración de Proyectos, iniciaron un mes posterior, no fue posible hasta este momento nivelarlos."));
        avancesPorUnidad.add(new AvancePorUnidad(6, (float) 0.5, "Iniciada"));
        
        avance = new AvanceProgramatico();
        avance.setAvancesDeUnidad(avancesPorUnidad);
        
        avanceDAO = new AvanceProgramaticoDAO();
    }
    
    @Test
    public void recuperarAvancesPorUnidad() {
        int cantidadEsperada = 3;

        ArrayList<AvancePorUnidad> avances = avanceDAO.obtenerAvancesPorUnidad("AVA-1");
        
        assertEquals("Probar unidad recuperada", this.avance.getAvancesDeUnidad().size(), avances.size());
        
        for (int a = 0; a < avances.size(); a++){
            assertEquals("Probar unidad recuperada", this.avance.getAvancesDeUnidad().get(a).getUnidad(), avances.get(a).getUnidad());
            assertEquals("Probar porcentaje recuperado", this.avance.getAvancesDeUnidad().get(a).getPorcentaje(), avances.get(a).getPorcentaje(), 0);
            assertEquals("Probar observacion recuperada", this.avance.getAvancesDeUnidad().get(a).getObservacion(), avances.get(a).getObservacion());
            
        }
    }
    
}
