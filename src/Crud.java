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
	 * @param libelle
	 * @param description
	 * @param quantite_stock
	 * @param quantite_disponible
	 * @param prix
	 * @return
	 * @throws SQLException
	 */
	public boolean create (int id, String libelle, String description, int quantite_stock,  int quantite_disponible, double prix) throws SQLException {
		conn = Pool.getConnection();
		
		boolean ret = true;
		stmt = conn.createStatement();
        
		String sql = 
        "INSERT INTO MARCHANDISE (id, libelle, description, quantite_stock, quantite_disponible, prix) "
        + "VALUES (" + id + ",'" + libelle + "','" + description + "'," + quantite_stock + "," + quantite_disponible + "," + prix + ")";
        
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

		String sql = "SELECT * FROM marchandise";
		ResultSet rs = stmt.executeQuery(sql);
		
		String line = "";
		while ( rs.next() ) {
			line = "";
			line += " ID = " + rs.getInt("id");
			line += " libelle = " + rs.getString("libelle");
			line += " description = " + rs.getString("description");
			line += " quantite_stock = " + rs.getInt("quantite_stock");
			line += " quantite_disponible = " + rs.getInt("quantite_disponible");
			line += " prix " + rs.getDouble("prix");
			ret.add(line);
		}
		
		stmt.close();
		Pool.release(conn);
		return ret ;
	}
	
	/**
	 * UPDATE
	 * @param id
	 * @param libelle
	 * @param description
	 * @param quantite_stock
	 * @param quantite_disponible
	 * @param prix
	 * @return ret
	 * @throws SQLException
	 */
	public boolean update (int id, String libelle, String description, int quantite_stock,  int quantite_disponible, double prix) throws SQLException {
		conn = Pool.getConnection();
		
		boolean ret = true;
		stmt = conn.createStatement();
		
		// sql 
		String sql = 
				" UPDATE marchandise "
				+ "SET "
				+ " libelle = '" + libelle + "'"
				+ ", description = '" + description + "'"
				+ ", quantite_stock = " + quantite_stock + ""
				+ ", quantite_disponible = " + quantite_disponible +  ""
				+ ", prix = " + prix +
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
		
		String sql = "DELETE FROM marchandise WHERE id =" + id; // sql
		
		int i = stmt.executeUpdate(sql);
		if (i != 1) {
			ret = false;
		}
		stmt.close();
		Pool.release(conn);
		return ret;
	}
	
	public boolean emptyTable () throws SQLException {
		conn = Pool.getConnection();
		
		boolean ret = false;
		stmt = conn.createStatement();
		
		int result = stmt.executeUpdate("TRUNCATE marchandise");
		
		if(result == 0)
		{
			ret = true ;
		}
		
		return ret ;
	}
	
	public void close () {
		Pool.closeAll();
	}
	
	

}
