package com.selenium.reviews.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.selenium.reviews.model.Campaing;
import com.selenium.reviews.model.Post;
import com.selenium.reviews.model.Task;
import com.selenium.reviews.model.User;
import com.selenium.reviews.model.Vpn;

import Controller.RobotController;
import Controller.VpnController;


public class InicioController {
	
	private final String PAGE = "www.google.com";
	private final String PATH_IMAGE_SIKULI ="C:\\ImagenesSikuli\\";
	private List<User> usuarios;
	private List<Task> tareas;
	private static VpnController vpn;
	private RobotController robot;
	private Screen s;
	public InicioController(List<User> usuarios,List<Task> listTask, Screen s) {
		this.usuarios = usuarios;
		this.tareas = listTask;
		this.s = s;
	}
	
	public void init() throws InterruptedException, FindFailed {
		
		for(int i = 0; i<tareas.size(); i++) {
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
					vpn = new VpnController(vp.getVpnUser());
					try {
						vpn.connectVpn();
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
						robot = new RobotController();
						try {
							//Lanzamiento de la pagina   
							robot.openChromeIncognit();
						    Thread.sleep(7150);
						    //Maximizar Chrome
							robot.maximizar();
							//Escribir la pagina a ingresar
							robot.inputWrite(PAGE);
							//Darle enter parawww ir a la pagina
							robot.enter();
							Thread.sleep(12150);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						Thread.sleep(1520);
						
						IniciaSesion inicioSesion = new IniciaSesion(s,robot,usuarios.get(j).getEmail(), usuarios.get(j).getPassword());
						
						if(inicioSesion.init()) {								
							if(s.exists(new Pattern(PATH_IMAGE_SIKULI+"confirm_phone-Google.png").exact()) != null) {
								try {
									s.click(new Pattern(PATH_IMAGE_SIKULI+"confirm_phone-Google.png").exact());
								}catch (IllegalThreadStateException e) {
									// TODO: handle exception
								}
								Thread.sleep(5000);
							}
							try {
								s.click(new Pattern(PATH_IMAGE_SIKULI+"google-com.png").exact());
							}catch (IllegalThreadStateException e) {
								// TODO: handle exception
							}
							Thread.sleep(456);
							Campaing campa = new Campaing();
							campa.setCampaings_id(tareas.get(i).getCampaings_id());
							campa = campa.getCampaing();
							robot.inputWrite(campa.getLink());
							robot.enter();
							Thread.sleep(12000);
							
							
							if(s.exists(new Pattern(PATH_IMAGE_SIKULI+"opinion_write-Google.png").exact()) != null) {
								try {
									s.click(new Pattern(PATH_IMAGE_SIKULI+"opinion_write-Google.png").exact());
								}catch (IllegalThreadStateException e) {
									// TODO: handle exception
								}
								Thread.sleep(5000);
								
								try {
									s.click(new Pattern(PATH_IMAGE_SIKULI+"share_details-Google.png").exact());	
								}catch (IllegalThreadStateException e) {
									// TODO: handle exception
								}
								
								Thread.sleep(2000);
								
								robot.inputWrite(tareas.get(i).getComentario());
								
								Thread.sleep(2500);
								System.out.println("Seleccionar 5 estrellas");

								robot.pressShiftTabulador();
								Thread.sleep(450);
								robot.enter();
								
								Thread.sleep(3500);
								try {
									System.out.println("Pulsar publicar");
									s.click(new Pattern(PATH_IMAGE_SIKULI+"public-Google.png").exact());
								}catch (IllegalThreadStateException e) {
									// TODO: handle exception
								}
								post.setCampaings_id(campa.getCampaings_id());
								post.setTasks_id(tareas.get(i).getTasks_id());
								post.insert();
								System.out.println("Post insertado");
								Thread.sleep(3500);
								robot.pressEsc();
								Thread.sleep(1000);
								robot.pressEsc();
								Thread.sleep(1000);
								
								robot.close();
							}else if(s.exists(new Pattern(PATH_IMAGE_SIKULI+"opinion_write1-Google.png").exact()) != null) {
								try {
									s.click(new Pattern(PATH_IMAGE_SIKULI+"opinion_write1-Google.png").exact());
								}catch (IllegalThreadStateException e) {
									// TODO: handle exception
								}
								Thread.sleep(5000);
								
								try {
									s.click(new Pattern(PATH_IMAGE_SIKULI+"share_details1-Google.png").exact());	
								}catch (IllegalThreadStateException e) {
									// TODO: handle exception
								}
								
								Thread.sleep(2000);
								
								robot.inputWrite(tareas.get(i).getComentario());
								
								Thread.sleep(2500);
								System.out.println("Seleccionar 5 estrellas");

								robot.pressShiftTabulador();
								Thread.sleep(450);
								robot.enter();
								
								Thread.sleep(3500);
								try {
									System.out.println("Pulsar publicar");
									s.click(new Pattern(PATH_IMAGE_SIKULI+"public1-Google.png").exact());
								}catch (IllegalThreadStateException e) {
									// TODO: handle exception
								}
								post.setCampaings_id(campa.getCampaings_id());
								post.setTasks_id(tareas.get(i).getTasks_id());
								post.insert();
								System.out.println("Post insertado");
								Thread.sleep(3500);
								robot.pressEsc();
								Thread.sleep(1000);
								robot.pressEsc();
								Thread.sleep(1000);
								
								robot.close();
								
							}else {
								robot.pressTab();
								Thread.sleep(880);
								robot.pressTab();
								Thread.sleep(880);
								robot.enter();
								
								Thread.sleep(5000);
								if(s.exists(new Pattern(PATH_IMAGE_SIKULI+"share_details-Google.png").exact()) != null) {
									try {
										s.click(new Pattern(PATH_IMAGE_SIKULI+"share_details-Google.png").exact());	
									}catch (IllegalThreadStateException e) {
									}
									
									Thread.sleep(2000);
									
									robot.inputWrite(tareas.get(i).getComentario());
									
									Thread.sleep(2500);
									System.out.println("Seleccionar 5 estrellas");

									robot.pressShiftTabulador();
									Thread.sleep(450);
									robot.enter();
									
									Thread.sleep(3500);
									try {
										System.out.println("Pulsar publicar");
										s.click(new Pattern(PATH_IMAGE_SIKULI+"public-Google.png").exact());
									}catch (IllegalThreadStateException e) {
										// TODO: handle exception
									}
									post.setCampaings_id(campa.getCampaings_id());
									post.setTasks_id(tareas.get(i).getTasks_id());
									post.insert();
									System.out.println("Post insertado");
									Thread.sleep(3500);
									robot.pressEsc();
									Thread.sleep(1000);
									robot.pressEsc();
									Thread.sleep(1000);
									
									robot.close();
								}else if(s.exists(new Pattern(PATH_IMAGE_SIKULI+"share_details1-Google.png").exact()) != null) {
									try {
										s.click(new Pattern(PATH_IMAGE_SIKULI+"share_details1-Google.png").exact());	
									}catch (IllegalThreadStateException e) {
									}
									
									Thread.sleep(2000);
									
									robot.inputWrite(tareas.get(i).getComentario());
									
									Thread.sleep(2500);
									System.out.println("Seleccionar 5 estrellas");

									robot.pressShiftTabulador();
									Thread.sleep(450);
									robot.enter();
									
									Thread.sleep(3500);
									try {
										System.out.println("Pulsar publicar");
										s.click(new Pattern(PATH_IMAGE_SIKULI+"public1-Google.png").exact());
									}catch (IllegalThreadStateException e) {
										// TODO: handle exception
									}
									post.setCampaings_id(campa.getCampaings_id());
									post.setTasks_id(tareas.get(i).getTasks_id());
									post.insert();
									System.out.println("Post insertado");
									Thread.sleep(3500);
									robot.pressEsc();
									Thread.sleep(1000);
									robot.pressEsc();
									Thread.sleep(1000);
									
									robot.close();
								}else {
									System.out.println("No hay donde publicar para este usuario");
								}
							}
								
						}else {
							System.out.println("El usuario "+usuarios.get(j).getEmail()+" tiene error de usuario o contraseña");
							i--;
						}//Fin del if usuario o contraseña correcto o incorrecto
								
					}
						
						
				}//Fin del If VPN
				vpn.disconnectVpn();
				usuarios.remove(j);
				break;
				}//Fin del for usuarios 
			}//Fin del for Campañas
		System.out.println("Finalizo el programa");
		}//Fin de la funcion

	
	
	
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
