package br.com.controleHoras.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Horas {
	
	@Id
	@GeneratedValue
	private Integer idHoras;
	String cliente;
	String projeto;
	String item;
	Double horasExec;
	Double horasEstim;
	Double lucroHoras;
	Double lucro;
	Double valorHora;
	String data;
	String dtConclusao;
	String status;
	String obs;
	String mes;
	
	public Integer getIdHoras() {
		return idHoras;
	}

	public void setIdHoras(Integer idHoras) {
		this.idHoras = idHoras;
	}

	public String getProjeto() {
		return projeto;
	}
	
	public void setProjeto(String projeto) {
		this.projeto = projeto;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public Double getHorasExec() {
		return horasExec;
	}
	public void setHorasExec(Double horasExec) {
		this.horasExec = horasExec;
	}
	public Double getHorasEstim() {
		return horasEstim;
	}
	public void setHorasEstim(Double horasEstim) {
		this.horasEstim = horasEstim;
	}
	public Double getLucroHoras() {
		return lucroHoras;
	}
	public void setLucroHoras(Double lucroHoras) {
		this.lucroHoras = lucroHoras;
	}
	public Double getLucro() {
		return lucro;
	}
	public void setLucro(Double lucro) {
		this.lucro = lucro;
	}
	public Double getValorHora() {
		return valorHora;
	}
	public void setValorHora(Double valorHora) {
		this.valorHora = valorHora;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDtConclusao() {
		return dtConclusao;
	}

	public void setDtConclusao(String dtConclusao) {
		this.dtConclusao = dtConclusao;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

}
