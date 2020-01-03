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

public class Post implements Model {

	private final String TABLE_NAME ="posts";
	private int posts_id;
	private int users_id;
	private int campaings_id;
	private int tasks_id;
	private String created_at;
	private Date date = new Date();
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Conexion conn = new Conexion();
	
	Statement st;
	ResultSet rs;
	public void insert() {
		setCreated_at(dateFormat.format(date));
		String insert = "INSERT INTO "+TABLE_NAME+"(users_id,campaings_id,tasks_id,created_at) "
				+ " VALUE (?,?,?,?);";
		try (Connection conexion = conn.conectar();
				PreparedStatement exe = conexion.prepareStatement(insert);){
			
			
			exe.setInt(1, getUsers_id());
			exe.setInt(2,getCampaings_id());
			exe.setInt(3, getTasks_id());
			exe.setString(4, getCreated_at());
			
			exe.executeUpdate();

		}catch(SQLException e) {
			System.err.println(e);
		}
	}

	public boolean getUserPost() {
		
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		c.add(Calendar.DAY_OF_MONTH, -2);
		Date twoDaysAgo = c.getTime();
		String queryExce = "SELECT * FROM users us " + 
				"WHERE EXISTS (SELECT 1 FROM "+TABLE_NAME+" pt WHERE pt.users_id = us.users_id " + 
				"AND DATE(pt.created_at) BETWEEN ? AND ? " + 
				"AND pt.users_id = ?) " + 
				"AND us.active = 1 LIMIT 1;";
		try (Connection conexion = conn.conectar();
				PreparedStatement  query = conexion.prepareStatement(queryExce);){
			
			
			query.setString(1, dateFormat.format(twoDaysAgo));
			query.setString(2, dateFormat.format(now));
			query.setInt(3, getUsers_id());
			query.setInt(4, 1);
			rs = query.executeQuery();
			if(rs.next()) return true;
		}catch(SQLException e) {
			e.getStackTrace();
		}
		
		return false;
		
	}
	
	public List<Integer> getAllPostUser(){
		List<Integer> list = new ArrayList<Integer>();
		
		String queryExce = "SELECT DISTINCT(ca.campaings_id) as tareas FROM "+TABLE_NAME+" pt " + 
				"INNER JOIN campaings ca ON ca.campaings_id = pt.campaings_id " + 
				"WHERE pt.users_id = ?;";
		try (Connection conexion = conn.conectar();
				PreparedStatement  query = conexion.prepareStatement(queryExce);){
			
		
		query.setInt(1, getUsers_id());
		rs = query.executeQuery();
			
		
		while(rs.next()) {
			list.add(rs.getInt("tareas"));
		}
		}catch(SQLException e) {
			e.getStackTrace();
		}
		return list;
	}
	
	
	public int getPosts_id() {
		return posts_id;
	}

	public void setPosts_id(int posts_id) {
		this.posts_id = posts_id;
	}

	public int getUsers_id() {
		return users_id;
	}

	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}

	public int getCampaings_id() {
		return campaings_id;
	}

	public void setCampaings_id(int campaings_id) {
		this.campaings_id = campaings_id;
	}

	public int getTasks_id() {
		return tasks_id;
	}

	public void setTasks_id(int tasks_id) {
		this.tasks_id = tasks_id;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

}
