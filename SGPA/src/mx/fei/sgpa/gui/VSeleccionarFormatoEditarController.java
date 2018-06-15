/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   09/06/2018				  */
/* Ultima modificación: 13/06/2018				  */
/* Descripción: Controlador de eventos para la interfaz gráfica   */
/*              VSeleccionarFormatoEditar.                        */
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
 * Controlador de eventos para la IU VSeleccionarFormatoEditar
 */
public class VSeleccionarFormatoEditarController implements Initializable {
    
    private String formatoElegido;
    private static final Logger loggerDelSistema = LogManager.getLogger(VSeleccionarFormatoEditarController.class);
    
    @FXML
    private ComboBox comboBoxElegirFormato;
    
    @FXML
    private Button buttonEditar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        formatoElegido = "";
    }

    /**
     * Establece los tipos de Formato de Academia que el Académico puede
     * editar si es COORDINADOR
     */
    public void establecerOpcionesDeCoordinador() {
        comboBoxElegirFormato.getItems().addAll("Plan de Trabajo de Academia", "Minuta");
    }
    
    /**
     * Establece los tipos de Formato de Academia que el Académico puede
     * editar si es DOCENTE
     */    
    public void establecerOpcionesDeDocente() {
        comboBoxElegirFormato.getItems().addAll("Plan de Curso", "Avance Programático");
    }
    
    /**
     * Cambia el texto del botón editar para hacer saber al Académico
     * el tipo de Formato de Academia que está a punto de editar
     */
    public void cambiarNombreButtonEditar() {
        formatoElegido = comboBoxElegirFormato.getValue().toString();
        buttonEditar.setText("Editar " + formatoElegido);
    }
    
    /**
     * Envia al controlador JavaFX de la IU VSGPAPrincipal el tipo de
     * Formato de Academia que eligió editar el Académico
     */
    public void editarFormato() {
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VSGPAPrincipal.fxml"));
        try {
            Parent padre = cargadorFXML.load();
            VSGPAPrincipalController principal = cargadorFXML.getController();
            try {
                if (!formatoElegido.equals("")) {
                    Stage escenaActual = (Stage) buttonEditar.getScene().getWindow();
                    escenaActual.close();
                    principal.iniciarEdicionFormato(formatoElegido);
                }
                else {
                    principal.mostrarMensajeDeSistema("Por favor elija un Formato de Academia");
                }
            }
            catch (UnsupportedOperationException excepcion) {
                principal.mostrarMensajeDeSistema("Por el momento solo es posible editar Plan de Trabajo de Academia");
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
            loggerDelSistema.fatal("Ocurrió un error I/O en método editarFormato");
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
            Stage escenaActual = (Stage) buttonEditar.getScene().getWindow();
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
