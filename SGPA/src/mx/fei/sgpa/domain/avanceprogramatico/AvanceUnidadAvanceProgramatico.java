
package mx.fei.sgpa.domain.avanceprogramatico;

public class AvanceUnidadAvanceProgramatico {
    int unidad;
    float porcentaje;
    String observacion;

    public AvanceUnidadAvanceProgramatico(int unidad, float porcentaje, String observacion) {
        this.unidad = unidad;
        this.porcentaje = porcentaje;
        this.observacion = observacion;
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
