package nl.cofx.jpa.experiment;

import org.springframework.boot.SpringApplication;

public class TestExperimentApplication {

	public static void main(String[] args) {
		SpringApplication.from(ExperimentApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
