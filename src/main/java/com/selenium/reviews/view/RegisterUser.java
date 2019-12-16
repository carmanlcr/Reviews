package com.selenium.reviews.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.selenium.reviews.model.User;
import com.selenium.reviews.model.Vpn;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class RegisterUser {

	private JFrame frmRegistrarUsuario;
	private JTextField userText;
	private JTextField passText;
	private JLabel lblVpn;
	private Vpn v = new Vpn();
	private List<String> vpn = v.getAllActive();
	private JComboBox<String> comboBox = setComboBoxVpn(vpn);
	
	/**
	 * Launch the application.
	 */
	public void init() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterUser window = new RegisterUser();
					window.frmRegistrarUsuario.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RegisterUser() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRegistrarUsuario = new JFrame();
		frmRegistrarUsuario.setTitle("Registrar Usuario");
		frmRegistrarUsuario.setBounds(100, 100, 393, 363);
		frmRegistrarUsuario.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		JLabel lblUsuario = new JLabel("Email");
		lblUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
		
		userText = new JTextField();
		userText.setColumns(10);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setFont(new Font("Arial", Font.PLAIN, 12));
		
		passText = new JTextField();
		passText.setColumns(10);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(userText.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "El campo de email no puede quedar vacio","Failed",JOptionPane.ERROR_MESSAGE);
				}else if(passText.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "El campo de password no puede quedar vacio","Failed",JOptionPane.ERROR_MESSAGE);
				}else if(comboBox.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null, "Debe seleccionar una vpn","Failed",JOptionPane.ERROR_MESSAGE);
				}else {
		        	String nameVpn = (String) comboBox.getSelectedItem();
					int id = 0;
					try {
						id = v.getFind(nameVpn);
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
					User usuario = new User();
					usuario.setEmail(userText.getText());
					usuario.setPassword(passText.getText());
					usuario.setVpn_id(id);
					
					try {
						usuario.insert();
						JOptionPane.showMessageDialog(null, "Se agrego correctamente el mensaje");
						userText.setText("");
						passText.setText("");
						comboBox.setSelectedIndex(0);
						InitFrame init = new InitFrame();
						init.insertUsersInPanel();
					} catch (SQLException e) {
						
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error al registrar al usuario","Failed",JOptionPane.ERROR_MESSAGE);
						
					}
				
				}
			}
		});
		btnRegistrar.setFont(new Font("Arial", Font.PLAIN, 11));
		
		lblVpn = new JLabel("Vpn");
		lblVpn.setFont(new Font("Arial", Font.PLAIN, 12));
		
		GroupLayout groupLayout = new GroupLayout(frmRegistrarUsuario.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(39)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblUsuario, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblContrasea, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblVpn, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
							.addGap(45)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(comboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(passText)
								.addComponent(userText, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(145)
							.addComponent(btnRegistrar)))
					.addContainerGap(58, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(49)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUsuario)
						.addComponent(userText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(56)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblContrasea, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(passText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblVpn, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
					.addGap(50)
					.addComponent(btnRegistrar)
					.addGap(25))
		);
		frmRegistrarUsuario.getContentPane().setLayout(groupLayout);
	}
	
	private JComboBox<String> setComboBoxVpn(final List<String> vpn2) {
		comboBox = new JComboBox<String>();
		
		for (String jugador : vpn2){
			
			comboBox.addItem(jugador);
		}
	    return comboBox;
	}
}
