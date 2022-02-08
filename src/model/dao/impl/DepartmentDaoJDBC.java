package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{
	
	private Connection connection;

	public DepartmentDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement prepared = null;
		
		try {
			prepared = connection.prepareStatement("INSERT INTO department "
												   + "(Name)"
												   + " VALUES"
												   + "(?)", Statement.RETURN_GENERATED_KEYS);
			prepared.setString(1, obj.getName());
		    int rowsAffected = prepared.executeUpdate();
			
		    if (rowsAffected > 0) {
		    	ResultSet result = prepared.getGeneratedKeys();
		    	while(result.next()) {
		    		int id = result.getInt(1);
		    		obj.setId(id);
		    	}
		      DB.closeResultSet(result);
		    }
		    
		}
		catch(SQLException e) {
			e.getStackTrace();
		}
	}

	@Override
	public void update(Department obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Department findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Department> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
