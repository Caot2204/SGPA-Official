/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   16/04/2018				  */
/* Ultima modificación: 18/05/2018				  */
/* Descripción: Detalles de la Unidad de Planeación contenida en  */
/*              un AvanceProgramatico.				  */
/****************************************************************/

package mx.fei.sgpa.domain.avanceprogramatico;

import java.util.ArrayList;
import java.sql.Date;

public class UnidadDePlaneacion {
    private int unidad;
    private ArrayList<String> temasDesarrollados;
    private String rangoFecha;
    private ArrayList<String> tareasPracticas;
    private ArrayList<String> tecnicasDidacticas;
    
    public UnidadDePlaneacion(){
        
    }

    public int getUnidad() {
        return unidad;
    }

    public ArrayList<String> getTemasDesarrollados() {
        return temasDesarrollados;
    }

    public String getRangoFecha() {
        return rangoFecha;
    }

    public ArrayList<String> getTareasPracticas() {
        return tareasPracticas;
    }

    public ArrayList<String> getTecnicasDidacticas() {
        return tecnicasDidacticas;
    }

    public void setUnidad(int unidad) {
        this.unidad = unidad;
    }

    public void setTemasDesarrollados(ArrayList<String> temasDesarrollados) {
        this.temasDesarrollados = temasDesarrollados;
    }

    public void setRangoFecha(String fechas) {
        this.rangoFecha = fechas;
    }

    public void setTareasPracticas(ArrayList<String> tareasPracticas) {
        this.tareasPracticas = tareasPracticas;
    }

    public void setTecnicasDidacticas(ArrayList<String> tecnicasDidacticas) {
        this.tecnicasDidacticas = tecnicasDidacticas;
    }

    
}
