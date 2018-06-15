/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   09/06/2018				  */
/* Ultima modificación: 13/06/2018				  */
/* Descripción: Controlador de eventos para la interfaz gráfica   */
/*              VSeleccionarAcademia.                             */
/****************************************************************/
package mx.fei.sgpa.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import mx.fei.sgpa.domain.Academia;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Controlador de eventos para la IU VSeleccionarAcademia
 */
public class VSeleccionarAcademiaController implements Initializable {
    
    private ArrayList<Academia> academias;
    private static final Logger loggerDelSistema = LogManager.getLogger(VSeleccionarAcademiaController.class);
    
    @FXML
    private ComboBox comboBoxAcademias;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    /**
     * Establece en la IU las Academias disponibles para el Académico COORDINADOR
     * 
     * @param academias Lista de Academia bajo cargo del Académico
     */
    public void setAcademias(ArrayList<Academia> academias) {
        this.academias = academias;
        comboBoxAcademias.getItems().addAll(academias);
        comboBoxAcademias.getSelectionModel().select(0);
    }
    
    /**
     * Envia al controlador JavaFX de la IU VSGPAPrincipal el identificador
     * de la Academia a la cual se creará el Plan de Trabajo de Academia
     */
    public void crearPlanAcademia() {
        String idAcademia = "";
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VSGPAPrincipal.fxml"));
        try {
            Parent padre = cargadorFXML.load();
            VSGPAPrincipalController principal = cargadorFXML.getController();
            for (int a = 0; a < academias.size(); a++) {
                if (comboBoxAcademias.getValue().toString().equals(academias.get(a).getNombreAcademia())) {
                    idAcademia = academias.get(a).getIdAcademia();
                }
            }
            Stage escenaActual = (Stage) comboBoxAcademias.getScene().getWindow();
            escenaActual.close();
            principal.crearPlan(idAcademia);
        }
        catch (IllegalStateException excepcionEstadoIlegal) {
            loggerDelSistema.fatal("No se pudo cargar VSGPAPrincipal.fxml");
        }
        catch (IOException excepcionEntradaSalida) {
            loggerDelSistema.fatal("Ocurrió un error I/O en método crearPlanAcademia");
        }
    }
}
