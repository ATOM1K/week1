import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(properties = {
        "spring.profiles.active=test",
        "logging.level.org.springframework.transaction=DEBUG"
})
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldCreateAndFindUser() {
        // Given
        UserDto userDto = UserDto.builder()
                .email("test@example.com")
                .name("Test User")
                .build();

        // When
        UserDto createdUser = userService.createUser(userDto);

        // Then
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getEmail()).isEqualTo("test@example.com");

        User foundUser = userRepository.findById(createdUser.getId())
                .orElseThrow();
        assertThat(foundUser.getName()).isEqualTo("Test User");
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // When & Then
        assertThatThrownBy(() -> userService.getUserById(999L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Пользователь с ID 999 не найден");
    }
}