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
	
	private int posts_id;
	private int users_id;
	private int campaings_id;
	private String created_at;
	private Date date = new Date();
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
	private static Conexion conn = new Conexion();
	
	Statement st;
	ResultSet rs;
	public void insert() {
		setCreated_at(dateFormat.format(date));
		Connection conexion = conn.conectar();
		try {
			String insert = "INSERT INTO posts(users_id,campaings_id,created_at) "
					+ " VALUE ("+getUsers_id()+", "+getCampaings_id()+",'"+getCreated_at()+"');";
			st = (Statement) conexion.createStatement();
			st.executeUpdate(insert);

			conexion.close();
		}catch(SQLException e) {
			System.err.println(e);
		}
	}

	public boolean getUserPost() {
		Connection conexion = conn.conectar();
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		c.add(Calendar.DAY_OF_MONTH, -2);
		Date twoDaysAgo = c.getTime();
		try {
			String queryExce = "SELECT * FROM users us " + 
					"WHERE EXISTS (SELECT 1 FROM posts pt WHERE pt.users_id = us.users_id " + 
					"AND DATE(pt.created_at) BETWEEN ? AND ? " + 
					"AND pt.users_id = ?) " + 
					"AND us.active = 1 LIMIT 1;";
			PreparedStatement  query = (PreparedStatement) conexion.prepareStatement(queryExce);
			query.setString(1, dateFormat.format(twoDaysAgo));
			query.setString(2, dateFormat.format(now));
			query.setInt(3, getUsers_id());
			query.setInt(4, 1);
			
			rs = query.executeQuery();
			conexion.close();
			if(rs.next()) return true;
		}catch(SQLException e) {
			e.getStackTrace();
		}
		
		return false;
		
	}
	
	public List<Integer> getAllPostUser(){
		List<Integer> list = new ArrayList<Integer>();
		Connection conexion = conn.conectar();
		
		try {
			String queryExce = "SELECT DISTINCT(ca.campaings_id) as tareas FROM posts pt " + 
					"INNER JOIN campaings ca ON ca.campaings_id = pt.campaings_id " + 
					"WHERE pt.users_id = ?;";
	
		PreparedStatement  query = (PreparedStatement) conexion.prepareStatement(queryExce);
		query.setInt(1, getUsers_id());
		rs = query.executeQuery();
			
		
		while(rs.next()) {
			list.add(rs.getInt("tareas"));
		}
		conexion.close();
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

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

}
