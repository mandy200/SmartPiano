import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * CRUD class, handle Create Read Update queries
 * @author suriya
 *
 */
public class Crud  {

	Connection conn ;
	Statement stmt;
	
	/**
	 * CONSTRUCTOR
	 * @throws SQLException 
	 */
	public Crud () throws SQLException {
		Pool.initialize(1, 25);
	}
	
	/**
	 *  INSERT
	 * @param id
	 * @param name
	 * @param age
	 * @param address
	 * @param salary
	 * @return
	 * @throws SQLException
	 */
	public boolean create (String id, String name, String age, String address, String salary) throws SQLException {
		conn = Pool.getConnection();
		
		boolean ret = true;
		stmt = conn.createStatement();
        
		String sql = 
        "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
        + "VALUES (" + id + ",'" + name + "'," + age + ",'" + address + "'," + salary + ")";
        
		int i =  stmt.executeUpdate(sql);
        if(i != 1) {
        	ret = false ;
        }
        stmt.close();
        Pool.release(conn);
        return ret;
	}
	
	/**
	 * SELECT
	 * @return ret
	 * @throws SQLException
	 */
	public ArrayList <String> read () throws SQLException {
		conn = Pool.getConnection();
		
		ArrayList <String> ret = new ArrayList<String>();
		stmt = conn.createStatement();

		String sql = "SELECT * FROM company";
		ResultSet rs = stmt.executeQuery(sql);
		
		String line = "";
		while ( rs.next() ) {
			line = "";
			line += " ID = " + rs.getInt("id");
			line += " NAME = " + rs.getString("name");
			line += " AGE = " + rs.getInt("age");
			line += " ADDRESS = " + rs.getString("address");
			line += " SALARY = " + rs.getInt("salary");
			ret.add(line);
		}
		
		stmt.close();
		Pool.release(conn);
		return ret ;
	}
	
	/**
	 * UPDATE
	 * @param id
	 * @param name
	 * @param age
	 * @param address
	 * @param salary
	 * @return ret
	 * @throws SQLException
	 */
	public boolean update (String id, String name, String age, String address, String salary) throws SQLException {
		conn = Pool.getConnection();
		
		boolean ret = true;
		stmt = conn.createStatement();
		
		// sql 
		String sql = 
				" UPDATE company "
				+ "SET "
				+ "name = '" + name + "'"
				+ ", age = " + age + ""
				+ ", address = '" + address + "'"
				+ ", salary = " + salary + 
				" WHERE id = " + id;
		
		int i = stmt.executeUpdate(sql);
		if (i != 1 ) {
			ret = false;
		}
		stmt.close();
		Pool.release(conn);
		return ret;
	}
	
	/**
	 * DELETE
	 * @param id
	 * @return ret
	 * @throws SQLException
	 */
	public boolean delete (int id) throws SQLException {
		conn = Pool.getConnection();
		
		boolean ret = true;
		stmt = conn.createStatement();
		
		String sql = "DELETE FROM company WHERE id =" + id; // sql
		
		int i = stmt.executeUpdate(sql);
		if (i != 1) {
			ret = false;
		}
		stmt.close();
		Pool.release(conn);
		return ret;
	}
	
	public void close () {
		Pool.closeAll();
	}
	
	

}
