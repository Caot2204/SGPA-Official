
package mx.fei.sgpa.datasource;

import com.mysql.jdbc.CommunicationsException;
import java.sql.Connection;
import javax.naming.CommunicationException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.Timeout;

public class DataBaseTest {
    
    @Rule
    public Timeout tiempoLimite = new Timeout(2000);
    
    public DataBaseTest() {
        
    }
    
    @Test
    public void probarServidorCaido(){
        Connection conexionBD = DataBase.getDataBaseConnection();
    }
    
    //@Rules
}
