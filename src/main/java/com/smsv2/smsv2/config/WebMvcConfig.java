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
				"/api/user/login",
				"/api/user/verifyEmail",
				"/api/user/verifyPhone",
				"/api/user//forgetPassword",
				"/api/user/sendOtpEmail",
				"/api/dept/allDept",
				"/api/dept/allDeptbyid/{id}",
				"/api/dept/allDeptbysemid/{semId}",
				"/api/feedback/allfeedback",
				"/api/feedback/feedbackbyId/{id}",
				"/api/sem/allsem",
				"/api/sem/addsem/{role}",
				"/api/dept/addDept/{role}",
				"/api/sem/allsembyid/{id}",
				"/api/sem/allsembydeptID/{id}",
				"/api/student/registerstudent");

	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOriginPatterns("*") // Change allowedOrigins to allowedOriginPatterns
		.allowedMethods("GET", "POST", "PUT", "DELETE")
		.allowedHeaders("*")
		.allowCredentials(true);
	}
}
