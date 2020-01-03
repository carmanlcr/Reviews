package com.selenium.reviews.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.selenium.reviews.model.Campaing;
import com.selenium.reviews.model.Phrase;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;

/**
 * 
 * Clase deprecada, el registro de la frase se hace directamente en la tarea 
 * y no como metodo independiente
 * 
 * @deprecated
 * @author Luis Morales
 * @version 1.0.0
 *
 */
public class RegisterPhrase extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3847090931438169794L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public void init() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterPhrase frame = new RegisterPhrase();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RegisterPhrase() {
		setTitle("Registrar Frase");
		setResizable(false);
		setBounds(100, 100, 423, 319);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblFrase = new JLabel("Frase");
		lblFrase.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		final JTextArea textArea = new JTextArea();
		
		JLabel lblCategora = new JLabel("Categor\u00EDa");
		lblCategora.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		final JComboBox<String> comboBox = new JComboBox<String>();
		Campaing camp = new Campaing();
		List<Campaing> list = camp.getAllActive();
		for(Campaing a : list) comboBox.addItem(a.getName());
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textArea.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "El campo no debe estar vacio","Failed",JOptionPane.ERROR_MESSAGE);
				}else {
					Phrase ph = new Phrase();
				    ph.setCampaings_id(comboBox.getSelectedIndex()+1);
				    ph.setPhrase(textArea.getText());
				    try {
						ph.insert();
						JOptionPane.showMessageDialog(null, "Frase registrada con exito","Exito",JOptionPane.INFORMATION_MESSAGE);
						textArea.setText("");
						comboBox.setSelectedIndex(0);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, "Error al registrar frase","Failed",JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(180)
							.addComponent(lblFrase, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(54)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 299, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblCategora, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
									.addGap(63)
									.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(31))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(142)
							.addComponent(btnRegistrar)))
					.addContainerGap(54, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(26)
					.addComponent(lblFrase)
					.addGap(18)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
					.addGap(40)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCategora, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(28)
					.addComponent(btnRegistrar)
					.addContainerGap(28, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
