/** ************************************************************* */
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   08/06/2018				  */
/* Ultima modificación: 08/06/2018				  */
/* Descripción: Controlador del CU Elaborar Plan de Trabajo de    */
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
import mx.fei.sgpa.dao.academia.AcademiaDAO;
import mx.fei.sgpa.dao.plantrabajoacademia.PlanTrabajoAcademiaDAO;
import mx.fei.sgpa.domain.Academia;
import mx.fei.sgpa.domain.Academico;
import mx.fei.sgpa.domain.EstadoDeDocumento;
import mx.fei.sgpa.domain.plantrabajoacademia.DetallesPlanTrabajoAcademia;
import mx.fei.sgpa.domain.plantrabajoacademia.EEConParcial;
import mx.fei.sgpa.domain.plantrabajoacademia.FormaDeEvaluacion;
import mx.fei.sgpa.domain.plantrabajoacademia.ObjetivoParticular;
import mx.fei.sgpa.domain.plantrabajoacademia.PlanTrabajoAcademia;

public class AdministradorElaboracionPlanTrabajoAcademia {

    private static AdministradorElaboracionPlanTrabajoAcademia administradorElaboracion;

    private AcademiaDAO academiaDAO;
    private PlanTrabajoAcademiaDAO planAcademiaDAO;
    private String nombreAcademia;
    private String nombreCoordinador;
    private DetallesPlanTrabajoAcademia detallesPlan;
    private ArrayList<Academico> integrantesAcademia;
    private ArrayList<ObjetivoParticular> objetivosParticulares;
    private ArrayList<EEConParcial> examenesParciales;
    private ArrayList<FormaDeEvaluacion> formasDeEvaluacion;

    public static AdministradorElaboracionPlanTrabajoAcademia obtenerInstancia() {
        if (administradorElaboracion == null) {
            administradorElaboracion = new AdministradorElaboracionPlanTrabajoAcademia();
        }
        return administradorElaboracion;
    }
    
    public static boolean existeInstancia() {
        boolean existe = false;
        if (administradorElaboracion != null) {
            existe = true;
        }        
        return existe;
    }
    
    public static void finalizarCasoUso() {
        administradorElaboracion = null;
    }

    public void obtenerAcademiasBajoCargo(int numeroDePersonal) {
        academiaDAO = new AcademiaDAO();
        ArrayList<Academia> academiasDeCoordinador = academiaDAO.obtenerAcademias(numeroDePersonal);
        
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VSGPAPrincipal.fxml"));
        Parent padre = null;
        try {
            padre = cargadorFXML.load();
        } 
        catch (IOException ex) {
            Logger.getLogger(AdministradorElaboracionPlanTrabajoAcademia.class.getName()).log(Level.SEVERE, null, ex);
        }
        VSGPAPrincipalController principal = cargadorFXML.getController();
        
        if (academiasDeCoordinador.isEmpty()) {
            principal.mostrarMensajeDeSistema("No tiene academias asignadas para generar el Plan de Trabajo de Academia\"");
            regresarAPrincipal();
        }
        else {
            principal.desplegarListaAcademias(academiasDeCoordinador);
        }
    }

    public void crearPlanAcademia(String idAcademia) {
        Academia academia = academiaDAO.obtenerAcademia(idAcademia);
        nombreAcademia = academia.getNombreAcademia();
        nombreCoordinador = academia.obtenerNombreDeCoordinador();
        integrantesAcademia = academiaDAO.obtenerIntegrantesAcademia(idAcademia);

        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VPlanTrabajoAcademia.fxml"));
        Parent padre = null;
        try {
            padre = cargadorFXML.load();
        } 
        catch (IOException ex) {
            Logger.getLogger(AdministradorElaboracionPlanTrabajoAcademia.class.getName()).log(Level.SEVERE, null, ex);
        }
        VPlanTrabajoAcademiaController planAcademiaIU = cargadorFXML.getController();
        planAcademiaIU.establecerDatosDeAcademia(nombreAcademia, nombreCoordinador, integrantesAcademia);

        Stage escenario = new Stage();
        escenario.setScene(new Scene(padre));
        escenario.setTitle("Plan de Trabajo de Academia");
        escenario.setResizable(false);
        escenario.show();
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
        
        if (planAcademiaDAO.guardarPlanTrabajoAcademia(planAcademia)) {
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
        } catch (IOException ex) {
            Logger.getLogger(AdministradorElaboracionPlanTrabajoAcademia.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AdministradorElaboracionPlanTrabajoAcademia.class.getName()).log(Level.SEVERE, null, ex);
        }
        VPlanTrabajoAcademiaController planAcademiaControlador = cargadorFXML.getController();
        return planAcademiaControlador;
    }
    
}
