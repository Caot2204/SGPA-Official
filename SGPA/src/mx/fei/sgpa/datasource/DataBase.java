/****************************************************************/
/* Nombre: Carlos Alberto Onorio Torres.			  */
/* Fecha de creación:   16/04/2018				  */
/* Ultima modificación: 08/05/2018				  */
/* Descripción: Definir la clase para obtener conexión con la     */
/*              base de datos.				          */
/****************************************************************/

package mx.fei.sgpa.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Establece y cierra una conexión con la base de datos del sistema
 */
public class DataBase {
    private static Connection connection; 
    private static final Logger loggerDelSistema = LogManager.getLogger(DataBase.class);
    
    /**
     * Establece una conexión con la Base de Datos del SGPA
     */
    private static void makeConnection(){
        try {
            String url= "jdbc:mysql://localhost/";
            String databaseName = "sgpa_database";
            String userName = "administrador_sgpa";
            String password = "administrador";
       
            connection = (Connection)DriverManager.getConnection(url+databaseName,userName,password);
        }
        catch (SQLException excepcionBaseDatos) {
            loggerDelSistema.error("makeConnection: Ocurrió un problema en BD");
        }
    }
    
    /**
     * Obtiene la conexión establecida a la Base de Datos del SGPA
     * @return Conexión a la Base de Datos del SGPA
     */
    public static Connection getDataBaseConnection() {        
        makeConnection();
        return DataBase.connection;
 
    }
    
    /**
     * Cierra la conexión actual con la Base de Datos del SGPA
     */
    public static void closeConnection(){
        if(connection!=null){
            try {
                if(!connection.isClosed()){
                    connection.close();                
                }
            }
            catch (SQLException excepcionBaseDatos) {
                loggerDelSistema.error("closeConnection: Ocurrió un problema en BD");
            }
        }
    }
}
