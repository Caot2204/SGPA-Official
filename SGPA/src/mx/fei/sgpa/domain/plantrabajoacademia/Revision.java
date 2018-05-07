
package mx.fei.sgpa.domain.plantrabajoacademia;

import java.sql.Date;

public class Revision {
    private int numeroRevision;
    private Date fecha;
    private String seccionPaginaModificada;
    private String descripcionDeModificacion;
    
    public Revision(){
        
    }

    public Revision(int numeroRevision, Date fecha, String seccionPaginaModificada, String descripcionDeModificacion) {
        this.numeroRevision = numeroRevision;
        this.fecha = fecha;
        this.seccionPaginaModificada = seccionPaginaModificada;
        this.descripcionDeModificacion = descripcionDeModificacion;
    }

    public int getNumeroRevision() {
        return numeroRevision;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getSeccionPaginaModificada() {
        return seccionPaginaModificada;
    }

    public String getDescripcionDeModificacion() {
        return descripcionDeModificacion;
    }

    public void setNumeroRevision(int numeroRevision) {
        this.numeroRevision = numeroRevision;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setSeccionPaginaModificada(String seccionPaginaModificada) {
        this.seccionPaginaModificada = seccionPaginaModificada;
    }

    public void setDescripcionDeModificacion(String descripcionDeModificacion) {
        this.descripcionDeModificacion = descripcionDeModificacion;
    }
    
}
