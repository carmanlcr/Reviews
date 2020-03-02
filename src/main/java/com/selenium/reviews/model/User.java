package com.selenium.reviews.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.selenium.reviews.interfaces.Model;

public class User implements Model {
	
	private final String TABLE_NAME = "users";
	private int users_id;
	private String email;
	private String password;
	private boolean active;
	private String created_at;
	private int vpn_id;
	private Date date = new Date();
	private DateFormat dateFormatTimeStamp = new SimpleDateFormat("yyyy-MM-dd H:m:s");
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static Conexion conn = new Conexion();
	Statement st;
	ResultSet rs;
	
	public void insert() throws SQLException{
		setCreated_at(dateFormatTimeStamp.format(date));
		String insert = "INSERT INTO "+TABLE_NAME+"(email,password,vpn_id,created_at) "
				+ " VALUE (?,?,?,?);";
		try (Connection conexion = conn.conectar();
				PreparedStatement  query = conexion.prepareStatement(insert);){
			
			query.setString(1, getEmail());
			query.setString(2, getPassword());
			query.setInt(3,getVpn_id());
			query.setString(4,getCreated_at());
			query.executeUpdate();
		}catch(SQLException e) {
			System.err.println(e);
		}
	}
	
	
	public List<User> getUsers(){
		List<User> listU = new ArrayList<User>();
		
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		c.add(Calendar.DAY_OF_MONTH, -2);
		Date twoDaysAgo = c.getTime();
		String queryExce = "SELECT u.users_id,u.email,u.password,u.active,u.vpn_id FROM reviews.users u WHERE u.users_id NOT IN " + 
				"(SELECT p.users_id FROM reviews.posts p WHERE p.campaings_id IN " + 
				"(SELECT t.campaings_id FROM reviews.tasks t WHERE DATE(t.date_publication) = ? " + 
				"AND t.campaings_id NOT IN (SELECT t.campaings_id FROM reviews.tasks t " + 
				"INNER JOIN reviews.posts p ON p.tasks_id = t.tasks_id " + 
				"WHERE DATE(p.created_at) = ?))) " + 
				"AND u.users_id NOT IN (SELECT p.users_id FROM reviews.posts p WHERE DATE(p.created_at) >= ?) " + 
				"AND u.email IS NOT NULL AND u.password IS NOT NULL " +
				"GROUP BY u.users_id,u.email,u.password,u.active,u.vpn_id;";
		try (Connection conexion = conn.conectar();
				PreparedStatement  query = conexion.prepareStatement(queryExce);){
			
			
			query.setString(1, dateFormat.format(now));
			query.setString(2, dateFormat.format(now));
			query.setString(3, dateFormat.format(twoDaysAgo));
			
			rs = query.executeQuery();
			
			while(rs.next()) {
				User u = new User();
				u.setUsers_id(rs.getInt("u.users_id"));
				u.setEmail(rs.getString("u.email"));
				u.setPassword(rs.getString("u.password"));
				u.setActive(rs.getBoolean("u.active"));
				u.setVpn_id(rs.getInt("u.vpn_id"));
				listU.add(u);
			}
			
		}catch(SQLException e) {
			
		}
		return listU;
	}
	

	public int getUsers_id() {
		return users_id;
	}

	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public int getVpn_id() {
		return vpn_id;
	}

	public void setVpn_id(int vpn_id) {
		this.vpn_id = vpn_id;
	}

}
