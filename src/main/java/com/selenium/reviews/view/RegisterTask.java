package com.selenium.reviews.view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.selenium.reviews.model.Campaing;
import com.selenium.reviews.model.Task;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JButton;
import com.toedter.calendar.JDateChooser;

public class RegisterTask {

	private JFrame frmRegistrarTarea;
	private Campaing camp = new Campaing();
	private JComboBox<String> comboBox_1_1 = new JComboBox<String>();
	private JButton btnNewButton = new JButton("Registrar");
	private DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * Launch the application.
	 */
	public void init() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterTask window = new RegisterTask();
					window.frmRegistrarTarea.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RegisterTask() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRegistrarTarea = new JFrame();
		frmRegistrarTarea.setTitle("Registrar Tarea");
		frmRegistrarTarea.setResizable(false);
		frmRegistrarTarea.setBounds(100, 100, 425, 494);
		
		JLabel lblNewLabel = new JLabel("Fecha");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 12));
		
		JLabel lblComentario = new JLabel("Comentario");
		lblComentario.setFont(new Font("Arial", Font.BOLD, 12));
		
		final JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		
		JLabel lblCampaa = new JLabel("Campa\u00F1a");
		lblCampaa.setFont(new Font("Arial", Font.BOLD, 12));
		
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 11));
		
		final JDateChooser dateChooser = new JDateChooser();
		GroupLayout groupLayout = new GroupLayout(frmRegistrarTarea.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(196, Short.MAX_VALUE)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addGap(143))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(61)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 319, Short.MAX_VALUE)
					.addGap(39))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(177, Short.MAX_VALUE)
					.addComponent(lblCampaa, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addGap(162))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(87)
					.addComponent(comboBox_1_1, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(93, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(164)
					.addComponent(btnNewButton)
					.addContainerGap(172, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(171, Short.MAX_VALUE)
					.addComponent(lblComentario, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addGap(168))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(130, Short.MAX_VALUE)
					.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
					.addGap(126))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(16)
					.addComponent(lblNewLabel)
					.addGap(18)
					.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(62)
					.addComponent(lblComentario, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
					.addGap(32)
					.addComponent(lblCampaa, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(comboBox_1_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(44)
					.addComponent(btnNewButton)
					.addContainerGap(21, Short.MAX_VALUE))
		);
		frmRegistrarTarea.getContentPane().setLayout(groupLayout);
		
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Date fecha = dateChooser.getDate();
				Date date = new Date();
				String comentario = textArea.getText().trim();
				Campaing camp = new Campaing();
				camp.setName((String)comboBox_1_1.getSelectedItem());
				camp = camp.getIdCampaing();
				if(fecha == null) {
					JOptionPane.showMessageDialog(null, "La fecha no puede quedar vacia","Failed",JOptionPane.ERROR_MESSAGE);
				}else if(comentario.isEmpty()){
					JOptionPane.showMessageDialog(null, "La fecha no puede quedar vacia","Failed",JOptionPane.ERROR_MESSAGE);
				}else if(camp == null) {
					JOptionPane.showMessageDialog(null, "La campaña es incorrecta","Failed",JOptionPane.ERROR_MESSAGE);
				}else {
					Task task = new Task();
					task.setDate_publication(dateFormat1.format(fecha));
					task.setComentario(comentario);
					task.setCampaings_id(camp.getCampaings_id());
					try {
						task.insert();
						textArea.setText("");
						comboBox_1_1.setSelectedIndex(0);
						if(dateFormat1.format(fecha).equals(dateFormat1.format(date))) {
							InitFrame ini = new InitFrame();
							ini.insertUsersInPanel();
						}
						JOptionPane.showMessageDialog(null,"Se creo la tarea con exito","Success",JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				}
				
			}
		});
		
		comboBox_1_1 = setComboBoxVpn(camp.getAllActive());
		
	}
	private JComboBox<String> setComboBoxVpn(final List<Campaing> vpn2) {
		
		for (Campaing jugador : vpn2){
			comboBox_1_1.addItem(jugador.getName());
		}
	    return comboBox_1_1;
	}  
}
