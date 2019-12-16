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

import com.mysql.jdbc.Statement;
import com.selenium.reviews.interfaces.Model;

public class Campaing implements Model {
	
	private int campaings_id;
	private String link;
	private String name;
	private String created_at;
	private static Conexion conn = new Conexion();
	private Date date = new Date();
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
	private DateFormat dateFormatSimple = new SimpleDateFormat("yyyy-MM-dd");
	Statement st;
	ResultSet rs;
	
	public void insert() throws SQLException{
		setCreated_at(dateFormat.format(date));
		Connection conexion = conn.conectar();
		try {
			String insert = "INSERT INTO campaings(link,name,created_at) "
					+ " VALUE ('"+getLink()+"', '"+getName()+"', '"+getCreated_at()+"');";
			st = (Statement) conexion.createStatement();
			st.executeUpdate(insert);
			conexion.close();
		}catch(SQLException e) {
			System.err.println(e);
		}
	}

	public List<Campaing> getAllActiveWithPhrase()  {
		ArrayList<Campaing> list = new ArrayList<Campaing>();
		Connection conexion = conn.conectar();
		ResultSet rs;
		try {
			String queryExce = "SELECT * FROM reviews.campaings ca " + 
					"INNER JOIN reviews.phrases ph ON ca.campaings_id = ph.campaings_id AND ph.active = ? "
					+"WHERE ca.active = ?;";
			PreparedStatement  query = (PreparedStatement) conexion.prepareStatement(queryExce);
			query.setInt(1, 1);
			query.setInt(2, 1);
			rs = query.executeQuery();


			while (rs.next() ) {
				Campaing camp = new Campaing();
				camp.setCampaings_id(rs.getInt("ca.campaings_id"));
				camp.setName(rs.getString("ca.name"));
				camp.setLink(rs.getString("ca.link"));
				camp.setCreated_at(rs.getString("ca.created_at"));
				list.add(camp);
               
			}
			conexion.close();
		}catch(SQLException e) {
			System.err.println(e);
		}finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public List<Campaing> getAllActive()  {
		ArrayList<Campaing> list = new ArrayList<Campaing>();
		Connection conexion = conn.conectar();
		ResultSet rs;
		try {
			String queryExce = "SELECT * FROM reviews.campaings ca " 
					+"WHERE ca.active = ?;";
			PreparedStatement  query = (PreparedStatement) conexion.prepareStatement(queryExce);
			query.setInt(1, 1);
			rs = query.executeQuery();


			while (rs.next() ) {
				Campaing camp = new Campaing();
				camp.setCampaings_id(rs.getInt("ca.campaings_id"));
				camp.setName(rs.getString("ca.name"));
				camp.setLink(rs.getString("ca.link"));
				camp.setCreated_at(rs.getString("ca.created_at"));
				list.add(camp);
               
			}
			conexion.close();
		}catch(SQLException e) {
			System.err.println(e);
		}finally {
			try {
				conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}

	public int getCampaingDiferent(String values){
		int list = 0;
		Connection conexion = conn.conectar();
		try {
			
			String queryExce = "SELECT ca.campaings_id FROM campaings ca " + 
					"LEFT JOIN posts pt ON pt.campaings_id = ca.campaings_id AND DATE(pt.created_at) = ? " + 
					"WHERE ca.campaings_id NOT IN ("+values+") " + 
					"GROUP BY ca.campaings_id " + 
					"HAVING count(*) < 3 " + 
					"ORDER BY rand()LIMIT 1;";
			
			PreparedStatement  query = (PreparedStatement) conexion.prepareStatement(queryExce);
			query.setString(1, dateFormatSimple.format(date));
			rs = query.executeQuery();
			
			while (rs.next() ) {
				list = rs.getInt("ca.campaings_id");
			}
			conexion.close();
			return list;
		}catch(SQLException e) {
			System.err.println(e);
		}
		return list;
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


	public String getCreated_at() {
		return created_at;
	}


	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	

}
