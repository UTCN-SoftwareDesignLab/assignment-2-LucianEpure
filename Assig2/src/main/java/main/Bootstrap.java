package main;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import entity.Role;
import repository.RoleRepository;

public class Bootstrap {
	
	private static RoleRepository roleRepository;
	
	public Bootstrap (RoleRepository roleRepository){
		this.roleRepository = roleRepository;
		roleRepository.save(new Role("administrator"));
		roleRepository.save(new Role("regUser"));
	}
}
