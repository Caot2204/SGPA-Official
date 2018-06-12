/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   08/06/2018				  */
/* Ultima modificación: 08/06/2018				  */
/* Descripción: Implementación del acceso a BD para recuperar y   */
/*              guardar Academias                                 */
/****************************************************************/
package mx.fei.sgpa.dao.academia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.sgpa.datasource.DataBase;
import mx.fei.sgpa.domain.Academia;
import mx.fei.sgpa.domain.Academico;

public class AcademiaDAO implements IAcademiaDAO {
    
    private Connection conexionDB;
    private String consultaSQL;
    private PreparedStatement sentencia;

    @Override
    public boolean guardarAcademia(Academia academia) {
        boolean guardadoExitoso = false;
        consultaSQL = "INSERT INTO academia VALUES (?,?,?)";
        conexionDB = DataBase.getDataBaseConnection();
        try {
            sentencia = conexionDB.prepareStatement(consultaSQL);
            sentencia.setString(1, academia.getIdAcademia());
            sentencia.setString(2, academia.getNombreAcademia());
            sentencia.setInt(3, academia.getCoordinadorAcademia());
            sentencia.execute();
            guardadoExitoso = true;
        } 
        catch (SQLException ex) {
            Logger.getLogger(AcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            DataBase.closeConnection();
        }
        return guardadoExitoso;        
    }

    @Override
    public ArrayList<Academia> obtenerAcademias(int numeroPersonalCoordinador) {
        ArrayList<Academia> academiasDeCoordinador = new ArrayList<>();
        consultaSQL = "SELECT * FROM academia WHERE Coordinador=?";
        conexionDB = DataBase.getDataBaseConnection();
        try {
            sentencia = conexionDB.prepareStatement(consultaSQL);
            sentencia.setInt(1, numeroPersonalCoordinador);
            ResultSet resultadoConsulta = sentencia.executeQuery();
            while (resultadoConsulta.next()){
                Academia academia = new Academia();
                academia.setIdAcademia(resultadoConsulta.getString("Id"));
                academia.setNombreAcademia(resultadoConsulta.getString("Nombre"));
                academia.setCoordinadorAcademia(resultadoConsulta.getInt("Coordinador"));
                academiasDeCoordinador.add(academia);
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(AcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return academiasDeCoordinador;        
    }

    @Override
    public Academia obtenerAcademia(String idAcademia) {
        Academia academia = new Academia();
        consultaSQL = "SELECT * FROM academia WHERE Id=?";
        conexionDB = DataBase.getDataBaseConnection();
        try {
            sentencia = conexionDB.prepareStatement(consultaSQL);
            sentencia.setString(1, idAcademia);
            ResultSet resultadoConsulta = sentencia.executeQuery();
            while (resultadoConsulta.next()){
                academia.setIdAcademia(resultadoConsulta.getString("Id"));
                academia.setNombreAcademia(resultadoConsulta.getString("Nombre"));
                academia.setCoordinadorAcademia(resultadoConsulta.getInt("Coordinador"));
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(AcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return academia;        
    }
    
    @Override
    public Academia obtenerAcademiaPorNombre(String nombreAcademia) {
        Academia academia = new Academia();
        consultaSQL = "SELECT * FROM academia WHERE Nombre=?";
        conexionDB = DataBase.getDataBaseConnection();
        try {
            sentencia = conexionDB.prepareStatement(consultaSQL);
            sentencia.setString(1, nombreAcademia);
            ResultSet resultadoConsulta = sentencia.executeQuery();
            while (resultadoConsulta.next()){
                academia.setIdAcademia(resultadoConsulta.getString("Id"));
                academia.setNombreAcademia(resultadoConsulta.getString("Nombre"));
                academia.setCoordinadorAcademia(resultadoConsulta.getInt("Coordinador"));
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(AcademiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return academia;        
    }

    @Override
    public ArrayList<Academico> obtenerIntegrantesAcademia(String idAcademia) {
        ArrayList<Academico> integrantes = new ArrayList<>();
        
        return integrantes;
    }
        
}
