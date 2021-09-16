package com.techcourse.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.techcourse.domain.User;
import com.techcourse.repository.InMemoryUserRepository;
import com.techcourse.service.RegisterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nextstep.mvc.view.ModelAndView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("RegisterController는")
class RegisterControllerTest {

    private HttpServletRequest request;
    private HttpServletResponse response;

    private InMemoryUserRepository userRepository;
    private RegisterController registerController;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        userRepository = new InMemoryUserRepository();
        RegisterService registerService = new RegisterService(userRepository);
        registerController = new RegisterController(registerService);
    }

    @DisplayName("/register GET 요청시")
    @Nested
    class RegisterGet {

        @BeforeEach
        void setUp() {
            when(request.getRequestURI()).thenReturn("/register");
            when(request.getMethod()).thenReturn("GET");
        }

        @DisplayName("/register.jsp 화면을 반환한다.")
        @Test
        void loginPage() {
            // when
            ModelAndView modelAndView = registerController.show(request, response);

            // then
            assertThat(modelAndView.getViewName()).isEqualTo("/register.jsp");
        }
    }

    @DisplayName("/register POST 요청시")
    @Nested
    class RegisterPost {

        @BeforeEach
        void setUp() {
            when(request.getRequestURI()).thenReturn("/register");
            when(request.getMethod()).thenReturn("POST");
        }

        @DisplayName("회원가입에 성공한 경우 /index.jsp 이동을 반환한다.")
        @Test
        void registerSuccess() {
            // given
            when(request.getParameter("account")).thenReturn("lion");
            when(request.getParameter("password")).thenReturn("password");
            when(request.getParameter("email")).thenReturn("mail@mail");

            // when
            ModelAndView modelAndView = registerController.save(request, response);

            // then
            assertThat(modelAndView.getViewName()).isEqualTo("redirect:/index.jsp");
        }

        @DisplayName("회원가입에 실패할 경우 /401.jsp 이동을 반환한다.")
        @Test
        void registerFailed() {
            // given
            userRepository.save(new User("lion", "password", "mail@mail"));

            when(request.getParameter("account")).thenReturn("lion");
            when(request.getParameter("password")).thenReturn("password");
            when(request.getParameter("email")).thenReturn("mail@mail");

            // when
            ModelAndView modelAndView = registerController.save(request, response);

            // then
            assertThat(modelAndView.getViewName()).isEqualTo("redirect:/409.jsp");
        }
    }
}
