/** ************************************************************* */
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   10/06/2018				  */
/* Ultima modificación: 10/06/2018				  */
/* Descripción: Administrador del Caso de Uso Visualizar Plan de  */
/*              Trabajo de Academia.             		  */
/** ************************************************************* */
package mx.fei.sgpa.gui;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.fei.sgpa.dao.plantrabajoacademia.PlanTrabajoAcademiaDAO;
import mx.fei.sgpa.domain.plantrabajoacademia.PlanTrabajoAcademia;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Capa lógica encargada de administrar la visualización de un PlanTrabajoAcademia
 */
public class AdministradorVisualizacionPlanesAcademia {
    
    private static AdministradorVisualizacionPlanesAcademia administradorVisualizacion;
    private static final Logger loggerDelSistema = LogManager.getLogger(AdministradorVisualizacionPlanesAcademia.class);
    
    private PlanTrabajoAcademiaDAO planAcademiaDAO;
    private PlanTrabajoAcademia planAcademia;
    private ArrayList<PlanTrabajoAcademia> planesConcluidos;
    
    /**
     * Obtiene una instancia para utilizar los métodos del AdministradorVisualizacionPlanTrabajoAcademia.
     * Si ya existe la regresa, sino, crea una nueva instancia
     * @return Objeto de tipo AdministradorElaboracionPlanTrabajoAcademia
     */
    public static AdministradorVisualizacionPlanesAcademia obtenerInstancia() {
        if (administradorVisualizacion == null) {
            administradorVisualizacion = new AdministradorVisualizacionPlanesAcademia();
        }
        return administradorVisualizacion;
    }
    
    /**
     * Verifica si existe una instancia del AdministradorVisualizacionPlanTrabajoAcademia.
     * @return true si existe la instancia, false si no existe
     */
    public static boolean existeInstancia() {
        boolean existe = false;
        if (administradorVisualizacion != null) {
            existe = true;
        }        
        return existe;
    }
    
    /**
     * Elimina la instancia del AdministradorVisualizacionPlanTrabajoAcademia cuando
     * esta ya no se vaya a utilizar
     */
    public static void finalizarCasoUso() {
        administradorVisualizacion = null;
    }
    
    /**
     * Obtiene los PlanTrabajoAcademia con estado CONCLUIDO elaborados por un Coordinador
     * y los envia al controlador JavaFX de la IU VSGPAPrincipal para que los muestre
     * 
     * @param numeroDePersonal Número de Personal de un Académico COORDINADOR
     */
    public void obtenerPlanesAcademiaDeCoordinador(int numeroDePersonal) {
        planAcademiaDAO = new PlanTrabajoAcademiaDAO();
        planesConcluidos = planAcademiaDAO.buscarPlanTrabajoByCoordinador(numeroDePersonal);
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VSGPAPrincipal.fxml"));
        try {
            Parent padre = cargadorFXML.load();
            VSGPAPrincipalController principal = cargadorFXML.getController();
            if (planesConcluidos.isEmpty()) {
                principal.mostrarMensajeDeSistema("No tiene Planes de Trabajo de Academia que pueda visualizar");
                regresarAPrincipal();
            }
            else {
                principal.mostrarListaPlanes(planesConcluidos);
            }
        } 
        catch (IllegalStateException excepcionEstadoIlegal) {
            loggerDelSistema.fatal("No se pudo cargar VSGPAPrincipal.fxml");
        }
        catch (IOException excepcionEntradaSalida) {
            loggerDelSistema.fatal("Ocurrió un error I/O en método obtenerPlanesAcademiaDeCoordinador");
        }
    }
    
    /**
     * Obtiene los datos del PlanTrabajoAcademia y los muestra en la IU VPlanTrabajoAcademia
     * 
     * @param idPlanTrabajoAcademia Identificador del PlanTrabajoAcademia del cual se recuperarán los datos para mostrarlos
     */
    public void desplegarPlan(String idPlanTrabajoAcademia) {
        obtenerDatosPlan(idPlanTrabajoAcademia);
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VPlanTrabajoAcademia.fxml"));
        try {
            Parent padre = cargadorFXML.load();
            VPlanTrabajoAcademiaController planAcademiaIU = cargadorFXML.getController();
            planAcademiaIU.mostrarPlan(planAcademia);

            Stage escenario = new Stage();
            escenario.setScene(new Scene(padre));
            escenario.setTitle("Plan de Trabajo de Academia");
            escenario.setResizable(false);
            escenario.show();
        } 
        catch (IllegalStateException excepcionEstadoIlegal) {
            loggerDelSistema.fatal("No se pudo cargar VPlanTrabajoAcademia.fxml");
        }
        catch (IOException excepcionEntradaSalida) {
            loggerDelSistema.fatal("Ocurrió un error I/O en método desplegarPlan");
        }        
    }
    
    /**
     * Obtiene los datos de un PlanTrabajoAcademia existentes en el AdministradorVisualizacionPlanTrabajoAcademia
     * 
     * @param idPlanTrabajoAcademia Identificador del PlanTrabajoAcademia del cual se recuperarán los datos
     */
    public void obtenerDatosPlan(String idPlanTrabajoAcademia) {
        for (int a = 0; a < planesConcluidos.size(); a++) {
            if (planesConcluidos.get(a).getId().equals(idPlanTrabajoAcademia)) {
                planAcademia = planesConcluidos.get(a);
            }
        }        
    }
    
    /**
     * Despliega la IU VSGPAPrincipal
     */
    public void regresarAPrincipal() {
        try {
            Parent padre = FXMLLoader.load(getClass().getResource("VSGPAPrincipal.fxml"));
            Scene escena = new Scene(padre);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("SGPA - Principal");
            escenario.setResizable(false);

            finalizarCasoUso();

            escenario.show();
        } 
        catch (IllegalStateException excepcionEstadoIlegal) {
            loggerDelSistema.fatal("No se pudo cargar VSGPAPrincipal.fxml");
        }
        catch (IOException excepcionEntradaSalida) {
            loggerDelSistema.fatal("Ocurrió un error I/O en método regresarAPrincipal");
        }           
    }
    
}
