/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   16/05/2018				  */
/* Ultima modificación: 13/06/2018				  */
/* Descripción: Controlador de eventos para la interfaz           */
/*              VSGPAPrincipal.                 		  */
/** ************************************************************* */
package mx.fei.sgpa.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mx.fei.sgpa.domain.Academia;
import mx.fei.sgpa.domain.RolAcademico;
import mx.fei.sgpa.domain.SesionActual;
import mx.fei.sgpa.domain.plantrabajoacademia.PlanTrabajoAcademia;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Controlador de eventos para la IU VSGPAPrincipal
 */
public class VSGPAPrincipalController implements Initializable {
    
    private static SesionActual sesionActual;
    private static final Logger loggerDelSistema = LogManager.getLogger(VSGPAPrincipalController.class);
    
    @FXML
    private Button buttonElaborarFormato;
    
    @FXML
    private Label labelBienvenida;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sesionActual = SesionActual.obtenerSesionActual();
        labelBienvenida.setText("Hola " + sesionActual.getNombreAcademico());        
    }
    
    /**
     * Despliega la IU VSeleccionarFormatoElaborar para seleccionar un
     * tipo de Formato de Academia a elaborar
     */
    public void elaborarDocumento() {        
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VSeleccionarFormatoElaborar.fxml"));
        try {
            Parent padre = cargadorFXML.load();
            VSeleccionarFormatoElaborarController seleccionarFormato = cargadorFXML.getController();
            if (sesionActual.getRolAcademico().equals(RolAcademico.COORDINADOR)) {
                seleccionarFormato.establecerOpcionesDeCoordinador();
            }
            else if (sesionActual.getRolAcademico().equals(RolAcademico.DOCENTE)) {
                seleccionarFormato.establecerOpcionesDeDocente();
            }
            Stage escenario = new Stage();
            escenario.setScene(new Scene(padre));
            escenario.setTitle("SGPA - Principal");
            escenario.setResizable(false);

            Stage escenaActual = (Stage) buttonElaborarFormato.getScene().getWindow();
            escenaActual.close();

            escenario.show();
        } 
        catch (IllegalStateException excepcionEstadoIlegal) {
            loggerDelSistema.fatal("No se pudo cargar VSeleccionarFormatoElaborar.fxml");
        }
        catch (IOException excepcionEntradaSalida) {
            loggerDelSistema.fatal("Ocurrió un error I/O en método elaborarDocumento");
        }
    }
    
    /**
     * Inicia la elaboración de un Formato de Academia específico
     * 
     * @param formatoElegido Tipo de Formato de Academia a elaborar
     */
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
    
    /**
     * Inicia la elaboración de un Plan de Trabajo de Academia
     * enviando al AdministradorElaboracionPlanTrabajoAcademia el numero de personal
     * del Académico que inicio sesión
     */
    public void crearPlanTrabajoAcademia() {
        AdministradorElaboracionPlanTrabajoAcademia administradorElaboracionPlan = AdministradorElaboracionPlanTrabajoAcademia.obtenerInstancia();
        administradorElaboracionPlan.obtenerAcademiasBajoCargo(sesionActual.getNumeroPersonal());
    }
    
    /**
     * Despliega la IU VSeleccionarAcademia para que el Académico seleccione
     * una Académia y elaborar su Plan de Trabajo
     * 
     * @param academiasDeCoordinador Lista de Academias bajo cargo del Académico COORDINADOR
     */
    public void desplegarListaAcademias(ArrayList<Academia> academiasDeCoordinador) {
        if (academiasDeCoordinador.isEmpty()) {
            mostrarMensajeDeSistema("No tiene academias asignadas para generar el Plan de Trabajo de Academia");            
        }
        else {
            FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VSeleccionarAcademia.fxml"));
            try {
                Parent padre = cargadorFXML.load();
                VSeleccionarAcademiaController seleccionarAcademia = cargadorFXML.getController();
                seleccionarAcademia.setAcademias(academiasDeCoordinador);

                Stage escenario = new Stage();
                escenario.setScene(new Scene(padre));
                escenario.setTitle("Academias bajo cargo");
                escenario.setResizable(false);
                escenario.show();
            }
            catch (IllegalStateException excepcionEstadoIlegal) {
                loggerDelSistema.fatal("No se pudo cargar VSeleccionarAcademia.fxml");                
            }
            catch (IOException excepcionEntradaSalida) {
                loggerDelSistema.fatal("Ocurrió un problema de I/O en  método desplegarListaAcademias");
            }
        }        
    }
    
    /**
     * Inicia la creación del Plan de Trabajo de la Academia elegida
     * 
     * @param idAcademia Identificador de la Academia a la cual se realizará su Plan de Trabajo
     */
    public void crearPlan(String idAcademia) {
        AdministradorElaboracionPlanTrabajoAcademia administradorElaboracionPlan = AdministradorElaboracionPlanTrabajoAcademia.obtenerInstancia();
        administradorElaboracionPlan.crearPlanAcademia(idAcademia);
    }
    
    /**
     * Despliega la IU VSeleccionarFormatoEditar para que el Académico
     * inicie la elaboración de un Formato de Academia específico
     */
    public void editarDocumento() {        
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VSeleccionarFormatoEditar.fxml"));
        try {
            Parent padre = cargadorFXML.load();
            VSeleccionarFormatoEditarController seleccionarFormato = cargadorFXML.getController();
            if (sesionActual.getRolAcademico().equals(RolAcademico.COORDINADOR)) {
                seleccionarFormato.establecerOpcionesDeCoordinador();
            }
            else if (sesionActual.getRolAcademico().equals(RolAcademico.DOCENTE)) {
                seleccionarFormato.establecerOpcionesDeDocente();
            }
            Stage escenario = new Stage();
            escenario.setScene(new Scene(padre));
            escenario.setTitle("SGPA - Principal");
            escenario.setResizable(false);
            
            Stage escenaActual = (Stage) buttonElaborarFormato.getScene().getWindow();
            escenaActual.close();

            escenario.show();
        } 
        catch (IllegalStateException excepcionEstadoIlegal) {
            loggerDelSistema.fatal("No se pudo cargar VSeleccionarFormatoEditar.fxml");
        }
        catch (IOException excepcionEntradaSalida) {
            loggerDelSistema.fatal("Ocurrió un error I/O en método editarDocumento");
        }
    }

    /**
     * Inicia la edición del Formato de Academia elegido por el Académico
     * en la IU VSeleccionarFormatoEditar
     * 
     * @param formatoElegido Tipo de Formato de Academia a editar
     */
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
    
    /**
     * Solicita al AdministradorEdicionPlanTrabajoAcademia los PlanTrabajoAcademia
     * con estado EN_EDICION y al Académico COORDINADOR que inicio sesión como su autor
     */
    public void editarPlanTrabajoAcademia() {
        AdministradorEdicionPlanTrabajoAcademia administradorEdicion = AdministradorEdicionPlanTrabajoAcademia.obtenerInstancia();
        administradorEdicion.solicitarPlanesEnEdicion(sesionActual.getNumeroPersonal());       
    }
    
    /**
     * Despliega la IU VListaPlanesAcademiaEdicion con los PlanTrabajoAcademia que tengan
     * el estado EN_EDICION recuperados del Academico
     * 
     * @param planesEnEdicion Lista de PlanTrabajoAcademia con estado EN_EDICION
     */
    public void desplegarListaPlanesEnEdicion(ArrayList<PlanTrabajoAcademia> planesEnEdicion) {
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VListaPlanesAcademiaEdicion.fxml"));
        try {
            Parent padre = cargadorFXML.load();
            VListaPlanesAcademiaEdicionController seleccionarPlan = cargadorFXML.getController();
            seleccionarPlan.setPlanesEnEdicion(planesEnEdicion);

            Stage escenario = new Stage();
            escenario.setScene(new Scene(padre));
            escenario.setTitle("Seleccione un Plan de Trabajo de Academia para editar");
            escenario.setResizable(false);
            escenario.show(); 
        } 
        catch (IllegalStateException excepcionEstadoIlegal) {
            loggerDelSistema.fatal("No se pudo cargar VSeleccionarPlanesAcademiaEdicion.fxml");
        }
        catch (IOException excepcionEntradaSalida) {
            loggerDelSistema.fatal("Ocurrió un error I/O en método desplegarListaPlanesEnEdicion");
        }
    }
    
    /**
     * Envia el identificador del PlanTrabajoAcademia al AdministradorEdicionPlanTrabajoAcademia
     * para que este despliegue sus datos
     * 
     * @param idPlanAcademia Identificador del PlanTrabajoAcademia a editar
     */
    public void abrirPlanParaEditar(String idPlanAcademia) {
        AdministradorEdicionPlanTrabajoAcademia administradorEdicion = AdministradorEdicionPlanTrabajoAcademia.obtenerInstancia();
        administradorEdicion.desplegarFormularioConDatosPlan(idPlanAcademia);
    }
    
    /**
     * Despliega la IU VSeleccionarFormatoVisualizar para que el Académico seleccione
     * el tipo de Formato de Academia que desea ver
     */
    public void visualizarDocumento() {        
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VSeleccionarFormatoVisualizar.fxml"));
        try {
            Parent padre = cargadorFXML.load();
            VSeleccionarFormatoVisualizarController seleccionarFormato = cargadorFXML.getController();
            if (sesionActual.getRolAcademico().equals(RolAcademico.COORDINADOR)) {
                seleccionarFormato.establecerOpcionesDeCoordinador();
            }
            if (sesionActual.getRolAcademico().equals(RolAcademico.DOCENTE)) {
                seleccionarFormato.establecerOpcionesDeDocente();
            }
            Stage escenario = new Stage();
            escenario.setScene(new Scene(padre));
            escenario.setTitle("SGPA - Principal");
            escenario.setResizable(false);
            
            Stage escenaActual = (Stage) buttonElaborarFormato.getScene().getWindow();
            escenaActual.close();

            escenario.show();
        } 
        catch (IllegalStateException excepcionEstadoIlegal) {
            loggerDelSistema.fatal("No se pudo cargar VSeleccionarFormatoVisualizar.fxml");
        }
        catch (IOException excepcionEntradaSalida) {
            loggerDelSistema.fatal("Ocurrió un error I/O en método visualizarDocumento");
        }
    }
    
    /**
     * Inicia la visualizacion de un Formato de Academia específico
     * 
     * @param formatoElegido Tipo de Formato de Academia a visualizar
     */
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
    
    /**
     * Inicia el flujo para Visualizar un PlanTrabajoAcademia, solicitando al 
     * AdministradorVisualizacionPlanesAcademia los PlanTrabajoAcademia con 
     * estado CONCLUIDO y que tengan al Académico que inicio sesión como su autor
     */
    public void visualizarPlanesAcademia() {
        AdministradorVisualizacionPlanesAcademia administradorVisualizacion = AdministradorVisualizacionPlanesAcademia.obtenerInstancia();
        administradorVisualizacion.obtenerPlanesAcademiaDeCoordinador(sesionActual.getNumeroPersonal());           
    }
    
    /**
     * Despliega la lista de PlanTrabajoAcademia con estado CONCLUIDO para que
     * el Academico seleccione uno y visualizar sus datos
     * 
     * @param planesConcluidos Lista de PlanTrabajoAcademia con estado CONCLUIDO
     */
    public void mostrarListaPlanes(ArrayList<PlanTrabajoAcademia> planesConcluidos) {
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VEleccionPlanAcademia.fxml"));
        try {
            Parent padre = cargadorFXML.load();
            VEleccionPlanAcademiaController seleccionarPlan = cargadorFXML.getController();
            seleccionarPlan.mostrarListaPlanes(planesConcluidos);

            Stage escenario = new Stage();
            escenario.setScene(new Scene(padre));
            escenario.setTitle("Seleccione un Plan de Trabajo de Academia a visualizar");
            escenario.setResizable(false);
            escenario.show();
        } 
        catch (IllegalStateException excepcionEstadoIlegal) {
            loggerDelSistema.fatal("No se pudo cargar VSeleccionarPlanAcademia.fxml");
        }
        catch (IOException excepcionEntradaSalida) {
            loggerDelSistema.fatal("Ocurrió un error I/O en método mostrarListaPlanes");
        }
    }
    
    /**
     * Despliega un mensaje al Académico que inició sesión en el sistema
     * 
     * @param mensaje Mensaje a mostrar al Académico
     */
    public void mostrarMensajeDeSistema(String mensaje) {
        Alert mensajeDeSistema = new Alert(AlertType.INFORMATION);
        mensajeDeSistema.setTitle("SGPA - Mensaje del sistema");
        mensajeDeSistema.setHeaderText(null);
        mensajeDeSistema.setContentText(mensaje);
        mensajeDeSistema.showAndWait();
    }
    
}
