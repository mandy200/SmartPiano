import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 
 * CrudGUI
 * @author Tinhinane, Suriya
 *
 */
public class CrudGUI extends JFrame implements ActionListener {
    
	JButton btnCreate, btnRead, btnUpdate, btnDelete; // buttons
	Crud CRUD; // new object crud
	int idCPT; // counter
	String msg; // message success or error
	
	public CrudGUI() throws SQLException {
		
		CRUD = new Crud ();
		CRUD.emptyTable(); // clean table
		idCPT = 1; // initialize
		this.setTitle("CRUD"); // title
		
		JPanel panneau = new JPanel(new FlowLayout());
		JLabel readLabel = new JLabel ();
		
        btnCreate = new JButton("Ajouter une marchandise");
        btnCreate.addActionListener(this);
        btnCreate.setPreferredSize(new Dimension(200, 40));

        btnRead = new JButton("Afficher une marchandise");
        btnRead.addActionListener(this);
        btnRead.setPreferredSize(new Dimension(200, 40));
        
        btnUpdate = new JButton("Modifier une marchandise");
        btnUpdate.addActionListener(this);
        btnUpdate.setPreferredSize(new Dimension(200, 40));
        
        btnDelete = new JButton("Supprimer une marchandise");
        btnDelete.addActionListener(this);
        btnDelete.setPreferredSize(new Dimension(200, 40));
        
        panneau.add(btnCreate, BorderLayout.CENTER);
        panneau.add(btnRead, BorderLayout.CENTER);
        panneau.add(btnUpdate, BorderLayout.CENTER);
        panneau.add(btnDelete, BorderLayout.CENTER);
     
        getContentPane().add(panneau, BorderLayout.CENTER);
        
		this.setSize(500, 150); // Window size
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit the application
		this.setLocationRelativeTo(null); // Centering the window
		this.setVisible(true); // Show component
	}
	
	/**
	 * Create Event
	 */
	public void createEvent () {
		int id = idCPT;
		String libelle = "Oreiller";
		String description = "En coton";
		int quantite_stock = 13;
		int quantite_disponible = 15;
		double prix = 8.99;
		
		try {
			CRUD.create(id, libelle, description, quantite_stock, quantite_disponible, prix);
			idCPT++;
			msg = (" La marchandise n�" + id + " a bien �t� cr�e !");
		} catch (SQLException e1) {
			msg = "Erreur ! Impossible de cr�er la marchandise n�" + id + ".";
		}
		JOptionPane.showMessageDialog(this, msg);
	}
	
	/**
	 * Read Event
	 */
	public void readEvent () {
		try {
			String[] entetes = {"Id", "Libelle", "description", "Quantit� en stock", "Quantit� disponible", "prix"};
			msg = "";
			ArrayList<String> List = CRUD.read();
		
			for(String s : List)
			{
				msg += s + "\n" ;
			}
			if (msg.equals("")) {
				msg = "Erreur! La lecture n'a r�cup�r� aucune marchandises.";
			}
		}
		catch (SQLException e1) {
			msg = "Erreur! Impossible de lire les marchandises.";
		}
		JOptionPane.showMessageDialog(this, msg);
	}
	
	/**
	 * Update Event
	 */
	public void updateEvent () {
		int id = 1;
		try {
			if (CRUD.update(id, "Couverture", "Couverture cachemire", 67, 12, 89.65)) {
				msg = " La marchandise " + id + " a bien �t� modifi�e !";
			}
			else {
				msg = " Erreur! Impossible de mettre � jour la marchandise num�ro " + id + ".";
			}
		} catch (SQLException e1) {
			msg = " Erreur! Impossible de mettre � jour la marchandise num�ro " + id + ".";
		}	
		JOptionPane.showMessageDialog(this, msg);
	}
	
	/**
	 * Delete Event
	 */
	public void deleteEvent () {
		int id = 1;
		try {
			if (CRUD.delete(id)) {
				msg = "La marchandise " + id + " a bien �t� supprim�e !";
			}
			else {
				msg = "Erreur! Impossible de supprimer la marchandise num�ro " + id + ".";
			}
		} 
		catch (SQLException e1) {
			msg = "Erreur! Impossible de supprimer la marchandise num�ro " + id + ".";
		}	
		JOptionPane.showMessageDialog(this, msg);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() ==  btnCreate) {
			createEvent();
		}
		if (e.getSource() == btnRead) {
			readEvent();
		}
		if (e.getSource() == btnUpdate) {
			updateEvent();
		}
		if (e.getSource() == btnDelete) {
			deleteEvent();
		}
		
	}

	
}
