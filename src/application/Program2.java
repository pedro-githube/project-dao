package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		
	   /* System.out.println("==== TEST 1: department insert ====");
		Department newDepartment = new Department(null, "Vehicles");
		departmentDao.insert(newDepartment);
		System.out.println("Inserted ! new id = " + newDepartment.getId()); */
		
		System.out.println("==== TEST 2: findById  ====");
		Department dep = departmentDao.findById(9);
		System.out.println(dep);
		
		


	}

}
