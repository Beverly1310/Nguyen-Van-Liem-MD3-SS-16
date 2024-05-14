package com.ra.btss16.config;

import javafx.beans.property.Property;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.ra")
public class AppConfig {
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource source = new DriverManagerDataSource();
        source.setDriverClassName("com.mysql.cj.jdbc.Driver");
        source.setUrl("jdbc:mysql://localhost:3306/quanlynhanvien");
        source.setUsername("root");
        source.setPassword("26092002");
        return source;
    }

    @Bean
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
        sf.setDataSource(getDataSource());
        sf.setPackagesToScan("com.ra.btss16.model");
        Properties props = new Properties();
        props.put("hibernate.show_sql", true);
        props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        sf.setHibernateProperties(props);
        try {
            sf.afterPropertiesSet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sf.getObject();
    }
}
