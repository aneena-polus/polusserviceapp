package com.polus.service.app.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.polus.service.app.Constants;
import com.polus.service.app.dto.EmployeeDto;
import com.polus.service.app.dto.LoginRequest;
import com.polus.service.app.dto.LoginResponse;
import com.polus.service.app.dto.RoleDto;
import com.polus.service.app.dto.SignUpRequest;
import com.polus.service.app.entities.Country;
import com.polus.service.app.entities.Employee;
import com.polus.service.app.entities.EmployeeRole;
import com.polus.service.app.entities.Role;
import com.polus.service.app.exceptions.UsernameAlreadyExistsException;
import com.polus.service.app.repository.CountryRepository;
import com.polus.service.app.repository.EmployeeRepository;
import com.polus.service.app.repository.EmployeeRoleRepository;
import com.polus.service.app.repository.RoleRepository;

@Service
public class EmployeeService {

	private Logger logger = LogManager.getLogger(EmployeeService.class);

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	EmployeeRoleRepository employeeRoleRepository;

	@Autowired
	CountryRepository countryRespository;

	public ResponseEntity<Object> signUpOrEdit(SignUpRequest signUpRequest) {
		Map<String, String> message = new HashMap<>();
		try {
			Employee employee = new Employee();
			Optional<Country> country = countryRespository.findById(signUpRequest.getCountryCode());
			if (signUpRequest.getEmployeeId() == null) {
				if (employeeRepository.existsByUsername(signUpRequest.getUsername())) {
					throw new UsernameAlreadyExistsException("Username already exists");
				}
				employee.setCreatedDate(Timestamp.from(Instant.now()));
				saveOrUpdateEmployee(employee, signUpRequest, country);
				employeeRepository.save(employee);
				assignDefaultRoleToEmployee(employee.getEmployeeId());
				logger.info("User signup successfully");
				message.put("Message", "User signup successfully");
				message.put("Username", signUpRequest.getUsername());
				message.put("Password", signUpRequest.getPassword());
				return ResponseEntity.ok(message);
			} else {
				saveOrUpdateEmployee(employee, signUpRequest, country);
				employee.setEmployeeId(signUpRequest.getEmployeeId());
				Employee savedEmployee = employeeRepository.save(employee);
				logger.info("User edit successfully");
				message.put("Message", "User edit successfully");
                return ResponseEntity.ok(message);
			}
		} catch (UsernameAlreadyExistsException e) {
			logger.error("Username already exist: {}", e.getMessage());
			message.put("Message", "Username already exists");
			return ResponseEntity.ok(message);
		} catch (Exception e) {
			logger.error("Error in sign-up: {}", e.getMessage());
			message.put("Message", "Failed");
			return ResponseEntity.ok(message);
		}
	}

	public Employee saveOrUpdateEmployee(Employee employee, SignUpRequest signUpRequest, Optional<Country> country) {
		employee.setFirstname(signUpRequest.getFirstname());
		employee.setLastname(signUpRequest.getLastname());
		employee.setFullname(signUpRequest.getFirstname() + " " + signUpRequest.getLastname());
		employee.setEmail(signUpRequest.getEmail());
		employee.setDesignation(signUpRequest.getDesignation());
		employee.setState(signUpRequest.getState());
		employee.setCountryCode(country.get());
		employee.setPhoneNumber(signUpRequest.getPhoneNumber());
		employee.setUsername(signUpRequest.getUsername());
		employee.setPassword(signUpRequest.getPassword());
		return employee;
	}

	public LoginResponse authenticate(LoginRequest loginRequest) {
		try {
			Employee employee = employeeRepository.findByUsername(loginRequest.getUsername());
			if (employee != null && employee.getPassword().equals(loginRequest.getPassword())) {
				LoginResponse loginResponse = new LoginResponse();
				loginResponse.setEmployeeId(employee.getEmployeeId());
				loginResponse.setUsername(employee.getUsername());
				loginResponse.setFirstname(employee.getFirstname());
				loginResponse.setLastname(employee.getLastname());
				loginResponse.setEmail(employee.getEmail());
				loginResponse.setDesignation(employee.getDesignation());
				loginResponse.setState(employee.getState());
				loginResponse.setCountryCode(employee.getCountryCode());
				loginResponse.setPhoneNumber(employee.getPhoneNumber());
				loginResponse.setCreatedDate(employee.getCreatedDate());
				loginResponse.setRoles(getRole(employee));
				return loginResponse;
			}
		} catch (DataAccessException e) {
			logger.error("Error in login: {}", e.getMessage());
			throw new RuntimeException(e);
		}
		return null;
	}

	public boolean assignRole(int id, int roleId) {
		try {
			Employee employee = employeeRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Employee not found"));
			EmployeeRole employeeRole = new EmployeeRole();
			employeeRole.setEmployee(employee);
			if (roleId == Constants.APPLICATION_ADMINISTRATOR_ID) {
				Role role = roleRepository.findById(Constants.APPLICATION_ADMINISTRATOR_ID).orElseThrow();
				employeeRole.setRole(role);
			}
			employeeRoleRepository.save(employeeRole);
			return true;
		} catch (Exception e) {
			logger.error("Error in role assigning: {}", e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public List<EmployeeDto> getAllEmployeesWithRole(int roleId) {
		try {
			if (roleId == Constants.APPLICATION_ADMINISTRATOR_ID) {
				List<Employee> employeeList = employeeRepository.findApplicationAdministrator(roleId);
				if (employeeList.isEmpty()) {
					return Collections.emptyList();
				}
				return getEmployeeListByRole(employeeList);
			}
			if (roleId == Constants.PRINCIPAL_INVESTIGATOR_ID) {
				List<Employee> employeeList = employeeRepository.findPrincipalInvestigators();
				if (employeeList.isEmpty()) {
					return Collections.emptyList();
				}
				return getEmployeeListByRole(employeeList);
			}
		} catch (DataAccessException e) {
			logger.error("Error in fetching all employees: {}", e.getMessage());
			throw new RuntimeException(e);
		}
		return null;
	}

	public Map<String, String> revokeRole(Integer adminId, Integer employeeId, Integer roleId) {
		try {
			Map<String, String> message = new HashMap<>();
			employeeRoleRepository.findById(adminId).orElseThrow(() -> new RuntimeException("Employee not found."));
			if (employeeRoleRepository.existsById(employeeId)) {
				employeeRoleRepository.deleteByEmployeeAndRole(employeeId, roleId);
				message.put("Message", "Role deleted successfully");
			} else
				message.put("Message", "Employee not found");
			return message;
		} catch (Exception e) {
			logger.info("Error in {}", e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public List<EmployeeDto> getEmployeeListByRole(List<Employee> employeeList) {
		List<EmployeeDto> employeeDto = new ArrayList<>();
		for (Employee employee : employeeList) {
			EmployeeDto list = new EmployeeDto();
			list.setEmployeeId(employee.getEmployeeId());
			list.setFirstName(employee.getFirstname());
			list.setLastName(employee.getLastname());
			list.setRoles(getRole(employee));
			employeeDto.add(list);
		}
		return employeeDto;
	}

	public List<RoleDto> getRole(Employee employee) {
		List<RoleDto> roles = employee.getEmployeeRoles().stream().map(employeeRole -> {
			RoleDto roleDto = new RoleDto();
			roleDto.setRoleId(employeeRole.getRole().getRoleId());
			roleDto.setRoleName(employeeRole.getRole().getRoleName());
			roleDto.setRoleDescription(employeeRole.getRole().getRoleDescription());
			return roleDto;
		}).collect(Collectors.toList());
		return roles;
	}

	private void assignDefaultRoleToEmployee(Integer employeeId) {
		Role role = roleRepository.findById(Constants.PRINCIPAL_INVESTIGATOR_ID)
				.orElseThrow(() -> new RuntimeException("Default role not found"));
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new RuntimeException("Employee not found"));
		EmployeeRole employeeRole = new EmployeeRole();
		employeeRole.setEmployee(employee);
		employeeRole.setRole(role);
		employeeRoleRepository.save(employeeRole);
	}
}
