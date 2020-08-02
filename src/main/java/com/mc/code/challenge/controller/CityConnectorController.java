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

@RestController
public class CityConnectorController {

	@Autowired
	@Qualifier("cityConnections")
	private ImmutableGraph<String> cityGraph;

	@RequestMapping(method = RequestMethod.GET, value = "/connected", produces = MediaType.TEXT_PLAIN_VALUE)
	public String areConnected(@RequestParam(value = "origin", required = true) String origin,
			@RequestParam(value = "destination", required = true) String destination) {

		if (inValid(origin) || inValid(destination)) {
			return "no";
		}
		if (notInFile(origin) || notInFile(destination)) {
			return "no";
		}

		return Graphs.reachableNodes(cityGraph, origin.toLowerCase()).contains(destination.toLowerCase()) ? "yes" : "no";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> allCities() {
		return cityGraph.nodes().stream().sorted().collect(Collectors.toList());
	}

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
