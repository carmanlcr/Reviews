package com.selenium.reviews.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.selenium.reviews.interfaces.Model;

public class Campaing implements Model {
	
	private final String TABLE_NAME ="campaings";
	private int campaings_id;
	private String link;
	private String name;
	private boolean active;
	private boolean isCampaing;
	private String created_at;
	private static Conexion conn = new Conexion();
	private Date date = new Date();
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
	private DateFormat dateFormatSimple = new SimpleDateFormat("yyyy-MM-dd");
	Statement st;
	ResultSet rs;
	
	public void insert() throws SQLException{
		setCreated_at(dateFormat.format(date));
		String insert = "INSERT INTO "+TABLE_NAME+"(link,name,isCampaing,created_at) "
				+ " VALUE (?,?,?,?);";
		try (Connection conexion = conn.conectar();
				PreparedStatement exe = conexion.prepareStatement(insert);) {
			
			exe.setString(1, getLink());
			exe.setString(2, getName());
			exe.setBoolean(3, isCampaing());
			exe.setString(4, getCreated_at());
			
			exe.executeUpdate();
		}catch(SQLException e) {
			System.err.println(e);
		}
	}

	public Campaing getCampaing()  {
		Campaing camp = null;
		String queryExce = "SELECT * FROM "+TABLE_NAME+" ca " + 
				"WHERE ca.active = 1 AND ca.campaings_id = ?;";
		ResultSet rs;
		try (Connection conexion = conn.conectar();
				PreparedStatement  query = conexion.prepareStatement(queryExce);){
			
			
			query.setInt(1, getCampaings_id());
			rs = query.executeQuery();
			

			while (rs.next() ) {
				camp = new Campaing();
				camp.setCampaings_id(rs.getInt("ca.campaings_id"));
				camp.setName(rs.getString("ca.name"));
				camp.setLink(rs.getString("ca.link"));
				camp.setCreated_at(rs.getString("ca.created_at"));
               
			}
		}catch(SQLException e) {
			System.err.println(e);
		}
		
		return camp;
	}
	
	public List<Campaing> getAllActive()  {
		ArrayList<Campaing> list = new ArrayList<Campaing>();
		String queryExce = "SELECT * FROM "+TABLE_NAME+" ca " 
				+"WHERE ca.active = ? AND ca.isCampaing = ?;";
		
		ResultSet rs;
		try (Connection conexion = conn.conectar();
				PreparedStatement  query = conexion.prepareStatement(queryExce);){
			
			query.setInt(1, 1);
			query.setInt(2, 1);
			rs = query.executeQuery();

			while (rs.next() ) {
				Campaing camp = new Campaing();
				camp.setCampaings_id(rs.getInt("ca.campaings_id"));
				camp.setName(rs.getString("ca.name"));
				camp.setLink(rs.getString("ca.link"));
				camp.setCampaing(rs.getBoolean("ca.active"));
				camp.setCreated_at(rs.getString("ca.created_at"));
				list.add(camp);
               
			}
			conexion.close();
		}catch(SQLException e) {
			System.err.println(e);
		}
		
		return list;
	}

	public int getCampaingDiferent(String values){
		int list = 0;
		String queryExce = "SELECT ca.campaings_id FROM "+TABLE_NAME+" ca " + 
				"LEFT JOIN posts pt ON pt.campaings_id = ca.campaings_id AND DATE(pt.created_at) = ? " + 
				"WHERE ca.campaings_id NOT IN ("+values+") " + 
				"GROUP BY ca.campaings_id " + 
				"HAVING count(*) < 2 " + 
				"ORDER BY rand()LIMIT 1;";
		try (Connection conexion = conn.conectar();
				PreparedStatement  query = (PreparedStatement) conexion.prepareStatement(queryExce);){
			
			query.setString(1, dateFormatSimple.format(date));
			rs = query.executeQuery();
			
			while (rs.next() ) {
				list = rs.getInt("ca.campaings_id");
			}
		}catch(SQLException e) {
			System.err.println(e);
		}
		return list;
	}
	
	public Campaing getIdCampaing() {
		Campaing camp = null;
		
		String queryExce = "SELECT * FROM "+TABLE_NAME
				+ " WHERE name = ?";
		try (Connection conexion = conn.conectar();
				PreparedStatement  query = (PreparedStatement) conexion.prepareStatement(queryExce);){
			
			query.setString(1, getName());
			rs = query.executeQuery();
			
			while (rs.next() ) {
				camp = new Campaing();
				camp.setCampaings_id(rs.getInt("campaings_id"));
				camp.setName(rs.getString("name"));
				camp.setActive(rs.getBoolean("active"));
				camp.setCampaing(rs.getBoolean("isCampaing"));
				camp.setLink(rs.getString("link"));
			}
		}catch(SQLException e) {
			System.err.println(e);
		}
		
		
		return camp;
	}
	public int getCampaings_id() {
		return campaings_id;
	}

	public void setCampaings_id(int campaings_id) {
		this.campaings_id = campaings_id;
	}

	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isCampaing() {
		return isCampaing;
	}

	public void setCampaing(boolean isCampaing) {
		this.isCampaing = isCampaing;
	}

	public String getCreated_at() {
		return created_at;
	}


	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	

}
