package com.mc.code.challenge.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;
import com.google.common.graph.MutableGraph;

@Configuration
public class AppConfig {

	@Bean(name = "cityConnections")
	public ImmutableGraph<String> getCityMapping() throws IOException {
		
		MutableGraph<String> graph = GraphBuilder.undirected().allowsSelfLoops(true).build();

		Resource resource = new ClassPathResource("static/city.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
		String line = null;
		String[] cities = null;

		while ((line = reader.readLine()) != null) {

			//ignore comment line
			if(line.trim().startsWith("#"))
				continue;
			
			cities = line.split(",");

			if (!StringUtils.isEmpty(cities[0].trim())) {
				graph.addNode(cities[0].trim().toLowerCase());
			}
			if (cities.length == 2) {

				if (!StringUtils.isEmpty(cities[1].trim())) {
					graph.addNode(cities[1].trim().toLowerCase());
				}

				//add an edge only when both nodes are in the graph
				if (!StringUtils.isEmpty(cities[0].trim()) && !StringUtils.isEmpty(cities[1].trim())) {
					graph.putEdge(cities[0].trim().toLowerCase(), cities[1].trim().toLowerCase());
				}
			}
		}

		return ImmutableGraph.copyOf(graph);
	}

}
