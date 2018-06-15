/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   16/04/2018				  */
/* Ultima modificación: 17/05/2018				  */
/* Descripción: Aplicación principal del SGPA.  		  */
/****************************************************************/

package mx.fei.sgpa.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Aplicación principal del SGPA
 */
public class SGPA extends Application {
    
    @Override
    public void start(Stage escenario) throws Exception {
        Parent padre = FXMLLoader.load(getClass().getResource("VInicioSesion.fxml"));
        Scene escena = new Scene(padre);
        escenario.setScene(escena);
        escenario.setTitle("SPGA - Iniciar sesión");
        escenario.setResizable(false);
        escenario.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
