package com.springbootlearning.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.springbootlearning.model.User;
import com.springbootlearning.service.UserService;

/**
 * @author Peter Zhou 
 *
 */
@RestController
@RequestMapping("/api")
public class RestApiController {
	
	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/user")
	 ResponseEntity<?> home(User user) {
		try {
		user = userService.create(user);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
		} catch( DuplicateKeyException e) {
			logger.error("unable to add Userto database",  user.getUserId());
			return new ResponseEntity<String>("Unable to add User with id " + user.getUserId() + " to database, it already exist", HttpStatus.CONFLICT);
		}catch( Exception e) {
			logger.error("unable to add Userto database",  user.getUserId());
			return new ResponseEntity<String>("Unable to add User with id " + user.getUserId() + " to database", HttpStatus.CONFLICT);
		}
	}
 
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findUserById(@PathVariable("id") Integer id) {
	    try {
			User user = userService.findUserById(id);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {
	
			logger.error("User with id {} not found.", id);
            return new ResponseEntity<String>("User with id " + id 
                    + " not found", HttpStatus.NOT_FOUND);
		} catch(Exception e) {
			e.printStackTrace();
			 return new ResponseEntity<String>("internal exception", HttpStatus.NO_CONTENT);
		}
	    
	}
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUserById(@PathVariable("id") Integer id) {
	    try {
	    	User user = userService.findUserById(id);
	    	userService.deleteUserById(id);
			return new ResponseEntity<String>("User with id " + id 
                    + " was deleted", HttpStatus.NOT_FOUND);
		} catch (EmptyResultDataAccessException e) {
			logger.error("Unable to delete, User with id {} not found.", id);
            return new ResponseEntity<String>("Unable to delete, User with id " + id 
                    + " not found", HttpStatus.NOT_FOUND);
		} catch(Exception e) {
			e.printStackTrace();
			 return new ResponseEntity<String>("internal exception", HttpStatus.NO_CONTENT);
		}
	    
	}
	
	@RequestMapping("/users")
    List<User> findAllUsers() {
		List<User> users = userService.findAll();
        return users;
    }
}
