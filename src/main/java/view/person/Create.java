package view.person;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import controller.GeneralPublicController;
import controller.PersonController;
import controller.ResearcherController;
import model.vo.GeneralPublicVO;
import model.vo.PersonVO;
import model.vo.ResearcherVO;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;

public class Create {

	private JFrame frame;
	private JTextField txtName;
	private JFormattedTextField txtDocument;
	private JTextField txtInstitution;
	private PersonController personController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Create window = new Create();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Create() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			frame = new JFrame();
			frame.setBounds(100, 100, 450, 241);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(null);
			
			JLabel lblNome = new JLabel("Nome");
			lblNome.setBounds(10, 11, 46, 14);
			frame.getContentPane().add(lblNome);
			
			txtName = new JTextField();
			txtName.setBounds(10, 36, 414, 20);
			frame.getContentPane().add(txtName);
			txtName.setColumns(10);
			
			personController = new PersonController();
			ArrayList<String> genres = personController.getGenre();
			JComboBox cbGenre = new JComboBox(genres.toArray());
			cbGenre.setBounds(10, 91, 97, 20);
			frame.getContentPane().add(cbGenre);
			
			JLabel lblGenero = new JLabel("Genero");
			lblGenero.setBounds(10, 67, 46, 14);
			frame.getContentPane().add(lblGenero);
	
			MaskFormatter mascaraCpf = new MaskFormatter("###.###.###-##");
			
			JLabel lblCpf = new JLabel("CPF");
			lblCpf.setBounds(117, 67, 46, 14);
			frame.getContentPane().add(lblCpf);

			txtDocument = new JFormattedTextField(mascaraCpf);
			txtDocument.setBounds(117, 91, 177, 20);
			frame.getContentPane().add(txtDocument);
			txtDocument.setColumns(10);
			
			JLabel lblNewLabel = new JLabel("Tipo pessoa");
			lblNewLabel.setBounds(304, 67, 83, 14);
			frame.getContentPane().add(lblNewLabel);
			
			ArrayList<String> typePerson = this.getTypePerson();
			JComboBox cbTypePerson = new JComboBox(typePerson.toArray());
			cbTypePerson.setBounds(304, 91, 120, 20);
			frame.getContentPane().add(cbTypePerson);
			
			JLabel lblInstituio = new JLabel("Institui\u00E7\u00E3o");
			lblInstituio.setBounds(10, 122, 97, 14);
			frame.getContentPane().add(lblInstituio);
			
			txtInstitution = new JTextField();
			txtInstitution.setBounds(10, 147, 284, 20);
			frame.getContentPane().add(txtInstitution);
			txtInstitution.setColumns(10);
			
			JLabel lblNewLabel_1 = new JLabel("Volunt\u00E1rio");
			lblNewLabel_1.setBounds(304, 122, 83, 14);
			frame.getContentPane().add(lblNewLabel_1);
			
			JCheckBox chVolunteer = new JCheckBox("Sim");
			chVolunteer.setEnabled(false);
			chVolunteer.setBounds(304, 146, 97, 23);
			frame.getContentPane().add(chVolunteer);
			
			cbTypePerson.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (cbTypePerson.getSelectedItem() == "Pesquisador") {
						txtInstitution.setEnabled(true);
						txtInstitution.setEditable(true);
						chVolunteer.setEnabled(false);
						chVolunteer.setSelected(false);
					} else {
						txtInstitution.setEnabled(false);
						txtInstitution.setEditable(false);
						chVolunteer.setEnabled(true);
					}
				}
			});
			
			JButton btnSave = new JButton("Salvar");
			btnSave.setBounds(335, 176, 89, 23);
			frame.getContentPane().add(btnSave);
			
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String typePerson = (String) cbTypePerson.getSelectedItem();
					String message;
					
					if (typePerson == "Pesquisador") {
						ResearcherVO researcherVO = new ResearcherVO();
						ResearcherController researcherController = new ResearcherController();
						
						researcherVO.setName(txtName.getText());
						researcherVO.setGenre((String) cbGenre.getSelectedItem());
						researcherVO.setDocument(txtDocument.getText());
						researcherVO.setInstitution(txtInstitution.getText());
						
						message = researcherController.store(researcherVO);
					} else {
						GeneralPublicVO generalPublicVO = new GeneralPublicVO();
						GeneralPublicController generalPublicController = new GeneralPublicController();
						
						generalPublicVO.setName(txtName.getText());
						generalPublicVO.setGenre((String) cbGenre.getSelectedItem());
						generalPublicVO.setDocument(txtDocument.getText());
						generalPublicVO.setVolunteers(chVolunteer.isSelected());
						
						message = generalPublicController.store(generalPublicVO);
					}					

					JOptionPane.showMessageDialog(frame.getContentPane(), message);
				}
			});
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
	}

	private ArrayList<String> getTypePerson() {
		ArrayList<String> genres = new ArrayList<String>();
		genres.add("Pesquisador");
		genres.add("Público Geral");
		
		return genres;
	}
}
