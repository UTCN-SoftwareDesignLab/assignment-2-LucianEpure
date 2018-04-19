package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.User;

public interface UserRepository extends JpaRepository<User,Integer>{

	User findByUsernameAndPassword(String username, String password);
	
}
