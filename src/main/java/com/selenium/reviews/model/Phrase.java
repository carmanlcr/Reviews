package com.selenium.reviews.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.selenium.reviews.interfaces.Model;


public class Phrase implements Model {

	private int phrases_id;
	private String phrase;
	private boolean active;
	private String created_at;
	private int campaings_id;
	private static Conexion conn = new Conexion();
	private Date date = new Date();
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
	Statement st;
	ResultSet rs;
	
	public void insert() throws SQLException {
		Connection conexion = conn.conectar();
		setCreated_at(dateFormat.format(date));
		
		try {
			String insert = "INSERT INTO phrases(phrase,created_at,campaings_id) VALUE "
					+ " ('"+getPhrase()+"','"+getCreated_at()+"',"+getCampaings_id()+");";
			st = (Statement) conexion.createStatement();
			st.executeUpdate(insert);
			conexion.close();
		}catch(SQLException e) {
			System.err.println(e);
		}
	}
	
public String getPhraseRandom() throws SQLException{
		
		String list = "";
		Connection conexion = conn.conectar();
		Statement st = (Statement) conexion.createStatement();
		ResultSet rs = null;
		try {
			
			String queryExce = "SELECT ph.phrase FROM phrases ph "
					+ "WHERE ph.active = ? AND ph.campaings_id = ? "
					+ "ORDER BY RAND() LIMIT 1;";
			PreparedStatement  query = (PreparedStatement) conexion.prepareStatement(queryExce);
			query.setInt(1, 1);
			query.setInt(2, getCampaings_id());
			rs = query.executeQuery();

			while (rs.next() ) {
               list +=  rs.getString("phrase");
			}
		}catch(Exception e) {
			System.err.println(e);
		}finally {
			st.close();
			conexion.close();
		}
		
		return list;
 	}

	public int getPhrases_id() {
		return phrases_id;
	}

	public void setPhrases_id(int phrases_id) {
		this.phrases_id = phrases_id;
	}

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
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

	public int getCampaings_id() {
		return campaings_id;
	}

	public void setCampaings_id(int campaings_id) {
		this.campaings_id = campaings_id;
	}
	
	

}
