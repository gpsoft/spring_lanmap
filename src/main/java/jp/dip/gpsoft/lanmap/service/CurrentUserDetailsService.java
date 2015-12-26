package jp.dip.gpsoft.lanmap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.dip.gpsoft.lanmap.model.CurrentUser;

@Service
public class CurrentUserDetailsService implements UserDetailsService {
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String name)
			throws UsernameNotFoundException {
		return new CurrentUser(userService.findOneByName(name));
	}

}
