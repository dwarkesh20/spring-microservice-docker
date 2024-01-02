package com.ifs.gateway.filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class RouteValidator {


	public static final List<String> openApiEndpoints = List.of(
			"/api/auth/create-user", 
			"/api/auth/authenticate",
			"/eureka");

	public Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints.stream()
			.noneMatch(uri -> request.getURI().getPath().contains(uri));

	public static final List<String> commonApiEndpoints = List.of(
			"/api/feedback/getall", 
			"/api/feedback/id/",
			"/api/feedback/candidate/");

	public Predicate<ServerHttpRequest> isSecuredCommon = request -> commonApiEndpoints.stream()
			.anyMatch(uri -> request.getURI().getPath().contains(uri));

	public static final List<String> hrApiEndpoints = List.of(
			"/api/feedback/update/", 
			"/api/feedback/delete/");

	public Predicate<ServerHttpRequest> isSecuredHR = request -> hrApiEndpoints.stream()
			.anyMatch(uri -> request.getURI().getPath().contains(uri));

	public static final List<String> interviewerApiEndpoints = List.of(
			"/api/feedback/excel", 
			"/api/feedback/upload");

	public Predicate<ServerHttpRequest> isSecuredInterviewer = request -> interviewerApiEndpoints.stream()
			.anyMatch(uri -> request.getURI().getPath().contains(uri));
}
