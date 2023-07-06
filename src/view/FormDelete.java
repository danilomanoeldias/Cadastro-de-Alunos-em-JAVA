package view;

import dao.DaoAluno;

public class FormDelete {

	public static void main(String[] args) {
		DaoAluno dao = new DaoAluno();
		dao.delete(1);

	}

}
