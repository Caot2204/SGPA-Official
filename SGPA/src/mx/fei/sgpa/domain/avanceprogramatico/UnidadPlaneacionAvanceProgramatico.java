
package mx.fei.sgpa.domain.avanceprogramatico;

import java.util.ArrayList;
import java.sql.Date;

public class UnidadPlaneacionAvanceProgramatico {
    int unidad;
    ArrayList<String> temasDesarrollados;
    ArrayList<Date> fechas;
    ArrayList<String> tareasPracticas;
    ArrayList<String> tecnicasDidacticas;

    public int getUnidad() {
        return unidad;
    }

    public ArrayList<String> getTemasDesarrollados() {
        return temasDesarrollados;
    }

    public ArrayList<Date> getFechas() {
        return fechas;
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

    public void setFechas(ArrayList<Date> fechas) {
        this.fechas = fechas;
    }

    public void setTareasPracticas(ArrayList<String> tareasPracticas) {
        this.tareasPracticas = tareasPracticas;
    }

    public void setTecnicasDidacticas(ArrayList<String> tecnicasDidacticas) {
        this.tecnicasDidacticas = tecnicasDidacticas;
    }

    
}
