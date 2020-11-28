package com.fz.zf.config;

import com.ex.framework.util.SpringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class BeanConfig {

    /**
     * 替换内置的ObjectMapper，添加Java8时间类支持
     *
     * @return
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        //java 8 datetime
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        DateTimeFormatter formatter_short = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        //LocalDate
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(formatter_short));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(formatter_short));
        //LocalDateTime
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
        //LocalTime
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(formatter));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(formatter));

        mapper.registerModule(javaTimeModule);

        return mapper;
    }

    /**
     * 注册SpringUtils
     *
     * @return
     */
    @Bean
    public SpringUtils springUtils() {
        return new SpringUtils();
    }

}
