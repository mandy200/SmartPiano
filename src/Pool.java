import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * class handling connection pooling
 * @author suriya
 */
public class Pool {

    private static ArrayList<Connection> pool;
    private static String driver = "org.postgresql.Driver";
    private static String url = "jdbc:postgresql://127.0.0.1:5432/db_crud_test"; //192.168.0.2
    private static String user = "suriya";
    private static String password = "azerty";
    private static int minConnection;
    private static int maxConnection;
    private static int nbConnection;
    
    /**
     * Initialization of the connection pooling static class
     * @param min number of connection at least available at the beginning
     * @param max maximum number of connection that can be used in parallel
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void initialize( int min, int max ){
        minConnection = min ;
        maxConnection = max ;
        nbConnection = 0 ;
        pool = new ArrayList<Connection>() ;
        try{
            Class.forName(driver);
            for (int i = 0 ; i < minConnection ; i++){
                pool.add(DriverManager.getConnection(url, user, password)) ;
            }
            nbConnection = minConnection ;
        } catch (Exception e){
        	System.out.println("Error initializing DB connection pool");
        }
    }
    
    
    /**
     * Check if there's a connection available in the pool
     * @return boolean return true if there is at least one Connection available, false otherwise
     * @throws SQLException
     */
    public static boolean checkAvailableConnection() throws SQLException{
        if (pool.size() > 0){
            return true ;
        }else if (nbConnection < maxConnection){
            pool.add(DriverManager.getConnection(url, user, password)) ;
            nbConnection++ ;
            return true ;
        }else if (nbConnection >= maxConnection){
            return false ;
        }else if (pool.size() == 0){
            return false ;
        }
        return false ;
    }  
    
    /**
     * Return a connection from pool
     * @return Connection if there is one available, return null otherwise
     * @throws SQLException
     */
    public static synchronized Connection getConnection() throws SQLException{
        Connection c = null ;
        if(!checkAvailableConnection()){
            return c ;
        }else{
            c = pool.get(pool.size() - 1) ;
            pool.remove(pool.size() - 1) ;
        }
        return c ;
    }
    
    /**
     * Return a connection to the pool
     * @param c The connection to return to the pool
     */
    public static synchronized void release(Connection c){
        pool.add(c) ;
    }   
    
    /**
     * Close all connections in the pool
     */
    public static void closeAll() {
    	for(Connection c : pool){		
    		try
    		{
    			c.close() ;
    		}
    		catch(SQLException se)
    		{
    			se.printStackTrace();
    		}
    	}
    }
}
