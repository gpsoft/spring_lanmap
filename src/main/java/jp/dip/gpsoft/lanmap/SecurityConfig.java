package jp.dip.gpsoft.lanmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// トップページとログイン画面は誰でも開ける。
				.antMatchers("/", "/login", "/autologin").permitAll()
				// ユーザ編集できるのは管理者のみ。
				.antMatchers("/users/edit/**", "/users/create/**",
						"/users/delete/**")
				.hasRole("ADMIN")
				// それ以外の画面は、ログイン済みなら誰でも開ける。
				.anyRequest().authenticated();
		http.formLogin()
				// ログイン画面のURL。
				.loginPage("/login")
				// ログインPOSTを受け取るURL。
				.loginProcessingUrl("/login")
				// ログイン失敗時のリダイレクト先。
				.failureUrl("/login?error")
				// ログイン成功時のデフォルトのリダイレクト先。
				.defaultSuccessUrl("/", /* alwaysUse */false)
				// POSTデータの名前。
				.usernameParameter("name").passwordParameter("password");
		// 特定のページに対してhttpsを強制したい場合の設定。
		// Tomcatの設定も必要なので、開発中は無効にしておく。
		// http.requiresChannel().antMatchers("/login").requiresSecure();
		http.logout()
				// ログアウトURL。
				// CSRF対策の観点から、POSTのみ許可したいときはlogoutUrl()を使い、
				// GETでログアウトしたいならlogoutRequestMatcher()を使う。
				.logoutRequestMatcher(
						new AntPathRequestMatcher("/logout", "GET"))
				// ログアウト後のリダイレクト先。
				.logoutSuccessUrl("/login");
		http.exceptionHandling().accessDeniedPage("/error");
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// ログインのPOSTデータを
		// DBのユーザ情報と照合したい。
		// そのために必要なのが
		// - モデル: UserDetailsインターフェイスを実装したもの
		// - サービス: UserDetailsServiceインターフェイスを実装したもの
		// ここでは、サービスを登録する。
		auth.userDetailsService(userDetailsService) // UserDetails用のサービス。
				// パスワードの符号化方式。
				.passwordEncoder(new BCryptPasswordEncoder());
	}
}
