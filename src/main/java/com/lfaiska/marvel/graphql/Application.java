package com.lfaiska.marvel.graphql;

import com.lfaiska.marvel.graphql.exception.GraphQLErrorAdapter;
import com.lfaiska.marvel.graphql.resolver.CharacterResolver;
import com.lfaiska.marvel.graphql.resolver.QueryResolver;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Bean(name = "appRestClient")
	public RestTemplate getRestClient() {
		RestTemplate restClient = new RestTemplate(
				new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

		restClient.setInterceptors(Collections.singletonList((request, body, execution) -> {
		    log.info("Acessando: " + request.getURI().toString());
			return execution.execute(request, body);
		}));

		return restClient;
	}

    @Bean
    public GraphQLErrorHandler errorHandler() {
	    return new GraphQLErrorHandler() {
	        @Override
            public List<GraphQLError> processErrors(List<GraphQLError> errors) {
	            List<GraphQLError> clientErrors = errors.stream()
                        .filter(this::isClientError)
                        .collect(Collectors.toList());

	            List<GraphQLError> serverErrors = errors.stream()
                        .filter(e -> !isClientError(e))
                        .map(GraphQLErrorAdapter::new)
                        .collect(Collectors.toList());

	            List<GraphQLError> e = new ArrayList<>();
	            e.addAll(clientErrors);
	            e.addAll(serverErrors);
	            return e;
	        }

	        protected boolean isClientError(GraphQLError error) {
	            return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
	        }
	    };
	}

	@Bean
    public CharacterResolver characterResolver() {
	    return new CharacterResolver();
    }

    @Bean
    public QueryResolver queryResolver() {
	    return new QueryResolver();
    }

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
	}
}
