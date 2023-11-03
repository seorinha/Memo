package com.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component //spring bean(서비스, mapper, controller가 모두 아닐때 component)
//component가 있을 때는 new가 아니라 autowired로 사용한다  
public class FileManagerService {

	//실제 업로드가 된 이미지가 저장 될 경로(서버의 주소)
	//상수로 하고자 할 때는 static final을 사용한다 , 상수는 관례적으로 대문자
	//학원경로
	public static final String FILE_UPLOAD_PATH = "D:\\하서린\\5_spring_project\\MEMO\\workspace\\images/";
	
	//집 경로
	//public static final String FILE_UPLOAD_PATH = "C:\\Users\\ASUS\\Desktop\\웹개발\\5_spring_project\\MEMO\\workspace\\images/";
	
	
	//input: userLoginId, file(이미지)
	//output: web imagePath
	public String saveFile(String loginId, MultipartFile file) {
		
		//폴더 생성
		//폴더 경로의 예: aaaa_178945646347/sun.png
		//directory : 폴더라는 의미
		String directoryName = loginId + "_" + System.currentTimeMillis();
		String filePath = FILE_UPLOAD_PATH + directoryName; //D:\\하서린\\5_spring_project\\MEMO\\workspace\\images/aaaa_178945646
		
		File directory = new File(filePath);
		if (directory.mkdir() == false) { //폴더생성에 실패 시
			//이미지 경로 null로 return
			return null;
		}
		
		//폴더 안에 파일 업로드: byte 단위로 업로드 한다
		try {
			byte[] bytes = file.getBytes();
			//★★★★★이미지는 한글이름으로 올릴 수 없으므로 나중에 영문자로 바꿔서 올리기, 개인프로젝트 할 때는 영문으로 바꾸는 로직을 사용하면 된다
			Path path = Paths.get(filePath + "/" + file.getOriginalFilename()); //여태까지의 디렉토리 경로 + 사용자가 올린 파일명
			Files.write(path, bytes); // 진짜 파일 업로드
		} catch (IOException e) {
			e.printStackTrace();
			return null; // 이미지 업로드 실패시 null 리턴
		}
		
		//파일 업로드 성공을 하면 웹 이미지 url path를 return
		//주소는 이렇게 될것이다 라고 예언을 하는 것임 실제로 주소가 생기는건 아님
		//예시 형식 : /images/aaaa_1789456156/sun.png 
		return "/images/" + directoryName + "/" + file.getOriginalFilename();
	}
	
	
}
