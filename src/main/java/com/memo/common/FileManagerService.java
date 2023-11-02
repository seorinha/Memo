package com.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component //spring bean
public class FileManagerService {

	//실제 업로드가 된 이미지가 저장 될 경로(서버의 주소)
	//상수로 하고자 할 때는 static final , 관례적으로 대문자
	public static final String FILE_UPLOAD_PATH = "D:\\하서린\\5_spring_project\\MEMO\\workspace\\images/";
	//집 경로
	//public static final String
	
	
	//input: userLoginId, file(이미지)
	//output: web imagePath
	public String saveFile(String loginId, MultipartFile file) {
		//폴더 생성
		//예: aaaa_178945646/sun.png
		//directory : 폴더라는 의미
		String directoryName = loginId + "_" + System.currentTimeMillis();
		String filePath = FILE_UPLOAD_PATH + directoryName; //D:\\하서린\\5_spring_project\\MEMO\\workspace\\images/aaaa_178945646
		
		File directory = new File(filePath);
		if (directory.mkdir() == false) { //폴더생성에 실패 시
			//이미지 경로 null로 return
			return null;
		}
		
		//폴더 안에 파일 업로드: byte 단위로 업로드 
		try {
			byte[] bytes = file.getBytes();
			//★★★★★ 한글 이름 이미지는 올릴 수 업으므로 나중에 영문자로 바꿔서 올리기
			Path path = Paths.get(filePath + "/" + file.getOriginalFilename()); //여태까지의 디렉토리 경로 + 사용자가 올린 파일명
			Files.write(path, bytes); //파일 업로드
		} catch (IOException e) {
			e.printStackTrace();
			return null; // 이미지 업로드 실패시 null 리턴
		}
		
		//파일 업로드 성공을 하면 웹 이미지 url path를 return
		//주소는 이렇게 될것이다 라고 예언을 하는 것임 실제로 주소가 생기는건 아님
		//  /images/aaaa_1789456156/sun.png 
		return "/images/" + directoryName + "/" + file.getOriginalFilename();
	}
	
	
}
