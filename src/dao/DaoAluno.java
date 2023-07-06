package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import modelo.Aluno;

public class DaoAluno {
	private Connection conn;
	private PreparedStatement st;
	private ResultSet rs;
	
	public DaoAluno() {
		conn = DB.getConnection();
		st = null;
		rs = null;
	}
	
	// Inclui um registro no banco de dados
	public void insert(Aluno aluno) {
		String sql = "insert into aluno (nome, email, idade) values (?, ?, ?)";
		
		try {
			st = conn.prepareStatement(sql);
			
			st.setString(1, aluno.getNome());
			st.setString(2, aluno.getEmail());
			st.setInt(3, aluno.getIdade());
			
			st.execute();
			System.out.println("Registro inserido com sucesso");
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Falha na inclusÃ£o do Registro");
		}finally {
			DB.closeStatement(st);
			//DB.closeConnection();
		}
	}
	/*
	// Retorna uma lista com todos os registros do banco de dados
	public List<Aluno>select(){
		String sql = "Select * from aluno";
		return executaSelect(sql);
	}
	
	// Retorna uma lista apartir de uma pesquisa por nome
	public List<Aluno>selectNome(String nome){
		String sql = "Select * from aluno where nome like '%" + nome + "%'";
		return executaSelect(sql);
	}
	*/
	
	public List<Aluno>selectIdade(String nome,String email,int idade){
        String sql = "Select * from aluno where nome like '%" + nome + "%' AND email like '%" + email + "' AND idade = " + idade;
        return executaSelect(sql);

 }
public List<Aluno>select() {
    String sql = "select * from aluno";
    return executaSelect(sql);
}
public List<Aluno>selectNome(String nome, String email) {
    String sql = "Select * from aluno where nome like '%" + nome + "%' AND email like '%" + email + "%'";
    return executaSelect(sql);
}
	
	//executa qualquer select (query)
	private List<Aluno> executaSelect(String sql){
		
		List<Aluno> listaAlunos = new ArrayList<>();
		
		try {
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			
			while(rs.next()) {
				Aluno aluno = new Aluno();
				aluno.setId(rs.getInt("id"));
				aluno.setNome(rs.getString("nome"));
				aluno.setEmail(rs.getString("email"));
				aluno.setIdade(rs.getInt("idade"));
				
				listaAlunos.add(aluno);
			}
		}catch (Exception e) {
			e.printStackTrace();
			listaAlunos = null;
		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			//DB.closeConnection();
		}
		
		return listaAlunos;
	}
	
	public void update(Aluno aluno) {
		String sql = "update aluno set nome = ?, email = ?, idade = ? where id = ?";

		try {

		st = conn.prepareStatement(sql);

		st.setString(1, aluno.getNome());
		st.setString(2, aluno.getEmail());
		st.setInt(3, aluno.getIdade());
		st.setInt(4, aluno.getId());

		st.execute();
		System.out.println("Registro alterado com sucesso");
		} catch (Exception e ) {
		e.printStackTrace();
		System.out.println("Falha na alteração do registro");
		} finally {
		DB.closeStatement(st);
		//DB.closeConnection();
		}
		}
	public void delete(int codigo) {
		String sql = "delete from aluno where id = ?";

		try {
		st = conn.prepareStatement(sql);

		st.setInt(1, codigo);

		st.execute();
		System.out.println("Registro excluido com sucesso");
		} catch (Exception e ) {
		e.printStackTrace();
		System.out.println("Falha na exclusão do registro");
		} finally {
		DB.closeStatement(st);
		//DB.closeConnection();
		}
		}
	
	
}
