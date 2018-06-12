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
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBase {
    private static Connection connection;    
    
    private static void makeConnection(){
        try {
            String url= "jdbc:mysql://localhost/";
            String databaseName = "sgpa_database";
            String userName = "administrador_sgpa";
            String password = "administrador";
       
            connection = (Connection)DriverManager.getConnection(url+databaseName,userName,password);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static Connection getDataBaseConnection() {        
        makeConnection();
        return DataBase.connection;
 
    }
    
    public static void closeConnection(){
        if(connection!=null){
            try {
                if(!connection.isClosed()){
                    connection.close();                
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
