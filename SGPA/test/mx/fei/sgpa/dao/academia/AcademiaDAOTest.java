/** ************************************************************* */
/* Nombre: Carlos Alberto Onorio Torres.			  */
 /* Fecha de creación:   08/06/2018				  */
 /* Ultima modificación: 08/06/2018				  */
 /* Descripción: Prueba unitaria de AcademiaDAO.  		  */
/** ************************************************************* */
package mx.fei.sgpa.dao.academia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.sgpa.dao.academia.suitepruebas.SuiteGuardadoAcademia;
import mx.fei.sgpa.dao.academia.suitepruebas.SuiteLecturaAcademia;
import mx.fei.sgpa.dao.plantrabajoacademia.PlanTrabajoAcademiaDAOTest;
import mx.fei.sgpa.datasource.DataBase;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({SuiteGuardadoAcademia.class,SuiteLecturaAcademia.class})

public class AcademiaDAOTest {

    public AcademiaDAOTest() {
    }

    @AfterClass
    public static void limpiarDatosUtilizadosEnPruebas() {
        Connection conexionDB = DataBase.getDataBaseConnection();
        String consultaSQL = "DELETE FROM academia WHERE Id=?";
        
        try {
            PreparedStatement sentenciaSQL = conexionDB.prepareStatement(consultaSQL);
            sentenciaSQL.setString(1, "INGESOFT");
            sentenciaSQL.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PlanTrabajoAcademiaDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
