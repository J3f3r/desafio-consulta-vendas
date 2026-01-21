package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;

public class SaleSummaryDTO {

	private String name;
	private Double total;
	
	SaleSummaryDTO(){}

	public SaleSummaryDTO(String name, Double total) {
		this.name = name;
		this.total = total;
	}
	
	public SaleSummaryDTO(Sale entity) {
		name = entity.toString();
		total = entity.getAmount();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
}
