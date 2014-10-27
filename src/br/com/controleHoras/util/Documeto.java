package br.com.controleHoras.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import br.com.controleHoras.DAO.HorasDAO;
import br.com.controleHoras.bean.Horas;

@ManagedBean (name = "documentoMB")
public class Documeto {

	Horas horas = new Horas();
	
	public void gerarDocExcel() {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet firstSheet = workbook.createSheet("Tabela");
		boolean isStatus = true;
		
		String arquivo = "PLANILHA_"+ horas.getMes().toUpperCase() + "_";
    	Date data = new Date();
    	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
    	String dataFormatada = df.format(data);
    	String nmArquivo = dataFormatada.replace(" ", "_");
        String nmArquivoFormatado = nmArquivo.replace("/", "_");
        String nmArquivoSemCaracter = nmArquivoFormatado.replace(":", "_");
    	String path = arquivo + nmArquivoSemCaracter + ".xls";
    	
    	HorasDAO dao = new HorasDAO();
		List<Horas> lista = dao.listaPorMes(horas.getMes());
		
		if(!lista.isEmpty()){
			
			for(Horas h:lista){
				 if(h.getStatus().equals("2")){
					 isStatus = false;
					 break;
				 }
			}
			
		if(isStatus){
    	
    	try{
    	
	    	FileOutputStream fileOut = new FileOutputStream(path);
	    	workbook.write(fileOut);
	    	fileOut.close();
    	
    	System.out.println("Arquivo criado corretamente");
    	
    	}catch (Exception e) {
			System.out.println("erro ao criar arquivo");
		}
	
		FileOutputStream fos = null;
		HSSFCellStyle estilo = workbook.createCellStyle();
		estilo.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		
		// Define o tamanho padrão das colunas
		firstSheet.setDefaultColumnWidth((short) 20);
					
		try {
			fos = new FileOutputStream(new File("C:\\Users\\Digo\\Documents\\arquivos\\" + path));

			int i = 0;
			
			//Formatando a fonte

            HSSFFont fonte = workbook.createFont();
            fonte.setFontHeightInPoints((short)10);
            fonte.setFontName("Arial");
            fonte.setColor(HSSFColor.GREEN.index);
            fonte.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            fonte.setItalic(true);

			//Alinhando ao centro

            //Adicionando bordas
			
			estilo.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            estilo.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            estilo.setBottomBorderColor(HSSFColor.BLUE.index);
            estilo.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            estilo.setLeftBorderColor(HSSFColor.GREEN.index);
            estilo.setBorderRight(HSSFCellStyle.BORDER_THIN);
            estilo.setRightBorderColor(HSSFColor.BLUE.index);
            estilo.setBorderTop(HSSFCellStyle.BORDER_MEDIUM_DASHED);
            estilo.setTopBorderColor(HSSFColor.BLUE.index);
            estilo.setFillBackgroundColor(HSSFColor.BLUE.index);
            estilo.setFont(fonte);
			
			for (Horas hrs : lista) {
				
				
				for (int j=0 ; j<1 ; j++) {
					HSSFRow row2 = firstSheet.createRow(j);
					HSSFCell cell = null;
					
					cell = row2.createCell((short) (0));
					cell.setCellValue("DATA INICIO");
					cell.setCellStyle(estilo);
					cell = row2.createCell((short) (1));
					cell.setCellValue("DATA CONCLUSÃO");
					cell.setCellStyle(estilo);
					cell = row2.createCell((short) (2));
					cell.setCellValue("CLIENTE");
					cell.setCellStyle(estilo);
					cell = row2.createCell((short) (3));
					cell.setCellValue("PROJETO");
					cell.setCellStyle(estilo);
					cell = row2.createCell((short) (4));
					cell.setCellValue("ITEM");
					cell.setCellStyle(estilo);
					cell = row2.createCell((short) (5));
					cell.setCellValue("HORAS ESTIMADAS");
					cell.setCellStyle(estilo);
					cell = row2.createCell((short) (6));
					cell.setCellValue("HORAS EXECUTADAS");
					cell.setCellStyle(estilo);
					cell = row2.createCell((short) (7));
					cell.setCellValue("LUCRO HORAS");
					cell.setCellStyle(estilo);
					cell = row2.createCell((short) (8));
					cell.setCellValue("VALOR HORAS");
					cell.setCellStyle(estilo);
					cell = row2.createCell((short) (9));
					cell.setCellValue("LUCRO");
					cell.setCellStyle(estilo);
					cell = row2.createCell((short) (10));
					cell.setCellValue("OBSERVAÇÃO");
					cell.setCellStyle(estilo);
				

				} // fim do for
				
				HSSFRow row = firstSheet.createRow(i+1);

				row.createCell(0).setCellValue(hrs.getData());
				row.createCell(1).setCellValue(hrs.getDtConclusao());
				row.createCell(2).setCellValue(hrs.getCliente());
				row.createCell(3).setCellValue(hrs.getProjeto());
				row.createCell(4).setCellValue(hrs.getItem());
				row.createCell(5).setCellValue(hrs.getHorasEstim());
				row.createCell(6).setCellValue(hrs.getHorasExec());
				row.createCell(7).setCellValue(hrs.getLucroHoras());
				row.createCell(8).setCellValue(hrs.getValorHora());
				row.createCell(9).setCellValue(hrs.getLucro());
				row.createCell(10).setCellValue(hrs.getObs());

				i++;

			} // fim do for

			workbook.write(fos);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro ao exportar arquivo");
		} finally {
			try {
				fos.flush();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		 FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Planilha referente ao mês de " + horas.getMes() + " criada com sucesso no dretório ","");
		 FacesContext.getCurrentInstance().addMessage(null, message);
			
	  }else{
		  FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Planilha não pode ser gerada por existir trabalhos em aberto. ","");
			FacesContext.getCurrentInstance().addMessage(null, message);
	  }
	}else{
		 FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Não existe trabalhos cadastrados referentes ao mês de " + horas.getMes(),"");
			FacesContext.getCurrentInstance().addMessage(null, message);
	}
		
	}// fim do metodo exp

	public Horas getHoras() {
		return horas;
	}

	public void setHoras(Horas horas) {
		this.horas = horas;
	}

}
