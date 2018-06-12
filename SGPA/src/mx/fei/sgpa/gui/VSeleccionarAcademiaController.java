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
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import mx.fei.sgpa.domain.Academia;

public class VSeleccionarAcademiaController implements Initializable {
    
    private ArrayList<Academia> academias;
    
    @FXML
    private ComboBox comboBoxAcademias;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setAcademias(ArrayList<Academia> academias) {
        this.academias = academias;
        comboBoxAcademias.getItems().addAll(academias);
    }
    
    public void crearPlanAcademia() {
        String idAcademia = "";
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VSGPAPrincipal.fxml"));
        Parent padre = null;
        try {
            padre = cargadorFXML.load();
        } catch (IOException ex) {
            Logger.getLogger(VSGPAPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        VSGPAPrincipalController principal = cargadorFXML.getController();
        Stage escenaActual = (Stage) comboBoxAcademias.getScene().getWindow();
        escenaActual.close();
        
        for (int a = 0; a < academias.size(); a++) {
            if (comboBoxAcademias.getValue().toString().equals(academias.get(a).getNombreAcademia())) {
                idAcademia = academias.get(a).getIdAcademia();
            }
        }
        principal.crearPlan(idAcademia);
    }
}
