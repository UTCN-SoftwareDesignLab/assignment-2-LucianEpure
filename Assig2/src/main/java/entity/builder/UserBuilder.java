package entity.builder;

import entity.User;

public class UserBuilder {

	private User user;
	
	public UserBuilder(){
		this.user = new User();
	}
	
	public void setUsername(String username){
		this.user.setUsername(username);
	}
	
	public void setPassword(String password){
		this.user.setPassword(password);
	}
}
