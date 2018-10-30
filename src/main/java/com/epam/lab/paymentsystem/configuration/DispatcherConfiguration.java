package com.epam.lab.paymentsystem.configuration;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * This class declares three(ТУТ ПОПРАВИТЕ С УЧЕТОМ ВАШИХ БИНОВ) @Bean methods and may be processed
 * by the Spring container using @Configuration to generate bean definitions and
 * service requests for those beans at runtime. {@code DispatcherConfiguration} class
 * configures the following front-end application components: jsp pages, localization.
 *
 * @author unascribed
 * @since 0.0.1
 */

@Configuration
@ComponentScan("com.epam.lab.paymentsystem")
@EnableWebMvc
public class DispatcherConfiguration implements WebMvcConfigurer {
  private static final String PREFIX = "/WEB-INF/jsp/";
  private static final String SUFFIX = ".jsp";

  /**
   * This @Bean method produces {@code ViewResolver} which allows us to set properties
   * such as prefix or suffix to the view name to generate the final view page URL.
   *
   * @return view resolver
   * @link org.springframework.web.servlet
   */
  @Bean
  public ViewResolver internalResourceViewResolver() {
    InternalResourceViewResolver bean = new InternalResourceViewResolver();
    bean.setPrefix(PREFIX);
    bean.setSuffix(SUFFIX);
    return bean;
  }

  /**
   * This @Bean method produces {@code ResourceBundleMessageSource} which allows to support
   * internationalization, it requires the capability of resolving
   * text messages for different locales. Spring’s context is able to resolve text
   * messages for a target locale by their keys. Typically, the messages for one locale should be
   * stored in one separate properties file.
   *
   * @return Resource Bundle
   * @link org.springframework.context.support.ResourceBundleMessageSource
   */
  @Bean
  public ResourceBundleMessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("messages");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

  /**
   * This @Bean method for web-based locale resolution strategies that allows for both locale
   * resolution via the request and locale modification via request and response.
   *
   * @return locale resolver
   * @link org.springframework.web.servlet
   */
  @Bean
  public LocaleResolver localeResolver() {
    CookieLocaleResolver localeResolver = new CookieLocaleResolver();
    localeResolver.setDefaultLocale(new Locale("en"));
    localeResolver.setCookieName("myLocaleName");
    localeResolver.setCookieMaxAge(4800);
    return localeResolver;
  }

  /**
   * Add Spring MVC lifecycle interceptors for pre- and post-processing of controller
   * method invocations. Interceptors can be registered to apply to all requests or be
   * limited to a subset of URL patterns.
   *
   * @param registry interceptor registry
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
    localeChangeInterceptor.setParamName("lang");
    registry.addInterceptor(localeChangeInterceptor);
  }
}