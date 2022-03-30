package com.example.fs.subdirs;

import java.io.ByteArrayInputStream;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.content.fs.config.FilesystemStoreConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterRegistry;

@SpringBootApplication
public class SubDirectoriesApplication implements ApplicationRunner {

    private static Logger LOG = LoggerFactory.getLogger(SubDirectoriesApplication.class);

    @Autowired
    private FileRepository repo;

    @Autowired
    private FileContentStore store;

	public static void main(String[] args) {
		SpringApplication.run(SubDirectoriesApplication.class, args);
	}

    @Override
    public void run(ApplicationArguments args) throws Exception {

        File f = new File();
        f.setContentId(new CustomId("foo", UUID.randomUUID().toString()));
        f = store.setContent(f, new ByteArrayInputStream("foo".getBytes()));
        f = repo.save(f);

        LOG.info(IOUtils.toString(store.getContent(f)));
    }

    @Configuration
    public static class Config {
        @Bean
        public Converter<CustomId, String> customIdConverter() {
            return new Converter<CustomId, String>() {

                @Override
                public String convert(CustomId source) {
                    return source.getDirectory() + "/" + source.getId();
                }

            };
        }

        @Bean
        public FilesystemStoreConfigurer configurer() {
            return new FilesystemStoreConfigurer() {

                @Override
                public void configureFilesystemStoreConverters(ConverterRegistry registry) {
                    registry.addConverter(customIdConverter());
                }
            };
        }
    }
}

