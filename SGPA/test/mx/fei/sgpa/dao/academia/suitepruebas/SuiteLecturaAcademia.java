/** ************************************************************* */
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   08/06/2018				  */
/* Ultima modificación: 08/06/2018				  */
/* Descripción: Suite de pruebas de recuperación de datos de      */
/*              Academias                                         */
/** ************************************************************* */
package mx.fei.sgpa.dao.academia.suitepruebas;

import java.util.ArrayList;
import mx.fei.sgpa.dao.academia.AcademiaDAO;
import mx.fei.sgpa.domain.Academia;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SuiteLecturaAcademia {
    
    private final Academia academia;
    private final AcademiaDAO academiaDAO;
    
    public SuiteLecturaAcademia() {
        academia = new Academia();
        academiaDAO = new AcademiaDAO();
        academia.setIdAcademia("INGESOFT");
        academia.setNombreAcademia("Ingeniería de Software");
        academia.setCoordinadorAcademia(12345);        
    }
    
    @Test
    public void obtenerAcademiasDeCoordinador() {
        ArrayList<Academia> academiasObtenidas = academiaDAO.obtenerAcademias(12345);
        int valorEsperado = 1;
        
        assertEquals("Prueba de cantidad de academias recuperadas", valorEsperado, academiasObtenidas.size());
        assertEquals("Prueba de idAcademia obtenido", academia.getIdAcademia(), academiasObtenidas.get(0).getIdAcademia());
        assertEquals("Prueba de Nombre academia obtenido", academia.getNombreAcademia(), academiasObtenidas.get(0).getNombreAcademia());
        assertEquals("Prueba de Coordinador obtenido", academia.getCoordinadorAcademia(), academiasObtenidas.get(0).getCoordinadorAcademia());
    }
    
    @Test
    public void obtenerDatosDeAcademia() {
        Academia academiaObtenida = academiaDAO.obtenerAcademia("INGESOFT");
        
        assertEquals("Prueba de idAcademia obtenido", academia.getIdAcademia(), academiaObtenida.getIdAcademia());
        assertEquals("Prueba de Nombre academia obtenido", academia.getNombreAcademia(), academiaObtenida.getNombreAcademia());
        assertEquals("Prueba de Coordinador obtenido", academia.getCoordinadorAcademia(), academiaObtenida.getCoordinadorAcademia()); 
    }
}
