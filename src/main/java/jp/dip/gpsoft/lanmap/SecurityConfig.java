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
				.antMatchers("/", "/login").permitAll()
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
//		http.requiresChannel().antMatchers("/login").requiresSecure();
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
