package mx.fei.sgpa.dao.plantrabajoacademia;

import java.util.ArrayList;
import mx.fei.sgpa.domain.plantrabajoacademia.PlanTrabajoAcademia;


public interface IPlanTrabajoAcademiaDAO {
    boolean guardarPlanTrabajoAcademia(PlanTrabajoAcademia planAcademia);
    PlanTrabajoAcademia buscarPlanTrabajoByID(String id);
    ArrayList<PlanTrabajoAcademia> buscarPlanTrabajoByCoordinador(String idCoordinador);
}
