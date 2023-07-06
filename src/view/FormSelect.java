package view;

import java.util.ArrayList;
import java.util.List;

import dao.DaoAluno;
import modelo.Aluno;

public class FormSelect {

	public static void main(String[] args) {
		DaoAluno dao = new DaoAluno();
		List<Aluno> listaAlunos = new ArrayList<>();
		
		listaAlunos = dao.select();
		//listaAlunos = dao.SelectNome("danilo");
		
		for (Aluno aluno : listaAlunos) {
			System.out.println("ID: " + aluno.getId());
			System.out.println("Nome: " + aluno.getNome());
			System.out.println("E-mail: " + aluno.getEmail());
			System.out.println("Idade: " + aluno.getIdade());
			System.out.println();
		}
	}

}
