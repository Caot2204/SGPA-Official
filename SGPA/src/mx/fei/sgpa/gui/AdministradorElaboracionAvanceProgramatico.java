/** ************************************************************* */
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   08/06/2018				  */
/* Ultima modificación: 08/06/2018				  */
/* Descripción: Controlador del CU Elaborar Avance Programatico   */
/** ************************************************************* */
package mx.fei.sgpa.gui;

import java.util.ArrayList;
import mx.fei.sgpa.domain.Curso;

public class AdministradorElaboracionAvanceProgramatico {
    
    private static AdministradorElaboracionAvanceProgramatico administradorElaboracion;
    
    private ArrayList<Curso> cursosDelDocente;
    
    public static AdministradorElaboracionAvanceProgramatico obtenerInstancia() {
        if (administradorElaboracion == null) {
            administradorElaboracion = new AdministradorElaboracionAvanceProgramatico();
        }
        return administradorElaboracion;
    }
    
    public static boolean existeInstancia() {
        boolean existe = false;
        if (administradorElaboracion != null) {
            existe = true;
        }        
        return existe;
    }
    
    public static void finalizarCasoUso() {
        administradorElaboracion = null;
    }
    
    public ArrayList<Curso> obtenerCursosDelDocente(int numeroDePersonal) {
        return cursosDelDocente;
    }
    
}
