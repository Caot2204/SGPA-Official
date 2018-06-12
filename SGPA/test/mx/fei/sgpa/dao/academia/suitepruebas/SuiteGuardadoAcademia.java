/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   08/06/2018				  */
/* Ultima modificación: 08/06/2018				  */
/* Descripción: Aplicación principal del SGPA.  		  */
/****************************************************************/
package mx.fei.sgpa.dao.academia.suitepruebas;

import mx.fei.sgpa.dao.academia.AcademiaDAO;
import mx.fei.sgpa.domain.Academia;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SuiteGuardadoAcademia {
    
    private final Academia academia;
    private final AcademiaDAO academiaDAO;
    
    public SuiteGuardadoAcademia() {
        academia = new Academia();
        academiaDAO = new AcademiaDAO();
        academia.setIdAcademia("INGESOFT");
        academia.setNombreAcademia("Ingeniería de Software");
        academia.setCoordinadorAcademia(12345);
    }
    
    @Test
    public void guardarAcademia() {
        boolean valorEsperado = true;
        boolean valorObtenido = academiaDAO.guardarAcademia(academia);
        assertEquals("Prueba de guardar academia", valorEsperado, valorObtenido);
    }
        
}
