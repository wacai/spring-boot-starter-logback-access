package com.wacai.springboot.logback.access;

import ch.qos.logback.access.tomcat.LogbackValve;
import org.apache.catalina.Context;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@Configuration
public class LogbackAccessConfiguration {

    @Bean
    @ConditionalOnProperty(name = "logback.access.config.path")
    public EmbeddedServletContainerCustomizer containerCustomizer(
            final @Value("${logback.access.config.path:}") String path) throws FileNotFoundException {
        final File file = ResourceUtils.getFile(path);
        return new EmbeddedServletContainerCustomizer() {

            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                if (container instanceof TomcatEmbeddedServletContainerFactory) {
                    ((TomcatEmbeddedServletContainerFactory) container)
                            .addContextCustomizers(new TomcatContextCustomizer() {

                                @Override
                                public void customize(Context context) {
                                    LogbackValve logbackValve = new LogbackValve();
                                    logbackValve.setFilename(file.getAbsolutePath());
                                    context.getPipeline().addValve(logbackValve);
                                }

                            });
                }
            }
        };

    }

}
