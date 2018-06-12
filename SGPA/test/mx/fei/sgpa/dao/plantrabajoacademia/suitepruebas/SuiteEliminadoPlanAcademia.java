
package mx.fei.sgpa.dao.plantrabajoacademia.suitepruebas;

import mx.fei.sgpa.dao.plantrabajoacademia.PlanTrabajoAcademiaDAO;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SuiteEliminadoPlanAcademia {
    
    @Test
    public void limpiarDatosUtilizadosEnPruebas(){
        PlanTrabajoAcademiaDAO planDAO = new PlanTrabajoAcademiaDAO();
        boolean valorObtenido = planDAO.eliminarDatosPlan("PLAT-2");
        
        assertEquals("Prueba de eliminar datos plan academia", true, valorObtenido);
    }
    
    @Test
    public void limpiarDatosUtilizadosEnPruebasB(){
        PlanTrabajoAcademiaDAO planDAO = new PlanTrabajoAcademiaDAO();
        boolean valorObtenido = planDAO.eliminarDatosPlan("PLAT-3");
        
        assertEquals("Prueba de eliminar datos plan academia", true, valorObtenido);
    }
    
}
