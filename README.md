# 프로젝트 소개

마켓 컬리 클론 프로젝트

시연 영상 : [https://youtu.be/6Q4pqA0q8Q0](https://youtu.be/6Q4pqA0q8Q0)

서비스 주소 : [http://kbumsoo.s3-website.ap-northeast-2.amazonaws.com/login](http://kbumsoo.s3-website.ap-northeast-2.amazonaws.com/login)

## 조원

### 백엔드

- 김혜림 ([https://github.com/nameLim](https://github.com/nameLim))
- 하원빈 ([https://github.com/woodimora](https://github.com/woodimora))

### 프론트엔드

- 김범수 ([https://github.com/kimbumsoo0820](https://github.com/kimbumsoo0820))
- 김덕현 ([https://github.com/deokhyun-dev](https://github.com/deokhyun-dev))
- 박주승 ([https://github.com/GitPJS](https://github.com/GitPJS))

## 기술 스택

- Spring Boot
- AWS
- MySQL
- JPA
- Spring Security

## 모델링

- 데이터베이스 스키마
 <img width="660" alt="Screen_Shot_2021-10-19_at_2 57 47_PM" src="https://user-images.githubusercontent.com/70922665/138451938-7d7cba18-01e2-4312-b21c-03a30b6eedb6.png">
  
    
## Api(Swagger)
http://15.165.159.211:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/
    

# 트러블 슈팅 문제점/해결 과정
## CORS Origin '*' 했는데 왜 에러가 발생할까? 
프론트 측에서 CORS를 전부 허용해달라고 요청을 했었습니다. 
Access-Control-Allow-Orign 에 *을 줬는데 CORS에러가 떴습니다.
원인 : WebSecurtiyConfig의 configure에서 http.cors()가 빠져있어 발생한 문제였습니다.


# 테스트 코드 작성
## 1. MockMvc를 이용한 Controller 테스트
https://github.com/MarketKurly-Clone/Backend/tree/dev/src/test/java/com/sparta/kerly_clone/controller
## 2. Mock을 이용한 Service 테스트
https://github.com/MarketKurly-Clone/Backend/tree/dev/src/test/java/com/sparta/kerly_clone/service


## 3. MockMvc의 한글 깨짐
테스트 코드 작성중 한글이 깨지는 경우가 있다. 이때는 addFileters로 UTF-8로 필터를 추가하면 정상적으로 한글이 출력된다.
``` java
@Before
public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
            .alwaysDo(print())
            .build();
}
```
이때 Content-Type검사를 할 때, application/json 이 아닌 application/json;charset=UTF-8 로 나오는 것을 염두해두면 된다.
