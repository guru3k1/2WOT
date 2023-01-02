package com.cgasystems.BCP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BCPApplication {
	public static void main(String[] args) {

		SpringApplication.run(BCPApplication.class, args);

		/*Dto.User myUser = new Dto.User("Jose");

		String myUserName = myUser.name();

		System.out.println("Name"+ myUserName);

		Dto.User other = new Dto.User(null);

		System.out.println("Name"+ other.name());

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Set<ConstraintViolation<Dto.User>> violations = validator.validate(myUser);

		Set<ConstraintViolation<Dto.User>> violationsOther = validator.validate(other);

		for (ConstraintViolation<Dto.User> violation : violations) {
			System.out.println(violation.getMessage());
		}

		for (ConstraintViolation<Dto.User> violation : violationsOther) {
			System.out.println(violation.getMessage());
		}*/
	}




}


