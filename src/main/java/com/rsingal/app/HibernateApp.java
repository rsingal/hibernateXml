package com.rsingal.app;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.rsingal.entity.Account;
import com.rsingal.entity.Address;
import com.rsingal.entity.Company;
import com.rsingal.entity.EmpTrngMappingExtended;
import com.rsingal.entity.EmpTrngMappingExtendedId;
import com.rsingal.entity.Employee;
import com.rsingal.entity.Training;
import com.rsingal.util.HibernateUtil;

//	Hibernate Mapping:
//	-----------------
//						One to One				One to Many
//			Address	 -------						--------- Account
//							|						|
//							-------- Employee -------
//							|						|
//			Training -------						--------- Company
//						Many to Many			Many to One
//
public class HibernateApp {

	private static final Logger logger = LogManager.getLogger(HibernateApp.class);
	private static Session session = null;
	private Integer accountNumberGen = 1000;

	public static void main(String[] args) {
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			HibernateApp hibernateApp = new HibernateApp();
			// Create Company and Training/s
			Integer companyId = hibernateApp.createCompany("Ericsson");
			List<Integer> trainingsList = hibernateApp.createTrainings();
			// Create Employee/s
			Integer idEmployee1 = hibernateApp.createEmployee("Ramesh", "Gurgaon", companyId, trainingsList);
			Integer idEmployee2 = hibernateApp.createEmployee("Manish", "Delhi", companyId, trainingsList);
			// Update Employee1
			hibernateApp.updateEmployee(idEmployee1, "Ramesh Singal");
			// Delete Employee/s
			hibernateApp.deleteEmployee(idEmployee1);
			hibernateApp.deleteEmployee(idEmployee2);
			// Delete Company and Training/s
			hibernateApp.deleteCompany(companyId);
			trainingsList.stream().forEach(trainingId -> hibernateApp.deleteTraining(trainingId));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			HibernateUtil.shutdown();
		}
	}

	private Integer createEmployee(String name, String addr, Integer companyId, List<Integer> trainings) {
		Transaction txn = null;
		Integer employeeID = null;
		try {
			txn = session.beginTransaction();
			final Employee employee = new Employee(name);
			// One to One Mapping: Employee <--> Address
			Address address = new Address(addr);
			employee.setAddress(address);
			// Many to One Mapping: Employee <--> Company
			Company company = findCompany(companyId);
			employee.setCompany(company);
			// Many to Many Mapping: Employee <--> Training via Employee_Trng_Mapping table
			trainings.stream().forEach(trainingId -> {
				Training training = findTraining(trainingId);
				employee.getTrainings().add(training);
			});
			// Create Employee/Address/Company/Training
			employeeID = (Integer) session.save(employee);
			// Many to Many Mapping: Employee <--> Training via Employee_Trng_Mapping_Extended table
			trainings.stream().forEach(trainingId -> {
				Training training = findTraining(trainingId);
				EmpTrngMappingExtended empTrngMappingExt = new EmpTrngMappingExtended(new EmpTrngMappingExtendedId(employee, training), 0);
				employee.getEmpTrngMappingExts().add(empTrngMappingExt);
				session.save(empTrngMappingExt);
			});
			// One to Many Mapping: Employee <--> Account
			List<Account> accts = createAccounts();
			accts.stream().forEach(acct -> {
				acct.setEmployee(employee);
				employee.getAccounts().add(acct);
				session.save(acct);
			});
			//
			txn.commit();
			//
			Employee employeeDB = findEmployee(employeeID);
			logger.info("Created Employee: {}", employeeDB);
		} catch (HibernateException e) {
			if (txn != null)
				txn.rollback();
			e.printStackTrace();
		}
		return employeeID;
	}

	@SuppressWarnings("all")
	private Employee findEmployee(Integer employeeID) {
		Transaction txn = null;
		Employee employee = null;
		try {
			txn = session.beginTransaction();
			Query query = session.createQuery("FROM Employee where id = :empID");
			query.setParameter("empID", employeeID);
			employee = (Employee) query.getSingleResult();
			txn.commit();
		} catch (HibernateException e) {
			if (txn != null)
				txn.rollback();
			e.printStackTrace();
		}
		return employee;
	}

	private void updateEmployee(Integer employeeID, String name) {
		Transaction txn = null;
		try {
			txn = session.beginTransaction();
			Employee employee = session.get(Employee.class, employeeID);
			employee.setEmployeeName(name);
			// Removing HDFC account mapping from Employee. To see the DB update, comment out HibernateApp.deleteEmployee()
			// If Employee.hbm.xml (set name="accounts") has inverse="true", ACCOUNT table will be responsible to take care of the relationship not the EMPLOYEE
			// so removing HDFC account from set under Employee and updating Employee in DB will not try to clear(NULL) the ACCOUNT.EMPLOYEE_ID_EMPLOYEE.
			// Set inverse="false", now EMPLOYEE table will be owner for maintaining relationships and it will make the ACCOUNT.EMPLOYEE_ID_EMPLOYEE as
			// null for HDFC account.
			employee.getAccounts().removeIf(acct -> acct.getBankName() == "HDFC");
			// Remove mapping(in table Employee_Trng_Mapping) of this employee with training named "Cassandra"
			employee.getTrainings().removeIf(training -> training.getTrainingName() == "Cassandra");
			// Remove mapping(in table Employee_Trng_Mapping_Extended) of this employee with training named "Cassandra"
			employee.getEmpTrngMappingExts().removeIf(empTrngMappingExt -> empTrngMappingExt.getTraining().getTrainingName() == "Cassandra");
			// Update mapping(in table Employee_Trng_Mapping_Extended) of this employee with training named "Java" as status = 1
			employee.getEmpTrngMappingExts().stream().//
					filter(empTrngMappingExt -> empTrngMappingExt.getTraining().getTrainingName() == "Java8").//
					forEach(empTrngMappingExt -> empTrngMappingExt.setStatus(1));
			session.update(employee);
			txn.commit();
			employee = findEmployee(employeeID);
			logger.info("Updated Employee: {}", employee);
		} catch (HibernateException e) {
			if (txn != null)
				txn.rollback();
			e.printStackTrace();
		}
	}

	private void deleteEmployee(Integer employeeID) {
		Transaction txn = null;
		try {
			txn = session.beginTransaction();
			Employee employee = session.get(Employee.class, employeeID);
			session.delete(employee);
			txn.commit();
			logger.info("Deleted employee with id: {}", employeeID);
		} catch (HibernateException e) {
			if (txn != null)
				txn.rollback();
			e.printStackTrace();
		}
	}

	private Integer createCompany(String name) {
		Transaction txn = null;
		Integer companyID = null;
		try {
			txn = session.beginTransaction();
			Company company = new Company(name);
			companyID = (Integer) session.save(company);
			txn.commit();
		} catch (HibernateException e) {
			if (txn != null)
				txn.rollback();
			e.printStackTrace();
		}
		return companyID;
	}

	@SuppressWarnings("all")
	private Company findCompany(Integer companyID) {
		List<Company> companyList = null;
		Query query = session.createQuery("FROM Company where companyId = :compId");
		query.setParameter("compId", companyID);
		return (Company) query.getSingleResult();
	}

	private void deleteCompany(Integer companyID) {
		Transaction txn = null;
		try {
			txn = session.beginTransaction();
			Company company = session.get(Company.class, companyID);
			session.delete(company);
			txn.commit();
		} catch (HibernateException e) {
			if (txn != null)
				txn.rollback();
			e.printStackTrace();
		}
	}

	private List<Integer> createTrainings() {
		List<Integer> trainingsList = new ArrayList<Integer>();
		trainingsList.add(createTraining("Java8"));
		trainingsList.add(createTraining("Cassandra"));
		return trainingsList;
	}

	private Integer createTraining(String name) {
		Transaction txn = null;
		Integer trainingID = null;
		try {
			txn = session.beginTransaction();
			Training training = new Training(name);
			trainingID = (Integer) session.save(training);
			txn.commit();
		} catch (HibernateException e) {
			if (txn != null)
				txn.rollback();
			e.printStackTrace();
		}
		return trainingID;
	}

	@SuppressWarnings("all")
	private Training findTraining(Integer trainingID) {
		List<Company> companyList = null;
		Query query = session.createQuery("FROM Training where trainingId = :trngId");
		query.setParameter("trngId", trainingID);
		return (Training) query.getSingleResult();
	}

	private void deleteTraining(Integer trainingID) {
		Transaction txn = null;
		try {
			txn = session.beginTransaction();
			Training company = session.get(Training.class, trainingID);
			session.delete(company);
			txn.commit();
		} catch (HibernateException e) {
			if (txn != null)
				txn.rollback();
			e.printStackTrace();
		}
	}

	private List<Account> createAccounts() {
		List<Account> accts = new ArrayList<Account>();
		accts.add(new Account("ICICI", ++accountNumberGen));
		accts.add(new Account("HDFC", ++accountNumberGen));
		return accts;
	}
}
