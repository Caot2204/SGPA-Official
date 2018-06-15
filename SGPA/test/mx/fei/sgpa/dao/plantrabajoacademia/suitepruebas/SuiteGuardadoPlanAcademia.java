package mx.fei.sgpa.dao.plantrabajoacademia.suitepruebas;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import mx.fei.sgpa.dao.academico.AcademicoDAO;
import mx.fei.sgpa.dao.plantrabajoacademia.PlanTrabajoAcademiaDAO;
import mx.fei.sgpa.domain.Academico;
import mx.fei.sgpa.domain.EstadoDeDocumento;
import mx.fei.sgpa.domain.RolAcademico;
import mx.fei.sgpa.domain.plantrabajoacademia.AccionDeMeta;
import mx.fei.sgpa.domain.plantrabajoacademia.ExperienciaEducativaConParciales;
import mx.fei.sgpa.domain.plantrabajoacademia.ExamenParcial;
import mx.fei.sgpa.domain.plantrabajoacademia.FirmaAutorizacion;
import mx.fei.sgpa.domain.plantrabajoacademia.FormaDeEvaluacion;
import mx.fei.sgpa.domain.plantrabajoacademia.MetaDeObjetivo;
import mx.fei.sgpa.domain.plantrabajoacademia.ObjetivoParticular;
import mx.fei.sgpa.domain.plantrabajoacademia.PlanTrabajoAcademia;
import mx.fei.sgpa.domain.plantrabajoacademia.Revision;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class SuiteGuardadoPlanAcademia {

    private PlanTrabajoAcademiaDAO planAcademiaDAO;
    private final PlanTrabajoAcademia planAcademia;
    private final PlanTrabajoAcademia planAcademiaB;
    private final ArrayList<AccionDeMeta> accionesDeMeta;
    private final ArrayList<MetaDeObjetivo> metasDeObjetivo;
    private final ArrayList<ObjetivoParticular> objetivosParticulares;
    private final ArrayList<ExperienciaEducativaConParciales> eesConParciales;
    private final ArrayList<ExamenParcial> examenesParciales;
    private final ArrayList<FormaDeEvaluacion> formasDeEvaluacion;
    private final ArrayList<Revision> historicoDeRevisiones;
    private final FirmaAutorizacion firmaDeAutorizacion;
    
    
    public SuiteGuardadoPlanAcademia() throws ParseException {
        planAcademiaDAO = new PlanTrabajoAcademiaDAO();
        planAcademia = new PlanTrabajoAcademia();
        planAcademiaB = new PlanTrabajoAcademia();
        metasDeObjetivo = new ArrayList<>();
        objetivosParticulares = new ArrayList<>();
        eesConParciales = new ArrayList<>();
        examenesParciales = new ArrayList<>();
        historicoDeRevisiones = new ArrayList<>();
        firmaDeAutorizacion = new FirmaAutorizacion();
        
        AccionDeMeta accionAMeta1_1 = new AccionDeMeta();
        AccionDeMeta accionBMeta1_1 = new AccionDeMeta();
        AccionDeMeta accionCMeta1_1 = new AccionDeMeta();
        
        accionAMeta1_1.setDescripcionAccion("Proporcionar a la coordinación de la academia las propuestas de actualización de los programas de estudios de las EE de la academia.");
        accionAMeta1_1.setFechaSemana("02/02/2016-29/02/2016");
        accionAMeta1_1.setFormaOperar("Los profesores de una misma EE presentan las propuestas de actualización del programa. La academia las analiza y en su caso avala los cambios propuestos. Los programas modificados se dan a conocer a las autoridades de la facultad");
        
        accionBMeta1_1.setDescripcionAccion("Revisar los cambios propuestos en la junta semestral previa al nuevo principio de semestre.");
        accionBMeta1_1.setFechaSemana("02/02/2016-29/02/2016");
        accionBMeta1_1.setFormaOperar("Los profesores de una misma EE presentan las propuestas de actualización del programa. La academia las analiza y en su caso avala los cambios propuestos. Los programas modificados se dan a conocer a las autoridades de la facultad");
        
        accionCMeta1_1.setDescripcionAccion("Pasar en limpio los cambios aprobados.");
        accionCMeta1_1.setFechaSemana("02/02/2016-29/02/2016");
        accionCMeta1_1.setFormaOperar("Los profesores de una misma EE presentan las propuestas de actualización del programa. La academia las analiza y en su caso avala los cambios propuestos. Los programas modificados se dan a conocer a las autoridades de la facultad");
        
        accionesDeMeta = new ArrayList<>();
        accionesDeMeta.add(accionAMeta1_1);
        accionesDeMeta.add(accionBMeta1_1);
        accionesDeMeta.add(accionCMeta1_1);
        
        MetaDeObjetivo meta = new MetaDeObjetivo();
        
        meta.setId("MTA-1.1");
        meta.setDescripcion("Cuatro programas de estudio revisados y/o actualizados.");
        meta.setAccionesDeMeta(accionesDeMeta);        
        
        metasDeObjetivo.add(meta);
        
        ObjetivoParticular objetivoParticularA = new ObjetivoParticular();
        objetivoParticularA.setId("OBP-1");
        objetivoParticularA.setDescripcion("Mantener actualizados los programas de estudio de las EE de esta academia con base en la experiencia de su aplicación y los problemas detectados.");
        objetivoParticularA.setMetasDeObjetivo(metasDeObjetivo);
        
        objetivosParticulares.add(objetivoParticularA);
        
        ArrayList<String> temasPrimerParcial = new ArrayList<>();
        temasPrimerParcial.add("Introducción a la Ingeniería de Software");
        temasPrimerParcial.add("Procesos de desarrollo de software");
        temasPrimerParcial.add("Principios de análisis");
        temasPrimerParcial.add("Análisis en el Análisis Estructurado Moderno de Yourdon");
        temasPrimerParcial.add("Introducción a la Calidad de Software");
        temasPrimerParcial.add("Métricas de análisis");
        
        ExamenParcial primerParcial = new ExamenParcial(1, temasPrimerParcial);
        
        ArrayList<String> temasSegundoParcial = new ArrayList<>();
        temasSegundoParcial.add("Principios de diseño");
        temasSegundoParcial.add("Diseño en el Análisis Estructurado Moderno de Yourdon");
        temasSegundoParcial.add("Métricas de Diseño");
        temasSegundoParcial.add("Otras Métricas");
        temasSegundoParcial.add("Prueba de software");
        
        ExamenParcial segundoParcial = new ExamenParcial(2, temasSegundoParcial);
        
        examenesParciales.add(primerParcial);
        examenesParciales.add(segundoParcial);
        
        ExperienciaEducativaConParciales ingenieriaI = new ExperienciaEducativaConParciales("Ingeniería de Software I", examenesParciales);
        
        eesConParciales.add(ingenieriaI);
        
        formasDeEvaluacion = new ArrayList<>();
        formasDeEvaluacion.add(new FormaDeEvaluacion("Participación", (float) 0.10));
        formasDeEvaluacion.add(new FormaDeEvaluacion("Prácticas", (float) 0.15));
        formasDeEvaluacion.add(new FormaDeEvaluacion("Trabajos", (float) 0.45));
        formasDeEvaluacion.add(new FormaDeEvaluacion("Exámenes parciales", (float) 0.30));
        formasDeEvaluacion.add(new FormaDeEvaluacion("Reportes de investigación", (float) 0));
        formasDeEvaluacion.add(new FormaDeEvaluacion("Exposiciones", (float) 0));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsed = format.parse("2018-04-27");
        java.sql.Date fechaA = new java.sql.Date(parsed.getTime());
        java.util.Date parsedB = format.parse("2018-04-30");
        java.sql.Date fechaB = new java.sql.Date(parsedB.getTime());
        
        historicoDeRevisiones.add(new Revision(1, fechaA, "Meta-1.1", "Modificacion de la primera accion de la meta"));
        historicoDeRevisiones.add(new Revision(2, fechaB, "Objetivo particular 1", "Modificacion de la descripción"));
        
        java.util.Date parsedDateA = format.parse("2013-01-23");
        java.sql.Date fechaAutorizacion = new java.sql.Date(parsed.getTime());
        java.util.Date parsedDateB = format.parse("2013-02-05");
        java.sql.Date fechaEntradaVigor = new java.sql.Date(parsedB.getTime());
        
        firmaDeAutorizacion.setPersonaQueProponePlan("Mtro. Gerardo Contreras Vega");
        firmaDeAutorizacion.setPersonaQueAutorizaPlan("Mtra. Enriqueta Sarabia Ramírez");
        firmaDeAutorizacion.setFechaAutorizacion(fechaAutorizacion);
        firmaDeAutorizacion.setFechaEntradaEnVigor(fechaEntradaVigor);
        
        planAcademia.setId("PLAT-2");
        planAcademia.setFechaAprobacion(fechaAutorizacion);
        planAcademia.setProgramaEducativo("2002");
        planAcademia.setPeriodoEscolar("Febrero - Julio 2016");
        planAcademia.setNombreAcademia("Ingeniería de Software");
        planAcademia.setNombreCoordinador("Juan Carlos Pérez");
        planAcademia.setObjetivoGeneral("Revisar programa de las EE que conforman la académia. Acordar fechas de exámenes parciales y finales. Estandarizar métodos de evaluación y ponderación de exámenes teóricos y prácticos.");
        planAcademia.setObjetivosParticulares(objetivosParticulares);
        planAcademia.setExamenesParciales(eesConParciales);
        planAcademia.setFormasDeEvaluacion(formasDeEvaluacion);
        planAcademia.setHistoricoDeRevisiones(historicoDeRevisiones);
        planAcademia.setAutorizacion(firmaDeAutorizacion);
        planAcademia.setEstado(EstadoDeDocumento.CONCLUIDO);
        
        planAcademiaB.setId("PLAT-3");
        planAcademiaB.setFechaAprobacion(fechaAutorizacion);
        planAcademiaB.setProgramaEducativo("2002");
        planAcademiaB.setPeriodoEscolar("Febrero - Julio 2018");
        planAcademiaB.setNombreAcademia("Ingeniería de Software");
        planAcademiaB.setNombreCoordinador("Juan Carlos Pérez");
        planAcademiaB.setObjetivoGeneral("Revisar programa de las EE que conforman la académia. Acordar fechas de exámenes parciales y finales. Estandarizar métodos de evaluación y ponderación de exámenes teóricos y prácticos.");
        planAcademiaB.setObjetivosParticulares(new ArrayList<ObjetivoParticular>());
        planAcademiaB.setExamenesParciales(new ArrayList<ExperienciaEducativaConParciales>());
        planAcademiaB.setFormasDeEvaluacion(new ArrayList<FormaDeEvaluacion>());
        planAcademiaB.setHistoricoDeRevisiones(new ArrayList<Revision>());
        planAcademiaB.setAutorizacion(new FirmaAutorizacion());
        planAcademiaB.setEstado(EstadoDeDocumento.EN_EDICION);
        
        Academico academico = new Academico();
        AcademicoDAO academicoDAO = new AcademicoDAO();
        academico.setNumeroPersonal(203910);
        academico.setNombreAcademico("Juan Carlos Pérez");
        academico.setGradoEstudios("Lic. en Ingeniería de Software");
        academico.setRolAcademico(RolAcademico.COORDINADOR);
        academicoDAO.guardarAcademico(academico);
    }
    
    @Test
    public void almacenarPlanAcademiaConcluido(){
        boolean valorEsperado = true;
        boolean valorObtenido = planAcademiaDAO.guardarPlanTrabajoAcademiaCompleto(planAcademia);
        
        assertEquals("Prueba guardar plan trabajo academia completo", valorEsperado, valorObtenido);       
    }

    @Test
    public void almacenarPlanAcademiaEnEdicion(){
        boolean valorEsperado = true;
        boolean valorObtenido = planAcademiaDAO.guardarPlanTrabajoAcademiaCompleto(planAcademiaB);
        
        assertEquals("Prueba guardar plan trabajo academia completo", valorEsperado, valorObtenido);       
    }
    
}
