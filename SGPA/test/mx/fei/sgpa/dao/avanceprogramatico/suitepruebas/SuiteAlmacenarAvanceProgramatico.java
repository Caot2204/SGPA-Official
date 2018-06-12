
package mx.fei.sgpa.dao.avanceprogramatico.suitepruebas;

import java.sql.Date;
import java.util.ArrayList;
import mx.fei.sgpa.dao.avanceprogramatico.AvanceProgramaticoDAO;
import mx.fei.sgpa.domain.avanceprogramatico.AvancePorUnidad;
import mx.fei.sgpa.domain.avanceprogramatico.AvanceProgramatico;
import mx.fei.sgpa.domain.avanceprogramatico.UnidadDePlaneacion;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SuiteAlmacenarAvanceProgramatico {
    
    public AvanceProgramaticoDAO avanceDAO;
    public AvanceProgramatico avance;
    
    public SuiteAlmacenarAvanceProgramatico() {
        ArrayList<String> temasA = new ArrayList<>();
        temasA.add("Objetivo y Ámbito del proyecto");
        temasA.add("Selección del proceso");
        temasA.add("Descomposición; red de actividades");
        temasA.add("Diagrama de Gantt");
        temasA.add("Asignación de recursos");
        temasA.add("Plan de configuración (breve)");
        temasA.add("Plan de aseguramiento de calidad (breve)");
        
        String rangoFechaA = "01/04/16 al 15/04/16 (2 sem)";
        
        ArrayList<String> tareasPracticasA = new ArrayList<>();
        ArrayList<String> tecnicasDidacticasA = new ArrayList<>();
        
        ArrayList<String> temasB = new ArrayList<>();
        ArrayList<Date> fechasB = new ArrayList<>();
        ArrayList<String> tareasPracticasB = new ArrayList<>();
        ArrayList<String> tecnicasDidacticasB = new ArrayList<>();        
        
        UnidadDePlaneacion unidadPlaneacionA = new UnidadDePlaneacion();
        unidadPlaneacionA.setUnidad(5);
        unidadPlaneacionA.setTemasDesarrollados(temasA);
        unidadPlaneacionA.setFechas(fechasA);
        unidadPlaneacionA.setTareasPracticas(tareasPracticasA);
        unidadPlaneacionA.setTecnicasDidacticas(tecnicasDidacticasA);
        
        UnidadDePlaneacion unidadPlaneacionB = new UnidadDePlaneacion();
        unidadPlaneacionB.setUnidad(6);
        unidadPlaneacionB.setTemasDesarrollados(temasB);
        unidadPlaneacionB.setFechas(fechasB);
        unidadPlaneacionB.setTareasPracticas(tareasPracticasB);
        unidadPlaneacionB.setTecnicasDidacticas(tecnicasDidacticasB);
        
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
