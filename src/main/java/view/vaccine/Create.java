package view.vaccine;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;

import controller.VaccineController;
import model.vo.ResearcherVO;
import model.vo.VaccineVO;

public class Create {

	private JFrame frame;
	private JTextField txtCountry;
	private VaccineController vaccineController;

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
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 229);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblPasDeOrigem = new JLabel("Pa\u00EDs de origem");
		lblPasDeOrigem.setBounds(10, 11, 127, 14);
		frame.getContentPane().add(lblPasDeOrigem);
		
		txtCountry = new JTextField();
		txtCountry.setBounds(10, 36, 218, 20);
		frame.getContentPane().add(txtCountry);
		txtCountry.setColumns(10);
		
		JLabel lblEstgioDaPesquisa = new JLabel("Est\u00E1gio da Pesquisa");
		lblEstgioDaPesquisa.setBounds(238, 11, 157, 14);
		frame.getContentPane().add(lblEstgioDaPesquisa);
		
		vaccineController = new VaccineController();
		ArrayList<String> stages = vaccineController.getStages();
		JComboBox cbStage = new JComboBox(stages.toArray());
		cbStage.setBounds(238, 36, 186, 20);
		frame.getContentPane().add(cbStage);

		DatePickerSettings dateSettings = new DatePickerSettings();
		dateSettings.setAllowKeyboardEditing(false);
		
		final DateTimePicker date = new DateTimePicker(dateSettings, null);
		date.setBounds(10, 95, 654, 30);
		frame.getContentPane().add(date);
		
		JLabel lblDataDeIncio = new JLabel("Data de in\u00EDcio da pesquisa");
		lblDataDeIncio.setBounds(10, 67, 127, 14);
		frame.getContentPane().add(lblDataDeIncio);
		
		JLabel lblPesquisador = new JLabel("Pesquisador");
		lblPesquisador.setBounds(10, 136, 78, 14);
		frame.getContentPane().add(lblPesquisador);
		
		ArrayList<String> researchers = vaccineController.getReasearchers();
		JComboBox cbResearcher = new JComboBox(researchers.toArray());
		cbResearcher.setBounds(10, 161, 315, 20);
		frame.getContentPane().add(cbResearcher);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(335, 160, 89, 23);
		frame.getContentPane().add(btnSalvar);

		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VaccineVO vaccine = new VaccineVO();
				vaccine.setCountry(txtCountry.getText());
				vaccine.setStage((String) cbStage.getSelectedItem());
				LocalDate datePicker = date.getDatePicker().getDate();
				vaccine.setStartDate(datePicker);
				vaccine.setResearcher(new ResearcherVO());
				vaccine.getResearcher().setName((String) cbResearcher.getSelectedItem());
				String message = vaccineController.store(vaccine);

				JOptionPane.showMessageDialog(frame.getContentPane(), message);
			}
		});
	}
}
