/** ************************************************************* */
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   09/06/2018				  */
/* Ultima modificación: 09/06/2018				  */
/* Descripción: Prueba de los métodos de acceso a la DB           */
/*              para Academico.                    		  */
/** ************************************************************* */
package mx.fei.sgpa.dao.academico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.sgpa.dao.academico.suitepruebas.SuiteGuardadoAcademico;
import mx.fei.sgpa.dao.academico.suitepruebas.SuiteLecturaAcademico;
import mx.fei.sgpa.datasource.DataBase;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({SuiteGuardadoAcademico.class,SuiteLecturaAcademico.class})

public class AcademicoDAOTest {
    
    public AcademicoDAOTest() {
    }

    @AfterClass
    public static void limpiarDatosDePrueba() {
        Connection conexionDB = DataBase.getDataBaseConnection();
        String consultaSQL = "DELETE FROM academico where Numero_Personal=?";
        try {
            PreparedStatement sentencia = conexionDB.prepareStatement(consultaSQL);
            sentencia.setInt(1, 203910);
            sentencia.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AcademicoDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
