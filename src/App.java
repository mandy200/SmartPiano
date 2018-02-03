import java.sql.SQLException;
import java.util.ArrayList;

/**
 * CRUD with Pool connection
 * @author ssuri
 */
public class App {

	public static void main(String[] args) {	
		try 
		{
			// New CRUD
			Crud CRUD = new Crud();
			
			/** CREATE */
			CRUD.create("4", "Tinhinane", "22", "Test create", "3000");
			
			/** DELETE */
			CRUD.delete(2);
			
			/** UPDATE */
			CRUD.update("1", "Suriya", "20", "Test update", "2000");
			
			/** READ */
			ArrayList<String> List = CRUD.read();
			for(String s : List)
			{
				System.out.println(s);
			}
			
			CRUD.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}
