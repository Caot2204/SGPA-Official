/** ************************************************************* */
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   25/04/2018				  */
/* Ultima modificación: 09/06/2018				  */
/* Descripción: Prueba de métodos para guardar datos              */
/*              de Academico en DB.                    		  */
/** ************************************************************* */
package mx.fei.sgpa.dao.plantrabajoacademia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.sgpa.dao.plantrabajoacademia.suitepruebas.SuiteEliminadoPlanAcademia;
import mx.fei.sgpa.dao.plantrabajoacademia.suitepruebas.SuiteGuardadoPlanAcademia;
import mx.fei.sgpa.dao.plantrabajoacademia.suitepruebas.SuiteLecturaPlanAcademia;
import mx.fei.sgpa.datasource.DataBase;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({SuiteGuardadoPlanAcademia.class,SuiteLecturaPlanAcademia.class,SuiteEliminadoPlanAcademia.class})

public class PlanTrabajoAcademiaDAOTest {
    
    @AfterClass
    public static void limpiarDatosDePrueba() {
        Connection conexionDB = DataBase.getDataBaseConnection();
        String consultaSQL = "DELETE FROM academico where Numero_Personal=?";
        try {
            PreparedStatement sentencia = conexionDB.prepareStatement(consultaSQL);
            sentencia.setInt(1, 203910);
            sentencia.execute();
        } 
        catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
