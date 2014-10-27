package br.com.controleHoras.controle;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.pattern.IntegerPatternConverter;
import org.primefaces.event.CellEditEvent;

import br.com.controleHoras.DAO.HorasDAO;
import br.com.controleHoras.bean.Horas;
import br.com.controleHoras.bean.Saldo;
import br.com.controleHoras.util.ValidarHoras;

@ManagedBean (name ="horasMB")
@SessionScoped
public class HorasMB {

	static HorasDAO dao = new HorasDAO();
	static Horas horas = new Horas();
	static Saldo saldoParcial = new Saldo();
	Horas mostrarHoras = new Horas();
	List<Horas> listaItens = new ArrayList<Horas>();
	Double calcHoras;
	static Double valorHoras;
	Double saldoTotal;
	String msg;

	public HorasMB() throws ClassNotFoundException, SQLException {
		listarItens();
	}
	
	public void inserirItem() throws ClassNotFoundException, SQLException{
		
		ValidarHoras valida = new ValidarHoras();
		boolean isValidate = valida.validar(horas);
		
		Saldo saldo = new Saldo();
		Date data = new Date();  
   		Calendar c = Calendar.getInstance();  
   		c.setTime(data);  
   		// formata e exibe a data e hora  
   		Format format = new SimpleDateFormat("MM");  
   		String mesAtual = format.format(c.getTime()).replace("0", "");
   	
		
		if(isValidate != true){
	    	
			Date dataAtual = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy - HH:mm:ss");
			
			horas.setData(sdf.format(dataAtual).toString());
			horas.setStatus("2");
			Double calcLucros = calcLucros(horas.getHorasEstim(), horas.getValorHora());
			horas.setLucro(calcLucros);
			horas.setMes(mesAtual);
			
			calcSaldo(horas);
			
			dao.inserir(horas);
			
			listarItens();
			
			horas = new Horas();
		}else{
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Todos os campos são obrigatórios!","");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
	}
	
	public void calcSaldo(Horas horas) throws ClassNotFoundException, SQLException{
		
		Saldo saldo = new Saldo();
		
   		
   		Double calcLucro ;
		Double calcLucrosGravados = 0.0;
		Double calcSaldoParcial;
   		
		Integer mesAtual = buscaMesAtual();
   		List<Saldo> lista = dao.listaPorMes(mesAtual);
   		
   		if(!lista.isEmpty()){
   			
   			calcLucrosGravados = dao.buscaSaldo(Integer.parseInt(lista.get(0).getMes()));
		    calcLucro = calcLucros(horas.getHorasEstim(), horas.getValorHora());
			calcSaldoParcial = calcLucrosGravados + calcLucro;
			
			saldo.setId(lista.get(0).getId());
			saldo.setSaldoParcial(calcSaldoParcial);
			dao.updateSaldo(saldo);
			
   		}else{
   			calcSaldoParcial = calcLucros(horas.getHorasEstim(), horas.getValorHora());
   			saldo.setMes(mesAtual.toString());
   			saldo.setSaldoParcial(calcSaldoParcial);
   			dao.inserirSaldo(saldo);
   		}
		
	}
	
	public Integer buscaMesAtual(){
		Date data = new Date();  
   		Calendar c = Calendar.getInstance();  
   		c.setTime(data);  
   		// formata e exibe a data e hora  
   		Format format = new SimpleDateFormat("MM");  
   		String mesAtual = format.format(c.getTime()).replace("0", "");
   		
   		return Integer.parseInt(mesAtual);
	}
	
	public void alterarItem(CellEditEvent event) throws ClassNotFoundException, SQLException{
		
		List<Horas> listaHoras = new ArrayList<Horas>();
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();
		
		UIComponent form = (UIComponent) event.getColumn();
		UIComponent component =  event.getComponent();
		UIInput input = (UIInput) component.findComponent("hiddenId");
		
		Integer id = (Integer) input.getValue();
		
		
		if(form.getId().equals("horasEstim") || form.getId().equals("valorHora")){
			Double valor = (Double) event.getNewValue();
			dao.alterar(form.getId(), valor, id);
			
			Horas buscaHoras = dao.buscaPorId(id);
			
			
			Double calcLucro = calcLucros(buscaHoras.getHorasEstim(), buscaHoras.getValorHora());
			dao.alterarValorLucros(calcLucro, id);
			
			listaHoras = dao.listaItens();
			saldoTotal = 0.0;
			
			for(Horas hrs : listaHoras){
				saldoTotal += hrs.getLucro();
			}
			
			Integer mesAtual = buscaMesAtual();
			
			saldoParcial.setId(mesAtual);
			saldoParcial.setSaldoParcial(saldoTotal);
			dao.inserirSaldo(saldoParcial);
			
			if(form.getId().equals("horasEstim")){
				calcHoras(buscaHoras.getHorasExec(), id);
			}
		}else{
			String valor = (String) event.getNewValue();
			dao.alterar(form.getId(), valor, id);
		}
		
		if(newValue != null && !newValue.equals(oldValue)) {  
	            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, form.getId() +  " alterado", "valor antigo: " + oldValue + ", novo valor: " + newValue);  
	            FacesContext.getCurrentInstance().addMessage(null, msg);  
		}
		
		listarItens();
	
	}
	
	public void calcHoras(Double valor, Integer id) throws ClassNotFoundException, SQLException{
		
		Horas horaEstim = dao.buscaPorId(id);
		calcHoras =  horaEstim.getHorasEstim() - valor;
		horas.setLucroHoras(calcHoras);
		
		dao.alterarLucrosHora(calcHoras, id);
	}
	
	
	public static Double calcLucros(Double horaEstim, Double valorHora) throws ClassNotFoundException, SQLException{
		
		valorHoras = horaEstim * valorHora;
		
		return valorHoras;

	}
	
	public void encerrarItem() throws ClassNotFoundException, SQLException{
		
		Date dataAtual = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
		
		Integer id = mostrarHoras.getIdHoras();
		
		horas.setDtConclusao(sdf.format(dataAtual).toString());
		horas.setStatus("1");
		calcHoras(mostrarHoras.getHorasExec(), id);
		
		dao.alterarItemEncerrado(horas.getStatus(), horas.getDtConclusao(), mostrarHoras.getIdHoras(), mostrarHoras.getHorasExec(), mostrarHoras.getObs());
		
		listarItens();
		
	}
	
	public void excluirItem() throws ClassNotFoundException, SQLException{
		
		dao.excluir(mostrarHoras);
		Double saldo = dao.buscaSaldo(Integer.parseInt(mostrarHoras.getMes()));
		Double saldoTotal = saldo - mostrarHoras.getLucro();
		
		List<Saldo> listaSaldo = dao.listaPorMes(Integer.parseInt(mostrarHoras.getMes()));
		saldoParcial.setId(listaSaldo.get(0).getId());
		saldoParcial.setSaldoParcial(saldoTotal);
		saldoParcial.setMes(listaSaldo.get(0).getMes());
		dao.inserirSaldo(saldoParcial);
		
		listarItens();
		
		 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Confirmação",  "Item excluido com sucesso..");  
	     FacesContext.getCurrentInstance().addMessage(null, message); 
	}
	
	public void listarItens() throws ClassNotFoundException, SQLException{
		
		Integer mesAtual = buscaMesAtual();
		saldoTotal = dao.buscaSaldo(mesAtual);
		listaItens = dao.listaItens();
	}
	
	public void teste(ChangeEvent event){
		System.out.println("metodo teste esta chegando aqui.");
	}
		
	public Horas getHoras() {
		return horas;
	}

	public void setHoras(Horas horas) {
		this.horas = horas;
	}

	public List<Horas> getListaItens() {
		return listaItens;
	}

	public void setListaItens(List<Horas> listaItens) {
		this.listaItens = listaItens;
	}

	public Double getCalcHoras() {
		return calcHoras;
	}

	public void setCalcHoras(Double calcHoras) {
		this.calcHoras = calcHoras;
	}

	public Horas getMostrarHoras() {
		return mostrarHoras;
	}

	public void setMostrarHoras(Horas mostrarHoras) {
		this.mostrarHoras = mostrarHoras;
	}

	public Saldo getSaldoParcial() {
		return saldoParcial;
	}

	public void setSaldoParcial(Saldo saldoParcial) {
		this.saldoParcial = saldoParcial;
	}

	public Double getSaldoTotal() {
		return saldoTotal;
	}

	public void setSaldoTotal(Double saldoTotal) {
		this.saldoTotal = saldoTotal;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


}
	
