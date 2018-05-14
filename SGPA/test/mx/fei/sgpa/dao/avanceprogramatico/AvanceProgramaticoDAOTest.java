
package mx.fei.sgpa.dao.avanceprogramatico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.sgpa.dao.avanceprogramatico.suitepruebas.SuiteAlmacenarAvanceProgramatico;
import mx.fei.sgpa.dao.avanceprogramatico.suitepruebas.SuiteRecuperarAvanceProgramatico;
import mx.fei.sgpa.datasource.DataBase;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({SuiteAlmacenarAvanceProgramatico.class, SuiteRecuperarAvanceProgramatico.class})
public class AvanceProgramaticoDAOTest {
    
    @AfterClass
    public static void limpiarDatosUtilizadosEnPruebas(){
        Connection conexionDB = DataBase.getDataBaseConnection();
        ArrayList<String> consultasSQL = new ArrayList<>();
        consultasSQL.add("DELETE FROM avance_programatico where Id=?");
        consultasSQL.add("DELETE FROM avance_programatico_avance_unidad where Id_Avance_Programatico=?");        
        consultasSQL.add("DELETE FROM avance_programatico_detalle_unidad where Id_Avance_Programatico=?");
        consultasSQL.add("DELETE FROM avance_programatico_unidad_planeacion where Id_Avance_Programatico=?");
        
        for (int a = 0; a < consultasSQL.size(); a++){
            try {
                PreparedStatement sentenciaSQL = conexionDB.prepareStatement(consultasSQL.get(a));
                sentenciaSQL.setString(1, "AVA-1");
                sentenciaSQL.execute();
            } catch (SQLException ex) {
                Logger.getLogger(AvanceProgramaticoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
