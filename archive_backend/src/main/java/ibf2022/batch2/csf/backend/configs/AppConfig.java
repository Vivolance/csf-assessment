package ibf2022.batch2.csf.backend.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AppConfig {
    // @Value("${DO_STORAGE_KEY}")
    private String accessKey = "DO00FQHLTQZWN98NCKPN"; 
 
    // @Value("${DO_STORAGE_SECRETKEY}")
    private String secretKey = "gUgk2qaSGZ7E8mxog5DBOmwgSck6vPTJBfPGVCb7XhA"; 
 
    // @Value("${DO_STORAGE_ENDPOINT}")
    private String endPoint = "https://my-bucket-two.sgp1.digitaloceanspaces.com"; 
 
    // @Value("${DO_STORAGE_ENDPOINT_REGION}")
    private String endPointRegion = "sgp1";
   
    @Bean
    public AmazonS3 createS3Client(){
            System.out.println("accessKey: " + accessKey);
            System.out.println("secretKey: " + secretKey);
            System.out.println("endPoint: " + endPoint);
            System.out.println("endPointRegion: " + endPointRegion);

            ClientConfiguration clientConfiguration = new ClientConfiguration();
            clientConfiguration.setConnectionTimeout(300000); // 5 minutes
            clientConfiguration.setSocketTimeout(300000); // 5 minutes
            clientConfiguration.setConnectionMaxIdleMillis(300000); // 5 minutes
            clientConfiguration.setClientExecutionTimeout(300000); // 5 minutes
            clientConfiguration.withConnectionTTL(300000); // 5 minutes
            clientConfiguration.setConnectionMaxIdleMillis(300000); // 5 minutes
            
            BasicAWSCredentials cred =
                    new BasicAWSCredentials(accessKey, secretKey);
            EndpointConfiguration ep = 
                    new EndpointConfiguration(endPoint, endPointRegion);

            return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(ep)
                .withCredentials(new AWSStaticCredentialsProvider(cred))
                .withClientConfiguration(clientConfiguration)
                .build();
    }
}
