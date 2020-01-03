package com.selenium.reviews.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.selenium.reviews.interfaces.Model;

public class Task implements Model {

	private final String TABLE_NAME = "tasks";
	private int tasks_id;
	private String date_publication;
	private String comentario;
	private int campaings_id;
	private boolean active;
	private String created_at;
	private Date date = new Date();
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Conexion conn = new Conexion();
	
	@Override
	public void insert() throws SQLException {
		setCreated_at(dateFormat.format(date));
		String query = "INSERT INTO "+TABLE_NAME+ "(date_publication,comentario, campaings_id,created_at)"
				+ " VALUES (?,?,?,?);";
		try (Connection conexion = conn.conectar();
				PreparedStatement pre = conexion.prepareStatement(query)){
			pre.setString(1, getDate_publication());
			pre.setString(2, getComentario());
			pre.setInt(3, getCampaings_id());
			pre.setString(4, getCreated_at());
			pre.executeUpdate();
		}catch (SQLException e) {
			e.getStackTrace();
		}
	}

	public List<Task> getTasksOfTheDay() throws SQLException{
		
		List<Task> list = new ArrayList<Task>();
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		ResultSet rs = null;
		String sql = "SELECT * FROM "+TABLE_NAME+
				" WHERE active = 1 AND date_publication = ?"
				+ "AND tasks_id NOT IN(SELECT tasks_id FROM "
				+ "posts p)";
		try(Connection conexion = conn.conectar();
				PreparedStatement pre = conexion.prepareStatement(sql);){
			pre.setString(1, dateFormat1.format(date));
			
			
			rs = pre.executeQuery();
			
			while(rs.next()) {
				Task task = new Task();
				task.setTasks_id(rs.getInt("tasks_id"));
				task.setComentario(rs.getString("comentario"));
				task.setDate_publication(rs.getString("date_publication"));
				task.setCampaings_id(rs.getInt("campaings_id"));
				task.setActive(rs.getBoolean("active"));
				list.add(task);
			}
		}catch(SQLException e) {
			e.getStackTrace();
		}
		return list;
	}

	public int getTasks_id() {
		return tasks_id;
	}


	public void setTasks_id(int tasks_id) {
		this.tasks_id = tasks_id;
	}


	public String getDate_publication() {
		return date_publication;
	}


	public void setDate_publication(String date_publication) {
		this.date_publication = date_publication;
	}


	public String getComentario() {
		return comentario;
	}


	public void setComentario(String comentario) {
		this.comentario = comentario;
	}


	public int getCampaings_id() {
		return campaings_id;
	}


	public void setCampaings_id(int campaings_id) {
		this.campaings_id = campaings_id;
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

}
