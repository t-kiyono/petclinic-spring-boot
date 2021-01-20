package net.be_geek.petclinic

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenAPIConfig {

  @Bean
  fun openAPI(): OpenAPI = OpenAPI()
      .info(
          Info()
              .title("petclinic-api")
              .version("0.0.1")
      )

}
