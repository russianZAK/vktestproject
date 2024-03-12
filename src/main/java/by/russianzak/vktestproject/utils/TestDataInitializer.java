package by.russianzak.vktestproject.utils;

import by.russianzak.vktestproject.user.UserTestDataGeneratorService;
import by.russianzak.vktestproject.user.role.UserRoleTestDataGeneratorService;
import javax.transaction.Transactional;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class TestDataInitializer implements ApplicationRunner {

  private final UserRoleTestDataGeneratorService userRoleTestDataGeneratorService;
  private final UserTestDataGeneratorService userTestDataGeneratorService;

  public TestDataInitializer(UserRoleTestDataGeneratorService userRoleTestDataGeneratorService,
      UserTestDataGeneratorService userTestDataGeneratorService) {
    this.userRoleTestDataGeneratorService = userRoleTestDataGeneratorService;
    this.userTestDataGeneratorService = userTestDataGeneratorService;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    userRoleTestDataGeneratorService.generateTestData();

    userTestDataGeneratorService.generateTestData();
  }
}
