package com.mercadolibre.microitem.health.service;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.mercadolibre.microitem.health.model.CustomHealth;
import com.mercadolibre.microitem.health.model.InfoRequest;

import reactor.core.publisher.Mono;

@Service
public class HealthServiceImpl implements HealthService {

	private final HttpTraceRepository htttpTraceRepository;
	private final WebClient webClient;
	private final List<CustomHealth> historical;
	private CustomHealth totalHealth;

	public HealthServiceImpl(HttpTraceRepository htttpTraceRepositoryIncoming, WebClient webClient) {
		super();
		this.htttpTraceRepository = htttpTraceRepositoryIncoming;
		this.webClient = webClient;
		this.historical = new LinkedList<CustomHealth>();
		this.totalHealth = new CustomHealth();
		this.totalHealth.setDate(ZonedDateTime.now());
	}

	public List<CustomHealth> getHealthData() {
		return historical;
	}

	/**
	 * Job to add the latest health data to the historical list
	 */
	@Scheduled(fixedDelay = 60000)
	private void scheduleMetricGeneration() {
		historical.add(addIncomingInfo(addOutcomingInfo(addTrace(new CustomHealth()))));
	}

	/**
	 * Add the status count information to the health object
	 * @param health 
	 * @return health recibed as parameter with the new data added
	 */
	private CustomHealth addTrace(CustomHealth health) {
		Instant min = totalHealth.getDate().toInstant().minus(1, ChronoUnit.MINUTES);
		health.setDate(min.atZone(totalHealth.getDate().getZone()));
		List<HttpTrace> incoming = htttpTraceRepository.findAll();
		Map<Integer, Long> statusMap = incoming.parallelStream().filter(t -> Duration.between(min, t.getTimestamp()).toMinutes() < 1)
				.map(t -> t.getResponse().getStatus()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		List<InfoRequest> statusCount = statusMap.entrySet().parallelStream().map(e -> {
			return new InfoRequest(e.getKey(), e.getValue());
		}).collect(Collectors.toList());
		health.setInfoRequestList(statusCount);
		
		return health;
	}
	
	/**
	 * Add the metrics related to the called rest services
	 * @param health 
	 * @return health recibed as parameter with the new data added
	 */
	private CustomHealth addIncomingInfo(CustomHealth customHealth) {
		JsonNode jsonNode = fetchIncomingMetrics().block();
		long count = 0;
		long totalTime = 0;
		if (jsonNode != null) {
			for (JsonNode measurement : jsonNode.get("measurements")) {
				if (measurement.get("statistic").asText().equals("COUNT")) {
					count = measurement.get("value").asLong();
				} else if (measurement.get("statistic").asText().equals("TOTAL_TIME")) {
					totalTime = Math.round(measurement.get("value").asDouble() * 1000L);
				}
			}
			customHealth.setTotalCountApiCalls(count - totalHealth.getTotalCountApiCalls());
			customHealth.setAvgReponseTimeApiCalls(Math.abs(totalTime - totalHealth.getAvgReponseTimeApiCalls())
					/ customHealth.getTotalCountApiCalls());
		}

		totalHealth.setTotalCountApiCalls(count);
		totalHealth.setAvgReponseTimeApiCalls(totalTime);

		return customHealth;
	}

	/**
	 * Add the metrics related to the request recibed by the services
	 * @param health 
	 * @return health recibed as parameter with the new data added
	 */
	private CustomHealth addOutcomingInfo(CustomHealth customHealth) {
		JsonNode jsonNode = fetchOutcomingMetrics().block();
		long count = 0;
		long totalTime = 0;
		if (jsonNode != null) {
			for (JsonNode measurement : jsonNode.get("measurements")) {
				if (measurement.get("statistic").asText().equals("COUNT")) {
					count = measurement.get("value").asLong();
				} else if (measurement.get("statistic").asText().equals("TOTAL_TIME")) {
					totalTime = Math.round(measurement.get("value").asDouble() * 1000L);
				}
			}
			customHealth.setTotalRequests(count - totalHealth.getTotalRequests());
			customHealth.setAvgResponseTime(
					Math.abs(totalTime - totalHealth.getAvgReponseTimeApiCalls()) / customHealth.getTotalRequests());
		}

		totalHealth.setTotalRequests(count);
		totalHealth.setAvgResponseTime(totalTime);

		return customHealth;
	}

	/**
	 * Get metrics data from the actuator api (http.client.requests)
	 * @return Mono reference of the outcoming http metrics
	 */
	private Mono<JsonNode> fetchOutcomingMetrics() {
		return webClient.get().uri("localhost:8091/actuator/metrics/http.client.requests").retrieve()
				.bodyToMono(JsonNode.class).onErrorResume(e -> Mono.empty());
	}

	/**
	 * Get metrics data from the actuator api (http.server.requests)
	 * @return Mono reference of the incoming http metrics
	 */
	private Mono<JsonNode> fetchIncomingMetrics() {
		return webClient.get().uri("localhost:8091/actuator/metrics/http.server.requests").retrieve()
				.bodyToMono(JsonNode.class).onErrorResume(e -> Mono.empty());
	}

}
