package com.mercadolibre.microitem.health.model;

import java.time.ZonedDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomHealth {

	private ZonedDateTime date;
	@JsonProperty("avg_response_time")
	private long avgResponseTime;
	@JsonProperty("total_requests")
	private long totalRequests;
	@JsonProperty("avg_reponse_time_api_calls")
	private long avgReponseTimeApiCalls;
	@JsonProperty("total_count_api_calls")
	private long totalCountApiCalls;
	@JsonProperty("info_request")
	private List<InfoRequest> infoRequestList;
	

}
