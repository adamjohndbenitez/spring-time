//package com.example.demo.authenticatingldap;
//
//import org.springframework.context.annotation.Configuration;
//
//FIXME: importing security is not found. Resolved by using implementation instead of compile.
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@Configuration
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .authorizeRequests()
//                .anyRequest()
//                .fullyAuthenticated()
//                .and().formLogin();
//    }
//
//    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder
//                .ldapAuthentication()
//                .userDnPatterns("uid={0}, ou=people")
//                .groupSearchBase("ou=groups")
//                .contextSource()
//                .url("ldap://localhost:8389/dc=springframework, dc=org")
//                .and()
//                .passwordCompare()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .passwordAttribute("userPassword");
//    }
//}

/*FIXME: Error starting ApplicationContext. To display the conditions report re-run your application with 'debug' enabled.
2021-06-08 09:57:48.325 ERROR 37797 --- [           main] o.s.boot.SpringApplication               : Application run failed

org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'springSecurityFilterChain' defined in class path resource [org/springframework/security/config/annotation/web/configuration/WebSecurityConfiguration.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [javax.servlet.Filter]: Factory method 'springSecurityFilterChain' threw exception; nested exception is java.lang.IllegalArgumentException: Root DNs must be the same when using multiple URLs
	at org.springframework.beans.factory.support.ConstructorResolver.instantiate(ConstructorResolver.java:658) ~[spring-beans-5.3.7.jar:5.3.7]
	at org.springframework.beans.factory.support.ConstructorResolver.instantiateUsingFactoryMethod(ConstructorResolver.java:486) ~[spring-beans-5.3.7.jar:5.3.7]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.instantiateUsingFactoryMethod(AbstractAutowireCapableBeanFactory.java:1334) ~[spring-beans-5.3.7.jar:5.3.7]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1177) ~[spring-beans-5.3.7.jar:5.3.7]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:564) ~[spring-beans-5.3.7.jar:5.3.7]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:524) ~[spring-beans-5.3.7.jar:5.3.7]
	at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335) ~[spring-beans-5.3.7.jar:5.3.7]
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234) ~[spring-beans-5.3.7.jar:5.3.7]
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333) ~[spring-beans-5.3.7.jar:5.3.7]
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208) ~[spring-beans-5.3.7.jar:5.3.7]
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:322) ~[spring-beans-5.3.7.jar:5.3.7]
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208) ~[spring-beans-5.3.7.jar:5.3.7]
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:944) ~[spring-beans-5.3.7.jar:5.3.7]
	at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918) ~[spring-context-5.3.7.jar:5.3.7]
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583) ~[spring-context-5.3.7.jar:5.3.7]
	at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:145) ~[spring-boot-2.5.0.jar:2.5.0]
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:758) [spring-boot-2.5.0.jar:2.5.0]
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:438) [spring-boot-2.5.0.jar:2.5.0]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:337) [spring-boot-2.5.0.jar:2.5.0]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1336) [spring-boot-2.5.0.jar:2.5.0]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1325) [spring-boot-2.5.0.jar:2.5.0]
	at com.example.demo.DemoApplication.main(DemoApplication.java:61) [main/:na]
Caused by: org.springframework.beans.BeanInstantiationException: Failed to instantiate [javax.servlet.Filter]: Factory method 'springSecurityFilterChain' threw exception; nested exception is java.lang.IllegalArgumentException: Root DNs must be the same when using multiple URLs
	at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:185) ~[spring-beans-5.3.7.jar:5.3.7]
	at org.springframework.beans.factory.support.ConstructorResolver.instantiate(ConstructorResolver.java:653) ~[spring-beans-5.3.7.jar:5.3.7]
Caused by: org.springframework.beans.BeanInstantiationException: Failed to instantiate [javax.servlet.Filter]: Factory method 'springSecurityFilterChain' threw exception; nested exception is java.lang.IllegalArgumentException: Root DNs must be the same when using multiple URLs

	... 21 common frames omitted
Caused by: java.lang.IllegalArgumentException: Root DNs must be the same when using multiple URLs
Caused by: java.lang.IllegalArgumentException: Root DNs must be the same when using multiple URLs

	at org.springframework.util.Assert.isTrue(Assert.java:121) ~[spring-core-5.3.7.jar:5.3.7]
	at org.springframework.security.ldap.DefaultSpringSecurityContextSource.<init>(DefaultSpringSecurityContextSource.java:72) ~[spring-security-ldap-5.5.0.jar:5.5.0]
	at org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer$ContextSourceBuilder.build(LdapAuthenticationProviderConfigurer.java:552) ~[spring-security-config-5.5.0.jar:5.5.0]
	at org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer$ContextSourceBuilder.access$100(LdapAuthenticationProviderConfigurer.java:445) ~[spring-security-config-5.5.0.jar:5.5.0]
	at org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer.getContextSource(LdapAuthenticationProviderConfigurer.java:380) ~[spring-security-config-5.5.0.jar:5.5.0]
	at org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer.build(LdapAuthenticationProviderConfigurer.java:94) ~[spring-security-config-5.5.0.jar:5.5.0]
	at org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer.configure(LdapAuthenticationProviderConfigurer.java:374) ~[spring-security-config-5.5.0.jar:5.5.0]
	at org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer.configure(LdapAuthenticationProviderConfigurer.java:60) ~[spring-security-config-5.5.0.jar:5.5.0]
	at org.springframework.security.config.annotation.AbstractConfiguredSecurityBuilder.configure(AbstractConfiguredSecurityBuilder.java:349) ~[spring-security-config-5.5.0.jar:5.5.0]
	at org.springframework.security.config.annotation.AbstractConfiguredSecurityBuilder.doBuild(AbstractConfiguredSecurityBuilder.java:303) ~[spring-security-config-5.5.0.jar:5.5.0]
	at org.springframework.security.config.annotation.AbstractSecurityBuilder.build(AbstractSecurityBuilder.java:38) ~[spring-security-config-5.5.0.jar:5.5.0]
	at org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter.authenticationManager(WebSecurityConfigurerAdapter.java:269) ~[spring-security-config-5.5.0.jar:5.5.0]
	at org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter.getHttp(WebSecurityConfigurerAdapter.java:204) ~[spring-security-config-5.5.0.jar:5.5.0]
	at org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter.init(WebSecurityConfigurerAdapter.java:315) ~[spring-security-config-5.5.0.jar:5.5.0]
	at org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter.init(WebSecurityConfigurerAdapter.java:93) ~[spring-security-config-5.5.0.jar:5.5.0]
	at com.example.demo.authenticatingldap.WebSecurityConfig$$EnhancerBySpringCGLIB$$78e72923.init(<generated>) ~[main/:na]
	at org.springframework.security.config.annotation.AbstractConfiguredSecurityBuilder.init(AbstractConfiguredSecurityBuilder.java:338) ~[spring-security-config-5.5.0.jar:5.5.0]
	at org.springframework.security.config.annotation.AbstractConfiguredSecurityBuilder.doBuild(AbstractConfiguredSecurityBuilder.java:300) ~[spring-security-config-5.5.0.jar:5.5.0]
	at org.springframework.security.config.annotation.AbstractSecurityBuilder.build(AbstractSecurityBuilder.java:38) ~[spring-security-config-5.5.0.jar:5.5.0]
	at org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration.springSecurityFilterChain(WebSecurityConfiguration.java:127) ~[spring-security-config-5.5.0.jar:5.5.0]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_251]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_251]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_251]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[na:1.8.0_251]
	at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:154) ~[spring-beans-5.3.7.jar:5.3.7]
	... 22 common frames omitted


> Task :DemoApplication.main() FAILED

Execution failed for task ':DemoApplication.main()'.
> Process 'command '/Library/Java/JavaVirtualMachines/jdk1.8.0_251.jdk/Contents/Home/bin/java'' finished with non-zero exit value 1
*/