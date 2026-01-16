package com.beyond.basic.b1_basic;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

//@Data : @Getter + @Setter + @ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    private String name;
    private String email;

//    MultipartFile : java/spring에서 파일처리 편의를 제공해주는 클래스
//    private MultipartFile profileImage;
}

