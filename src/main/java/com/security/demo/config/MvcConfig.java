package com.security.demo.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 资源配置
 */
@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {

	super.addViewControllers(registry);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
	//swagger ui
	registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
	registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

	registry.addResourceHandler("/*.txt").addResourceLocations("classpath:/static/");
    super.addResourceHandlers(registry);
  }

  @Bean
  public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
	final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
	converter.setObjectMapper(objectMapper());
	return converter;
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	converters.add(mappingJacksonHttpMessageConverter());
	super.configureMessageConverters(converters);
  }

  private static final DateTimeFormatter LOCAL_DATA_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @Bean
  @Primary
  public ObjectMapper objectMapper() {
	Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
	builder.deserializerByType(LocalDate.class, LocalDateDeserializer.INSTANCE);
	builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(LOCAL_DATA_TIME_FORMATTER));
	builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(LOCAL_DATA_TIME_FORMATTER));
	builder.serializerByType(LocalDate.class, LocalDateSerializer.INSTANCE);
	builder.serializationInclusion(JsonInclude.Include.NON_EMPTY);
	builder.featuresToDisable(
		SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
		DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,
		DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	builder.featuresToEnable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

	return builder.build();
  }
}
