package com.mc.code.challenge.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.graph.Graphs;
import com.google.common.graph.ImmutableGraph;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "City Connections Controller", description = "API calls to determine if two cities are connected as per city.txt data")
public class CityConnectorController {

	@Autowired
	@Qualifier("cityConnections")
	private ImmutableGraph<String> cityGraph;

	@ApiOperation(value = "Returns 'yes' if two cities are connected through a series of roads and 'no' otherwise", response = String.class)
	@ApiResponses(value = {@ApiResponse(code = 401, message = "Not Authorized to call this service") , @ApiResponse(code = 200, message = "if a connection is found (or not)") })
	@RequestMapping(method = RequestMethod.GET, value = "/connected", produces = MediaType.TEXT_PLAIN_VALUE)
	public String areConnected(@RequestParam(value = "origin", required = false) String origin,
			@RequestParam(value = "destination", required = false) String destination) {

		if (inValid(origin) || inValid(destination)) {
			return "no";
		}
		if (notInFile(origin) || notInFile(destination)) {
			return "no";
		}

		return Graphs.reachableNodes(cityGraph, origin.toLowerCase()).contains(destination.toLowerCase()) ? "yes" : "no";
	}

	@ApiOperation(value = "Returns all distinct cities from city.txt file", response = List.class)
	@RequestMapping(method = RequestMethod.GET, value = "/city", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> allCities() {
		return cityGraph.nodes().stream().sorted().collect(Collectors.toList());
	}

	@ApiOperation(value = "Returns all distinct city pairs from city.txt file", response = List.class)
	@RequestMapping(method = RequestMethod.GET, value = "/city/pairs", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> cityPairs() {
		return cityGraph.edges().stream().map(s -> s.toString()).collect(Collectors.toList());
	}

	private boolean inValid(String param) {
		return StringUtils.isEmpty(param) || (param.trim().length() == 0);
	}

	private boolean notInFile(String param) {
		return !cityGraph.nodes().contains(param.toLowerCase());
	}

}
