/** ************************************************************* */
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   09/06/2018				  */
/* Ultima modificación: 09/06/2018				  */
/* Descripción: Controlador del CU Editar Plan de Trabajo de      */
/*              Academia.                           		  */
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
import mx.fei.sgpa.domain.Academico;
import mx.fei.sgpa.domain.EstadoDeDocumento;
import mx.fei.sgpa.domain.plantrabajoacademia.DetallesPlanTrabajoAcademia;
import mx.fei.sgpa.domain.plantrabajoacademia.EEConParcial;
import mx.fei.sgpa.domain.plantrabajoacademia.FormaDeEvaluacion;
import mx.fei.sgpa.domain.plantrabajoacademia.ObjetivoParticular;
import mx.fei.sgpa.domain.plantrabajoacademia.PlanTrabajoAcademia;

public class AdministradorEdicionPlanTrabajoAcademia {

    private static AdministradorEdicionPlanTrabajoAcademia administradorEdicion;

    private PlanTrabajoAcademiaDAO planAcademiaDAO;
    private PlanTrabajoAcademia plan;
    private ArrayList<PlanTrabajoAcademia> planesEnEdicion;
    private DetallesPlanTrabajoAcademia detallesPlan;
    private ArrayList<Academico> integrantesAcademia;
    private ArrayList<ObjetivoParticular> objetivosParticulares;
    private ArrayList<EEConParcial> examenesParciales;
    private ArrayList<FormaDeEvaluacion> formasDeEvaluacion;

    public static AdministradorEdicionPlanTrabajoAcademia obtenerInstancia() {
        if (administradorEdicion == null) {
            administradorEdicion = new AdministradorEdicionPlanTrabajoAcademia();
        }
        return administradorEdicion;
    }
    
    public static boolean existeInstancia() {
        boolean existe = false;
        if (administradorEdicion != null) {
            existe = true;
        }        
        return existe;
    }
    
    public static void finalizarCasoUso() {
        administradorEdicion = null;
    }

    public void solicitarPlanesEnEdicion(int numeroDePersonal) {
        planAcademiaDAO = new PlanTrabajoAcademiaDAO();
        planesEnEdicion = planAcademiaDAO.obtenerPlanesEnEdicion(numeroDePersonal);
        
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VSGPAPrincipal.fxml"));
        Parent padre = null;
        try {
            padre = cargadorFXML.load();
        } 
        catch (IOException ex) { 
            Logger.getLogger(AdministradorEdicionPlanTrabajoAcademia.class.getName()).log(Level.SEVERE, null, ex);
        }
        VSGPAPrincipalController principal = cargadorFXML.getController();
        
        if (planesEnEdicion.isEmpty()) {
            principal.mostrarMensajeDeSistema("No tiene Planes de Trabajo de Academia que pueda editar");
            regresarAPrincipal();
        }
        else {
            principal.desplegarListaPlanesEnEdicion(planesEnEdicion);
        }
    }
    
    public void desplegarFormularioConDatosPlan(String idPlanAcademia) {
        obtenerDetallesPlan(idPlanAcademia);
        
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VPlanTrabajoAcademia.fxml"));
        Parent padre = null;
        try {
            padre = cargadorFXML.load();
        } 
        catch (IOException ex) { 
            Logger.getLogger(AdministradorEdicionPlanTrabajoAcademia.class.getName()).log(Level.SEVERE, null, ex);
        }
        VPlanTrabajoAcademiaController planAcademiaIU = cargadorFXML.getController();
        planAcademiaIU.establecerDatosRecuperados(plan);

        Stage escenario = new Stage();
        escenario.setScene(new Scene(padre));
        escenario.setTitle("Plan de Trabajo de Academia");
        escenario.setResizable(false);
        escenario.show();                
    }
    
    public void obtenerDetallesPlan(String idPlanAcademia) {
        for (int a = 0; a < planesEnEdicion.size(); a++) {
            if (planesEnEdicion.get(a).getId().equals(idPlanAcademia)) {
                plan = planesEnEdicion.get(a);
            }                        
        }
    }
    
    public void guardarPlan(DetallesPlanTrabajoAcademia detallesPlan, ArrayList<ObjetivoParticular> objetivosParticulares, ArrayList<EEConParcial> examenesParciales, ArrayList<FormaDeEvaluacion> formasDeEvaluacion) {
        this.detallesPlan = detallesPlan;
        this.objetivosParticulares = objetivosParticulares;
        this.examenesParciales = examenesParciales;
        this.formasDeEvaluacion = formasDeEvaluacion;

        if (validarDatosIngresados()) {
            lanzarMensajeConcluir();            
        } 
        else {
            lanzarMensajeDatosInvalidos();            
        }
    }

    public boolean validarDatosIngresados() {
        boolean datosValidos = true;
        if (detallesPlan.getObjetivoGeneral().equals("")) {
            datosValidos = false;
        }
        return datosValidos;
    }

    public void lanzarMensajeDatosInvalidos() {
        VPlanTrabajoAcademiaController planAcademiaControlador = comunicaciorConIUPlanTrabajoAcademia();
        planAcademiaControlador.mostrarMensajeDatosInvalidos();
    }
    
    public void lanzarMensajeConcluir() {
        VPlanTrabajoAcademiaController planAcademiaControlador = comunicaciorConIUPlanTrabajoAcademia();
        planAcademiaControlador.mostrarMensajeConcluir();
    }

    public void guardarPlan(String opcion) {
        planAcademiaDAO = new PlanTrabajoAcademiaDAO();
        
        PlanTrabajoAcademia planAcademia = new PlanTrabajoAcademia();
        planAcademia.setId(detallesPlan.getNombreAcademia().substring(0, 3) + "-" + detallesPlan.getNombreCoordinador().substring(0, 3) + "-" + detallesPlan.getProgramaEducativo().substring(0, 3) + detallesPlan.getPeriodoEscolar());
        planAcademia.setFechaAprobacion(detallesPlan.getFechaAprobacion());
        planAcademia.setProgramaEducativo(detallesPlan.getProgramaEducativo());
        planAcademia.setPeriodoEscolar(detallesPlan.getPeriodoEscolar());
        planAcademia.setNombreAcademia(detallesPlan.getNombreAcademia());
        planAcademia.setNombreCoordinador(detallesPlan.getNombreCoordinador());
        planAcademia.setObjetivoGeneral(detallesPlan.getObjetivoGeneral());
        planAcademia.setObjetivosParticulares(objetivosParticulares);
        planAcademia.setExamenesParciales(examenesParciales);
        planAcademia.setFormasDeEvaluacion(formasDeEvaluacion);        
        
        if (opcion.equals("Concluir")) {
            planAcademia.setEstado(EstadoDeDocumento.CONCLUIDO);
        }
        else if (opcion.equals("Guardar progreso")) {
            planAcademia.setEstado(EstadoDeDocumento.EN_EDICION);
        }
        
        VPlanTrabajoAcademiaController planAcademiaControlador = comunicaciorConIUPlanTrabajoAcademia();
        
        if (planAcademiaDAO.actualizarDatosPlan(planAcademia)) {
            planAcademiaControlador.mostrarMensajeExitoGuardado(opcion);
            regresarAPrincipal();
        }
        else {
            planAcademiaControlador.mostrarMensajeFalloGuardado();            
        }
    }
    
    public void regresarAPrincipal() {
        Parent padre = null;
        try {
            padre = FXMLLoader.load(getClass().getResource("VSGPAPrincipal.fxml"));
        } 
        catch (IOException ex) {
            Logger.getLogger(AdministradorEdicionPlanTrabajoAcademia.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene escena = new Scene(padre);
        Stage escenario = new Stage();
        escenario.setScene(escena);
        escenario.setTitle("SGPA - Principal");
        escenario.setResizable(false);
        
        finalizarCasoUso();
        
        escenario.show();        
    }
    
    public VPlanTrabajoAcademiaController comunicaciorConIUPlanTrabajoAcademia() {
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VPlanTrabajoAcademia.fxml"));
        Parent padre = null;
        try {
            padre = cargadorFXML.load();
        } 
        catch (IOException ex) { 
            Logger.getLogger(AdministradorEdicionPlanTrabajoAcademia.class.getName()).log(Level.SEVERE, null, ex);
        }
        VPlanTrabajoAcademiaController planAcademiaControlador = cargadorFXML.getController();
        return planAcademiaControlador;
    }

}
