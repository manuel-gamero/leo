package com.mg.service.user;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import com.mg.enums.UserAddressType;
import com.mg.exception.InvalidUserException;
import com.mg.exception.ServiceException;
import com.mg.model.UserAddress;
import com.mg.model.Users;
import com.mg.service.Service;
import com.mg.service.dto.UserSessionDTO;


/**
 * User service interface.
 * 
 *
 */
public interface UserService extends Service {
	
	
	/**
	 * Authenticates a user depending on his credentials.  
	 * @param user
	 * @return
	 */
	UserSessionDTO authenticate(String login, String password) throws ServiceException, InvalidUserException, NoSuchAlgorithmException, InvalidKeySpecException;
	
	/**
	 * Authenticates a user depending on his credentials with possibility 
	 * to pass an encrypted password(from a cookie for instance). 
	 * @param login
	 * @param password
	 * @param encryptedPassword
	 * @return
	 */
	UserSessionDTO authenticate(String login, String password, 
			boolean encryptedPassword) throws ServiceException, InvalidUserException, NoSuchAlgorithmException, InvalidKeySpecException;
	
	
	/**
	 * Creates a new user account.
	 * @param login
	 * @param password
	 * @param email
	 * @return
	 */
	UserSessionDTO createAccount(String login, String password, String email, long country, int primaryLanguage) throws ServiceException, InvalidUserException, NoSuchAlgorithmException, InvalidKeySpecException;
	
	/**
	 * Checks whether a usename exists in the system.
	 * @param username
	 * @return
	 * @throws ServiceException
	 */
	boolean usernameExists(String username) throws ServiceException;
	
	/**
	 * Checks whether an email exists in the system.
	 * @param email
	 * @return
	 * @throws ServiceException
	 */
	//boolean emailExists(String email) throws ServiceException;

	
	/**
	 * Sends user's forgotten password.
	 * @param email
	 * @return
	 */
	boolean sendUserPassword(String email);

	/**
	 * 
	 * @param userId
	 * @param login
	 * @return
	 * @throws ServiceException
	 */
/*	boolean updateLogin(long userId, String login) throws ServiceException;
	*//**
	 * 
	 * @param userId
	 * @param newPassord
	 * @return
	 * @throws ServiceException
	 *//*
	boolean updatePassword(long userId, String newPassord)
			throws ServiceException;
	
		
	*//**
	 * list of all active user (where active = true and typeId = 2 [USER]).
	 * @param basicUserDTO
	 * @return
	 * @throws ServiceException
	 *//*	
	List<UserSessionDTO> getAllUserAccounts(BasicUserDTO basicUserDTO) throws ServiceException;	
	
	*//**
	 * list of all users (where typeId = 2 [USER] only).
	 * @param basicUserDTO
	 * @return
	 * @throws ServiceException
	 *//*
	List<UserSessionDTO> getAllUsers(BasicUserDTO basicUserDTO) throws ServiceException;	
*/	
	/**
	 * Creates a new user.
	 * @param login
	 * @param password
	 * @param email
	 * @return
	 */
	int createUser(Users user) throws ServiceException, InvalidUserException;
	
/*
	*//**
	 * Get the user information by id.
	 * @param userId
	 * @return
	 * @throws ServiceException
	 *//*
	UserDTO getUserInformation(Long userId) throws ServiceException;
	
	*//**
	 * Update a user by id.
	 * @param userDTO
	 * @throws ServiceException
	 *//*
	public void updateUser(final UserDTO userDTO) throws ServiceException;
	
*/
	
	public UserAddress getUserAddress(int id) throws ServiceException;
	
	public int saveUserAddress(UserAddress userAddress) throws ServiceException;
	
	public void updateUserAddress(UserAddress userAddress) throws ServiceException;
	
	public List<UserAddress> getUserAddressList(int userId, UserAddressType type) throws ServiceException;
	
	public void deleteUserAddress(final UserAddress userAddress) throws ServiceException;
}