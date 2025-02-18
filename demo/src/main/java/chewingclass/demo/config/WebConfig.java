package chewingclass.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:C:/Users/dhlee/Downloads/demo/demo/build/resources/main/static/uploads");
    }
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // ✅ API 요청 허용
                .allowedOrigins("http://localhost:8080") // Vue 개발 서버 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
    }

}
