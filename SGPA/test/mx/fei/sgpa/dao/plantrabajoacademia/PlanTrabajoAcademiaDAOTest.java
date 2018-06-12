package mx.fei.sgpa.dao.plantrabajoacademia;

import mx.fei.sgpa.dao.plantrabajoacademia.suitepruebas.SuiteEliminadoPlanAcademia;
import mx.fei.sgpa.dao.plantrabajoacademia.suitepruebas.SuiteGuardadoPlanAcademia;
import mx.fei.sgpa.dao.plantrabajoacademia.suitepruebas.SuiteLecturaPlanAcademia;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({SuiteGuardadoPlanAcademia.class,SuiteLecturaPlanAcademia.class,SuiteEliminadoPlanAcademia.class})

public class PlanTrabajoAcademiaDAOTest {
    
}
