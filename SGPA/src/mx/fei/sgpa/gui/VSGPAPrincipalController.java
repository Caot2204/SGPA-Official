package mx.fei.sgpa.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import mx.fei.sgpa.domain.Academia;
import mx.fei.sgpa.domain.Academico;
import mx.fei.sgpa.domain.plantrabajoacademia.PlanTrabajoAcademia;

public class VSGPAPrincipalController implements Initializable {
    
    private Academico academico;
    
    @FXML
    private Button buttonElaborarFormato;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    public void elaborarDocumento() {
        Stage escenaActual = (Stage) buttonElaborarFormato.getScene().getWindow();
        escenaActual.close();
        
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VSeleccionarFormatoElaborar.fxml"));
        Parent padre = null;
        try {
            padre = cargadorFXML.load();
        } 
        catch (IOException ex) {
            Logger.getLogger(VSGPAPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        VSeleccionarFormatoElaborarController seleccionarFormato = cargadorFXML.getController();
        seleccionarFormato.establecerOpcionesDeCoordinador();
        
        Stage escenario = new Stage();
        escenario.setScene(new Scene(padre));
        escenario.setTitle("SGPA - Principal");
        escenario.setResizable(false);
        
        escenario.show();
    }
    
    public void iniciarCreacionFormato(String formatoElegido) {
        switch (formatoElegido) {
            case "Plan de Trabajo de Academia":
                crearPlanTrabajoAcademia();                
                break;
            case "Minuta":
                throw new UnsupportedOperationException("Operación no soportada");
            case "Plan de Curso":
                throw new UnsupportedOperationException("Operación no soportada");
            case "Avance Programático":
                throw new UnsupportedOperationException("Operación no soportada");
        }        
    }
    
    public void crearAvanceProgramatico() {
                
    }
    
    public void crearPlanTrabajoAcademia() {
        AdministradorElaboracionPlanTrabajoAcademia administradorElaboracionPlan = AdministradorElaboracionPlanTrabajoAcademia.obtenerInstancia();
        administradorElaboracionPlan.obtenerAcademiasBajoCargo(220498);
    }
    
    public void desplegarListaAcademias(ArrayList<Academia> academiasDeCoordinador) {
        if (academiasDeCoordinador.isEmpty()) {
            mostrarMensajeDeSistema("No tiene academias asignadas para generar el Plan de Trabajo de Academia");            
        }
        else {
            FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VSeleccionarAcademia.fxml"));
            Parent padre = null;
            try {
                padre = cargadorFXML.load();
            } 
            catch (IOException ex) {
                Logger.getLogger(VSGPAPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
            VSeleccionarAcademiaController seleccionarAcademia = cargadorFXML.getController();
            seleccionarAcademia.setAcademias(academiasDeCoordinador);

            Stage escenario = new Stage();
            escenario.setScene(new Scene(padre));
            escenario.setTitle("Academias bajo cargo");
            escenario.setResizable(false);
            escenario.show();
        }        
    }
    
    public void crearPlan(String idAcademia) {
        AdministradorElaboracionPlanTrabajoAcademia administradorElaboracionPlan = AdministradorElaboracionPlanTrabajoAcademia.obtenerInstancia();
        administradorElaboracionPlan.crearPlanAcademia(idAcademia);
    }
    
    public void editarDocumento() {
        Stage escenaActual = (Stage) buttonElaborarFormato.getScene().getWindow();
        escenaActual.close();
        
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VSeleccionarFormatoEditar.fxml"));
        Parent padre = null;
        try {
            padre = cargadorFXML.load();
        } 
        catch (IOException ex) {
            Logger.getLogger(VSGPAPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        VSeleccionarFormatoEditarController seleccionarFormato = cargadorFXML.getController();
        seleccionarFormato.establecerOpcionesDeCoordinador();
        
        Stage escenario = new Stage();
        escenario.setScene(new Scene(padre));
        escenario.setTitle("SGPA - Principal");
        escenario.setResizable(false);
        
        escenario.show();
    }

    public void iniciarEdicionFormato(String formatoElegido) {
        switch (formatoElegido) {
            case "Plan de Trabajo de Academia":
                editarPlanTrabajoAcademia();                
                break;
            case "Minuta":
                throw new UnsupportedOperationException("Operación no soportada");
            case "Plan de Curso":
                throw new UnsupportedOperationException("Operación no soportada");
            case "Avance Programático":
                throw new UnsupportedOperationException("Operación no soportada");
        }         
    }
    
    public void editarPlanTrabajoAcademia() {
        AdministradorEdicionPlanTrabajoAcademia administradorEdicion = AdministradorEdicionPlanTrabajoAcademia.obtenerInstancia();
        administradorEdicion.solicitarPlanesEnEdicion(220498);       
    }
    
    public void desplegarListaPlanesEnEdicion(ArrayList<PlanTrabajoAcademia> planesEnEdicion) {
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VListaPlanesAcademiaEdicion.fxml"));
        Parent padre = null;
        try {
            padre = cargadorFXML.load();
        } 
        catch (IOException ex) {
            Logger.getLogger(VSGPAPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        VListaPlanesAcademiaEdicionController seleccionarPlan = cargadorFXML.getController();
        seleccionarPlan.setPlanesEnEdicion(planesEnEdicion);

        Stage escenario = new Stage();
        escenario.setScene(new Scene(padre));
        escenario.setTitle("Seleccione un Plan de Trabajo de Academia para editar");
        escenario.setResizable(false);
        escenario.show();              
    }
    
    public void abrirPlanParaEditar(String idPlanAcademia) {
        AdministradorEdicionPlanTrabajoAcademia administradorEdicion = AdministradorEdicionPlanTrabajoAcademia.obtenerInstancia();
        administradorEdicion.desplegarFormularioConDatosPlan(idPlanAcademia);
    }
    
    public void visualizarDocumento() {
        Stage escenaActual = (Stage) buttonElaborarFormato.getScene().getWindow();
        escenaActual.close();
        
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VSeleccionarFormatoVisualizar.fxml"));
        Parent padre = null;
        try {
            padre = cargadorFXML.load();
        } 
        catch (IOException ex) {
            Logger.getLogger(VSGPAPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        VSeleccionarFormatoVisualizarController seleccionarFormato = cargadorFXML.getController();
        seleccionarFormato.establecerOpcionesDeCoordinador();
        
        Stage escenario = new Stage();
        escenario.setScene(new Scene(padre));
        escenario.setTitle("SGPA - Principal");
        escenario.setResizable(false);
        
        escenario.show();        
    }
    
    public void iniciarVisualizacionFormato(String formatoElegido) {
        switch (formatoElegido) {
            case "Plan de Trabajo de Academia":
                visualizarPlanesAcademia();                
                break;
            case "Minuta":
                throw new UnsupportedOperationException("Operación no soportada");
            case "Plan de Curso":
                throw new UnsupportedOperationException("Operación no soportada");
            case "Avance Programático":
                throw new UnsupportedOperationException("Operación no soportada");
        }        
    }
    
    public void visualizarPlanesAcademia() {
        AdministradorVisualizacionPlanesAcademia administradorVisualizacion = AdministradorVisualizacionPlanesAcademia.obtenerInstancia();
        administradorVisualizacion.obtenerPlanesAcademiaDeCoordinador(220498);           
    }
    
    public void mostrarListaPlanes(ArrayList<PlanTrabajoAcademia> planesConcluidos) {
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VEleccionPlanAcademia.fxml"));
        Parent padre = null;
        try {
            padre = cargadorFXML.load();
        } catch (IOException ex) {
            Logger.getLogger(VSGPAPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        VEleccionPlanAcademiaController seleccionarPlan = cargadorFXML.getController();
        seleccionarPlan.mostrarListaPlanes(planesConcluidos);
        
        Stage escenario = new Stage();
        escenario.setScene(new Scene(padre));
        escenario.setTitle("Seleccione un Plan de Trabajo de Academia a visualizar");
        escenario.setResizable(false);
        escenario.show();
    }
    
    public void mostrarMensajeDeSistema(String mensaje) {
        Alert mensajeDeSistema = new Alert(AlertType.INFORMATION);
        mensajeDeSistema.setTitle("SGPA - Mensaje del sistema");
        mensajeDeSistema.setHeaderText(null);
        mensajeDeSistema.setContentText(mensaje);
        mensajeDeSistema.showAndWait();
    }
    
}
