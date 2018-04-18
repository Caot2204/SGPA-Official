
package mx.fei.sgpa.dao.plantrabajoacademia;

import java.util.ArrayList;
import mx.fei.sgpa.domain.plantrabajoacademia.PlanTrabajoAcademia;

public class PlanTrabajoAcademiaDAO implements IPlanTrabajoAcademiaDAO{
    
    private PlanTrabajoAcademia planAcademia;
    private ArrayList<PlanTrabajoAcademia> planesAcademia;

    @Override
    public boolean guardarPlanTrabajoAcademia(PlanTrabajoAcademia planAcademia) {
        return true;
    }

    @Override
    public PlanTrabajoAcademia buscarPlanTrabajoByID(String id) {
        return this.planAcademia;
    }

    @Override
    public ArrayList<PlanTrabajoAcademia> buscarPlanTrabajoByCoordinador(String idCoordinador) {
        return this.planesAcademia;
    }
    
}
