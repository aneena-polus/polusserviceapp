package com.polus.service.app.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.polus.service.app.entities.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmployeeSearchDao {

	public final EntityManager entityManager;

	public List<Employee> getEmployee(String username, String password) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
		// SELECT * FROM employee
		Root<Employee> root = criteriaQuery.from(Employee.class);
		// (root)username LIKE 'maneshms'
		Predicate usernamePredicate = criteriaBuilder.like(root.get("username"), username);
		// (root)password LIKE 'maneshmspassword'
		Predicate passwordPredicate = criteriaBuilder.like(root.get("password"), password);
		// (root)firstname LIKE 'Manesh' OR lastname LIKE 'M S'
		Predicate usernameAndPasswordPredicate = criteriaBuilder.and(usernamePredicate, passwordPredicate);
		// SELECT * FROM employee WHERE username LIKE 'maneshms' AND password LIKE 'maneshmspassword';
		criteriaQuery.where(usernameAndPasswordPredicate);
		TypedQuery<Employee> query = entityManager.createQuery(criteriaQuery); //Final query is in query
		return query.getResultList();
	}
}