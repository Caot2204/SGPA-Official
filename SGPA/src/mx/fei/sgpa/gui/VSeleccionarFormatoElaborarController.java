/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   16/05/2018				  */
/* Ultima modificación: 13/06/2018				  */
/* Descripción: Controlador de eventos para la interfaz gráfica   */
/*              VSeleccionarFormatoElaborar.                      */
/****************************************************************/
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
 * Controlador de eventos para la IU VSeleccionarFormatoElaborar
 */
public class VSeleccionarFormatoElaborarController implements Initializable {
    
    private String formatoElegido;
    private static final Logger loggerDelSistema = LogManager.getLogger(VSeleccionarFormatoElaborarController.class);
    
    @FXML
    private ComboBox comboBoxElegirFormato;
    
    @FXML
    private Button buttonElaborar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        formatoElegido = "";
    }
    
    /**
     * Establece los tipos de Formato de Academia que el Académico puede
     * elaborar si es COORDINADOR
     */
    public void establecerOpcionesDeCoordinador() {
        comboBoxElegirFormato.getItems().addAll("Plan de Trabajo de Academia", "Minuta");
    }
    
    /**
     * Establece los tipos de Formato de Academia que el Académico puede
     * elaborar si es DOCENTE
     */ 
    public void establecerOpcionesDeDocente() {
        comboBoxElegirFormato.getItems().addAll("Plan de Curso", "Avance Programático");
    }
    
    /**
     * Cambia el texto del botón elaborar para hacer saber al Académico
     * el tipo de Formato de Academia que está a punto de elaborar
     */
    public void cambiarNombreButtonElaborar() {
        formatoElegido = comboBoxElegirFormato.getValue().toString();
        buttonElaborar.setText("Elaborar " + formatoElegido);
    }
    
    /**
     * Envía al controlador JavaFX de la IU VSGPAPrincipal el tipo de
     * Formato de Academia que eligió elaborar el Académico
     */
    public void elaborarFormato() {
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VSGPAPrincipal.fxml"));
        try {
            Parent padre = cargadorFXML.load();
            VSGPAPrincipalController principal = cargadorFXML.getController();
            try {
                if (!formatoElegido.equals("")) {
                    Stage escenaActual = (Stage) buttonElaborar.getScene().getWindow();
                    escenaActual.close();
                    principal.iniciarCreacionFormato(formatoElegido);
                }   
                else {
                    principal.mostrarMensajeDeSistema("Por favor elija un Formato de Academia");
                }
            }
            catch (UnsupportedOperationException excepcion) {
                principal.mostrarMensajeDeSistema("Por el momento solo es posible crear Plan de Trabajo de Academia");
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
            loggerDelSistema.fatal("Ocurrió un error I/O en método elaborarFormato");
        }
    }
    
    public void regresarAPrincipal() {
        try {
            Parent padre = FXMLLoader.load(getClass().getResource("VSGPAPrincipal.fxml"));
            Scene escena = new Scene(padre);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("SGPA - Principal");
            escenario.setResizable(false);
            Stage escenaActual = (Stage) buttonElaborar.getScene().getWindow();
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
