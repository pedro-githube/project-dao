package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			   Department dep = instantiateDepartment(result);
               Seller obj = instantiateSeller(result, dep);
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

	private Seller instantiateSeller(ResultSet result, Department dep) throws SQLException {
		Seller obj = new Seller();
        obj.setId(result.getInt("Id"));
        obj.setName(result.getString("Name"));
        obj.setEmail(result.getString("Email"));
        obj.setBaseSalary(result.getDouble("BaseSalary"));
        obj.setBirthDate(result.getDate("BirthDate"));
        obj.setDepartment(dep);
        return obj;
	}

	private Department instantiateDepartment(ResultSet result) throws SQLException {
		Department dep = new Department();
		dep.setId(result.getInt("DepartmentId"));
        dep.setName(result.getString("DepName")); 
        return dep;
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		   PreparedStatement prepared = null;
		   ResultSet result = null;
		   try {
			   prepared = connection.prepareStatement("SELECT seller.*,department.Name as DepName "
			   										  + "FROM seller INNER JOIN department "
			   										  + "ON seller.DepartmentId = department.Id "
			   										  + "WHERE DepartmentId = ? "
			   										  + "ORDER BY Name");
			   prepared.setInt(1, department.getId());
			   result = prepared.executeQuery();
			   
			   List<Seller> list = new ArrayList<>();
			   Map<Integer, Department> map = new HashMap<>();
			   
			   while(result.next()) {
				   
				   Department dep = map.get(result.getInt("DepartmentId"));
				   
	               if (dep == null) {
	            	   dep = instantiateDepartment(result);
	            	   map.put(result.getInt("DepartmentId"), dep);
	               }
	               
	               Seller obj = instantiateSeller(result, dep);
	               list.add(obj); 
			   }
			   
			   return list;
		   }
		   catch (SQLException e) {
			   throw new DbException(e.getMessage());
		   }
		   finally {
			   DB.closeStatement(prepared);
			   DB.closeResultSet(result);
		   }
	}

}
