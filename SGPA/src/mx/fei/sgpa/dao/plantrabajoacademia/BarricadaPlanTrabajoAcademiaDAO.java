/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   15/06/2018				  */
/* Ultima modificación: 15/06/2018				  */
/* Descripción: Capa encargada de validar lo datos a utilizar en  */
/*              PlanTrabajoAcademiaDAO                            */
/****************************************************************/
package mx.fei.sgpa.dao.plantrabajoacademia;

import java.util.ArrayList;
import mx.fei.sgpa.domain.EstadoDeDocumento;
import mx.fei.sgpa.domain.plantrabajoacademia.AccionDeMeta;
import mx.fei.sgpa.domain.plantrabajoacademia.ExamenParcial;
import mx.fei.sgpa.domain.plantrabajoacademia.ExperienciaEducativaConParciales;
import mx.fei.sgpa.domain.plantrabajoacademia.FirmaAutorizacion;
import mx.fei.sgpa.domain.plantrabajoacademia.FormaDeEvaluacion;
import mx.fei.sgpa.domain.plantrabajoacademia.MetaDeObjetivo;
import mx.fei.sgpa.domain.plantrabajoacademia.ObjetivoParticular;
import mx.fei.sgpa.domain.plantrabajoacademia.PlanTrabajoAcademia;
import mx.fei.sgpa.domain.plantrabajoacademia.Revision;

/**
 * Capa encargada de validar lo datos a utilizar en PlanTrabajoAcademiaDAO
 */
public class BarricadaPlanTrabajoAcademiaDAO {
    
    private static BarricadaPlanTrabajoAcademiaDAO barricadaPlanAcademiaDAO;
    
    /**
     * Devuelve una instancia de BarricadaCuentaUsuarioDAO. Si existe una, la devuelve
     * sino, crea una nueva
     * 
     * @return Objeto de tipo BarricadaCuentaUsuarioDAO
     */
    public static BarricadaPlanTrabajoAcademiaDAO obtenerInstancia() {
        if (barricadaPlanAcademiaDAO == null) {
            barricadaPlanAcademiaDAO = new BarricadaPlanTrabajoAcademiaDAO();
        }
        return barricadaPlanAcademiaDAO;
    }
    
    /**
     * Valida que los datos del PlanTrabajoAcademia cumplan con las restricciones para ser
     * almacenados (No Nulos, no empezar con espacio y menor al valor máximo entero)
     * 
     * @param planAcademiaAValidar Datos del PlanTrabajoAcademia a validar
     * @return true si los datos cumplen con las restricciones, false si no es así
     */
    public boolean validarDatosPlanAcademiaCompleto(PlanTrabajoAcademia planAcademiaAValidar) {
        boolean datosValidos = true;
        
        if (!validarIdPlanAcademia(planAcademiaAValidar.getId())) {
            datosValidos = false;
        }
        if (planAcademiaAValidar.getFechaAprobacion() == null) {
            datosValidos = false;
        }
        if (!validarCadena(planAcademiaAValidar.getProgramaEducativo()) || planAcademiaAValidar.getProgramaEducativo().length() > 100) {
            datosValidos = false;
        }
        if (!validarCadena(planAcademiaAValidar.getPeriodoEscolar()) || planAcademiaAValidar.getPeriodoEscolar().length() > 50) {
            datosValidos = false;
        }
        if (!validarCadena(planAcademiaAValidar.getNombreAcademia())) {
            datosValidos = false;
        }
        if (!validarCadena(planAcademiaAValidar.getNombreCoordinador())) {
            datosValidos = false;
        }
        if (!validarCadena(planAcademiaAValidar.getObjetivoGeneral()) || planAcademiaAValidar.getObjetivoGeneral().length() > 500) {
            datosValidos = false;
        }
        return datosValidos;        
    }
    
    /**
     * Valida que los datos del PlanTrabajoAcademia (sin histórico de revisiones ni firma
     * de autorización) cumplan con las restricciones para ser almacenados 
     * (No Nulos, no empezar con espacio y menor al valor máximo entero)
     * 
     * @param planAcademiaAValidar Datos del PlanTrabajoAcademia a validar
     * @return true si los datos cumplen con las restricciones, false si no es así
     */
    public boolean validarDatosPlanAcademia(PlanTrabajoAcademia planAcademiaAValidar) {
        boolean datosValidos = true;
        
        if (!validarIdPlanAcademia(planAcademiaAValidar.getId())) {
            datosValidos = false;
        }
        if (planAcademiaAValidar.getFechaAprobacion() == null) {
            datosValidos = false;
        }
        if (!validarCadena(planAcademiaAValidar.getProgramaEducativo()) || planAcademiaAValidar.getProgramaEducativo().length() > 100) {
            datosValidos = false;
        }
        if (!validarCadena(planAcademiaAValidar.getPeriodoEscolar()) || planAcademiaAValidar.getPeriodoEscolar().length() > 50) {
            datosValidos = false;
        }
        if (!validarCadena(planAcademiaAValidar.getNombreAcademia())) {
            datosValidos = false;
        }
        if (!validarCadena(planAcademiaAValidar.getNombreCoordinador())) {
            datosValidos = false;
        }
        if (!validarCadena(planAcademiaAValidar.getObjetivoGeneral()) || planAcademiaAValidar.getObjetivoGeneral().length() > 500) {
            datosValidos = false;
        }
        return datosValidos;        
    }
    
    /**
     * Valida que el Identificador del PlanTrabajoAcademia cumpla con las restricciones 
     * para ser almacenado (No Nulos, no empezar con espacio y menor al valor máximo entero)
     * 
     * @param idPlan Identificador del PlanTrabajoAcademia a validar
     * @return true si el identificador cumple con las restricciones, false si no es así
     */
    public boolean validarIdPlanAcademia(String idPlan) {
        boolean datoValido = false;
        if (validarCadena(idPlan) && idPlan.length() < 100) {
            datoValido = true;
        }        
        return datoValido;
    }    
    
    /**
     * Valida que los datos de los Objetivos Particulares, Metas y Acciones cumplan 
     * con las restricciones para ser almacenados (No Nulos, no empezar con espacio y 
     * menor al valor máximo entero)
     * 
     * @param objetivosAValidar Lista ObjetivoParticular con MetaDeObjetivo y AccionDeMeta a validar
     * @return true si los datos cumplen con las restricciones, false si no es así
     */
    public boolean validarObjetivosParticulares(ArrayList<ObjetivoParticular> objetivosAValidar) {
        boolean datosValidos = true;
        for (ObjetivoParticular objetivoIngresado : objetivosAValidar) {
            if (!validarCadena(objetivoIngresado.getDescripcion()) || objetivoIngresado.getDescripcion().length() > 500) {
                datosValidos = false;
                break;
            }
            ArrayList<MetaDeObjetivo> metasDeObjetivo = objetivoIngresado.getMetasDeObjetivo();
            for (MetaDeObjetivo metaIngresada : metasDeObjetivo) {
                if (!validarCadena(metaIngresada.getDescripcion()) || metaIngresada.getDescripcion().length() > 500) {
                    datosValidos = false;
                    break;
                }
                ArrayList<AccionDeMeta> accionesDeMeta = metaIngresada.getAccionesDeMeta();
                for (AccionDeMeta accionIngresada : accionesDeMeta) {
                    if (!validarCadena(accionIngresada.getDescripcionAccion()) || accionIngresada.getDescripcionAccion().length() > 10000) {
                        datosValidos = false;
                        break;
                    }
                    if (!validarCadena(accionIngresada.getFechaSemana()) || accionIngresada.getFechaSemana().length() > 100) {
                        datosValidos = false;
                        break;
                    }
                    if (!validarCadena(accionIngresada.getFormaOperar()) || accionIngresada.getFormaOperar().length() > 10000) {
                        datosValidos = false;
                        break;
                    }
                }
            }
        }
        return datosValidos;
    }
    
    /**
     * Valida que el Identificador del ObjetivoParticular cumpla con las restricciones 
     * para ser almacenado (No Nulos, no empezar con espacio y menor al valor máximo entero)
     * 
     * @param idObjetivo Identificador del ObjetivoParticular a validar
     * @return true si el identificador cumple con las restricciones, false si no es así
     */
    public boolean validarIdObjetivoParticular(String idObjetivo) {
        boolean datoValido = false;
        if (validarCadena(idObjetivo) && idObjetivo.length() < 50) {
            datoValido = true;
        }        
        return datoValido;
    }
    
    /**
     * Valida que el Identificador de la MetaDeObjetivo cumpla con las restricciones 
     * para ser almacenado (No Nulos, no empezar con espacio y menor al valor máximo entero)
     * 
     * @param idMeta Identificador del MetaDeObjetivo a validar
     * @return true si el identificador cumple con las restricciones, false si no es así
     */
    public boolean validarIdMeta(String idMeta) {
        boolean datoValido = false;
        if (validarCadena(idMeta) && idMeta.length() < 50) {
            datoValido = true;
        }        
        return datoValido;
    }
    
    /**
     * Valida que los datos de las ExperienciaEducativaConParciales cumplan 
     * con las restricciones para ser almacenados (No Nulos, no empezar con espacio y 
     * menor al valor máximo entero)
     * 
     * @param examenesAValidar Lista de ExperienciaEducativaConParciales a validar
     * @return true si los datos cumplen con las restricciones, false si no es así
     */
    public boolean validarExamenesParciales(ArrayList<ExperienciaEducativaConParciales> examenesAValidar) {
        boolean datosValidos = true;
        for (ExperienciaEducativaConParciales experienciaConExamen : examenesAValidar) {
            if (!validarCadena(experienciaConExamen.getExperienciaEducativa()) || experienciaConExamen.getExperienciaEducativa().length() > 100) {
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
                    if (!validarCadena(temaIngresado) || temaIngresado.length() > 100) {
                        datosValidos = false;
                        break;
                    }                    
                }
            }
        }
        return datosValidos;
    }
    
    /**
     * Valida que los datos de las FormaDeEvaluacion cumplan con las restricciones 
     * para ser almacenados (No Nulos, no empezar con espacio y menor al valor máximo entero)
     * 
     * @param evaluacionesAValidar Lista de FormaDeEvaluacion a validar
     * @return true si los datos cumplen con las restricciones, false si no es así
     */
    public boolean validarFormasDeEvaluacion(ArrayList<FormaDeEvaluacion> evaluacionesAValidar) {
        boolean datosValidos = true;
        for (FormaDeEvaluacion formaEvaluacionIngresada : evaluacionesAValidar) {
            if (!validarCadena(formaEvaluacionIngresada.getElemento()) || formaEvaluacionIngresada.getElemento().length() > 100) {
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
     * Valida que los datos de las Revision cumplan con las restricciones para ser 
     * almacenados (No Nulos, no empezar con espacio y menor al valor máximo entero)
     * 
     * @param revisionesAValidar Lista de Revision a validar
     * @return true si los datos cumplen con las restricciones, false si no es así
     */
    public boolean validarHistoricoDeRevision(ArrayList<Revision> revisionesAValidar) {
        boolean datosValidos = true;
        for (Revision revisionIngresada: revisionesAValidar) {
            if (revisionIngresada.getNumeroRevision() > Integer.MAX_VALUE) {
                datosValidos = false;
            }
            if (revisionIngresada.getFecha() == null) {
                datosValidos = false;
            }
            if (!validarCadena(revisionIngresada.getSeccionPaginaModificada()) || revisionIngresada.getSeccionPaginaModificada().length() > 100) {
                datosValidos = false;
            }
            if (!validarCadena(revisionIngresada.getDescripcionDeModificacion()) || revisionIngresada.getDescripcionDeModificacion().length() > 200) {
                datosValidos = false;
            }
        }
        return datosValidos;
    }
    
    /**
     * Valida que los datos de la FirmaAutorizacion cumpla con las restricciones 
     * para ser almacenados (No Nulos, no empezar con espacio y menor al valor máximo entero)
     * 
     * @param firmaAValidar FirmaAutorizacion a validar
     * @return true si los datos cumplen con las restricciones, false si no es así
     */
    public boolean validarFirmaAutorizacion(FirmaAutorizacion firmaAValidar) {
        boolean datosValidos = false;
        if (!validarCadena(firmaAValidar.getPersonaQueProponePlan()) || firmaAValidar.getPersonaQueProponePlan().length() > 100) {
            datosValidos = false;
        }
        if (!validarCadena(firmaAValidar.getPersonaQueAutorizaPlan()) || firmaAValidar.getPersonaQueAutorizaPlan().length() > 100) {
            datosValidos = false;
        }
        if (firmaAValidar.getFechaAutorizacion() == null) {
            datosValidos = false;
        }
        if (firmaAValidar.getFechaEntradaEnVigor() == null) {
            datosValidos = false;
        }
        return datosValidos;
    }
    
    /**
     * Valida que la cadena No sea nula, No empiece con espacio en blanco o este vacía
     * 
     * @param cadena Cadena de texto a validar
     * @return true si la cadena cumple con las restricciones, false si no es así
     */
    public boolean validarCadena(String cadena) {
        boolean cadenaValida = false;
        if (cadena != null) {
            if (!cadena.equals("") && !String.valueOf(cadena.charAt(0)).equals(" ")) {
                cadenaValida = true;
            }
        }
        return cadenaValida;
    }
    
}
