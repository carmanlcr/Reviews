package com.selenium.reviews.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.selenium.reviews.model.Campaing;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class RegisterCampaing {

	private JFrame frmRegistrarCampaa;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public void init() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterCampaing window = new RegisterCampaing();
					window.frmRegistrarCampaa.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RegisterCampaing() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRegistrarCampaa = new JFrame();
		frmRegistrarCampaa.setResizable(false);
		frmRegistrarCampaa.setTitle("Registrar Campa\u00F1a");
		frmRegistrarCampaa.setBounds(100, 100, 455, 392);
		
		JLabel lblCampaa = new JLabel("Campa\u00F1a");
		lblCampaa.setFont(new Font("Arial", Font.PLAIN, 12));
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JLabel lblLink = new JLabel("Link");
		lblLink.setFont(new Font("Arial", Font.PLAIN, 12));
		
		final JTextArea textArea = new JTextArea();
		textArea.setRows(20);
		textArea.setLineWrap(true);
		textArea.setTabSize(1);
		
		JButton btnNewButton = new JButton("Registrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textArea.getText().isEmpty() || textField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,"Los campos no pueden estar vacio", "Failed",JOptionPane.ERROR_MESSAGE);
				}else {
					Campaing campa = new Campaing();
					campa.setName(textField.getText());
					campa.setLink(textArea.getText());
					try {
						campa.insert();
						textField.setText("");
						textArea.setText("");
						JOptionPane.showMessageDialog(null, "Campaña agregada con exito","Extio",JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, "Error al agregar campaña","Failed",JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
				
			}
		});
		GroupLayout groupLayout = new GroupLayout(frmRegistrarCampaa.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(31)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblLink, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblCampaa, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(textField))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(159)
							.addComponent(btnNewButton)))
					.addContainerGap(57, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(54)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCampaa)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(50)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLink, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
					.addComponent(btnNewButton)
					.addContainerGap())
		);
		frmRegistrarCampaa.getContentPane().setLayout(groupLayout);
	}
}
