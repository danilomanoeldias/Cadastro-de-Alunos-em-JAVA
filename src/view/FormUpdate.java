package view;

import dao.DaoAluno;
import modelo.Aluno;

public class FormUpdate {

	public static void main(String[] args) {
		Aluno aluno = new Aluno();
		aluno.setNome("Maria Rita");
		aluno.setEmail("rita@gmail.com");
		aluno.setIdade(32);
		aluno.setId(7);
		
		DaoAluno dao = new DaoAluno();
		dao.update(aluno);

	}

}
