package com.techcourse.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.techcourse.domain.User;
import com.techcourse.repository.InMemoryUserRepository;
import com.techcourse.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nextstep.mvc.view.ModelAndView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("UserController는")
class UserControllerTest {

    private HttpServletRequest request;
    private HttpServletResponse response;

    private InMemoryUserRepository userRepository;
    private UserController userController;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        userRepository = new InMemoryUserRepository();
        UserService userService = new UserService(userRepository);
        userController = new UserController(userService);
    }

    @DisplayName("/api/user GET 요청시")
    @Nested
    class RegisterGet {

        @BeforeEach
        void setUp() {
            when(request.getRequestURI()).thenReturn("/api/user");
            when(request.getMethod()).thenReturn("GET");
        }

        @DisplayName("account에 해당하는 유저가 있다면 정보를 반환한다.")
        @Test
        void showSuccess() {
            // given
            userRepository.save(new User("lion", "password", "email"));

            when(request.getParameter("account")).thenReturn("lion");

            // when
            ModelAndView modelAndView = userController.show(request, response);

            // then
            assertThat(modelAndView.getObject("user")).isNotNull();
        }

        @DisplayName("account에 해당하는 유저가 없다면 예외 정보를 반환한다.")
        @Test
        void showFailed() {
            // given
            when(request.getParameter("account")).thenReturn("lion");

            // when
            ModelAndView modelAndView = userController.show(request, response);

            // then
            assertThat(modelAndView.getObject("user")).isNull();
            assertThat(modelAndView.getObject("exception")).isNotNull();
        }
    }
}