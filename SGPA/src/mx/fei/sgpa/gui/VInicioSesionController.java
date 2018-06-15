/** ************************************************************* */
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   13/06/2018				  */
/* Ultima modificación: 13/06/2018				  */
/* Descripción: Controlador de eventos para la interfaz           */
/*              VIniciarSesion.                 		  */
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
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.fei.sgpa.dao.cuentadeusuario.CuentaUsuarioDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Controlador de eventos para la IU VInicioSesion
 */
public class VInicioSesionController implements Initializable {
    
    private CuentaUsuarioDAO cuentaUsuarioDAO;
    private int numeroDePersonal;
    private static final Logger loggerDelSistema = LogManager.getLogger(VInicioSesionController.class);
    
    @FXML
    private TextField textFieldNombreUsuario;
    
    @FXML
    private PasswordField passwordFieldUsuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    /**
     * Obtiene los datos de la interfaz y los envia al 
     * AdministradorInicioSesion para validar la CuentaUsuario
     * ingresada
     */
    public void iniciarSesion() {
        String nombreUsuario;
        String password;
        boolean inicioSesionCorrecto;
        cuentaUsuarioDAO = new CuentaUsuarioDAO();
        
        if (validarDatosInterfazGrafica()) {
            nombreUsuario = textFieldNombreUsuario.getText();
            password = passwordFieldUsuario.getText();
            AdministradorInicioSesion administradorInicioSesion = new AdministradorInicioSesion();
            inicioSesionCorrecto = administradorInicioSesion.iniciarSesion(nombreUsuario, password);
            if (inicioSesionCorrecto) {
                mostrarPantallaPrincipal();
            }
            else {
                mostrarMensajeDeSistema("Nombre de Usuario o Contraseña incorrecta");
            }            
        }
        else {
            mostrarMensajeDeSistema("Datos inválidos");
        }
    }
    
    /**
     * Valida que los datos ingresados por el usuario cumplan con las restricciones
     * necesarias (No Nulos y sin espacios en blanco)
     * 
     * @return true si los datos cumplen con las restricciones, false si no fue así
     */    
    public boolean validarDatosInterfazGrafica() {
        boolean datosValidos = false;
        if (textFieldNombreUsuario.getText() != null && !textFieldNombreUsuario.getText().equals("")) {
            if (!String.valueOf(textFieldNombreUsuario.getText().charAt(0)).equals(" ")) {
                if (passwordFieldUsuario.getText() != null && !passwordFieldUsuario.getText().equals("")) {
                    if (!String.valueOf(passwordFieldUsuario.getText().charAt(0)).equals(" ")) {
                        datosValidos = true;
                    }
                }
            }            
        }
        return datosValidos;
    }
    
    /**
     * Despliega un mensaje para el Académico
     * @param mensaje Mensaje que será mostrado al Académico
     */
    public void mostrarMensajeDeSistema(String mensaje) {
        Alert mensajeDeSistema = new Alert(Alert.AlertType.ERROR);
        mensajeDeSistema.setTitle("SGPA - Mensaje del sistema");
        mensajeDeSistema.setHeaderText(null);
        mensajeDeSistema.setContentText(mensaje);
        mensajeDeSistema.showAndWait();
    }
    
    /**
     * Despliega la IU VSGPAPrincipal
     */
    public void mostrarPantallaPrincipal() {
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VSGPAPrincipal.fxml"));
        try {
            Parent padre = cargadorFXML.load();

            Stage escenario = new Stage();
            escenario.setScene(new Scene(padre));
            escenario.setTitle("SGPA - Principal");
            escenario.setResizable(false);

            Stage escenaActual = (Stage) textFieldNombreUsuario.getScene().getWindow();
            escenaActual.close();

            escenario.show();
        } 
        catch (IllegalStateException excepcionEstadoIlegal) {
            loggerDelSistema.fatal("mostrarPantallaPrincipal: No se pudo cargar VSGPAPrincipal.fxml");
        }
        catch (IOException excepcionEntradaSalida) {
            loggerDelSistema.fatal("mostrarPantallaPrincipal: Ocurrió un error I/O");
        }
    }
    
}
