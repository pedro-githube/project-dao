package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
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
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(prepared);
		}
	}

	@Override
	public void update(Department obj) {
		PreparedStatement prepared = null;
		
		try {
			prepared = connection.prepareStatement("UPDATE department SET Name = ? "
					                               + "WHERE Id = ?");
			
			prepared.setString(1, obj.getName());
			prepared.setInt(2, obj.getId());
			
			prepared.executeUpdate();
		} 
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(prepared);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement prepared = null;
		try {
			prepared = connection.prepareStatement("DELETE FROM department WHERE Id = ?");
			prepared.setInt(1, id);
			int rowsAffected = prepared.executeUpdate();
			
		    if (rowsAffected == 0) {
		    	throw new DbException("non-existent id");
		    }
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(prepared);
		}
	}

	@Override
	public Department findById(Integer id) {
		 PreparedStatement prepared = null;
		 ResultSet result = null;
		 try {
			 prepared = connection.prepareStatement("SELECT * FROM department "
			 							 			+ "WHERE Id = ?;");
			 prepared.setInt(1, id);
			 result = prepared.executeQuery();
			 
		  if(result.next()) {	 
			 Department department = new Department();
			 department.setId(id);
			 department.setName(result.getString("Name"));
			 return department;
		  }
			 return null;
		 }
		 catch(SQLException e) {
			 throw new DbException(e.getMessage());
		 }
		 finally {
				DB.closeStatement(prepared);
				DB.closeResultSet(result);
			}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement prepared = null;
		ResultSet result = null;
		try {
			prepared = connection.prepareStatement("SELECT * FROM department");
			
			result = prepared.executeQuery();
			List<Department> list = new ArrayList<>();
			
			while(result.next()) {
				Department department = new Department(result.getInt("Id"), result.getString("Name"));
				list.add(department);
			}
			
		  return list;
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(prepared);
			DB.closeResultSet(result);
		}
	}
	
	

}
