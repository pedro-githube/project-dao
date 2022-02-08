package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		
	    System.out.println("==== TEST 1: department insert ====");
		Department newDepartment = new Department(null, "Vehicles");
		departmentDao.insert(newDepartment);
		System.out.println("Inserted ! new id = " + newDepartment.getId()); 
		
		System.out.println("\n==== TEST 2: findById  ====");
		Department dep = departmentDao.findById(9);
		System.out.println(dep); 
		
		System.out.println("\n==== TEST 3: findByAll  ====");
		List<Department> list = departmentDao.findAll();
		for (Department department : list) {
			System.out.println(department);
		} 
		
		System.out.println("\n==== TEST 4: update ====");
		Department department = departmentDao.findById(1);
		department.setName("Keyboards");
		departmentDao.update(department);
		System.out.println(department); 
		
		System.out.println("\n==== TEST 5: delete ====");
		System.out.print("Enter id for delete department: ");
		int id = scanner.nextInt();
		departmentDao.deleteById(id);
		System.out.println("Delete completed !");
		

        scanner.close();
	}

}
