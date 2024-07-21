package br.com.fitnessconsultant;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableAsync
@RequiredArgsConstructor
public class FitnessConsultantApplication {


	public static void main(String[] args) {
		SpringApplication.run(FitnessConsultantApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/v1/**").allowedOrigins("http://localhost:8080");
			}
		};
	}

	/*@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder){
		return args -> {
			if(userRepository.findByEmail("admin@admin.com").isEmpty()){
				User user = new User();
				user.setFirstName("admin");
				user.setLastName("admin");
				user.setEmail("admin@admin.com");
				user.setPassword(passwordEncoder.encode("admin"));
				user.setEnabled(true);
				user.setPhone("779999999");
				user.setRole(Role.ROLE_ADMIN);
				userRepository.save(user);
			}
		};
	}*/

}
