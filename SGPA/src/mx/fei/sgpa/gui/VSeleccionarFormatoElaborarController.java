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

public class VSeleccionarFormatoElaborarController implements Initializable {
    
    private String formatoElegido;
    
    @FXML
    private ComboBox comboBoxElegirFormato;
    
    @FXML
    private Button buttonElaborar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void establecerOpcionesDeCoordinador() {
        comboBoxElegirFormato.getItems().addAll("Plan de Trabajo de Academia", "Minuta");
    }
    
    public void establecerOpcionesDeDocente() {
        comboBoxElegirFormato.getItems().addAll("Plan de Curso", "Avance Programático");
    }
    
    public void cambiarNombreButtonElaborar() {
        formatoElegido = comboBoxElegirFormato.getValue().toString();
        buttonElaborar.setText("Elaborar " + formatoElegido);
    }
    
    public void elaborarFormato() {
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VSGPAPrincipal.fxml"));
        Parent padre = null;
        try {
            padre = cargadorFXML.load();
        } 
        catch (IOException ex) {
            Logger.getLogger(VSeleccionarFormatoElaborarController.class.getName()).log(Level.SEVERE, null, ex);
        }
        VSGPAPrincipalController principal = cargadorFXML.getController();
        Stage escenaActual = (Stage) buttonElaborar.getScene().getWindow();
        escenaActual.close();
        try {
            principal.iniciarCreacionFormato(formatoElegido);            
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
    
}
