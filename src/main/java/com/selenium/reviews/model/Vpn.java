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

public class Vpn implements Model{

	private final String TABLE_NAME ="vpn";
	private int vpn_id;
	private String name;
	private boolean activo;
	private String created_at;
	private Date date = new Date();
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static Conexion conn = new Conexion();
	
	public List<String> getAllActive() {

		Connection conexion = conn.conectar();
		List<String> list = new ArrayList<String>();
		Statement st = null;
	    ResultSet rs = null;
		try {
			st = (Statement) conexion.createStatement();
			
			
			rs = st.executeQuery("SELECT name FROM "+TABLE_NAME+" WHERE active = 1 ORDER BY name ASC");
			
			list.add("Seleccione");
			while (rs.next() ) {
				list.add(rs.getString("name"));
               
			}
			conexion.close();
		}catch(SQLException e) {
			System.err.println(e);
		}
		
		return list;
	}
	
	public String getNameVPN(String name) throws SQLException {
		String nameVpn = "";
		Statement st = null;
		ResultSet rs = null;
		Connection conexion = conn.conectar();
		try {
			
			st = (Statement) conexion.createStatement();
			
			
			rs = st.executeQuery("SELECT * FROM "+TABLE_NAME+" WHERE name = '"+nameVpn+"';");
			
			while (rs.next() ) {
				nameVpn = rs.getString("name");
               
			}
			conexion.close();
		}catch(Exception e) {
			System.err.println(e);
		}finally{
			st.close();
			conexion.close();
		}
		
		return nameVpn;
		
	}
	
	public int getFind(String name) throws SQLException {
		int idVpn = 0;
		ResultSet rs = null;
		Statement st = null;
		Connection conexion = conn.conectar();
		try {
			
			st = (Statement) conexion.createStatement();
			String query = "SELECT * FROM "+TABLE_NAME+" WHERE name = '"+name+"';";
			
			rs = st.executeQuery(query);
			
			while (rs.next() ) {
				idVpn = rs.getInt("vpn_id");
               
			}
			conexion.close();
		}catch(Exception e) {
			System.err.println(e);
		}finally {
			rs.close();
			conexion.close();
		}
			
		
		
		return idVpn;
	}
	
	public String getVpnUser() {
		String idVpn = "";
		ResultSet rs = null;
		setCreated_at(dateFormat.format(date));
		Connection conexion = conn.conectar();
		try {
			
			String queryExce = "SELECT name FROM "+TABLE_NAME+" WHERE vpn_id = ?;";
			PreparedStatement  query = (PreparedStatement) conexion.prepareStatement(queryExce);
			query.setInt(1, getVpn_id());
			rs = query.executeQuery();
			
			while (rs.next() ) {
				idVpn = rs.getString("name");
               
			}
			conexion.close();
			return idVpn;
		}catch(SQLException e) {
			System.err.println(e);
		}
			
		
		
		return idVpn;
	}
	
	public int findOrCreate(String name) throws SQLException {
		int idVpn = 0;
		ResultSet rs = null;
		Statement st = null;
		Connection conexion = conn.conectar();
		try {
			
			st = (Statement) conexion.createStatement();
			String query = "SELECT * FROM "+TABLE_NAME+" WHERE UPPER(name) = '"+name.toUpperCase()+"';";
			
			rs = st.executeQuery(query);
			
			while (rs.next() ) {
				idVpn = rs.getInt("vpn_id");
               
			}
		}catch(Exception e) {
			System.err.println(e);
		}finally {
			rs.close();
			conexion.close();
		}
			
		if(idVpn==0) {
			setName(name);
			insert();
		}
		
		return idVpn;
	}
	
	public void insert() {
		Connection conexion = conn.conectar();

		try {
			String insert = "INSERT INTO "+TABLE_NAME+"(name,created_at) "
					+ "VALUES (?,?);";
			PreparedStatement exe = conexion.prepareStatement(insert);
			exe.setString(1, getName());
			exe.setString(2, getCreated_at());
			exe.executeUpdate();
			conexion.close();
		} catch(SQLException e)  {
			System.err.println(e);
		}
			

		
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	
}
