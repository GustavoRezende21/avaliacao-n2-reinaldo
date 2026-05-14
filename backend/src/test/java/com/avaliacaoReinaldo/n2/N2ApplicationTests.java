package com.avaliacaoReinaldo.n2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class N2ApplicationTests {

	@Test
	void contextLoads() {
	}

}
