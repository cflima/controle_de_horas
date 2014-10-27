package br.com.controleHoras.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Saldo {
	
	@Id
	@GeneratedValue
	Integer id;
	Double saldoParcial;
	String mes;

	public Double getSaldoParcial() {
		return saldoParcial;
	}

	public void setSaldoParcial(Double saldoParcial) {
		this.saldoParcial = saldoParcial;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}
}
