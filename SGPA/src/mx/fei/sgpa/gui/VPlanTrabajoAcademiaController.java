/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   15/05/2018				  */
/* Ultima modificación: 13/06/2018				  */
/* Descripción: Controlador de eventos para la interfaz           */
/*              VPlanTrabajoAcademia.           		  */
/** ************************************************************* */
package mx.fei.sgpa.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mx.fei.sgpa.domain.Academico;
import mx.fei.sgpa.domain.plantrabajoacademia.AccionDeMeta;
import mx.fei.sgpa.domain.plantrabajoacademia.DetallesPlanTrabajoAcademia;
import mx.fei.sgpa.domain.plantrabajoacademia.ExperienciaEducativaConParciales;
import mx.fei.sgpa.domain.plantrabajoacademia.ExamenParcial;
import mx.fei.sgpa.domain.plantrabajoacademia.FormaDeEvaluacion;
import mx.fei.sgpa.domain.plantrabajoacademia.MetaDeObjetivo;
import mx.fei.sgpa.domain.plantrabajoacademia.ObjetivoParticular;
import mx.fei.sgpa.domain.plantrabajoacademia.PlanTrabajoAcademia;
import mx.fei.sgpa.gui.adaptadorestablasclases.AdaptadorTablaAccionDeMeta;
import mx.fei.sgpa.gui.adaptadorestablasclases.AdaptadorTablaExamenParcial;
import mx.fei.sgpa.gui.adaptadorestablasclases.AdaptadorTablaFormaEvaluacion;

/**
 * Controlador de eventos para la IU VPlanTrabajoAcademia
 */
public class VPlanTrabajoAcademiaController implements Initializable {
    
    private String nombreAcademia;
    private String nombreCoordinador;
    private DetallesPlanTrabajoAcademia detallesPlan;
    private ArrayList<Academico> integrantes;
    private ArrayList<ObjetivoParticular> objetivosParticulares;
    private ArrayList<ExperienciaEducativaConParciales> examenesParciales;
    private ArrayList<FormaDeEvaluacion> formasDeEvaluacion;
    
    private AdministradorElaboracionPlanTrabajoAcademia administradorElaboracion;
    private AdministradorEdicionPlanTrabajoAcademia administradorEdicion;
    
    @FXML
    private TextField textFieldNombreAcademia;
    
    @FXML
    private TextField textFieldNombreCoordinador;
    
    @FXML
    private TextField textFieldProgramaEducativo;
    
    @FXML
    private TextField textFieldPeriodoEscolar;
    
    @FXML
    private TextArea textAreaObjetivoGeneral;
    
    @FXML
    private TextField textFieldPrimerMeta;
    
    @FXML
    private TextField textFieldPrimerObjetivoParticular;
    
    @FXML
    private TableView<AdaptadorTablaAccionDeMeta> tableViewAccionesMeta;
    
    @FXML
    private TableColumn tableColumnAccion;
    
    @FXML
    private TableColumn tableColumnFechaSemana;
    
    @FXML
    private TableColumn tableColumnFormaOperar;
    
    @FXML
    private TextField textFieldAccion;
    
    @FXML
    private TextField textFieldFechaSemana;
    
    @FXML
    private TextField textFieldFormaOperar;
    
    @FXML
    private Button buttonAgregarAccion;
    
    @FXML
    private Button buttonActualizarAccion;
    
    @FXML
    private Button buttonEliminarAccion;
    
    private ObservableList<AdaptadorTablaAccionDeMeta> accionesIngresadas;
    private int posicionSeleccionada;
    private final ListChangeListener<AdaptadorTablaAccionDeMeta> seleccionAccion = new ListChangeListener<AdaptadorTablaAccionDeMeta>() {
                @Override
                public void onChanged(ListChangeListener.Change<? extends AdaptadorTablaAccionDeMeta> escuchaAccion) {
                    mostrarDatosDeAccionParaEditar();
                }};
    
    @FXML
    private TableView<AdaptadorTablaExamenParcial> tableViewExamenesParciales;
    
    @FXML
    private TableColumn tableColumnNumeroParcial;
    
    @FXML
    private TableColumn tableColumnTemaDeParcial;
    
    @FXML
    private TextField textFieldNumeroParcial;
    
    @FXML
    private TextField textFieldTemaDeParcial;
    
    @FXML
    private Button buttonAgregarTema;
    
    @FXML
    private Button buttonActualizarTema;
    
    @FXML
    private Button buttonEliminarTema;
    
    private ObservableList<AdaptadorTablaExamenParcial> temasDeExamenesIngresados;
    private final ListChangeListener<AdaptadorTablaExamenParcial> seleccionTema = new ListChangeListener<AdaptadorTablaExamenParcial>() {
                @Override
                public void onChanged(ListChangeListener.Change<? extends AdaptadorTablaExamenParcial> escuchaParcial) {
                    mostrarDatosDeTemaParaEditar();
                }};
    
    @FXML
    private TableView<AdaptadorTablaFormaEvaluacion> tableViewFormasEvaluacion;
    
    @FXML
    private TableColumn tableColumnElemento;
    
    @FXML
    private TableColumn tableColumnPorcentajeElemento;
    
    @FXML
    private TextField textFieldElemento;
    
    @FXML
    private TextField textFieldPorcentajeElemento;
    
    @FXML
    private Button buttonAgregarFormaEvaluacion;
    
    @FXML
    private Button buttonActualizarFormaEvaluacion;
    
    @FXML
    private Button buttonEliminarFormaEvaluacion;
    
    private ObservableList<AdaptadorTablaFormaEvaluacion> formasEvaluacionIngresados;
    private final ListChangeListener<AdaptadorTablaFormaEvaluacion> seleccionFormaEvaluacion = new ListChangeListener<AdaptadorTablaFormaEvaluacion>() {
                @Override
                public void onChanged(ListChangeListener.Change<? extends AdaptadorTablaFormaEvaluacion> escuchaEvaluacion) {
                    mostrarDatosDeFormaEvaluacionParaEditar();
                }};
    
    @FXML
    private Button buttonGuardar;
    
    @FXML
    private Button buttonRegresar;
    
    @FXML
    private Button buttonAgregarObjetivo;
    
    @FXML
    private Button buttonAgregarMeta;
    
    @FXML
    private Button buttonAgregarEEExamen;
    
    @FXML
    private Button buttonAgregarEEEvaluacion;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableColumnAccion.setCellValueFactory(new PropertyValueFactory<AdaptadorTablaAccionDeMeta,String>("descripcionAccion"));
        tableColumnFechaSemana.setCellValueFactory(new PropertyValueFactory<AdaptadorTablaAccionDeMeta,String>("fechaSemana"));
        tableColumnFormaOperar.setCellValueFactory(new PropertyValueFactory<AdaptadorTablaAccionDeMeta,String>("formaOperar"));
        accionesIngresadas = FXCollections.observableArrayList();
        tableViewAccionesMeta.setItems(accionesIngresadas);
        final ObservableList<AdaptadorTablaAccionDeMeta> accionesEnTabla = tableViewAccionesMeta.getSelectionModel().getSelectedItems();
        accionesEnTabla.addListener(seleccionAccion);
        restaurarElementosIUAcciones();
        
        tableColumnNumeroParcial.setCellValueFactory(new PropertyValueFactory<AdaptadorTablaExamenParcial,Integer>("numeroParcial"));
        tableColumnTemaDeParcial.setCellValueFactory(new PropertyValueFactory<AdaptadorTablaExamenParcial,String>("temaDeParcial"));
        temasDeExamenesIngresados = FXCollections.observableArrayList();
        tableViewExamenesParciales.setItems(temasDeExamenesIngresados);
        final ObservableList<AdaptadorTablaExamenParcial> temasEnTabla = tableViewExamenesParciales.getSelectionModel().getSelectedItems();
        temasEnTabla.addListener(seleccionTema);
        restaurarElementosIUTemasExamenes();
        
        tableColumnElemento.setCellValueFactory(new PropertyValueFactory<AdaptadorTablaFormaEvaluacion,String>("elemento"));
        tableColumnPorcentajeElemento.setCellValueFactory(new PropertyValueFactory<AdaptadorTablaFormaEvaluacion,Float>("porcentaje"));
        formasEvaluacionIngresados = FXCollections.observableArrayList();
        tableViewFormasEvaluacion.setItems(formasEvaluacionIngresados);
        final ObservableList<AdaptadorTablaFormaEvaluacion> formasEvaluacionEnTabla = tableViewFormasEvaluacion.getSelectionModel().getSelectedItems();
        formasEvaluacionEnTabla.addListener(seleccionFormaEvaluacion);
        restaurarElementosIUFormasEvaluacion();
    }

    /**
     * Estable los datos de la Academia de la cual se realizará el Plan de Trabajo
     * de Academia
     * 
     * @param academia Nombre de la Academia
     * @param coordinador Nombre del Coordinador
     * @param integrantesAcademia Integrantes de la Academia
     */
    public void establecerDatosDeAcademia(String academia, String coordinador, ArrayList<Academico> integrantesAcademia) {
        this.nombreAcademia = academia;
        this.nombreCoordinador = coordinador;
        this.integrantes = integrantesAcademia;
        textFieldNombreAcademia.setText(nombreAcademia);
        textFieldNombreCoordinador.setText(nombreCoordinador);
    }
    
    /**
     * Establece todos los datos del PlanTrabajoAcademia en la IU VPlanTrabajoAcademia
     * utilizando a los métodos establecerObjetivosParticulares, establecerExamenesParciales
     * y establecerFormasEvaluacion
     * 
     * @param plan Datos del PlanTrabajoAcademia 
     */
    public void establecerDatosRecuperados(PlanTrabajoAcademia plan) {
        this.nombreAcademia = plan.getNombreAcademia();
        this.nombreCoordinador = plan.getNombreCoordinador();
        textFieldNombreAcademia.setText(nombreAcademia);
        textFieldNombreCoordinador.setText(nombreCoordinador);
        textFieldProgramaEducativo.setText(plan.getProgramaEducativo());
        textFieldProgramaEducativo.setEditable(false);
        textFieldPeriodoEscolar.setText(plan.getPeriodoEscolar());
        textFieldPeriodoEscolar.setEditable(false);
        textAreaObjetivoGeneral.setText(plan.getObjetivoGeneral());
        establecerObjetivosParticulares(plan.getObjetivosParticulares());
        establecerExamenesParciales(plan.getExamenesParciales());
        establecerFormasEvaluacion(plan.getFormasDeEvaluacion());
    }
    
    /**
     * Establece los objetivos, metas y acciones del PlanTrabajoAcademia en la
     * IU VPlanTrabajoAcademia
     * 
     * @param objetivosParticulares Lista de ObjetivoParticular del PlanTrabajoAcademia
     */
    public void establecerObjetivosParticulares(ArrayList<ObjetivoParticular> objetivosParticulares) {
        ObjetivoParticular objetivoParticular = objetivosParticulares.get(0);
        MetaDeObjetivo primerMeta = objetivoParticular.getMetasDeObjetivo().get(0);
        ArrayList<AccionDeMeta> accionesDeMeta = primerMeta.getAccionesDeMeta();
        
        textFieldPrimerObjetivoParticular.setText(objetivoParticular.getDescripcion());
        textFieldPrimerMeta.setText(primerMeta.getDescripcion());
        
        for (int i = 0; i < accionesDeMeta.size(); i++) {
            AdaptadorTablaAccionDeMeta accionEnTabla = new AdaptadorTablaAccionDeMeta();
            accionEnTabla.setDescripcionAccion(accionesDeMeta.get(i).getDescripcionAccion());
            accionEnTabla.setFechaSemana(accionesDeMeta.get(i).getFechaSemana());
            accionEnTabla.setFormaOperar(accionesDeMeta.get(i).getFormaOperar());
            accionesIngresadas.add(accionEnTabla);
        }        
    }
    
    /**
     * Establece los datos de los ExamenParcial de cada Experiencia Educativa
     * en la IU VPlanTrabajoAcademia
     * 
     * @param examenesParciales Lista de ExperienciaEducativaConParciales del PlanTrabajoAcademia
     */
    public void establecerExamenesParciales(ArrayList<ExperienciaEducativaConParciales> examenesParciales) {
        ArrayList<ExamenParcial> datosExamen = examenesParciales.get(0).getExamenesParciales();
        for (int i = 0; i < datosExamen.size(); i++) {
            ArrayList<String> temasDeExamen = datosExamen.get(i).getTemasDeParcial();
            for (int j = 0; j < temasDeExamen.size(); j++) {
                AdaptadorTablaExamenParcial examenParcialTabla = new AdaptadorTablaExamenParcial();
                examenParcialTabla.setNumeroParcial(i+1);
                examenParcialTabla.setTemaDeParcial(temasDeExamen.get(j));
                temasDeExamenesIngresados.add(examenParcialTabla);
            }
        }
    }
    
    /**
     * Establece las FormaDeEvaluacion de cada Experiencia Educativa en la IU
     * VPlanTrabajoAcademia
     * 
     * @param formasEvaluacion Lista de FormaDeEvaluacion del PlanTrabajoAcademia
     */
    public void establecerFormasEvaluacion(ArrayList<FormaDeEvaluacion> formasEvaluacion) {
        for (int i = 0; i < formasEvaluacion.size(); i++) {
            AdaptadorTablaFormaEvaluacion formaEvaluacionTabla = new AdaptadorTablaFormaEvaluacion();
            formaEvaluacionTabla.setElemento(formasEvaluacion.get(i).getElemento());
            formaEvaluacionTabla.setPorcentaje(formasEvaluacion.get(i).getPorcentaje());
            formasEvaluacionIngresados.add(formaEvaluacionTabla);
        }        
    }
    
    /**
     * Establece los datos del PlanTrabajoAcademia únicamente para visualizarlos,
     * NO editarlos
     * 
     * @param planAcademia Datos del PlanTrabajoAcademia
     */
    public void mostrarPlan(PlanTrabajoAcademia planAcademia) {
        establecerDatosRecuperados(planAcademia);
        
        textFieldProgramaEducativo.setEditable(false);
        textFieldPeriodoEscolar.setEditable(false);
        textAreaObjetivoGeneral.setEditable(false);
        textFieldPrimerObjetivoParticular.setEditable(false);
        textFieldPrimerMeta.setEditable(false);
        buttonAgregarObjetivo.setVisible(false);
        buttonAgregarMeta.setVisible(false);
        buttonAgregarEEExamen.setVisible(false);
        buttonAgregarEEEvaluacion.setVisible(false);
        buttonGuardar.setVisible(false);
        buttonRegresar.setVisible(false);
        textFieldAccion.setVisible(false);
        textFieldFechaSemana.setVisible(false);
        textFieldFormaOperar.setVisible(false);
        buttonAgregarAccion.setVisible(false);
        buttonActualizarAccion.setVisible(false);
        buttonEliminarAccion.setVisible(false);
        textFieldNumeroParcial.setVisible(false);
        textFieldTemaDeParcial.setVisible(false);
        buttonAgregarTema.setVisible(false);
        buttonActualizarTema.setVisible(false);
        buttonEliminarTema.setVisible(false);
        textFieldElemento.setVisible(false);
        textFieldPorcentajeElemento.setVisible(false);
        buttonAgregarFormaEvaluacion.setVisible(false);
        buttonActualizarFormaEvaluacion.setVisible(false);
        buttonEliminarFormaEvaluacion.setVisible(false);
    }
    
    /**
     * Obtiene los datos ingresados por el Académico COORDINADOR y los envía
     * al AdministradorElaboracionPlanTrabajoAcademia o AdministradorEdicionPlanTrabajoAcademia
     * (dependiendo de cual este instanciado en ese momento)
     */
    public void guardarPlan() {
        objetivosParticulares = new ArrayList<>();
        examenesParciales = new ArrayList<>();
        formasDeEvaluacion = new ArrayList<>();
        
        try {
            obtenerDatosDeIU();
            if (AdministradorElaboracionPlanTrabajoAcademia.existeInstancia()) {
                administradorElaboracion = AdministradorElaboracionPlanTrabajoAcademia.obtenerInstancia();
                administradorElaboracion.guardarPlan(detallesPlan, objetivosParticulares, examenesParciales, formasDeEvaluacion);            
            }
            else if (AdministradorEdicionPlanTrabajoAcademia.existeInstancia()) {
                administradorEdicion = AdministradorEdicionPlanTrabajoAcademia.obtenerInstancia();
                administradorEdicion.guardarPlan(detallesPlan, objetivosParticulares, examenesParciales, formasDeEvaluacion);
            }
        }
        catch (IllegalArgumentException excepcionArgumentoInvalido) {
            mostrarMensajeDatosInvalidos();
        }
    }
    
    /**
     * Obtiene los datos ingresados en la IU VPlanTrabajoAcademia utilizando los
     * métodos obtenerObjetivosParticulares, obtenerExamenesParciales y
     * obtenerFormasEvaluacion; y valida si son aptos para almacenarlos
     */
    public void obtenerDatosDeIU() {
        Date fechaActual = new Date();
        java.sql.Date fechaAprobacion = new java.sql.Date(fechaActual.getTime());
        
        detallesPlan = new DetallesPlanTrabajoAcademia();
        detallesPlan.setFechaAprobacion(fechaAprobacion);
        
        if (!textFieldProgramaEducativo.getText().equals("")) {
            String primerCaracterProgramaEducativo = String.valueOf(textFieldProgramaEducativo.getText().charAt(0));
            if (!primerCaracterProgramaEducativo.equals(" ")) {
                detallesPlan.setProgramaEducativo(textFieldProgramaEducativo.getText());
            }
            else {
                textFieldProgramaEducativo.requestFocus();
                throw new IllegalArgumentException("Datos inválidos en programa educativo");
            }
        }
        else {
            textFieldProgramaEducativo.requestFocus();
            throw new IllegalArgumentException("Datos inválidos en programa educativo");
        }
        
        if (!textFieldPeriodoEscolar.getText().equals("")) {
            String primerCaracterPeriodoEscolar = String.valueOf(textFieldPeriodoEscolar.getText().charAt(0));
            if (!primerCaracterPeriodoEscolar.equals(" ")) {
                detallesPlan.setPeriodoEscolar(textFieldPeriodoEscolar.getText());
            }
            else {
                textFieldPeriodoEscolar.requestFocus();
                throw new IllegalArgumentException("Datos inválidos en periodo escolar");
            }
        }
        else {
            textFieldPeriodoEscolar.requestFocus();
            throw new IllegalArgumentException("Datos inválidos en periodo escolar");
        }
        
        detallesPlan.setNombreAcademia(nombreAcademia);
        detallesPlan.setNombreCoordinador(nombreCoordinador);
        
        if (!textAreaObjetivoGeneral.getText().equals("")){
            String primerCaracterObjetivoGeneral = String.valueOf(textAreaObjetivoGeneral.getText().charAt(0));
            if (!primerCaracterObjetivoGeneral.equals(" ") && textAreaObjetivoGeneral.getText().length() < 500) {
                detallesPlan.setObjetivoGeneral(textAreaObjetivoGeneral.getText());
            }
            else {
                textAreaObjetivoGeneral.requestFocus();
                throw new IllegalArgumentException("Datos inválidos en objetivo general");
            }            
        }
        else {
            textAreaObjetivoGeneral.requestFocus();
            throw new IllegalArgumentException("Datos inválidos en objetivo general");            
        }
        
        obtenerObjetivosParticulares();
        obtenerExamenesParciales();
        obtenerFormasEvaluacion();
        
    }
    
    /**
     * Obtiene los datos de los Objetivos Particulares, Metas y Acciones ingresadas
     * en la IU VPlanTrabajoAcademia
     */
    public void obtenerObjetivosParticulares() {
        objetivosParticulares = new ArrayList<>();
        
        if (textFieldPrimerMeta.getText() != null && !textFieldPrimerMeta.getText().equals("")) {
            String primerCaracterMeta = String.valueOf(textFieldPrimerMeta.getText().charAt(0));
            if (!primerCaracterMeta.equals(" ")) {
                MetaDeObjetivo primerMeta = new MetaDeObjetivo();
                primerMeta.setId("MTA1.1");
                primerMeta.setDescripcion(textFieldPrimerMeta.getText());
                ArrayList<AccionDeMeta> accionesDeMeta = new ArrayList<>();
                for (int a = 0; a < accionesIngresadas.size(); a++) {
                    AccionDeMeta accion = new AccionDeMeta();
                    accion.setDescripcionAccion(accionesIngresadas.get(a).getDescripcionAccion());
                    accion.setFechaSemana(accionesIngresadas.get(a).getFechaSemana());
                    accion.setFormaOperar(accionesIngresadas.get(a).getFormaOperar());
                    accionesDeMeta.add(accion);
                }
                primerMeta.setAccionesDeMeta(accionesDeMeta);

                if (textFieldPrimerObjetivoParticular.getText() != null && !textFieldPrimerObjetivoParticular.getText().equals("")) {
                    String primerCaracterObjetivoParticular = String.valueOf(textFieldPrimerObjetivoParticular.getText().charAt(0));
                    if (!primerCaracterObjetivoParticular.equals(" ")) {
                        ObjetivoParticular primerObjetivo = new ObjetivoParticular();
                        primerObjetivo.setId("OBJP1");
                        primerObjetivo.setDescripcion(textFieldPrimerObjetivoParticular.getText());
                        ArrayList<MetaDeObjetivo> metasDeObjetivo = new ArrayList<>();
                        metasDeObjetivo.add(primerMeta);
                        primerObjetivo.setMetasDeObjetivo(metasDeObjetivo);
                        objetivosParticulares.add(primerObjetivo);
                    }
                    else {
                        textFieldPrimerObjetivoParticular.requestFocus();
                        throw new IllegalArgumentException("Datos inválidos en el primer objetivo particular");
                    }
                }
                else {
                    textFieldPrimerObjetivoParticular.requestFocus();
                    throw new IllegalArgumentException("Datos inválidos en el primer objetivo particular");
                }
            }
            else {
                textFieldPrimerMeta.requestFocus();
                throw new IllegalArgumentException("Datos inválidos en la primera meta");
            }
        }
        else {
            textFieldPrimerMeta.requestFocus();
            throw new IllegalArgumentException("Datos inválidos en la primera meta");
        }
                
    }
    
    /**
     * Obtiene los Temas para Exámenes Parciales de las Experiencias Educativas
     * ingresados en la IU VPlanTrabajoAcademia
     */
    public void obtenerExamenesParciales() {
        examenesParciales = new ArrayList<>();
        ExperienciaEducativaConParciales primerExperiencia = new ExperienciaEducativaConParciales();
        primerExperiencia.setExperienciaEducativa("Principios de Construcción de Software");
        ArrayList<ExamenParcial> examenes = new ArrayList<>();
        
        for (int a = 1; a <= 2; a++) {
            ExamenParcial examen = new ExamenParcial();
            examen.setId(a);
            ArrayList<String> temasDeParcial = new ArrayList<>();
            for (int b = 0; b < temasDeExamenesIngresados.size(); b++){
                if (temasDeExamenesIngresados.get(b).getNumeroParcial() == a) {
                    temasDeParcial.add(temasDeExamenesIngresados.get(b).getTemaDeParcial());
                }                
            }
            examen.setTemasDeParcial(temasDeParcial);
            examenes.add(examen);
        }
        
        primerExperiencia.setExamenesParciales(examenes);
        examenesParciales.add(primerExperiencia);
    }
    
    /**
     * Obtiene las Formas de Evaluación para cada Experiencia Educativa
     * ingresadas en la IU VPlanTrabajoAcademia
     */    
    public void obtenerFormasEvaluacion() {
        formasDeEvaluacion = new ArrayList<>();
        for (int a = 0; a < formasEvaluacionIngresados.size(); a++) {
            FormaDeEvaluacion formaEvaluacion = new FormaDeEvaluacion();
            formaEvaluacion.setElemento(formasEvaluacionIngresados.get(a).getElemento());
            formaEvaluacion.setPorcentaje(formasEvaluacionIngresados.get(a).getPorcentaje());
            formasDeEvaluacion.add(formaEvaluacion);
        }        
    }
    
    /**
     * Muestra el mensaje para indicar al Académico que ha ingresado datos inválidos o
     * campos sin llenar
     */
    public void mostrarMensajeDatosInvalidos() {
        Alert mensajeDatosInvalidos = new Alert(AlertType.WARNING);
        mensajeDatosInvalidos.setTitle("Datos inválidos");
        mensajeDatosInvalidos.setHeaderText("Ingresó datos inválidos, por favor corríjalos");
        mensajeDatosInvalidos.showAndWait();
    }
    
    /**
     * Muestra el mensaje para Concluir (asignar CONCLUIDO) o Guardar progreso (asignar EN_EDICION)
     * al PlanTrabajoAcademia ingresado
     */
    public void mostrarMensajeConcluir() {
        Alert mensajeConcluir = new Alert(AlertType.CONFIRMATION);
        mensajeConcluir.setTitle("Concluir Plan de Trabajo de Academia");
        mensajeConcluir.setHeaderText("¿Desea concluir el Plan de Trabajo de Academia?");

        ButtonType buttonTypeConcluir = new ButtonType("Concluir");
        ButtonType buttonTypeGuardarProgreso = new ButtonType("Guardar progreso");

        mensajeConcluir.getButtonTypes().setAll(buttonTypeConcluir, buttonTypeGuardarProgreso);

        Optional<ButtonType> opcionElegida = mensajeConcluir.showAndWait();
        
        if (opcionElegida.get() == buttonTypeConcluir) {
            ejecutarOpcionElegida("Concluir");            
        }
        if (opcionElegida.get() == buttonTypeGuardarProgreso) {
            ejecutarOpcionElegida("Guardar progreso");
        }
        
    }
    
    /**
     * Envia al AdministradorElaboracionPlanTrabajoAcademia o AdministradorEdicionPlanTrabajoAcademia
     * la opción que eligió el Académico para guardar el PlanTrabajoAcademia
     * 
     * @param opcion "Concluir": Estado CONCLUIDO o "Guardar progreso": Estado EN_EDICION
     */
    public void ejecutarOpcionElegida(String opcion) {
        if (AdministradorElaboracionPlanTrabajoAcademia.existeInstancia()) {
            administradorElaboracion = AdministradorElaboracionPlanTrabajoAcademia.obtenerInstancia();
            administradorElaboracion.guardarPlan(opcion);            
        }
        else if (AdministradorEdicionPlanTrabajoAcademia.existeInstancia()) {
            administradorEdicion = AdministradorEdicionPlanTrabajoAcademia.obtenerInstancia();
            administradorEdicion.guardarPlan(opcion);
        }               
    }
    
    /**
     * Muestra el mensaje de éxito de guardado al Académico dependiendo de la opción (Concluir o Guardar progreso)
     * elegida para guardar el PlanTrabajoAcademia
     * 
     * @param opcion Opción elegida por el Académico para guardar el PlanTrabajoAcademia
     */
    public void mostrarMensajeExitoGuardado(String opcion) {
        Alert mensajeExitoGuardado = new Alert(AlertType.INFORMATION);
        mensajeExitoGuardado.setTitle("Plan de Trabajo de Academia guardado");
        mensajeExitoGuardado.setHeaderText(null);
        if (opcion.equals("Concluir")) {
            mensajeExitoGuardado.setContentText("Plan de Trabajo de Academia concluido");            
        }
        else if (opcion.equals("Guardar progreso")) {
            mensajeExitoGuardado.setContentText("Éxito al guardar progreso del Plan de Trabajo de Academia");
        }
        mensajeExitoGuardado.showAndWait();
    }
    
    /**
     * Muestra un mensaje al ocurrir algún error al intentar guardar el PlanTrabajoAcademia
     */
    public void mostrarMensajeFalloGuardado() {
        Alert mensajeFalloGuardado = new Alert(AlertType.ERROR);
        mensajeFalloGuardado.setTitle("Error al guardar el Plan de Trabajo de Academia");
        mensajeFalloGuardado.setHeaderText("Error al guardar el Plan de Trabajo de Academia, intente de nuevo");
        
        ButtonType buttonTypeIntentar = new ButtonType("Intentar de nuevo");
        
        mensajeFalloGuardado.getButtonTypes().setAll(buttonTypeIntentar);
        
        Optional<ButtonType> opcionElegida = mensajeFalloGuardado.showAndWait();
        
        if (opcionElegida.get() == buttonTypeIntentar) {
            guardarPlan();            
        }        
    }

    /**
     * Obtiene el AdaptadorTablaAccionDeMeta seleccionada por el Académico
     * 
     * @return Objeto AdaptadorTablaAccionDeMeta con los datos de una AccionDeMeta
     */
    public AdaptadorTablaAccionDeMeta obtenerAccionSeleccionada() {
        AdaptadorTablaAccionDeMeta accionSeleccionada = null;
        if (tableViewAccionesMeta != null) {
            List<AdaptadorTablaAccionDeMeta> tabla = tableViewAccionesMeta.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                accionSeleccionada = tabla.get(0);
            }
        }
        return accionSeleccionada;
    }

    /**
     * Muestra los datos de un AdaptadorTablaAccionDeMeta seleccionada por el Académico para
     * ser editados
     */
    private void mostrarDatosDeAccionParaEditar() {
        AdaptadorTablaAccionDeMeta accion = obtenerAccionSeleccionada();
        posicionSeleccionada = accionesIngresadas.indexOf(accion);
        if (accion != null) {
            textFieldAccion.setText(accion.getDescripcionAccion());
            textFieldFechaSemana.setText(accion.getFechaSemana());
            textFieldFormaOperar.setText(accion.getFormaOperar());
            buttonAgregarAccion.setDisable(true);
            buttonActualizarAccion.setDisable(false);
            buttonEliminarAccion.setDisable(false);
        }
    }
    
    /**
     * Agrega un acción a la tabla de Acciones para un Meta de un Objetivo Particular
     */
    public void agregarAccion() {
        if (validarEntradaAccion()) {
            AdaptadorTablaAccionDeMeta accionDeMeta = new AdaptadorTablaAccionDeMeta();
            accionDeMeta.setDescripcionAccion(textFieldAccion.getText());
            accionDeMeta.setFechaSemana(textFieldFechaSemana.getText());
            accionDeMeta.setFormaOperar(textFieldFormaOperar.getText());
            accionesIngresadas.add(accionDeMeta);
            restaurarElementosIUAcciones();
        }
        else {
            mostrarMensajeDatosInvalidos();
        }
    }
    
    /**
     * Actualiza los datos de una Accion de la tabla acciones de una Meta de un Objetivo Particular
     */
    public void actualizarAccion() {
        if (validarEntradaAccion()) {
            AdaptadorTablaAccionDeMeta accionDeMeta = new AdaptadorTablaAccionDeMeta();
            accionDeMeta.setDescripcionAccion(textFieldAccion.getText());
            accionDeMeta.setFechaSemana(textFieldFechaSemana.getText());
            accionDeMeta.setFormaOperar(textFieldFormaOperar.getText());
            accionesIngresadas.set(posicionSeleccionada, accionDeMeta);
            restaurarElementosIUAcciones();
        }
        else {
            mostrarMensajeDatosInvalidos();
        }
    }
    
    /**
     * Valida que los datos ingresados para una Acción cumplan
     * con las restricciones para esta (No Nulos, espacios en blanco al inicio, longitud)
     * 
     * @return true si los datos cumplen con las restricciones, false si no es así
     */
    public boolean validarEntradaAccion() {
        boolean datosValidos = true;
        if (textFieldAccion.getText().length() > 10000 || textFieldAccion.getText().equals("") || String.valueOf(textFieldAccion.getText().charAt(0)).equals(" ")) {
            textFieldAccion.requestFocus();
            datosValidos = false;            
        }
        if (textFieldFechaSemana.getText().length() > 100 || textFieldFechaSemana.getText().equals("") || String.valueOf(textFieldFechaSemana.getText().charAt(0)).equals(" ")) {
            textFieldFechaSemana.requestFocus();
            datosValidos = false;
        }
        if (textFieldFormaOperar.getText().length() > 10000 || textFieldFormaOperar.getText().equals("") || String.valueOf(textFieldFormaOperar.getText().charAt(0)).equals(" ")) {
            textFieldFormaOperar.requestFocus();
            datosValidos = false;
        }
        return datosValidos;
    }
    
    /**
     * Elimina la Acción seleccionada por el Académico
     */
    public void eliminarAccion() {
        accionesIngresadas.remove(posicionSeleccionada);
        restaurarElementosIUAcciones();
    }
    
    /**
     * Devuelve a los valores por default los elementos de la IU para ingresar una Acción
     */
    public void restaurarElementosIUAcciones() {
        textFieldAccion.setText("");
        textFieldFechaSemana.setText("");
        textFieldFormaOperar.setText("");
        buttonAgregarAccion.setDisable(false);
        buttonAgregarAccion.requestFocus();
        buttonActualizarAccion.setDisable(true);
        buttonEliminarAccion.setDisable(true);
    }

    /**
     * Obtiene el AdaptadorTablaExamenParcial seleccionado por el Académico
     * 
     * @return Objeto AdaptadorTablaExamenParcial con el Número de Parcial y su respectivo tema
     */
    public AdaptadorTablaExamenParcial obtenerTemaSeleccionado() {
        AdaptadorTablaExamenParcial temaSeleccionado = null;
        if (tableViewExamenesParciales != null) {
            List<AdaptadorTablaExamenParcial> tabla = tableViewExamenesParciales.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                temaSeleccionado = tabla.get(0);
            }
        }
        return temaSeleccionado;
    }

    /**
     * Muestra los datos del AdaptadorTablaExamenParcial seleccionado por el Académico 
     * para ser editados
     */
    private void mostrarDatosDeTemaParaEditar() {
        AdaptadorTablaExamenParcial tema = obtenerTemaSeleccionado();
        posicionSeleccionada = temasDeExamenesIngresados.indexOf(tema);
        if (tema != null) {
            textFieldNumeroParcial.setText(Integer.toString(tema.getNumeroParcial()));
            textFieldTemaDeParcial.setText(tema.getTemaDeParcial());
            buttonAgregarTema.setDisable(true);
            buttonActualizarTema.setDisable(false);
            buttonEliminarTema.setDisable(false);
        }
    }
    
    /**
     * Agrega un tema para un ExamenParcial de una Experiencia Educativa
     */
    public void agregarTema() {
        try {
            if (validarEntradaTema()) {
                AdaptadorTablaExamenParcial temaDeParcial = new AdaptadorTablaExamenParcial();
                temaDeParcial.setNumeroParcial(Integer.parseInt(textFieldNumeroParcial.getText()));
                temaDeParcial.setTemaDeParcial(textFieldTemaDeParcial.getText());
                temasDeExamenesIngresados.add(temaDeParcial);
                restaurarElementosIUTemasExamenes(); 
            }
            else {
                mostrarMensajeDatosInvalidos();
            }  
        }
        catch (NumberFormatException excepcion) {
            mostrarMensajeDatosInvalidos();
            textFieldNumeroParcial.requestFocus();
        }
    }
    
    /**
     * Actualiza los datos de un tema seleccionado para un ExamenParcial de una Experiencia Educativa
     */
    public void actualizarTema() {
        try {
            if (validarEntradaTema()) {
                AdaptadorTablaExamenParcial temaDeParcial = new AdaptadorTablaExamenParcial();
                temaDeParcial.setNumeroParcial(Integer.parseInt(textFieldNumeroParcial.getText()));
                temaDeParcial.setTemaDeParcial(textFieldTemaDeParcial.getText());
                temasDeExamenesIngresados.set(posicionSeleccionada, temaDeParcial);
                restaurarElementosIUTemasExamenes();
            }
            else {
                mostrarMensajeDatosInvalidos();
            }            
        }
        catch (NumberFormatException excepcion) {
            mostrarMensajeDatosInvalidos();
            textFieldNumeroParcial.requestFocus();
        }
        
    }
    
    /**
     * Valida que los datos ingresados para un tema de un ExamenParcial cumplan con las
     * restricciones para este
     * 
     * @return true si los datos cumplen con las restricciones, false si no es así
     * @throws NumberFormatException Lanzada cuando el Académico intenta ingresar texto en el campo del número de parcial
     */
    public boolean validarEntradaTema() throws NumberFormatException{
        boolean datosValidos = true;
        int numeroParcialIngresado = Integer.parseInt(textFieldNumeroParcial.getText());
        if (numeroParcialIngresado > 2 || numeroParcialIngresado < 1) {
            textFieldNumeroParcial.requestFocus();
            datosValidos = false;            
        }
        if (textFieldTemaDeParcial.getText() != null) {
            if (textFieldTemaDeParcial.getText().length() > 100 || textFieldTemaDeParcial.getText().equals("") || String.valueOf(textFieldTemaDeParcial.getText().charAt(0)).equals(" ")) {
                textFieldTemaDeParcial.requestFocus();
                datosValidos = false;
            } 
        }
        else {
            textFieldTemaDeParcial.requestFocus();
            datosValidos = false;
        }
        
        return datosValidos;
    }
    
    /**
     * Elimina el tema seleccionado por el Académico para un ExamenParcial
     */
    public void eliminarTema() {
        temasDeExamenesIngresados.remove(posicionSeleccionada);
        restaurarElementosIUTemasExamenes();
    }
    
    /**
     * Restaura a sus valores por default a los elementos de la IU para ingresar un tema de ExamenParcial
     */
    public void restaurarElementosIUTemasExamenes() {
        textFieldNumeroParcial.setText("");
        textFieldTemaDeParcial.setText("");
        buttonAgregarTema.setDisable(false);
        buttonAgregarTema.requestFocus();
        buttonActualizarTema.setDisable(true);
        buttonEliminarTema.setDisable(true);
    }

    /**
     * Obtiene el AdaptadorTablaFormaEvaluacion seleccionado por el Académico
     * 
     * @return Objeto de tipo AdaptadorTablaFormaEvaluacion con los datos de una FormaDeEvaluacion
     */
    public AdaptadorTablaFormaEvaluacion obtenerFormaEvaluacionSeleccionada() {
        AdaptadorTablaFormaEvaluacion formaEvaluacionSeleccionada = null;
        if (tableViewFormasEvaluacion != null) {
            List<AdaptadorTablaFormaEvaluacion> tabla = tableViewFormasEvaluacion.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                formaEvaluacionSeleccionada = tabla.get(0);
                return formaEvaluacionSeleccionada;
            }
        }
        return formaEvaluacionSeleccionada;
    }

    /**
     * Muestra los datos del AdaptadorTablaFormaEvaluacion seleccionado por el Académico
     * para ser editados
     */
    private void mostrarDatosDeFormaEvaluacionParaEditar() {
        AdaptadorTablaFormaEvaluacion formaEvaluacion = obtenerFormaEvaluacionSeleccionada();
        posicionSeleccionada = formasEvaluacionIngresados.indexOf(formaEvaluacion);
        if (formaEvaluacion != null) {
            textFieldElemento.setText(formaEvaluacion.getElemento());
            textFieldPorcentajeElemento.setText(Float.toString(formaEvaluacion.getPorcentaje()));
            buttonAgregarFormaEvaluacion.setDisable(true);
            buttonActualizarFormaEvaluacion.setDisable(false);
            buttonEliminarFormaEvaluacion.setDisable(false);
        }
    }
    
    /**
     * Agrega una nueva Forma de Evaluación a la tabla de Formas de Evaluación para una Experiencia Educativa
     */
    public void agregarFormaEvaluacion() {
        try {
            if (validarEntradaFormaEvaluacion()) {
                AdaptadorTablaFormaEvaluacion formaEvaluacion = new AdaptadorTablaFormaEvaluacion();
                formaEvaluacion.setElemento(textFieldElemento.getText());
                formaEvaluacion.setPorcentaje(Float.parseFloat(textFieldPorcentajeElemento.getText()));
                formasEvaluacionIngresados.add(formaEvaluacion);
                restaurarElementosIUFormasEvaluacion();
            }
            else {
                mostrarMensajeDatosInvalidos();
            }   
        }
        catch (NumberFormatException excepcion) {
            mostrarMensajeDatosInvalidos();
            textFieldPorcentajeElemento.requestFocus();
        }
    }
    
    /**
     * Actualiza los datos de una Forma de Evaluación seleccionada por el Académico
     */
    public void actualizarFormaEvaluacion() {
        try {
            if (validarEntradaFormaEvaluacion()) {
                AdaptadorTablaFormaEvaluacion formaEvaluacion = new AdaptadorTablaFormaEvaluacion();
                formaEvaluacion.setElemento(textFieldElemento.getText());
                formaEvaluacion.setPorcentaje(Float.parseFloat(textFieldPorcentajeElemento.getText()));
                formasEvaluacionIngresados.set(posicionSeleccionada, formaEvaluacion);
                restaurarElementosIUFormasEvaluacion();
            }
            else {
                mostrarMensajeDatosInvalidos();
            } 
        }
        catch (NumberFormatException excepcion) {
            mostrarMensajeDatosInvalidos();
            textFieldPorcentajeElemento.requestFocus();
        }
    }
    
    /**
     * Valida que los datos de la Forma de Evaluación ingresada cumpla con las 
     * restricciones para esta
     * 
     * @return true si los datos cumplen con las restricciones, false si no es así
     * @throws NumberFormatException Lanzada cuando el Académico ingresa algún caracter en el campo del porcentaje del elemento
     */    
    public boolean validarEntradaFormaEvaluacion() throws NumberFormatException{
        boolean datosValidos = true;
        float porcentajeIngresado = Float.parseFloat(textFieldPorcentajeElemento.getText());
        if (textFieldElemento.getText() != null) {
            if (textFieldElemento.getText().length() > 100 || textFieldElemento.getText().equals("") || String.valueOf(textFieldElemento.getText().charAt(0)).equals(" ")) {
                textFieldElemento.requestFocus();
                datosValidos = false;            
            }
        }
        else {
            textFieldElemento.requestFocus();
            datosValidos = false;
        }
        if (porcentajeIngresado > 1 || porcentajeIngresado < 0) {
            textFieldPorcentajeElemento.requestFocus();
            datosValidos = false;
        }
        return datosValidos;
    }
    
    /**
     * Elimina la Forma de Evaluación seleccionada por el Académico
     */
    public void eliminarFormaEvaluacion() {
        formasEvaluacionIngresados.remove(posicionSeleccionada);
        restaurarElementosIUFormasEvaluacion();
    }
    
    /**
     * Restaura a sus valores por default a los elementos de la IU para ingresar una 
     * Forma de Evaluación
     */
    public void restaurarElementosIUFormasEvaluacion() {
        textFieldElemento.setText("");
        textFieldPorcentajeElemento.setText("");
        buttonAgregarFormaEvaluacion.setDisable(false);
        buttonAgregarFormaEvaluacion.requestFocus();
        buttonActualizarFormaEvaluacion.setDisable(true);
        buttonEliminarFormaEvaluacion.setDisable(true);
    }
    
    /**
     * Despliega la IU VSGPAPrincipal si el Académico elige cancelar la elaboración o edición del PlanTrabajoAcademia.
     * Si el Académico no desea cancelar, el sistema muestra un mensaje para permitir que continúe trabajando
     */
    public void regresarAPantallaPrincipal() {
        Alert mensajeRegresar = new Alert(AlertType.CONFIRMATION);
        mensajeRegresar.setTitle("Regresar");
        mensajeRegresar.setHeaderText("¿Desea cancelar la edición del Plan de Trabajo de Academia?");

        ButtonType buttonTypeSi = new ButtonType("Sí");
        ButtonType buttonTypeNo = new ButtonType("No");

        mensajeRegresar.getButtonTypes().setAll(buttonTypeSi, buttonTypeNo);

        Optional<ButtonType> opcionElegida = mensajeRegresar.showAndWait();
        
        if (opcionElegida.get() == buttonTypeSi) {
            Stage escenarioActual = (Stage) textFieldNombreAcademia.getScene().getWindow();
            escenarioActual.close();
            if (AdministradorElaboracionPlanTrabajoAcademia.existeInstancia()) {
                administradorElaboracion = AdministradorElaboracionPlanTrabajoAcademia.obtenerInstancia();
                administradorElaboracion.regresarAPrincipal();            
            }
            else if (AdministradorEdicionPlanTrabajoAcademia.existeInstancia()) {
                administradorEdicion = AdministradorEdicionPlanTrabajoAcademia.obtenerInstancia();
                administradorEdicion.regresarAPrincipal();
            }
        }
        if (opcionElegida.get() == buttonTypeNo) {
            mostrarMensajeDeSistema("Continue trabajando");
        }                 
    }
    
    /**
     * Despliega un mensaje al Académico que inició sesión en el sistema
     * 
     * @param mensaje Mensaje a mostrar al Académico
     */
    public void mostrarMensajeDeSistema(String mensaje) {
        Alert mensajeDeSistema = new Alert(AlertType.INFORMATION);
        mensajeDeSistema.setTitle("SGPA - Mensaje del sistema");
        mensajeDeSistema.setHeaderText(null);
        mensajeDeSistema.setContentText(mensaje);
        mensajeDeSistema.showAndWait();
    }
    
}
