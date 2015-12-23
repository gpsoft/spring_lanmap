package jp.dip.gpsoft.lanmap.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

// Spring Securityが認証(authentication)や認可(authorization)を制御するのに必要な
// ユーザ情報を抽象化したクラス。
public class CurrentUser implements UserDetails {
	private static final long serialVersionUID = -707937498786592064L;

	private User user;

	public CurrentUser(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 権限レベルをSpring Securityに教える。
		String auth = "USER";
		if (user.isAdmin()) {
			auth = "ADMIN";
		}
		return AuthorityUtils.createAuthorityList(auth);
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
