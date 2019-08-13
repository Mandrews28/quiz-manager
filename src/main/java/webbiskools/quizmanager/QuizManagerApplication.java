package webbiskools.quizmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@SpringBootApplication
@EnableJpaAuditing
@EnableSwagger2
public class QuizManagerApplication {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("webbiskools.quizmanager"))
				.paths(PathSelectors.any()).build()
				.apiInfo(new ApiInfo("Quiz Manager for Webbi Skools Ltd",
						"API for the Quiz Manager for Webbi Skools Ltd",
						null, null, null,null, null, new ArrayList()));
	}


	public static void main(String[] args) {
		SpringApplication.run(QuizManagerApplication.class, args);
	}

}
