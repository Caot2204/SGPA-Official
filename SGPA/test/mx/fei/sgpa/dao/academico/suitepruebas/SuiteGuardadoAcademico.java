/** ************************************************************* */
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   09/06/2018				  */
/* Ultima modificación: 09/06/2018				  */
/* Descripción: Prueba de métodos para guardar datos              */
/*              de Academico en DB.                    		  */
/** ************************************************************* */
package mx.fei.sgpa.dao.academico.suitepruebas;

import mx.fei.sgpa.dao.academico.AcademicoDAO;
import mx.fei.sgpa.domain.Academico;
import mx.fei.sgpa.domain.RolAcademico;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SuiteGuardadoAcademico {
    
    private final Academico academico;
    private final AcademicoDAO academicoDAO;
    
    public SuiteGuardadoAcademico() {
        academico = new Academico();
        academicoDAO = new AcademicoDAO();
        academico.setNumeroPersonal(203910);
        academico.setNombreAcademico("Juan Carlos Pérez");
        academico.setGradoEstudios("Lic. en Ingeniería de Software");
        academico.setRolAcademico(RolAcademico.DOCENTE);
    }
    
    @Test
    public void guardarAcademico() {
        boolean valorEsperado = true;
        boolean valorObtenido = academicoDAO.guardarAcademico(academico);
        
        assertEquals("Prueba de guardar academico", valorEsperado, valorObtenido);
    }
    
}
