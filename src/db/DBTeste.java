package db;

import java.sql.Connection;

public class DBTeste {

	public static void main(String[] args) {
		Connection conn = DB.getConnection();
		
		if(conn != null) {
			System.out.println("Conex�o realizada com sucesso");
		}else {
			System.out.println("Falha na conex�o com o banco");
		}

	}

}
