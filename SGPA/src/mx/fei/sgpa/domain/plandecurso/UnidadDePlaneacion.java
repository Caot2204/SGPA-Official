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
public class UnidadDePlaneacion {
    
    int unidad;
    String tema;
    Date fecha;
    String tareaPractica;
    String tecnicaDidactica;

    public UnidadDePlaneacion() {
    }
    

    public int getUnidad() {
        return unidad;
    }

    public String getTema() {
        return tema;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getTareaPractica() {
        return tareaPractica;
    }

    public String getTecnicaDidactica() {
        return tecnicaDidactica;
    }

    public void setUnidad(int unidad) {
        this.unidad = unidad;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setTareaPractica(String tareaPractica) {
        this.tareaPractica = tareaPractica;
    }

    public void setTecnicaDidactica(String tecnicaDidactica) {
        this.tecnicaDidactica = tecnicaDidactica;
    }
    
}
