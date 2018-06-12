/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   09/06/2018				  */
/* Ultima modificación: 09/06/2018				  */
/* Descripción: Detalles general del Formato de Academia          */
/*              PlanTrabajoAcademia.				  */
/****************************************************************/
package mx.fei.sgpa.domain.plantrabajoacademia;

import java.sql.Date;

public class DetallesPlanTrabajoAcademia {
    
    private Date fechaAprobacion;
    private String programaEducativo;
    private String periodoEscolar;
    private String nombreAcademia;
    private String nombreCoordinador;
    private String objetivoGeneral;

    public DetallesPlanTrabajoAcademia() {
        
    }
    
    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }

    public String getProgramaEducativo() {
        return programaEducativo;
    }

    public String getPeriodoEscolar() {
        return periodoEscolar;
    }

    public String getNombreAcademia() {
        return nombreAcademia;
    }

    public String getNombreCoordinador() {
        return nombreCoordinador;
    }

    public String getObjetivoGeneral() {
        return objetivoGeneral;
    }

    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public void setProgramaEducativo(String programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    public void setPeriodoEscolar(String periodoEscolar) {
        this.periodoEscolar = periodoEscolar;
    }

    public void setNombreAcademia(String nombreAcademia) {
        this.nombreAcademia = nombreAcademia;
    }

    public void setNombreCoordinador(String nombreCoordinador) {
        this.nombreCoordinador = nombreCoordinador;
    }

    public void setObjetivoGeneral(String objetivoGeneral) {
        this.objetivoGeneral = objetivoGeneral;
    }
        
}
