/** ************************************************************* */
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   09/06/2018				  */
/* Ultima modificación: 09/06/2018				  */
/* Descripción: Controlador del CU Editar Plan de Trabajo de      */
/*              Academia.                           		  */
/** ************************************************************* */

package mx.fei.sgpa.gui;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.fei.sgpa.dao.plantrabajoacademia.PlanTrabajoAcademiaDAO;
import mx.fei.sgpa.domain.Academico;
import mx.fei.sgpa.domain.EstadoDeDocumento;
import mx.fei.sgpa.domain.plantrabajoacademia.AccionDeMeta;
import mx.fei.sgpa.domain.plantrabajoacademia.DetallesPlanTrabajoAcademia;
import mx.fei.sgpa.domain.plantrabajoacademia.ExamenParcial;
import mx.fei.sgpa.domain.plantrabajoacademia.ExperienciaEducativaConParciales;
import mx.fei.sgpa.domain.plantrabajoacademia.FormaDeEvaluacion;
import mx.fei.sgpa.domain.plantrabajoacademia.MetaDeObjetivo;
import mx.fei.sgpa.domain.plantrabajoacademia.ObjetivoParticular;
import mx.fei.sgpa.domain.plantrabajoacademia.PlanTrabajoAcademia;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Capa lógica encargada de administrar la edición de un PlanTrabajoAcademia
 */
public class AdministradorEdicionPlanTrabajoAcademia {

    private static AdministradorEdicionPlanTrabajoAcademia administradorEdicion;
    private VPlanTrabajoAcademiaController planAcademiaControlador;
    private static final Logger loggerDelSistema = LogManager.getLogger(AdministradorEdicionPlanTrabajoAcademia.class);

    private PlanTrabajoAcademiaDAO planAcademiaDAO;
    private PlanTrabajoAcademia plan;
    private ArrayList<PlanTrabajoAcademia> planesEnEdicion;
    private DetallesPlanTrabajoAcademia detallesPlan;
    private ArrayList<Academico> integrantesAcademia;
    private ArrayList<ObjetivoParticular> objetivosParticulares;
    private ArrayList<ExperienciaEducativaConParciales> examenesParciales;
    private ArrayList<FormaDeEvaluacion> formasDeEvaluacion;

    /**
     * Obtiene una instancia para utilizar los métodos del AdministradorEdicionPlanTrabajoAcademia.
     * Si ya existe la regresa, sino crea una nueva instancia
     * @return Objeto de tipo AdministradorEdicionPlanTrabajoAcademia
     */
    public static AdministradorEdicionPlanTrabajoAcademia obtenerInstancia() {
        if (administradorEdicion == null) {
            administradorEdicion = new AdministradorEdicionPlanTrabajoAcademia();
        }
        return administradorEdicion;
    }
    
    /**
     * Verifica si existe una instancia del AdministradorEdicionPlanTrabajoAcademia.
     * @return true si existe la instancia, false si no existe
     */    
    public static boolean existeInstancia() {
        boolean existe = false;
        if (administradorEdicion != null) {
            existe = true;
        }        
        return existe;
    }
    
    /**
     * Elimina la instancia del AdministradorEdicionPlanTrabajoAcademia cuando
     * esta ya no se vaya a utilizar
     */
    public static void finalizarCasoUso() {
        administradorEdicion = null;
    }
    
    /**
     * Obtiene los PlanTrabajoAcademia con estado EN_EDICION elaborados por un Coordinador
     * y los envia al controlador JavaFX de la IU VSGPAPrincipal para que los muestre
     * 
     * @param numeroDePersonal Número de Personal de un Académico COORDINADOR
     */
    public void solicitarPlanesEnEdicion(int numeroDePersonal) {
        planAcademiaDAO = new PlanTrabajoAcademiaDAO();
        planesEnEdicion = planAcademiaDAO.obtenerPlanesEnEdicion(numeroDePersonal);
        
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VSGPAPrincipal.fxml"));
        try {
            Parent padre = cargadorFXML.load();
            VSGPAPrincipalController principal = cargadorFXML.getController();
            if (planesEnEdicion.isEmpty()) {
                principal.mostrarMensajeDeSistema("No tiene Planes de Trabajo de Academia que pueda editar");
                regresarAPrincipal();
            }
            else {
                principal.desplegarListaPlanesEnEdicion(planesEnEdicion);
            }
        } 
        catch (IllegalStateException excepcionEstadoIlegal) {
            loggerDelSistema.fatal("No se pudo cargar VSGPAPrincipal.fxml");
        }
        catch (IOException excepcionEntradaSalida) {
            loggerDelSistema.fatal("Ocurrió un error I/O en método solicitarPlanesEnEdicion");
        }
    }
    
    /**
     * Muestra la IU VPlanTrabajoAcademia con los datos de un PlanTrabajoAcademia recuperado
     * @param idPlanAcademia Identificador del PlanTrabajoAcademia que se mostrará en pantalla
     */
    public void desplegarFormularioConDatosPlan(String idPlanAcademia) {
        obtenerDetallesPlan(idPlanAcademia);
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VPlanTrabajoAcademia.fxml"));
        try {
            Parent padre = cargadorFXML.load();
            VPlanTrabajoAcademiaController planAcademiaIU = cargadorFXML.getController();
            planAcademiaIU.establecerDatosRecuperados(plan);

            Stage escenario = new Stage();
            escenario.setScene(new Scene(padre));
            escenario.setTitle("Plan de Trabajo de Academia");
            escenario.setResizable(false);
            escenario.show();
        } 
        catch (IllegalStateException excepcionEstadoIlegal) {
            loggerDelSistema.fatal("No se pudo cargar VPlanTrabajoAcademia.fxml");
        }
        catch (IOException excepcionEntradaSalida) {
            loggerDelSistema.fatal("Ocurrió un error I/O en método desplegarFormularioConDatosPlan");
        }                
    }
    
    /**
     * Obtiene los datos de un PlanTrabajoAcademia existentes en el AdministradorEdicionPlanTrabajoAcademia
     * @param idPlanAcademia Identificador del PlanTrabajoAcademia del cual se recuperarán los datos
     */
    public void obtenerDetallesPlan(String idPlanAcademia) {
        for (int a = 0; a < planesEnEdicion.size(); a++) {
            if (planesEnEdicion.get(a).getId().equals(idPlanAcademia)) {
                plan = planesEnEdicion.get(a);
            }                        
        }
    }
    
    /**
     * Establece los atributos detallesPlan, objetivosParticulares, examenesParciales y formasDeEvaluacion
     * dentro del AdministradorEdicionPlanTrabajoAcademia y consecutivamente los valida con el método 
     * validarDatosIngresados
     * 
     * @param detallesPlan Detalles generales del PlanTrabajoAcademia ingresado por el usuario
     * @param objetivosParticulares Objetivos particulares con Metas y Acciones ingresados por el usuario
     * @param examenesParciales Temas de Examenes Parciales de Experiencias Educativas
     * @param formasDeEvaluacion Formas de Evaluación para las Experiencias Educativas
     */
    public void guardarPlan(DetallesPlanTrabajoAcademia detallesPlan, ArrayList<ObjetivoParticular> objetivosParticulares, ArrayList<ExperienciaEducativaConParciales> examenesParciales, ArrayList<FormaDeEvaluacion> formasDeEvaluacion) {
        this.detallesPlan = detallesPlan;
        this.objetivosParticulares = objetivosParticulares;
        this.examenesParciales = examenesParciales;
        this.formasDeEvaluacion = formasDeEvaluacion;

        if (validarDatosIngresados()) {
            lanzarMensajeConcluir();            
        } 
        else {
            lanzarMensajeDatosInvalidos();            
        }
    }

    /**
     * Valida que las longitudes de todos los datos ingresados por el usuario
     * sean aptos para almacenarlos en la Base de Datos
     * 
     * @return true si todos los datos cumplen con las restricciones, false si no es así
     */
    public boolean validarDatosIngresados() {
        boolean datosValidos = true;
        if (detallesPlan.getProgramaEducativo().length() > 100) {
            datosValidos = false;
        }
        if (detallesPlan.getPeriodoEscolar().length() > 50) {
            datosValidos = false;
        }
        if (detallesPlan.getObjetivoGeneral().length() > 500) {
            datosValidos = false;
        }
        for (ObjetivoParticular objetivoIngresado : objetivosParticulares) {
            if (objetivoIngresado.getDescripcion().length() > 500) {
                datosValidos = false;
                break;
            }
            ArrayList<MetaDeObjetivo> metasDeObjetivo = objetivoIngresado.getMetasDeObjetivo();
            for (MetaDeObjetivo metaIngresada : metasDeObjetivo) {
                if (metaIngresada.getDescripcion().length() > 500) {
                    datosValidos = false;
                    break;
                }
                ArrayList<AccionDeMeta> accionesDeMeta = metaIngresada.getAccionesDeMeta();
                for (AccionDeMeta accionIngresada : accionesDeMeta) {
                    if (accionIngresada.getDescripcionAccion().length() > 10000) {
                        datosValidos = false;
                        break;
                    }
                    if (accionIngresada.getFechaSemana().length() > 100) {
                        datosValidos = false;
                        break;
                    }
                    if (accionIngresada.getFormaOperar().length() > 10000) {
                        datosValidos = false;
                        break;
                    }
                }
            }
        }
        for (ExperienciaEducativaConParciales experienciaConExamen : examenesParciales) {
            if (experienciaConExamen.getExperienciaEducativa().length() > 100) {
                datosValidos = false;
                break;
            }
            ArrayList<ExamenParcial> examenesDeExperiencia = experienciaConExamen.getExamenesParciales();
            for (ExamenParcial examenIngresado : examenesDeExperiencia) {
                if (examenIngresado.getId() > 2 || examenIngresado.getId() < 0) {
                    datosValidos = false;
                    break;
                }
                ArrayList<String> temasIngresados = examenIngresado.getTemasDeParcial();
                for(String temaIngresado : temasIngresados) {
                    if (temaIngresado.length() > 100) {
                        datosValidos = false;
                        break;
                    }                    
                }
            }
        }
        for (FormaDeEvaluacion formaEvaluacionIngresada : formasDeEvaluacion) {
            if (formaEvaluacionIngresada.getElemento().length() > 100) {
                datosValidos = false;
                break;
            }
            if (formaEvaluacionIngresada.getPorcentaje() > 1 || formaEvaluacionIngresada.getPorcentaje() < 0) {
                datosValidos = false;
                break;
            }
        }
        return datosValidos;
    }

    /**
     * Invoca al método mostrarMensajeDatosInvalidos del controlador JavaFX de la IU VPlanTrabajoAcademia
     */
    public void lanzarMensajeDatosInvalidos() {
        comunicaciorConIUPlanTrabajoAcademia();
        if (planAcademiaControlador != null) {
            planAcademiaControlador.mostrarMensajeDatosInvalidos();
        }
    }
    
    /**
     * Invoca el método mostrarMensajeConcluir del controlador JavaFX de la IU VPlanTrabajoAcademia
     */
    public void lanzarMensajeConcluir() {
        comunicaciorConIUPlanTrabajoAcademia();
        if (planAcademiaControlador != null) {
            planAcademiaControlador.mostrarMensajeConcluir();
        }
    }

    /**
     * Almacena los atributos detallesPlan, objetivosParticulares, examenesParciales
     * y formasDeEvaluacion del AdministradorEdicionPlanTrabajoAcademia en la Base 
     * de datos mediante PlanTrabajoAcademiaDAO
     * 
     * @param opcion "Concluido": Almacenará el PlanTrabajoAcademia asignándole el estado CONCLUIDO. "Guardar progreso": Almacenará el PlanTrabajoAcademia asignándole el estado EN_EDICION 
     */
    public void guardarPlan(String opcion) {
        planAcademiaDAO = new PlanTrabajoAcademiaDAO();
        
        PlanTrabajoAcademia planAcademia = new PlanTrabajoAcademia();
        planAcademia.setId(detallesPlan.getNombreAcademia().substring(0, 3) + "-" + detallesPlan.getNombreCoordinador().substring(0, 3) + "-" + detallesPlan.getProgramaEducativo().substring(0, 3) + detallesPlan.getPeriodoEscolar());
        planAcademia.setFechaAprobacion(detallesPlan.getFechaAprobacion());
        planAcademia.setProgramaEducativo(detallesPlan.getProgramaEducativo());
        planAcademia.setPeriodoEscolar(detallesPlan.getPeriodoEscolar());
        planAcademia.setNombreAcademia(detallesPlan.getNombreAcademia());
        planAcademia.setNombreCoordinador(detallesPlan.getNombreCoordinador());
        planAcademia.setObjetivoGeneral(detallesPlan.getObjetivoGeneral());
        planAcademia.setObjetivosParticulares(objetivosParticulares);
        planAcademia.setExamenesParciales(examenesParciales);
        planAcademia.setFormasDeEvaluacion(formasDeEvaluacion);        
        
        if (opcion.equals("Concluir")) {
            planAcademia.setEstado(EstadoDeDocumento.CONCLUIDO);
        }
        else if (opcion.equals("Guardar progreso")) {
            planAcademia.setEstado(EstadoDeDocumento.EN_EDICION);
        }
        
        comunicaciorConIUPlanTrabajoAcademia();
        
        if (planAcademiaControlador != null) {
            if (planAcademiaDAO.actualizarDatosPlan(planAcademia)) {
                planAcademiaControlador.mostrarMensajeExitoGuardado(opcion);
                regresarAPrincipal();
            }
            else {
                planAcademiaControlador.mostrarMensajeFalloGuardado();            
            }  
        }
    }
    
    /**
     * Despliega la IU VSGPAPrincipal al finalizar o cancelar la edición del 
     * PlanTrabajoAcademia
     */
    public void regresarAPrincipal() {
        try {
            Parent padre = FXMLLoader.load(getClass().getResource("VSGPAPrincipal.fxml"));
            Scene escena = new Scene(padre);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("SGPA - Principal");
            escenario.setResizable(false);

            finalizarCasoUso();

            escenario.show(); 
        } 
        catch (IllegalStateException excepcionEstadoIlegal) {
            loggerDelSistema.fatal("No se pudo cargar VSGPAPrincipal.fxml");
        }
        catch (IOException excepcionEntradaSalida) {
            loggerDelSistema.fatal("Ocurrió un error I/O en método regresarAPrincipal");
        }        
    }
    
    /**
     * Obtiene comunicación con el controlador de JavaFx de la IU VPlanTrabajoAcademia
     * para utilizar sus métodos
     */
    public void comunicaciorConIUPlanTrabajoAcademia() {
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VPlanTrabajoAcademia.fxml"));
        try {
            Parent padre = cargadorFXML.load();
            planAcademiaControlador = cargadorFXML.getController();
        } 
        catch (IllegalStateException excepcionEstadoIlegal) {
            loggerDelSistema.fatal("No se pudo cargar VPlanTrabajoAcademia.fxml");
        }
        catch (IOException excepcionEntradaSalida) {
            loggerDelSistema.fatal("Ocurrió un error I/O en método desplegarFormularioConDatosPlan");
        } 
    }

}
