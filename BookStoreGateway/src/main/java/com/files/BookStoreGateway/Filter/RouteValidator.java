package com.files.BookStoreGateway.Filter;

import java.util.List;
import java.util.function.Predicate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {

	
	public static final List<String> openApiEndpionts=List.of(
			"/users/register",
			 "/users/validate",
			 "/users/login",
			 "/actuator/**"
			);
	
	public Predicate<ServerHttpRequest> isSecured=
			request ->openApiEndpionts
			.stream()
			.noneMatch(uri -> request.getURI().getPath().contains(uri));
	
}
