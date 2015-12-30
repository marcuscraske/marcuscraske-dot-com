package com.limpygnome.website.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

@Configuration
@Order(4)
@EnableWebMvc
@ComponentScan({

        // Modules
        "com.limpygnome.website.module.**",

})
public class MvcConfig extends WebMvcConfigurerAdapter
{

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
    {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/favicon.ico").addResourceLocations("/resources/favicon.ico");
        registry.addResourceHandler("/robots.txt").addResourceLocations("/resources/robots.txt");
        registry.addResourceHandler("/content/**").addResourceLocations("/resources/content/");
    }

    @Bean
    public TilesConfigurer setupTilesConfigurer()
    {
        TilesConfigurer viewConfig = new TilesConfigurer();

        // Use any file that's 'views.xml'
        viewConfig.setDefinitions(new String[]{
                "/WEB-INF/views.xml",
                "/WEB-INF/**/views.xml"
        });

        return viewConfig;
    }

    @Bean
    public UrlBasedViewResolver setupTilesViewResolver()
    {
        UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
        viewResolver.setViewClass(TilesView.class);
        return viewResolver;
    }

}
