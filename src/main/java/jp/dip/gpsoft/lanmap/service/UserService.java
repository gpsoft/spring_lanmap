package jp.dip.gpsoft.lanmap.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jp.dip.gpsoft.lanmap.model.User;
import jp.dip.gpsoft.lanmap.repository.UserRepository;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;
	// SecurityConfigの中で@Beanメソッドを定義してる。

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public void save(User user, String password) {
		user.setPassword(passwordEncoder.encode(password));
		Timestamp now = new Timestamp(System.currentTimeMillis());
		if (user.getCreated() == null) {
			user.setCreated(now);
		}
		user.setModified(now);
		userRepository.saveAndFlush(user);
	}
}
