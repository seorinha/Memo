package com.memo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.common.FileManagerService;

@Configuration //설정을 위한 spring bean
public class WebMvcConfig implements WebMvcConfigurer {

	//웹 이미지 path와 서버에 업로드 된 이미지를 mapping설정
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler("/images/**") //images로 시작하고 그 뒤에 뭐가 있던간에 
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH); // 실제 파일 위치
	}
	
}
