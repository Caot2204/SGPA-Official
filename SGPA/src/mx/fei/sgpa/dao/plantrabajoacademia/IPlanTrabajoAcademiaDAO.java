package mx.fei.sgpa.dao.plantrabajoacademia;

import java.util.List;
import mx.fei.sgpa.domain.plantrabajoacademia.PlanTrabajoAcademia;


public interface IPlanTrabajoAcademiaDAO {
    boolean guardarPlanTrabajoAcademia();
    PlanTrabajoAcademia buscarPlanTrabajoByID(String id);
    List<PlanTrabajoAcademia> buscarPlanTrabajoByCoordinador(String idCoordinador);
}
