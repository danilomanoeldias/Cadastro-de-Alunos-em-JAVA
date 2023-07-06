package view;

import java.util.Scanner;

import dao.DaoAluno;
import modelo.Aluno;

public class FormInsert {

	public static void main(String[] args) {
		Scanner leitura = new Scanner(System.in);
		String nome, email;
		int idade;
		
		System.out.println("Informe seu nome");
		nome = leitura.nextLine();
		
		System.out.println("Informe seu e-mail");
		email = leitura.nextLine();
		
		System.out.println("Informe sua idade");
		idade = leitura.nextInt();
		
		Aluno aluno = new Aluno();
		aluno.setNome(nome);
		aluno.setEmail(email);
		aluno.setIdade(idade);
		
		DaoAluno dao = new DaoAluno();
		dao.insert(aluno);
	}

}
