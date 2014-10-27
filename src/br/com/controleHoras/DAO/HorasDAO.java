package br.com.controleHoras.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.controleHoras.bean.Horas;
import br.com.controleHoras.bean.Saldo;

public class HorasDAO {
	
	Connection connection;
	Conexao con = new Conexao();
	List<Horas> listaPorAno = new ArrayList<Horas>();
	
	public void inserir(Horas horas){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.saveOrUpdate(horas);
		t.commit();
		
	}
	
	public void inserirSaldo(Saldo saldo){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.saveOrUpdate(saldo);
		t.commit();
		
	}
	
	public void updateSaldo(Saldo saldo) throws SQLException, ClassNotFoundException{
			
			this.connection = con.getConnection();
			String sql ="UPDATE Saldo SET saldoParcial =? WHERE id = ?";
			
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setDouble(1, saldo.getSaldoParcial());
			stmt.setInt(2, saldo.getId());
			stmt.execute();
			stmt.close();
			
		}

	public void alterar(String campo, String valor, Integer id) throws SQLException, ClassNotFoundException{
		
		this.connection = con.getConnection();
		String sql ="UPDATE horas SET " + campo + "=? WHERE idHoras = ?";
		
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, valor);
		stmt.setInt(2, id);
		stmt.execute();
		stmt.close();
		
	}
	
	public void inserirObs(String valor, Integer id) throws SQLException, ClassNotFoundException{
		
		this.connection = con.getConnection();
		String sql ="UPDATE horas SET obs =? WHERE idHoras = ?";
		
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, valor);
		stmt.setInt(2, id);
		stmt.execute();
		stmt.close();
		
	}
	
	public void alterar(String campo, Double valor, Integer id) throws SQLException, ClassNotFoundException{
		
		this.connection = con.getConnection();
		String sql ="UPDATE horas SET " + campo + "=? WHERE idHoras = ?";
		
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setDouble(1, valor);
		stmt.setInt(2, id);
		stmt.execute();
		stmt.close();
		
	}
	
	public void alterarLucrosHora(Double valor, Integer id) throws SQLException, ClassNotFoundException{
		
		this.connection = con.getConnection();
		String sql ="UPDATE horas SET lucroHoras =? WHERE idHoras = ?";
		
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setDouble(1, valor);
		stmt.setInt(2, id);
		stmt.execute();
		stmt.close();
		
	}
	
public void alterarValorLucros(Double valor, Integer id) throws SQLException, ClassNotFoundException{
		
		this.connection = con.getConnection();
		String sql ="UPDATE horas SET lucro =? WHERE idHoras = ?";
		
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setDouble(1, valor);
		stmt.setInt(2, id);
		stmt.execute();
		stmt.close();
		
	}
	
	public void alterarItemEncerrado(String status, String dtConculsao, Integer id, Double horasExec, String obs) throws SQLException, ClassNotFoundException{
		
		this.connection = con.getConnection();
		String sql ="UPDATE horas SET dtConclusao =? , status =? , horasExec = ? , obs=? WHERE idHoras = ?";
		
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, dtConculsao);
		stmt.setString(2, status);
		stmt.setDouble(3, horasExec);
		stmt.setString(4, obs);
		stmt.setInt(5, id);
		stmt.execute();
		stmt.close();
		
	}
	
	
	public List<Horas> listaItens(){
		
		Saldo saldo = new Saldo();
		Date data = new Date();  
   		Calendar c = Calendar.getInstance();  
   		c.setTime(data);  
   		// formata e exibe a data e hora  
   		Format format = new SimpleDateFormat("MM");  
   		String mesAtual = format.format(c.getTime()).replace("0", "");
		
		List<Horas> listaPorMes = new ArrayList<Horas>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria crit = session.createCriteria(Horas.class);
		crit.add(Restrictions.like("mes", "%" + mesAtual + "%"));
		crit.addOrder(Order.asc("mes"));
		listaPorMes = crit.list();
		t.commit();
		
		listaPorAno = verificaAno(listaPorMes);
		
		
		return listaPorAno;
	}
	
	public List<Horas> listaSaldo(String mes){
		
		List<Horas> listaSaldoMes = new ArrayList<Horas>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria crit = session.createCriteria(Horas.class);
		crit.add(Restrictions.like("data", "%" + mes + "%"));
		crit.addOrder(Order.asc("data"));
		listaSaldoMes = crit.list();
		t.commit();

		return listaSaldoMes;
	}
	
		@SuppressWarnings("unchecked")
		public List<Saldo> listaPorMes(Integer mes) {

			List<Saldo> listaPorMes = new ArrayList<Saldo>();
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction t = session.beginTransaction();
			Criteria crit = session.createCriteria(Saldo.class);
			crit.add(Restrictions.like("mes", "%" + mes + "%"));
			crit.addOrder(Order.asc("mes"));
			listaPorMes = crit.list();
			t.commit();

			return listaPorMes;

		}
		
		@SuppressWarnings("unchecked")
		public List<Horas> listaPorMes(String mes) {

			List<Horas> listaPorMes = new ArrayList<Horas>();
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction t = session.beginTransaction();
			Criteria crit = session.createCriteria(Horas.class);
			crit.add(Restrictions.like("data", "%" + mes + "%"));
			crit.addOrder(Order.asc("data"));
			listaPorMes = crit.list();
			t.commit();
			
			listaPorAno = verificaAno(listaPorMes);

			return listaPorAno;

		}
	

	public Double buscaSaldo(Integer mes) throws SQLException, ClassNotFoundException{
		
		this.connection = con.getConnection();
		Saldo saldo = new Saldo();
		String sql = "select * FROM saldo WHERE mes = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, mes);
		
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()){
		
			saldo.setSaldoParcial(rs.getDouble("saldoParcial"));
			
		}
		
		Double saldoParcial = saldo.getSaldoParcial();
		
		return saldoParcial;
		
	}
	
	public Horas buscaPorId(Integer id) throws SQLException, ClassNotFoundException{
		
		this.connection = con.getConnection();
		Horas hora = new Horas();
		String sql = "select * FROM horas WHERE idHoras = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, id);
		
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()){
			hora.setHorasEstim(rs.getDouble("horasEstim"));
			hora.setValorHora(rs.getDouble("valorHora"));
			hora.setHorasExec(rs.getDouble("horasExec"));
			hora.setObs(rs.getString("obs"));
		}
		
		return hora;
		
	}
	
	public void excluir(Horas horas){

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.delete(horas);
		t.commit();
	}
	
	public List<Horas> verificaAno(List<Horas> listaPorMes){
		
		List<Horas> listaPorAno = new ArrayList<Horas>();
		
		for(Horas h:listaPorMes){
			
			if(h.getData().contains("2014")){
				listaPorAno.add(h);
			}
			
		}
		
		
		return listaPorAno;
	}
}
