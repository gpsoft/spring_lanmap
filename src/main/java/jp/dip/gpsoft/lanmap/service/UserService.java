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
	private StampService stampService;

	@Autowired
	PasswordEncoder passwordEncoder;
	// SecurityConfigの中で@Beanメソッドを定義してる。

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findOneById(Long id) {
		return userRepository.findOne(id);
	}

	public User findOneByName(String name) {
		return userRepository.findByName(name);
	}

	/**
	 * Userを保存。idがnullならINSERT。
	 * 
	 * @param user
	 * @param withRawPassword
	 */
	public void save(User user, boolean withRawPassword) {
		if (withRawPassword) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		Timestamp now = new Timestamp(System.currentTimeMillis());
		stampService.stamp(user.getStamp(), now);
		userRepository.saveAndFlush(user);
	}

    @Transactional(readOnly = true)
	public boolean isUniqueName(String name) {
        return userRepository.countByName(name) == 0;
	}
}
