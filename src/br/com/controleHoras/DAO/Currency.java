package br.com.controleHoras.DAO; 

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


 
public class Currency {
    	
       	public static void main(String[] args) {
       		Date data = new Date();  
       		Calendar c = Calendar.getInstance();  
       		c.setTime(data);  
       		  
       		// formata e exibe a data e hora  
       		Format format = new SimpleDateFormat("MM");  
       		System.out.println(format.format(c.getTime()));
		}
    }
     
