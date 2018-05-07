
package mx.fei.sgpa.domain.avanceprogramatico;

public class AvancePorUnidad {
    private int unidad;
    private float porcentaje;
    private String observacion;
    
    public AvancePorUnidad(){
        
    }

    public int getUnidad() {
        return unidad;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setUnidad(int unidad) {
        this.unidad = unidad;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    
}
