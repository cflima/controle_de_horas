package br.com.controleHoras.bean;

public enum Mes {
	
	JANEIRO(1),
	 FEVEREIRO(2),
	 MARCO(3),
	 ABRIL(4),
	 MAIO(5),
	 JUNHO(6),
	 JULHO(7),
	 AGOSTO(8),
	 SETEMBRO(9),
	 OUTUBRO(10),
	 NOVEMBRO(11),
	 DEZEMBRO(12);
	 
	 private int value;
	  
	 private Mes(int value) {
           this.value = value;
   }
	 
}
