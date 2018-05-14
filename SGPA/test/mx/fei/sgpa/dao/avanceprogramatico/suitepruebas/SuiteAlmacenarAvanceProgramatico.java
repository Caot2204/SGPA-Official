
package mx.fei.sgpa.dao.avanceprogramatico.suitepruebas;

import java.util.ArrayList;
import mx.fei.sgpa.dao.avanceprogramatico.AvanceProgramaticoDAO;
import mx.fei.sgpa.domain.avanceprogramatico.AvancePorUnidad;
import mx.fei.sgpa.domain.avanceprogramatico.AvanceProgramatico;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SuiteAlmacenarAvanceProgramatico {
    
    public AvanceProgramaticoDAO avanceDAO;
    public AvanceProgramatico avance;
    
    public SuiteAlmacenarAvanceProgramatico() {
        ArrayList<AvancePorUnidad> avancesPorUnidad = new ArrayList<>();
        
        avancesPorUnidad.add(new AvancePorUnidad(4, 1, "Se termino en la primera semana de abril"));
        avancesPorUnidad.add(new AvancePorUnidad(5, 1, "Terminada, sin embargo para primer parcial se omitió el tema, dejándolo para el segundo; por aplicar examen departamental y dado que el otro bloque y sección de la tarde de Administración de Proyectos, iniciaron un mes posterior, no fue posible hasta este momento nivelarlos."));
        avancesPorUnidad.add(new AvancePorUnidad(6, (float) 0.5, "Iniciada"));
        
        avance = new AvanceProgramatico();
        avance.setAvancesDeUnidad(avancesPorUnidad);
        
        avanceDAO = new AvanceProgramaticoDAO();
    }
    
    @Test
    public void guardarAvancesUnidad() {
        boolean valorEsperado = true;
        boolean valorObtenido = avanceDAO.guardarAvancesPorUnidades("AVA-1", avance.getAvancesDeUnidad());
        
        assertEquals("Prueba de guardar avances por unidad", valorEsperado, valorObtenido);
    }
}
