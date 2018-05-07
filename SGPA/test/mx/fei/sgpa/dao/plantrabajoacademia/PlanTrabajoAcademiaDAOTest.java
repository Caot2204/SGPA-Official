package mx.fei.sgpa.dao.plantrabajoacademia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.sgpa.dao.suitepruebas.SuiteGuardadoPlanAcademia;
import mx.fei.sgpa.dao.suitepruebas.SuiteLecturaPlanAcademia;
import mx.fei.sgpa.datasource.DataBase;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({SuiteGuardadoPlanAcademia.class,SuiteLecturaPlanAcademia.class})

public class PlanTrabajoAcademiaDAOTest {
    
    @AfterClass
    public static void limpiarDatosUtilizadosEnPruebas(){
        Connection conexionDB = DataBase.getDataBaseConnection();
        ArrayList<String> consultasSQL = new ArrayList<>();
        consultasSQL.add("DELETE FROM plan_trabajo_academia_objetivo_particular where Id_Plan_Academia=?");        
        consultasSQL.add("DELETE FROM plan_trabajo_academia_objetivo_particular_meta where Id_Plan_Academia=?");
        consultasSQL.add("DELETE FROM plan_trabajo_academia_objetivo_particular_meta_accion where Id_Plan_Academia=?");
        consultasSQL.add("DELETE FROM plan_trabajo_academia_examen_parcial_ee where Id_Plan_Academia=?");
        consultasSQL.add("DELETE FROM plan_trabajo_academia_examen_parcial_tema where Id_Plan_Academia=?");
        consultasSQL.add("DELETE FROM plan_trabajo_academia_forma_evaluacion where Id_Plan_Academia=?");
        consultasSQL.add("DELETE FROM plan_trabajo_academia_historico_de_revision where Id_Plan_Academia=?");
        consultasSQL.add("DELETE FROM plan_trabajo_academia_autorizacion where Id_Plan_Academia=?");
        consultasSQL.add("DELETE FROM plan_trabajo_academia where Id=?");
        
        for (int a = 0; a < consultasSQL.size(); a++){
            try {
                PreparedStatement sentenciaSQL = conexionDB.prepareStatement(consultasSQL.get(a));
                sentenciaSQL.setString(1, "PLAT-2");
                sentenciaSQL.execute();
            } catch (SQLException ex) {
                Logger.getLogger(PlanTrabajoAcademiaDAOTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
