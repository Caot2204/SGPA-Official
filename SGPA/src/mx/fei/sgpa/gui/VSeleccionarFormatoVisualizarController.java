/** ************************************************************* */
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   08/06/2018				  */
/* Ultima modificación: 08/06/2018				  */
/* Descripción: Controlador de eventos para la interfaz           */
/*              VSeleccionarFormatoVisualizar.        		  */
/** ************************************************************* */
package mx.fei.sgpa.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class VSeleccionarFormatoVisualizarController implements Initializable {

    private String formatoElegido;
    
    @FXML
    private ComboBox comboBoxElegirFormato;
    
    @FXML
    private Button buttonVisualizar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void establecerOpcionesDeCoordinador() {
        comboBoxElegirFormato.getItems().addAll("Plan de Trabajo de Academia", "Minuta");
    }
    
    public void establecerOpcionesDeDocente() {
        comboBoxElegirFormato.getItems().addAll("Plan de Curso", "Avance Programático");
    }
    
    public void cambiarNombreButtonVisualizar() {
        formatoElegido = comboBoxElegirFormato.getValue().toString();
        buttonVisualizar.setText("Visualizar " + formatoElegido);
    }
    
    public void visualizarFormato() {
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VSGPAPrincipal.fxml"));
        Parent padre = null;
        try {
            padre = cargadorFXML.load();
        } 
        catch (IOException ex) {
            Logger.getLogger(VSeleccionarFormatoElaborarController.class.getName()).log(Level.SEVERE, null, ex);
        }
        VSGPAPrincipalController principal = cargadorFXML.getController();
        Stage escenaActual = (Stage) buttonVisualizar.getScene().getWindow();
        escenaActual.close();
        try {
            principal.iniciarVisualizacionFormato(formatoElegido);
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
    
}
