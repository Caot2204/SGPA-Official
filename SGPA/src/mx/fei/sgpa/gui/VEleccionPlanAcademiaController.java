/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   10/06/2018				  */
/* Ultima modificación: 13/06/2018				  */
/* Descripción: Controlador de eventos para la interfaz           */
/*              VEleccionPlanAcademia.             		  */
/** ************************************************************* */
package mx.fei.sgpa.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mx.fei.sgpa.domain.plantrabajoacademia.PlanTrabajoAcademia;
import mx.fei.sgpa.gui.adaptadorestablasclases.AdaptadorTablaPlanTrabajoAcademia;

/**
 * Controlador de eventos para la IU VEleccionPlanAcademia
 */
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
    private final ListChangeListener<AdaptadorTablaPlanTrabajoAcademia> seleccionPlan = new ListChangeListener<AdaptadorTablaPlanTrabajoAcademia>() {
                @Override
                public void onChanged(ListChangeListener.Change<? extends AdaptadorTablaPlanTrabajoAcademia> escuchaCambiosEnTabla) {
                    mostrarPlan();
                }};
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
              
    }

    /**
     * Muestra en pantalla la lista de PlanTrabajoAcademia con estado CONCLUIDO
     * de un Académico COORDINADOR
     * 
     * @param planesConcluidos Lista de PlanTrabajoAcademia con estado CONCLUIDO
     */
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

    /**
     * Devuelve el AdaptadorPlanTrabajoAcademia seleccionado por el Académico
     * que contiene el identificador de un PlanTrabajoAcademia a mostrar
     * 
     * @return Objeto de tipo AdaptadorPlanTrabajoAcademia
     */
    public AdaptadorTablaPlanTrabajoAcademia obtenerPlanSeleccionado() {
        AdaptadorTablaPlanTrabajoAcademia planSeleccionado = null;
        if (tableViewListaPlanes != null) {
            List<AdaptadorTablaPlanTrabajoAcademia> tabla = tableViewListaPlanes.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                planSeleccionado = tabla.get(0);
            }
        }
        return planSeleccionado;
    }
    
    /**
     * Envia el identificador del PlanTrabajoAcademia a visualizar al
     * AdministradorVisualizacionPlanTrabajoAcademia
     */
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
