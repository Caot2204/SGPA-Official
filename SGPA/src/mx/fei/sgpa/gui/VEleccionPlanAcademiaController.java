
package mx.fei.sgpa.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mx.fei.sgpa.domain.plantrabajoacademia.PlanTrabajoAcademia;
import mx.fei.sgpa.gui.adaptadorestablasclases.AdaptadorTablaPlanTrabajoAcademia;

public class VEleccionPlanAcademiaController implements Initializable {
    
    private ArrayList<PlanTrabajoAcademia> planesConcluidos;
    
    @FXML
    private TableColumn tableColumnAcademia;
    
    @FXML
    private TableColumn tableColumnPeriodo;
    
    @FXML
    private TableView tableViewListaPlanes;
    
    private ObservableList<AdaptadorTablaPlanTrabajoAcademia> planesTabla;
    private int posicionSeleccionada;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
              
    }

    public void mostrarListaPlanes(ArrayList<PlanTrabajoAcademia> planesConcluidos) {
        this.planesConcluidos = planesConcluidos;
        tableColumnAcademia.setCellValueFactory(new PropertyValueFactory<AdaptadorTablaPlanTrabajoAcademia,String>("academia"));
        tableColumnPeriodo.setCellValueFactory(new PropertyValueFactory<AdaptadorTablaPlanTrabajoAcademia,String>("periodo"));
        planesTabla = FXCollections.observableArrayList();
        
        for (int a = 0; a < planesConcluidos.size(); a++) {
            AdaptadorTablaPlanTrabajoAcademia planEnTabla = new AdaptadorTablaPlanTrabajoAcademia();
            planEnTabla.setId(planesConcluidos.get(a).getId());
            planEnTabla.setAcademia(planesConcluidos.get(a).getNombreAcademia());
            planEnTabla.setPeriodo(planesConcluidos.get(a).getPeriodoEscolar());
            planesTabla.add(planEnTabla);
        }
        
        tableViewListaPlanes.setItems(planesTabla);
        final ObservableList<AdaptadorTablaPlanTrabajoAcademia> planesEnTabla = tableViewListaPlanes.getSelectionModel().getSelectedItems();
        planesEnTabla.addListener(seleccionPlan);
    }
    
    private final ListChangeListener<AdaptadorTablaPlanTrabajoAcademia> seleccionPlan =
            new ListChangeListener<AdaptadorTablaPlanTrabajoAcademia>() {
                @Override
                public void onChanged(ListChangeListener.Change<? extends AdaptadorTablaPlanTrabajoAcademia> escuchaCambiosEnTabla) {
                    mostrarPlan();
                }
            };

    public AdaptadorTablaPlanTrabajoAcademia obtenerPlanSeleccionado() {
        if (tableViewListaPlanes != null) {
            List<AdaptadorTablaPlanTrabajoAcademia> tabla = tableViewListaPlanes.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final AdaptadorTablaPlanTrabajoAcademia planSeleccionado = tabla.get(0);
                return planSeleccionado;
            }
        }
        return null;
    }

    private void mostrarPlan() {
        final AdaptadorTablaPlanTrabajoAcademia planSeleccionado = obtenerPlanSeleccionado();
        posicionSeleccionada = planesTabla.indexOf(planSeleccionado);

        if (planSeleccionado != null) {
            Stage escenaActual = (Stage) tableViewListaPlanes.getScene().getWindow();
            escenaActual.close();
            AdministradorVisualizacionPlanesAcademia administradorVisualizacion = AdministradorVisualizacionPlanesAcademia.obtenerInstancia();
            administradorVisualizacion.desplegarPlan(planSeleccionado.getId());                        
        }
    }
    
}
