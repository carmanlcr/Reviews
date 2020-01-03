package com.selenium.reviews.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;

import com.selenium.reviews.model.Campaing;
import com.selenium.reviews.model.Post;
import com.selenium.reviews.model.Task;
import com.selenium.reviews.model.User;
import com.selenium.reviews.model.Vpn;


public class InicioController {
	
	private List<User> usuarios;
	private List<Task> tareas;
	private static DriverController drive = null;
	private static VpnController vpn = new VpnController();
	private RobotClickController robot;
	private boolean banderaVpn = false;
	
	public InicioController(List<User> usuarios,List<Task> listTask) {
		this.usuarios = usuarios;
		this.tareas = listTask;
	}
	
	public void init() throws InterruptedException {
		
		for(int i = 0; i<tareas.size(); i++) {
			drive = null;
			Collections.shuffle(usuarios);
			for(int j = 0; j<usuarios.size();) {
				Post post = new Post();
				post.setUsers_id(usuarios.get(j).getUsers_id());
				//Si el usuario ya ha publicado en el día
				if(post.getUserPost()) {
					System.out.println("El usuario "+ usuarios.get(j).getEmail()+" ya hizo publicaciones en los ulitmos días");
					i--;
					usuarios.remove(j);
					break;
				//Si el usuario no ha publicado en el día	
				}else {
					String ip = validateIP();
					Vpn vp = new Vpn();
					vp.setVpn_id(usuarios.get(j).getVpn_id());
					try {
						vpn.iniciarVpn(vp.getVpnUser(),banderaVpn);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					String ipActual = validateIP();
					//Si el usuario no se ha podido conectar a la vpn
					if(ip.equals(ipActual)) {
						System.out.println("El usuario "+usuarios.get(j).getEmail()+ " no se pudo conectar a la vpn.");
						i--;
						usuarios.remove(j);
						break;
					//Si el usuario se conecto a la vpn
					}else {
						drive = new DriverController();
						drive.optionsChrome();
						
						drive.goPage("https://www.google.com/");
						
						Thread.sleep(1520);
						
						IniciaSesion inicioSesion = new IniciaSesion(drive, usuarios.get(j).getEmail(), usuarios.get(j).getPassword());
						
						if(inicioSesion.init()) {
							if(!validateUserBlock()) {
								post.setUsers_id(usuarios.get(j).getUsers_id());
								
								if(drive.searchElement(1, "/html/body/c-wiz[2]/c-wiz/div/div[1]/div/div/div/div[2]/div[3]/div/div[2]/div") != 0
									|| drive.searchElement(1, "//*[text()[contains(.,'Confirmar')]]") != 0
									|| drive.searchElement(1, "//*[text()[contains(.,'Proteger tu cuenta')]]") != 0) {
									try {
										drive.clickButton(1, "/html/body/c-wiz[2]/c-wiz/div/div[1]/div/div/div/div[2]/div[3]/div/div[2]/div", "Confirmar");
									}catch (ElementClickInterceptedException e) {
										drive.clickButton(1, "//*[text()[contains(.,'Confirmar')]]", "Confirmar");
									}
								}
								robot = new RobotClickController();
								Campaing campa = new Campaing();
								campa.setCampaings_id(tareas.get(i).getCampaings_id());
								campa = campa.getCampaing();
								drive.goPage(campa.getLink());
								Thread.sleep(1450);
								if(drive.searchElement(1, "wrl") != 0) {
									drive.clickButton(1, "wrl", "Escribir una opinion id");
								}else if(drive.searchElement(1, "//*[text()[contains(.,'Escribe una opinión')]]") != 0) {
									try {
										drive.clickButton(1, "//*[text()[contains(.,'Escribe una opinión')]]", "Escribir una opinión text");
									}catch(ElementNotInteractableException e) {
										drive.clickButton(1, "/html/body/span/g-lightbox/div[2]/div[3]/span/div/div/div/div[1]/div[1]/div[2]/span[1]/a", "Escribir una opinión xpath");
									}
								}
								

								Thread.sleep(2540);
								writePhrase(tareas.get(i).getComentario());
								
								Thread.sleep(2500);
								
								clickStartAndPublic();
								
								Thread.sleep(1490);
								
								robot.pressEsc();
								
								post.setCampaings_id(tareas.get(i).getCampaings_id());
								post.setTasks_id(tareas.get(i).getTasks_id());
								post.insert();
								
								System.out.println("El usuario publico correctamente");
								Thread.sleep(2540);
								
								//Presionar la tecla escape
								robot.pressEsc();
								
								Thread.sleep(2546);
									
							}else {
								i--;
							}//Fin del if de cuenta bloqueada
								
						}else {
							System.out.println("El usuario "+usuarios.get(j).getEmail()+" tiene error de usuario o contraseña");
							i--;
						}//Fin del if usuario o contraseña correcto o incorrecto
							
						}//La cuenta no es correcta
						
						
					}//Fin del If VPN
					if(drive != null) {
						drive.quit();
					}
					try {
						vpn.desconectVpn();
					} catch (IOException e) {
						e.printStackTrace();
					}
					banderaVpn = true;
					usuarios.remove(j);
					break;
				}//Fin del for usuarios 
			}//Fin del for Campañas
		System.out.println("Todas las publicaciones se hicieron correctamente");
		}//Fin de la funcion
	
	private boolean validateUserBlock() {
		if(drive.searchElement(1, "//*[text()[contains(.,'Verifica tu identidad')]]") != 0) {
			System.out.println("Usuario bloqueado");
			return true;
		}else if(drive.searchElement(1, "//*[text()[contains(.,'Verifica que eres tú')]]") != 0) {
			System.out.println("Usuario pide verificación");
			return true;
		}
		return false;
	}
	private void writePhrase(String frase) throws InterruptedException {
		robot.dimensions(450, 530);
		Thread.sleep(2303);
		robot.clickPressed();
		robot.copy(frase);
		Thread.sleep(235);
		robot.paste();
		
		
	}
	
	private void clickStartAndPublic() throws InterruptedException {
		robot.dimensions(590, 420);
		
		Thread.sleep(1490);
		
		robot.clickPressed();
		
		Thread.sleep(2540);
		if(drive.searchElement(1, "//*[text()[contains(.,'Publicar')]]") != 0) {
			drive.clickButton(1, "//*[text()[contains(.,'Publicar')]]", "Click boton de publicar");
		}else if(drive.searchElement(1, "/html/body/div/div/jsl/div/div[2]/div[3]/div[2]/div[2]/div") != 0) {
			drive.clickButton(1, "/html/body/div/div/jsl/div/div[2]/div[3]/div[2]/div[2]/div", "Click boton de publicar xpath");
		}else {
			robot.dimensions(859, 789);
			Thread.sleep(145);
			robot.clickPressed();
		}
		
		
		
		Thread.sleep(2650);
		
		
	}
	
	/*private void cerrarSesion() throws InterruptedException {
		if(drive.searchElement(1, "//*[text()[contains(.,'Revisar la actividad ahora')]]") != 0) {
			drive.clickButton(1, "/html/body/div[3]/div[1]/div/div[1]/div[2]/div/a", "Seleccionar el usuario xpath");
		}else {
			drive.clickButton(1, "/html/body/div[4]/div[1]/div/div[1]/div[2]/div/a", "Seleccionar el usuario xpath");
		}
		
		Thread.sleep(2546);
		
		
		if(drive.searchElement(1, "//*[text()[contains(.,'Salir')]]") != 0) {
			drive.clickButton(1, "//*[text()[contains(.,'Salir')]]", "Salir text");
		}else {
			if(drive.searchElement(1, "//*[text()[contains(.,'Revisar la actividad ahora')]]") != 0) {
				drive.clickButton(1, "/html/body/div[4]/div[1]/div/div[3]/div[4]/a", "Cerrar sesion 1 xpath");
			}else {
				drive.clickButton(1, "/html/body/div[3]/div[1]/div/div[3]/div[4]/a", "Cerrar sesion 2 xpath");
			}
			
		}
	}*/
	
	private String validateIP() {

		try {

			URL whatismyip = new URL("http://checkip.amazonaws.com");

			BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

			return in.readLine();

		} catch (MalformedURLException ex) {

			System.err.println(ex);
			return "190.146.186.130";
		} catch (IOException ex) {

			System.err.println(ex);
			return "190.146.186.130";
		}

	}
}
