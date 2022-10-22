package com.revature.ers;

import com.revature.ers.controller.AuthenticationController;
import com.revature.ers.controller.ReimbursementController;
import com.revature.ers.controller.UserController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ErsApplicationTests {

	@Autowired
	private AuthenticationController authenticationController;

	@Autowired
	private ReimbursementController reimbursementController;

	@Autowired
	private UserController userController;

	@Test
	void contextLoads() {
		Assertions.assertThat(authenticationController).isNotNull();
		Assertions.assertThat(reimbursementController).isNotNull();
		Assertions.assertThat(userController).isNotNull();
	}
}
