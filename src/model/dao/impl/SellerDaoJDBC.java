package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{
	
	private Connection connection;
	
	public SellerDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
	   PreparedStatement prepared = null;
	   ResultSet result = null;
	   try {
		   prepared = connection.prepareStatement("SELECT seller.*,department.Name as DepName "
		   										  + "FROM seller INNER JOIN department "
		   										  + "ON seller.DepartmentId = department.Id "
		   										  + "WHERE seller.Id = ?");
		   prepared.setInt(1, id);
		   result = prepared.executeQuery();
		   
		   if(result.next()) {
			   Department dep = new Department();
			   dep.setId(result.getInt("DepartmentId"));
               dep.setName(result.getString("DepName")); 
               
               Seller obj = new Seller();
               obj.setId(result.getInt("Id"));
               obj.setName(result.getString("Name"));
               obj.setEmail(result.getString("Email"));
               obj.setBaseSalary(result.getDouble("BaseSalary"));
               obj.setBirthDate(result.getDate("BirthDate"));
               obj.setDepartment(dep);
               
               return obj;
		   }
		   
		   return null;
	   }
	   catch (SQLException e) {
		   throw new DbException(e.getMessage());
	   }
	   finally {
		   DB.closeStatement(prepared);
		   DB.closeResultSet(result);
	   }
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
