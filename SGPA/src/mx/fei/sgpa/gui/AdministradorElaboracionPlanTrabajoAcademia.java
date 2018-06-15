/** ************************************************************* */
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   08/06/2018				  */
/* Ultima modificación: 12/06/2018				  */
/* Descripción: Controlador del CU Elaborar Plan de Trabajo de    */
/*              Academia.                           		  */
/** ************************************************************* */
package mx.fei.sgpa.gui;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.fei.sgpa.dao.academia.AcademiaDAO;
import mx.fei.sgpa.dao.plantrabajoacademia.PlanTrabajoAcademiaDAO;
import mx.fei.sgpa.domain.Academia;
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
 * Capa lógica encargada de administrar la elaboración de un PlanTrabajoAcademia
 */
public class AdministradorElaboracionPlanTrabajoAcademia {

    private static AdministradorElaboracionPlanTrabajoAcademia administradorElaboracion;
    private VPlanTrabajoAcademiaController planAcademiaControlador;
    private static final Logger loggerDelSistema = LogManager.getLogger(AdministradorElaboracionPlanTrabajoAcademia.class);

    private AcademiaDAO academiaDAO;
    private PlanTrabajoAcademiaDAO planAcademiaDAO;
    private String nombreAcademia;
    private String nombreCoordinador;
    private DetallesPlanTrabajoAcademia detallesPlan;
    private ArrayList<Academico> integrantesAcademia;
    private ArrayList<ObjetivoParticular> objetivosParticulares;
    private ArrayList<ExperienciaEducativaConParciales> examenesParciales;
    private ArrayList<FormaDeEvaluacion> formasDeEvaluacion;

    /**
     * Obtiene una instancia para utilizar los métodos del AdministradorElaboracionPlanTrabajoAcademia.
     * Si ya existe la regresa, sino, crea una nueva instancia
     * @return Objeto de tipo AdministradorElaboracionPlanTrabajoAcademia
     */
    public static AdministradorElaboracionPlanTrabajoAcademia obtenerInstancia() {
        if (administradorElaboracion == null) {
            administradorElaboracion = new AdministradorElaboracionPlanTrabajoAcademia();
        }
        return administradorElaboracion;
    }
    
    /**
     * Verifica si existe una instancia del AdministradorElaboracionPlanTrabajoAcademia.
     * @return true si existe la instancia, false si no existe
     */ 
    public static boolean existeInstancia() {
        boolean existe = false;
        if (administradorElaboracion != null) {
            existe = true;
        }        
        return existe;
    }
    
    /**
     * Elimina la instancia del AdministradorElaboracionPlanTrabajoAcademia cuando
     * esta ya no se vaya a utilizar
     */
    public static void finalizarCasoUso() {
        administradorElaboracion = null;
    }

    /**
     * Recupera las Academias bajo cargo de un Académico específico y las envía al
     * controlador JavaFX de la IU VSGPAPrincipal para que las muestre
     * 
     * @param numeroDePersonal Número de personal de un Academico COORDINADOR
     */
    public void obtenerAcademiasBajoCargo(int numeroDePersonal) {
        academiaDAO = new AcademiaDAO();
        ArrayList<Academia> academiasDeCoordinador = academiaDAO.obtenerAcademias(numeroDePersonal);
        
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VSGPAPrincipal.fxml"));
        try {
            Parent padre = cargadorFXML.load();
            VSGPAPrincipalController principal = cargadorFXML.getController();
            if (academiasDeCoordinador.isEmpty()) {
                principal.mostrarMensajeDeSistema("No tiene academias asignadas para generar el Plan de Trabajo de Academia\"");
                regresarAPrincipal();
            }
            else {
                principal.desplegarListaAcademias(academiasDeCoordinador);
            }
        } 
        catch (IllegalStateException excepcionEstadoIlegal) {
            loggerDelSistema.fatal("No se pudo cargar VSGPAPrincipal.fxml");
        }
        catch (IOException excepcionEntradaSalida) {
            loggerDelSistema.fatal("Ocurrió un error I/O en método obtenerAcademiasBajoCargo");
        }
    }

    /**
     * Obtiene los datos de una Academia y muestra la IU VPlanTrabajoAcademia
     * 
     * @param idAcademia Identificador de la Academia a recuperar sus datos
     */
    public void crearPlanAcademia(String idAcademia) {
        Academia academia = academiaDAO.obtenerAcademia(idAcademia);
        nombreAcademia = academia.getNombreAcademia();
        nombreCoordinador = academia.obtenerNombreDeCoordinador();
        integrantesAcademia = academiaDAO.obtenerIntegrantesAcademia(idAcademia);

        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("VPlanTrabajoAcademia.fxml"));
        try {
            Parent padre = cargadorFXML.load();
            VPlanTrabajoAcademiaController planAcademiaIU = cargadorFXML.getController();
            planAcademiaIU.establecerDatosDeAcademia(nombreAcademia, nombreCoordinador, integrantesAcademia);

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
            loggerDelSistema.fatal("Ocurrió un error I/O en método crearPlanAcademia");
        }
    }

    /**
     * Establece los atributos detallesPlan, objetivosParticulares, examenesParciales y formasDeEvaluacion
     * dentro del AdministradorElaboracionPlanTrabajoAcademia y consecutivamente los valida con el método 
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
     * Invoca al método mostrarMensajeConcluir del controlador JavaFX de la IU VPlanTrabajoAcademia
     */
    public void lanzarMensajeConcluir() {
        comunicaciorConIUPlanTrabajoAcademia();
        if (planAcademiaControlador != null) {
            planAcademiaControlador.mostrarMensajeConcluir();
        }
    }

    /**
     * Almacena los atributos detallesPlan, objetivosParticulares, examenesParciales
     * y formasDeEvaluacion del AdministradorElaboracionPlanTrabajoAcademia en la Base 
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
            if (planAcademiaDAO.guardarPlanTrabajoAcademia(planAcademia)) {
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
