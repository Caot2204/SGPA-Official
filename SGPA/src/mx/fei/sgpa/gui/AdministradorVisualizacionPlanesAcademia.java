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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.fei.sgpa.dao.plantrabajoacademia.PlanTrabajoAcademiaDAO;
import mx.fei.sgpa.domain.plantrabajoacademia.PlanTrabajoAcademia;

public class AdministradorVisualizacionPlanesAcademia {
    
    private static AdministradorVisualizacionPlanesAcademia administradorVisualizacion;
    
    private PlanTrabajoAcademiaDAO planAcademiaDAO;
    private PlanTrabajoAcademia planAcademia;
    private ArrayList<PlanTrabajoAcademia> planesConcluidos;
    
    public static AdministradorVisualizacionPlanesAcademia obtenerInstancia() {
        if (administradorVisualizacion == null) {
            administradorVisualizacion = new AdministradorVisualizacionPlanesAcademia();
        }
        return administradorVisualizacion;
    }
    
    public static boolean existeInstancia() {
        boolean existe = false;
        if (administradorVisualizacion != null) {
            existe = true;
        }        
        return existe;
    }
    
    public static void finalizarCasoUso() {
        administradorVisualizacion = null;
    }
    
    public void obtenerPlanesAcademiaDeCoordinador(int numeroDePersonal) {
        planAcademiaDAO = new PlanTrabajoAcademiaDAO();
        planesConcluidos = planAcademiaDAO.buscarPlanTrabajoByCoordinador(numeroDePersonal);
        
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VSGPAPrincipal.fxml"));
        Parent padre = null;
        try {
            padre = cargadorFXML.load();
        } 
        catch (IOException ex) { 
            Logger.getLogger(AdministradorEdicionPlanTrabajoAcademia.class.getName()).log(Level.SEVERE, null, ex);
        }
        VSGPAPrincipalController principal = cargadorFXML.getController();
        
        if (planesConcluidos.isEmpty()) {
            principal.mostrarMensajeDeSistema("No tiene Planes de Trabajo de Academia que pueda visualizar");
            regresarAPrincipal();
        }
        else {
            principal.mostrarListaPlanes(planesConcluidos);
        }
    }
    
    public void desplegarPlan(String idPlanTrabajoAcademia) {
        obtenerDatosPlan(idPlanTrabajoAcademia);
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VPlanTrabajoAcademia.fxml"));
        Parent padre = null;
        try {
            padre = cargadorFXML.load();
        } 
        catch (IOException ex) {
            Logger.getLogger(AdministradorVisualizacionPlanesAcademia.class.getName()).log(Level.SEVERE, null, ex);
        }
        VPlanTrabajoAcademiaController planAcademiaIU = cargadorFXML.getController();
        planAcademiaIU.mostrarPlan(planAcademia);

        Stage escenario = new Stage();
        escenario.setScene(new Scene(padre));
        escenario.setTitle("Plan de Trabajo de Academia");
        escenario.setResizable(false);
        escenario.show();        
    }
    
    public void obtenerDatosPlan(String idPlanTrabajoAcademia) {
        for (int a = 0; a < planesConcluidos.size(); a++) {
            if (planesConcluidos.get(a).getId().equals(idPlanTrabajoAcademia)) {
                planAcademia = planesConcluidos.get(a);
            }
        }        
    }
    
    public void regresarAPrincipal() {
        Parent padre = null;
        try {
            padre = FXMLLoader.load(getClass().getResource("VSGPAPrincipal.fxml"));
        } 
        catch (IOException ex) { 
            Logger.getLogger(AdministradorVisualizacionPlanesAcademia.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene escena = new Scene(padre);
        Stage escenario = new Stage();
        escenario.setScene(escena);
        escenario.setTitle("SGPA - Principal");
        escenario.setResizable(false);
        
        finalizarCasoUso();
        
        escenario.show();        
    }
    
}
