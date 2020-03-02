package com.selenium.reviews.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;

import com.selenium.reviews.controller.InicioController;
import com.selenium.reviews.model.Task;
import com.selenium.reviews.model.User;

import Controller.SikuliTest;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.ScrollPaneConstants;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;


public class InitFrame {

	private JFrame frmInicio;
	private JMenuBar menuBar = new JMenuBar();
	private final JMenu mnUsuarios = new JMenu("Usuarios");
	private final JMenuItem registrarUsuario = new JMenuItem("Registrar");
	private final JMenuItem importarUsuarios = new JMenuItem("Importar Usuarios");
	private final JMenu mnVpn = new JMenu("Vpn");
	private final JMenuItem registerVpn = new JMenuItem("Registrar");
	private final JMenu mnCampaing = new JMenu("Campañas");
	private final JMenuItem registerCampaing = new JMenuItem("Registrar");
	private final JMenu mnTarea = new JMenu("Tarea");
	private final JMenuItem mntmRegistrar = new JMenuItem("Registrar");
	private JButton btnNewButton = new JButton("Iniciar");
	private final List<JCheckBox> listUsersCheckBox = new ArrayList<JCheckBox>();
	private final List<String> listUsersSelec = new ArrayList<String>();
	private List<User> listUsers = new ArrayList<User>();
	private List<Task> listTask;
	private final List<User> listUserSelected = new ArrayList<User>();
	protected JPanel panel = new JPanel();
	private static Screen s;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		final SikuliTest si = new SikuliTest();
		si.run();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				s = si.getScreen();
				
			}
		}).start();
		
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InitFrame window = new InitFrame();
					window.frmInicio.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InitFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmInicio = new JFrame();
		frmInicio.setTitle("Inicio");
		frmInicio.setBounds(100, 100, 501, 567);
		frmInicio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		menuBar.setBorderPainted(false);
		frmInicio.setJMenuBar(menuBar);
		
		menuBar.add(mnUsuarios);
		registrarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RegisterUser regi = new RegisterUser();
				regi.init();
			}
		});
		
		mnUsuarios.add(registrarUsuario);
		
		importarUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		mnUsuarios.add(importarUsuarios);
		
		menuBar.add(mnCampaing);
		mnCampaing.add(registerCampaing);
		
		registerCampaing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterCampaing reC = new RegisterCampaing();
				reC.init();
			}
		});
		

		menuBar.add(mnVpn);
		mnVpn.add(registerVpn);
		
		menuBar.add(mnTarea);
		mntmRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RegisterTask regT = new RegisterTask();
				regT.init();
			}
		});
		
		mnTarea.add(mntmRegistrar);
		
		registerVpn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterVPN registerVpn = new RegisterVPN();
				registerVpn.inicio();
			}
		});
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		GroupLayout groupLayout = new GroupLayout(frmInicio.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 454, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(207)
							.addComponent(btnNewButton)))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(45)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 408, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
					.addComponent(btnNewButton)
					.addContainerGap())
		);
		
		
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		frmInicio.getContentPane().setLayout(groupLayout);
		
		
		try {
			listTask = getCountPost();
			if(listTask.size() < 1) {
				JOptionPane.showMessageDialog(null, "No hay tareas para realizar hoy");
				btnNewButton.setEnabled(false);
			}else {
				insertUsersInPanel();
				btnNewButton.setEnabled(true);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		
		
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				for(JCheckBox cs : listUsersCheckBox) if(cs.isSelected()) listUsersSelec.add(cs.getText());
				
				if(listUsersSelec.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Debe seleccionar al menos un usuario","Failed",JOptionPane.ERROR_MESSAGE);
				}else {
					for(String st : listUsersSelec) {
						for(User us : listUsers) {
							if(us.getEmail().equals(st)) {
								listUserSelected.add(us);
								break;
							}
						}
					}
					
					InicioController init = new InicioController(listUserSelected,listTask,s);
					try {
						init.init();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					} catch (FindFailed e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	}
	
	protected void insertUsersInPanel() {
		User u = new User();
		listUsers = u.getUsers();
		
		for(User us : listUsers){
			JCheckBox ch = new JCheckBox(us.getEmail());
			panel.add(ch);
			listUsersCheckBox.add(ch);
		}
		panel.updateUI();
	}
	
	protected List<Task> getCountPost() throws SQLException {
		Task task = new Task();
		return task.getTasksOfTheDay();
	}
}
