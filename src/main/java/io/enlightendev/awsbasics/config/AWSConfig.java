package io.enlightendev.awsbasics.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class AWSConfig {

    @Bean
    public AmazonS3 getS3Client(){
        return AmazonS3ClientBuilder.defaultClient();
    }
}
