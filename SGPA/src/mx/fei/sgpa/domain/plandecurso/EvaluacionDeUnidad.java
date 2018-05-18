/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.fei.sgpa.domain.plandecurso;

import java.util.Date;

/**
 *
 * @author beto
 */
public class EvaluacionDeUnidad {
    
    int unidad;
    Date fecha;
    String criterioDeEvaluacion;
    String instrumento;
    float porcentaje;

    public EvaluacionDeUnidad() {
        
    }

    public int getUnidad() {
        return unidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getCriterioDeEvaluacion() {
        return criterioDeEvaluacion;
    }

    public String getInstrumento() {
        return instrumento;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setUnidad(int unidad) {
        this.unidad = unidad;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setCriterioDeEvaluacion(String criterioDeEvaluacion) {
        this.criterioDeEvaluacion = criterioDeEvaluacion;
    }

    public void setInstrumento(String instrumento) {
        this.instrumento = instrumento;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }
}
