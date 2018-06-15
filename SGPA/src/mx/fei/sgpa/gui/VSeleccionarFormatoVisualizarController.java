/** ************************************************************* */
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   08/06/2018				  */
/* Ultima modificación: 13/06/2018				  */
/* Descripción: Controlador de eventos para la interfaz           */
/*              VSeleccionarFormatoVisualizar.        		  */
/** ************************************************************* */
package mx.fei.sgpa.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Controlador de eventos para la IU VSeleccionarFormatoVisualizar
 */
public class VSeleccionarFormatoVisualizarController implements Initializable {

    private String formatoElegido;
    private static final Logger loggerDelSistema = LogManager.getLogger(VSeleccionarFormatoVisualizarController.class);
    
    @FXML
    private ComboBox comboBoxElegirFormato;
    
    @FXML
    private Button buttonVisualizar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        formatoElegido = "";        
    }
    
    /**
     * Establece los tipos de Formato de Academia que el Académico puede
     * visualizar si es COORDINADOR
     */
    public void establecerOpcionesDeCoordinador() {
        comboBoxElegirFormato.getItems().addAll("Plan de Trabajo de Academia", "Minuta");
    }
    
    /**
     * Establece los tipos de Formato de Academia que el Académico puede
     * visualizar si es DOCENTE
     */ 
    public void establecerOpcionesDeDocente() {
        comboBoxElegirFormato.getItems().addAll("Plan de Curso", "Avance Programático");
    }
    
    /**
     * Cambia el texto del botón visualizar para hacer saber al Académico
     * el tipo de Formato de Academia que está a punto de visualizar
     */
    public void cambiarNombreButtonVisualizar() {
        formatoElegido = comboBoxElegirFormato.getValue().toString();
        buttonVisualizar.setText("Visualizar " + formatoElegido);
    }
    
    /**
     * Envía al controlador JavaFX de la IU VSGPAPrincipal el tipo de
     * Formato de Academia que eligió visualizar el Académico
     */
    public void visualizarFormato() {
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VSGPAPrincipal.fxml"));
        try {
            Parent padre = cargadorFXML.load();
            VSGPAPrincipalController principal = cargadorFXML.getController();
            try {
                if (!formatoElegido.equals("")) {
                    Stage escenaActual = (Stage) buttonVisualizar.getScene().getWindow();
                    escenaActual.close();
                    principal.iniciarVisualizacionFormato(formatoElegido);
                }
                else {
                    principal.mostrarMensajeDeSistema("Por favor elija un Formato de Academia");
                }
            }
            catch (UnsupportedOperationException excepcion) {
                principal.mostrarMensajeDeSistema("Por el momento solo es posible visualizar Plan de Trabajo de Academia");
                Stage escenario = new Stage();
                escenario.setScene(new Scene(padre));
                escenario.setTitle("SGPA-Principal");
                escenario.setResizable(false);
                escenario.show();
            }
        } 
        catch (IllegalStateException excepcionEstadoIlegal) {
            loggerDelSistema.fatal("No se pudo cargar VSGPAPrincipal.fxml");
        }
        catch (IOException excepcionEntradaSalida) {
            loggerDelSistema.fatal("Ocurrió un error I/O en método visualizarFormato");
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
            Stage escenaActual = (Stage) buttonVisualizar.getScene().getWindow();
            escenaActual.close();
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
