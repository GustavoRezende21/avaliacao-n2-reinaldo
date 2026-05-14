package com.avaliacaoReinaldo.n2;

import org.springframework.boot.SpringApplication;

public class TestN2Application {

	public static void main(String[] args) {
		SpringApplication.from(N2Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
