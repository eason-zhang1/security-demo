/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.security.demo.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ObjectMapperConfig {

  private static final DateTimeFormatter LOCAL_DATA_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @Bean
  @Primary
  public ObjectMapper objectMapper() {
	Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
	builder.deserializerByType(LocalDate.class, LocalDateDeserializer.INSTANCE);
	builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(LOCAL_DATA_TIME_FORMATTER));
	builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(LOCAL_DATA_TIME_FORMATTER));
	builder.serializerByType(LocalDate.class, LocalDateSerializer.INSTANCE);
	builder.serializationInclusion(Include.NON_EMPTY);
	builder.featuresToDisable(
		SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
		DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,
		DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	builder.featuresToEnable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

	return builder.build();
  }

}
