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
  
    
- Api
    <img width="1614" alt="Screen Shot 2021-10-22 at 9 15 42 PM" src="https://user-images.githubusercontent.com/70922665/138452114-4437e1d0-3409-4405-93a7-0d3744569530.png">
<img width="1608" alt="Screen Shot 2021-10-22 at 9 15 54 PM" src="https://user-images.githubusercontent.com/70922665/138452134-d9d259c5-17b1-4f9a-a754-b573a25265d2.png">
<img width="1604" alt="Screen Shot 2021-10-22 at 9 16 03 PM" src="https://user-images.githubusercontent.com/70922665/138452144-7e6ae7e9-0212-46ae-ac43-b5af7ea7b655.png">


    

# 트러블 슈팅

## 테스트 코드 작성
### 1.1 UserService 테스트
```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    JwtTokenProvider jwtTokenProvider;
    @Mock
    AuthenticationManagerBuilder authenticationManagerBuilder;
    @Mock
    PasswordEncoder passwordEncoder;

    SignupValidator signupValidator;
    UserService userService;

    @BeforeEach
    void init() {
        signupValidator = new SignupValidator(userRepository, passwordEncoder);
        userService = new UserService(
                signupValidator,
                userRepository,
                jwtTokenProvider,
                authenticationManagerBuilder,
                passwordEncoder);
    }
}
```
### 1.2 정상 테스트
```java
@Nested
@DisplayName("회원가입")
class SignUp {
    @Test
    @DisplayName("정상")
    void 회원가입() {
        //given
        SignupRequestDto signupRequestDto = new SignupRequestDto("user@email.com", "password486", "user");

        Mockito.when(userRepository.findByEmail(signupRequestDto.getEmail()))
                .thenReturn(Optional.empty());
        //when
        boolean result = userService.registerUser(signupRequestDto);
        //then
        assertThat(result).isEqualTo(true);
    }
}
```
### 1.3 Exception 발생 테스트
```java
@Nested
@DisplayName("회원정보 불일치")
class NotMatch {
    @Test
    @DisplayName("비밀번호가 일치하지 않을 경우 예외 발생")
    void 비밀번호불일치() {
        //given
        SignupRequestDto signupRequestDto = new SignupRequestDto("user@mail.com", "password486", "user");
        User user = new User(signupRequestDto);
        UserRequestDto userRequestDto = new UserRequestDto("user@mail.com", "asdf1234");

        Mockito.when(userRepository.findByEmail(userRequestDto.getEmail()))
                .thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword()))
                .thenReturn(false);
        //when - then
        assertThrows(UsernameNotFoundException.class,() -> userService.loginValidCheck(userRequestDto),"회원정보가 일치하지 않습니다. 다시 입력해주세요.");
    }
}
```

## 2. MockMvc를 이용한 Controller 테스트
### 2.1 ProductController 테스트
```java
@WebMvcTest(
        controllers = {ProductController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
class ProductControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @MockBean
    ProductService productService;

    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    private Page<Product> products;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .addFilters(new CharacterEncodingFilter("UTF-8",true))
                .build();

        mockProductSetup();
    }

    //테스트 데이터 추가
    private void mockProductSetup() {
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product("고구마", 10000L, "신선한 고구마", "채소", "고구마", "imageUrl", 0);
        Product product2 = new Product("감자", 8000L, "커다란 감자", "채소", "감자", "imageUrl", 0);
        Product product3 = new Product("삼겹살", 15000L, "냉동 삼겹살", "육류", "돼지고기", "imageUrl", 0);
        Product product4 = new Product("한우", 20000L, "최고급 한우", "육류", "소고기", "imageUrl", 0);
        Product product5 = new Product("상추", 3000L, "신선한 상추", "채소", "상추", "imageUrl", 0);
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
        productList.add(product5);

        products = new PageImpl<>(productList);
    }
}
```

### 2.2 ProductController 테스트
```java
@Test
@DisplayName("상품 상세 조회")
void 상품상세조회() throws Exception {
    //given
    when(productService.getProduct(1L)).thenReturn(products.getContent().get(0));
    //when
    MvcResult mvcResult = mvc.perform(get("/products/" + 1 ))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
            .andDo(print())
            .andReturn();
    //then
    JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
    JSONObject item = jsonObject.getJSONObject("data");
    Assertions.assertThat(item.getString("name")).isEqualTo("고구마");
}
```

## 3. MockMvc의 한글 깨짐
테스트 코드 작성중 한글이 깨지는 경우가 있다. 이때는 addFilters UTF-8로 필터를 추가하면 정상적으로 한글이 출력된다.
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
