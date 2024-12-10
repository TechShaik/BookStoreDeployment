package com.files.BookStoreGateway.FallbackController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class FallBackController {

    @RequestMapping("/orderFallBack")
    public ResponseEntity<Mono<String>> orderServiceFallBack() {
        return new ResponseEntity<Mono<String>>(Mono.just("Order Service is taking too long to respond or is down. Please try again later"),HttpStatus.BAD_GATEWAY);
    }
    
    @RequestMapping("/cartFallBack")
    public ResponseEntity<Mono<String>> cartServiceFallBack() {
        return new ResponseEntity<Mono<String>>(Mono.just("Cart Service is taking too long to respond or is down. Please try again later"),HttpStatus.BAD_GATEWAY);
    }
    
    @RequestMapping("/bookFallBack")
    public ResponseEntity<Mono<String>> bookServiceFallBack() {
        return new ResponseEntity<Mono<String>>(Mono.just("Book Service is taking too long to respond or is down. Please try again later"),HttpStatus.BAD_GATEWAY);
    }
    
    @RequestMapping("/userFallBack")
    public ResponseEntity<Mono<String>> userServiceFallBack() {
        return new ResponseEntity<Mono<String>>(Mono.just("User Service is taking too long to respond or is down. Please try again later"),HttpStatus.BAD_GATEWAY);
    }

}
