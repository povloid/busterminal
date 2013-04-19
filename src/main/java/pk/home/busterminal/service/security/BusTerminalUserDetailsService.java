package pk.home.busterminal.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.busterminal.dao.security.UserAccountDAO;

/**
 * Сервис управления детализацией пользователей
 * 
 * @author povloid
 *
 */
@Service
@Transactional
public class BusTerminalUserDetailsService implements UserDetailsService {

	@Autowired
	private UserAccountDAO userAccountDAO;

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {

		UserDetails userDetails = userAccountDAO
				.executeQueryByNameSingleResult("UserAccount.findByUsername",
						username);
		return userDetails;
	}

}
