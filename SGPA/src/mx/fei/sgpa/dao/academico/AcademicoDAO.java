/** ************************************************************* */
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   08/06/2018				  */
/* Ultima modificación: 08/06/2018				  */
/* Descripción: Implementación de los métodos de acceso a la DB   */
/*              para Academico.                    		  */
/** ************************************************************* */
package mx.fei.sgpa.dao.academico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.fei.sgpa.datasource.DataBase;
import mx.fei.sgpa.domain.Academico;
import mx.fei.sgpa.domain.RolAcademico;

public class AcademicoDAO implements IAcademicoDAO {
    
    private Connection conexionDB;
    private String consultaSQL;
    private PreparedStatement sentencia;

    @Override
    public boolean guardarAcademico(Academico academico) {
        boolean guardadoExitoso = true;
        consultaSQL = "INSERT INTO academico VALUES (?,?,?,?)";
        conexionDB = DataBase.getDataBaseConnection();
        try {
            sentencia = conexionDB.prepareStatement(consultaSQL);
            sentencia.setInt(1, academico.getNumeroPersonal());
            sentencia.setString(2, academico.getNombreAcademico());
            sentencia.setString(3, academico.getGradoEstudios());
            sentencia.setString(4, academico.getRolAcademico().name());
            sentencia.execute();
        } 
        catch (SQLException ex) {
            guardadoExitoso = false;
            Logger.getLogger(AcademicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            DataBase.closeConnection();
        }
        
        return guardadoExitoso;        
    }

    @Override
    public Academico obtenerAcademico(int numeroDePersonal) {
        Academico academico = new Academico();
        consultaSQL = "SELECT * FROM academico WHERE Numero_Personal=?";
        conexionDB = DataBase.getDataBaseConnection();
        try {
            sentencia = conexionDB.prepareStatement(consultaSQL);
            sentencia.setInt(1, numeroDePersonal);
            ResultSet resultadoConsulta = sentencia.executeQuery();
            while (resultadoConsulta.next()) {
                academico.setNumeroPersonal(numeroDePersonal);
                academico.setNombreAcademico(resultadoConsulta.getString("Nombre"));
                academico.setGradoEstudios(resultadoConsulta.getString("Grado_Estudios"));
                academico.setRolAcademico(RolAcademico.valueOf(resultadoConsulta.getString("Rol")));
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(AcademicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            DataBase.closeConnection();
        }
        return academico;
    }
    
    @Override
    public Academico obtenerAcademico(String nombreAcademico) {
        Academico academico = new Academico();
        consultaSQL = "SELECT * FROM academico WHERE Nombre=?";
        conexionDB = DataBase.getDataBaseConnection();
        try {
            sentencia = conexionDB.prepareStatement(consultaSQL);
            sentencia.setString(1, nombreAcademico);
            ResultSet resultadoConsulta = sentencia.executeQuery();
            while (resultadoConsulta.next()) {
                academico.setNumeroPersonal(resultadoConsulta.getInt("Numero_Personal"));
                academico.setNombreAcademico(nombreAcademico);
                academico.setGradoEstudios(resultadoConsulta.getString("Grado_Estudios"));
                academico.setRolAcademico(RolAcademico.valueOf(resultadoConsulta.getString("Rol")));
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(AcademicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            DataBase.closeConnection();
        }
        return academico;
    }
        
}
