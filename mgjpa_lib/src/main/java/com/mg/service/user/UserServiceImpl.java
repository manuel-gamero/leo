package com.mg.service.user;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.mg.dao.core.DaoCommand;
import com.mg.dao.core.DaoFactory;
import com.mg.dao.impl.UserAddressDAO;
import com.mg.dao.impl.UserDAO;
import com.mg.enums.Language;
import com.mg.enums.UserAddressType;
import com.mg.enums.UserStatus;
import com.mg.exception.DaoException;
import com.mg.exception.InvalidUserException;
import com.mg.exception.ServiceException;
import com.mg.model.UserAddress;
import com.mg.model.Users;
import com.mg.service.ServiceImpl;
import com.mg.service.dto.DTOFactory;
import com.mg.service.dto.UserSessionDTO;
import com.mg.util.CommonUtils;
import com.mg.util.communication.Receipt;

/**
 * Provides all users related logic in the system.
 * 
 * 
 */
public class UserServiceImpl extends ServiceImpl implements UserService {

	private static final Logger log = Logger.getLogger(UserServiceImpl.class);

	public UserServiceImpl() {
		super();
	}

	@Override
	public UserSessionDTO authenticate(final String login, final String password)
			throws ServiceException, InvalidUserException, NoSuchAlgorithmException, InvalidKeySpecException {
		return authenticate(login, password, true);
	}

	@Override
	public UserSessionDTO authenticate(final String login,
			final String password, final boolean encryptedPassword)
			throws ServiceException, InvalidUserException, NoSuchAlgorithmException, InvalidKeySpecException {

		if (!CommonUtils.isValidString(login)
				|| !CommonUtils.isValidString(password)) {
			throw new IllegalArgumentException(
					"Can't authenticate null or empty user and/or password.\n login-password="
							+ login + "-" + password);
		}

		Users user;

		try {
			daoManager.setCommitTransaction(true);
			user = (Users) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(UserDAO.class, em).findUserByName(
							login);
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}

		UserSessionDTO userSessionDTO;

		if (user != null) {
			// Get the password
			String passwordToBeChecked = (encryptedPassword) ? password
					: password; 
			if (validatePassword(passwordToBeChecked,user.getPassword())) {
				if (user.getStatusCode() == UserStatus.INACTIVE) {
					throw new InvalidUserException("Your account is disabled.");
				}
				userSessionDTO = DTOFactory.getUserSessionDTO(user);
			} else {
				throw new InvalidUserException(
						"bad login / password. Try again.");
			}
		} else {
			throw new InvalidUserException("bad login / password. Try again.");
		}

		return userSessionDTO;
	}

	@Override
	public UserSessionDTO createAccount(final String login,
			final String password, final String email, long country,
			int primaryLang) throws ServiceException, InvalidUserException, NoSuchAlgorithmException, InvalidKeySpecException {

		if (!CommonUtils.isValidString(login)
				|| !CommonUtils.isValidString(password)
				|| !CommonUtils.isValidString(email)) {
			throw new IllegalArgumentException(
					"Can't create a new account with invalid login-password-email="
							+ login + "-" + password + "-" + email);
		}
		return authenticate(login, password);
	}

	/*
	 * @Override public boolean emailExists(final String email) throws
	 * ServiceException { boolean result = false; try { result = (Boolean)
	 * daoManager.executeAndHandle(new DaoCommand() {
	 * 
	 * @Override public Object execute() throws DaoException { return
	 * DaoFactory.getDAO(UserDAO.class).findUserByEmail(email); } }); } catch
	 * (DaoException e) { throw (new ServiceException(e)); } return result; }
	 */

	@Override
	public boolean usernameExists(final String username)
			throws ServiceException {
		boolean result = false;
		try {
			daoManager.setCommitTransaction(true);
			result = (Boolean) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					if (DaoFactory.getDAO(UserDAO.class, em).findUserByName(
							username) == null) {
						return false;
					} else {
						return true;
					}
				}
			});
		} catch (Exception e) {
			throw (new ServiceException(e));
		}
		return result;
	}

	public Users getUser(final Integer id) throws ServiceException {
		Users result;
		try {
			daoManager.setCommitTransaction(true);
			result = (Users) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(UserDAO.class, em).findEntityById(
							id);
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}
	
	public Users getUser(final String userName) throws ServiceException {
		Users result;
		try {
			daoManager.setCommitTransaction(true);
			result = (Users) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(UserDAO.class, em).findUserByName(userName);
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}

	@Override
	public boolean sendUserPassword(final String email) {
		final String requestFor = "You request for Losting Email";
		try {
			daoManager.setCommitTransaction(true);
			final Users user = (Users) daoManager
					.executeAndHandle(new DaoCommand() {
						@Override
						public Object execute(EntityManager em)
								throws DaoException {
							return DaoFactory.getDAO(UserDAO.class, em)
									.findUserByName(email);
						}
					});

			String login = user.getLogin();
			String passwd = user.getPassword();
			String body = "Login: " + login + "\nPass word: " + passwd;

			Receipt.passwordRequest(user);

			if (log.isDebugEnabled()) {
				log.debug(user.getPassword());
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void updateUser(final Users users) throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					DaoFactory.getDAO(UserDAO.class, em).update(users);
					return (users);
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
	}

	@SuppressWarnings("unchecked")
	public List<Users> getAllUsers() throws ServiceException {
		List<Users> userList = null;
		try {
			daoManager.setCommitTransaction(true);
			userList = (List<Users>) daoManager
					.executeAndHandle(new DaoCommand() {
						@Override
						public Object execute(EntityManager em)
								throws DaoException {
							return DaoFactory.getDAO(UserDAO.class, em)
									.findAll();
						}
					});
		} catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return userList;
	}

	/*
	 * @Override public boolean updateLogin(long userId, String login) throws
	 * ServiceException { boolean reponse = false; final User user = new User();
	 * user.setId(userId); user.setLogin(login); try{
	 * daoManager.setCommitTransaction(true); reponse = (Boolean)
	 * daoManager.executeAndHandle(new DaoCommand() {
	 * 
	 * @Override public Object execute() throws DaoException { //here the
	 * database access daoManager.getUserDao().updateLogin(user); return true; }
	 * }) ; }catch (DaoException de) { throw (new ServiceException(de)); }
	 * return reponse; }
	 * 
	 * @Override public boolean updatePassword(long userId, String newPassord)
	 * throws ServiceException { boolean reponse = false; final User user = new
	 * User(); user.setId(userId); user.setPassword(newPassord); try{
	 * daoManager.setCommitTransaction(true); reponse = (Boolean)
	 * daoManager.executeAndHandle(new DaoCommand() {
	 * 
	 * @Override public Object execute(DaoManager daoManager) throws
	 * DaoException { //here the database access
	 * daoManager.getUserDao().updatePassword(user); return true; } }) ; }catch
	 * (DaoException de) { throw (new ServiceException(de)); } return reponse; }
	 * 
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<UserSessionDTO> getAllUserAccounts(BasicUserDTO
	 * basicUserDTO) throws ServiceException { final User user = new User();
	 * user.setActive(basicUserDTO.isActive());
	 * user.setTypeId(basicUserDTO.getTypeId()); List<User> userList = null; try
	 * { userList = (List<User>) daoManager.executeAndHandle(new DaoCommand() {
	 * 
	 * @Override public Object execute(DaoManager daoManager) throws
	 * DaoException { //here the database access return
	 * daoManager.getUserDao().getAllUserAccounts(user); } }) ; } catch
	 * (DaoException de) { throw (new ServiceException(de)); } return
	 * DTOFactory.getAllUsersAccountsDTO(userList); }
	 * 
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override
	 */

	@Override
	public int createUser(final Users user) throws ServiceException,
			InvalidUserException {
		int userId = 0;
		try {
			daoManager.setCommitTransaction(true);
			userId = (Integer) daoManager.executeAndHandle(new DaoCommand() {

				@Override
				public Object execute(EntityManager em) throws DaoException {
					DaoFactory.getDAO(UserDAO.class, em).save(user);
					return user.getId();
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}
		return userId;
	}

	/*
	 * @Override public UserDTO getUserInformation(final Long userId) throws
	 * ServiceException { User userResult = new User(); try {
	 * daoManager.setCommitTransaction(true); userResult = (User)
	 * daoManager.executeAndHandle(new DaoCommand() {
	 * 
	 * @Override public Object execute(com.loyauty.dao.core.DaoManager
	 * daoManager) throws DaoException { User usr =
	 * daoManager.getUserDao().getUser(userId); return usr; } }); } catch
	 * (DaoException de) { throw (new ServiceException(de)); } return
	 * (DTOFactory.getUserDTO(userResult)); }
	 * 
	 * @Override public void updateUser(final UserDTO userDTO) throws
	 * ServiceException { try { daoManager.setCommitTransaction(true);
	 * daoManager.executeAndHandle(new DaoCommand() {
	 * 
	 * @Override public Object execute(com.loyauty.dao.core.DaoManager
	 * daoManager) throws DaoException { User user = buildUser(userDTO);
	 * daoManager.getUserDao().updateUserById(user); return user; } }); } catch
	 * (DaoException e) { throw (new ServiceException(e)); } }
	 */
	private Language getLanguage(int value) {
		Language lang = null;
		if (value == 1)
			lang = Language.ENGLISH;
		if (value == 2)
			lang = Language.FRENCH;
		return lang;
	}

	@Override
	public UserAddress getUserAddress(final int id) throws ServiceException {
		UserAddress result;
		try {
			daoManager.setCommitTransaction(true);
			result = (UserAddress) daoManager
					.executeAndHandle(new DaoCommand() {
						@Override
						public Object execute(EntityManager em)
								throws DaoException {
							return DaoFactory.getDAO(UserAddressDAO.class, em)
									.findEntityById(id);
						}
					});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}

	@Override
	public int saveUserAddress(final UserAddress userAddress)
			throws ServiceException {
		int id = 0;
		try {
			daoManager.setCommitTransaction(true);
			id = (Integer)daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					DaoFactory.getDAO(UserAddressDAO.class, em).save(userAddress);
					return(userAddress.getId());
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}
		return id;		
	}

	@Override
	public void updateUserAddress(final UserAddress userAddress)
			throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					DaoFactory.getDAO(UserAddressDAO.class, em).update(userAddress);
					return(userAddress);
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserAddress> getUserAddressList(final int userId,
			final UserAddressType type) throws ServiceException {
		List<UserAddress> userAddressList = null;
		try {
			daoManager.setCommitTransaction(true);
			userAddressList = (List<UserAddress>) daoManager
					.executeAndHandle(new DaoCommand() {
						@Override
						public Object execute(EntityManager em)
								throws DaoException {
							return DaoFactory.getDAO(UserAddressDAO.class, em).getUserAddressList(userId, type);
						}
					});
		} catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return userAddressList;
	}
	
	private static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		String[] parts = storedPassword.split(":");
		int iterations = Integer.parseInt(parts[0]);
		byte[] salt = fromHex(parts[1]);
		byte[] hash = fromHex(parts[2]);
		
		PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] testHash = skf.generateSecret(spec).getEncoded();
		
		int diff = hash.length ^ testHash.length;
		for(int i = 0; i < hash.length && i < testHash.length; i++)
		{
			diff |= hash[i] ^ testHash[i];
		}
		return diff == 0;
	}
	private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
	{
		byte[] bytes = new byte[hex.length() / 2];
		for(int i = 0; i<bytes.length ;i++)
		{
			bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}

	@Override
	public void deleteUserAddress(final UserAddress userAddress) throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					DaoFactory.getDAO(UserAddressDAO.class, em).delete(userAddress);
					return(userAddress);
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}		
	}
	
	/*
	 * private User buildUser(final UserDTO userDTO) { User user = new User();
	 * int lang = 0; Language primary=null, secondary=null;
	 * user.setId(userDTO.getId()); user.setLogin(userDTO.getLogin());
	 * user.setCode(userDTO.getCode()); user.setTypeId(userDTO.getTypeId());
	 * user.setPassword(userDTO.getPassword());
	 * user.setEmail(userDTO.getEmail()); user.setActive(userDTO.isActive());
	 * user.setRealName(userDTO.getRealName()); //
	 * user.setAge(userDTO.getAge()); user.setAddress(userDTO.getAddress());
	 * user.setPostalCode(userDTO.getPostalCode());
	 * user.setPhone(userDTO.getPhone());
	 * 
	 * lang = userDTO.getPrimaryLanguage(); primary = getLanguage(lang);
	 * user.setPrimaryLanguage(primary);
	 * 
	 * lang = userDTO.getSecondaryLanguage(); secondary = getLanguage(lang);
	 * user.setSecondaryLanguage(secondary);
	 * 
	 * return user; }
	 */

}
