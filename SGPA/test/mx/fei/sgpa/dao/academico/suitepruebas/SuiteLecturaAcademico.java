/** ************************************************************* */
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   09/06/2018				  */
/* Ultima modificación: 09/06/2018				  */
/* Descripción: Prueba de métodos para obtener datos              */
/*              de Academico en DB.                    		  */
/** ************************************************************* */
package mx.fei.sgpa.dao.academico.suitepruebas;

import mx.fei.sgpa.dao.academico.AcademicoDAO;
import mx.fei.sgpa.domain.Academico;
import mx.fei.sgpa.domain.RolAcademico;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SuiteLecturaAcademico {
    
    private final Academico academicoEsperado;
    private final AcademicoDAO academicoDAO;
    
    public SuiteLecturaAcademico() {
        academicoEsperado = new Academico();
        academicoDAO = new AcademicoDAO();
        academicoEsperado.setNumeroPersonal(203910);
        academicoEsperado.setNombreAcademico("Juan Carlos Pérez");
        academicoEsperado.setGradoEstudios("Lic. en Ingeniería de Software");
        academicoEsperado.setRolAcademico(RolAcademico.DOCENTE);
    }
    
    @Test
    public void obtenerAcademico() {
        Academico academicoObtenido = academicoDAO.obtenerAcademico(203910);
        
        assertEquals("Prueba de obtener id academico", academicoEsperado.getNumeroPersonal(), academicoObtenido.getNumeroPersonal());
        assertEquals("Prueba de obtener nombre academico", academicoEsperado.getNombreAcademico(), academicoObtenido.getNombreAcademico());
        assertEquals("Prueba de obtener grado estudios academico", academicoEsperado.getGradoEstudios(), academicoObtenido.getGradoEstudios());
        assertEquals("Prueba de obtener rol academico", academicoEsperado.getRolAcademico(), academicoObtenido.getRolAcademico());
        
    }
    
    @Test
    public void obtenerAcademicoPorNombre() {
        Academico academicoObtenido = academicoDAO.obtenerAcademico("Juan Carlos Pérez");
        
        assertEquals("Prueba de obtener id academico", academicoEsperado.getNumeroPersonal(), academicoObtenido.getNumeroPersonal());
        assertEquals("Prueba de obtener nombre academico", academicoEsperado.getNombreAcademico(), academicoObtenido.getNombreAcademico());
        assertEquals("Prueba de obtener grado estudios academico", academicoEsperado.getGradoEstudios(), academicoObtenido.getGradoEstudios());
        assertEquals("Prueba de obtener rol academico", academicoEsperado.getRolAcademico(), academicoObtenido.getRolAcademico());
        
    }
    
}
