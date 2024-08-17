package com.smsv2.smsv2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private JwtRequestInterceptor jwtRequestInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtRequestInterceptor)
		.addPathPatterns("/api/**")
		.excludePathPatterns(
				// "/api/**",
				"/swagger-ui/index.html#",
				"/api/user/login",
				"/api/user/verifyEmail",
				"/api/user/verifyPhone",
				"/api/user/forgetPassword",
				"/api/user/sendOtpEmail",
				"/api/dept/allDept",
				"/api/dept/allDeptbyid/**",
				"/api/dept/allDeptbysemid/**",
//				"/api/dept/updateDept/**",
//				"/api/dept/addDept",
				"/api/feedback/allfeedback",
				"/api/feedback/feedbackbyId/**",
				"/api/sem/allsem",
//				"/api/sem/addSem",
				"/api/sem/allsembyid/**",
				"/api/sem/allsembydeptID/**",
				"/api/student/registerstudent"
//				"/api/admin/registeradmin/{role}"
				);

	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOriginPatterns("*") 
		.allowedMethods("GET", "POST", "PUT", "DELETE")
		.allowedHeaders("*")
		.allowCredentials(true);
	}
}
