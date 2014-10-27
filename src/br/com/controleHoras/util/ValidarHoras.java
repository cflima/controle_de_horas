package br.com.controleHoras.util;

import br.com.controleHoras.bean.Horas;

public class ValidarHoras {

	
	public boolean validar(Horas horas) {
		
		boolean validar = false;
		
		if(horas.getCliente().equals("")){
			validar = true;
		}
		
		if(horas.getProjeto().equals("")){
			validar = true;
		}
		
		if(horas.getHorasEstim().equals("")){
			validar = true;
		}
		
		if(horas.getItem().equals("")){
			validar = true;
		}
		
		if(horas.getHorasEstim() == null || horas.getHorasEstim() == 0){
			validar = true;
		}
		
		if(horas.getValorHora() == null || horas.getValorHora() == 0){
			validar = true;
		}
		return validar;
		
	
	}

	
}
