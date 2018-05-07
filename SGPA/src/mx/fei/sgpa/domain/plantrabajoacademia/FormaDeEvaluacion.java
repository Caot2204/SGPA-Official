
package mx.fei.sgpa.domain.plantrabajoacademia;

public class FormaDeEvaluacion {
    private String elemento;
    private float porcentaje;
    
    public FormaDeEvaluacion(){
        
    }

    public FormaDeEvaluacion(String elemento, float porcentaje) {
        this.elemento = elemento;
        this.porcentaje = porcentaje;
    }

    public String getElemento() {
        return elemento;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setElemento(String elemento) {
        this.elemento = elemento;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }
    
}
