package app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

import dao.DaoAluno;
import modelo.Aluno;

import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

public class Aplicacao extends JFrame {

	private JPanel contentPane;
	private JTextField textNome;
	private JTextField textIdade;
	private JTextField textEmail;
	private JTable tblResultado;

	private Aluno aluno;
	private DaoAluno dao;
	private List<Aluno> listaAlunos;
	private DefaultTableModel tableModel;

	//utilizado para saber se a operacao é insert ou update
	//idRef = 0  : insert
	//idRef != 0 : update
	private int idRef; 
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Aplicacao frame = new Aplicacao();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Aplicacao() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 629, 432);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNovo = new JButton("Novo");
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpaCampos();
				atualizarTabela();
			}
		});
		btnNovo.setBounds(25, 143, 89, 23);
		contentPane.add(btnNovo);
		
		JButton btnGravar = new JButton("Gravar");
		btnGravar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			gravar();
			limpaCampos();
			atualizarTabela();
			}
		});
		btnGravar.setBounds(134, 143, 89, 23);
		contentPane.add(btnGravar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpaCampos();
				atualizarTabela();
			}
		});
		btnCancelar.setBounds(244, 143, 89, 23);
		contentPane.add(btnCancelar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dao.delete(getIdRegistroSelecionado());
				atualizarTabela();
				mensagemdelete("Registro excluido com sucesso!");
			}
		});
		btnExcluir.setBounds(501, 143, 89, 23);
		contentPane.add(btnExcluir);
		
		textNome = new JTextField();
		textNome.setBounds(25, 98, 174, 20);
		contentPane.add(textNome);
		textNome.setColumns(10);
		
		textIdade = new JTextField();
		textIdade.setBounds(427, 98, 65, 20);
		contentPane.add(textIdade);
		textIdade.setColumns(10);
		
		textEmail = new JTextField();
		textEmail.setBounds(226, 98, 174, 20);
		contentPane.add(textEmail);
		textEmail.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(25, 73, 89, 14);
		contentPane.add(lblNome);
		
		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(226, 73, 89, 14);
		contentPane.add(lblEmail);
		
		JLabel lblIdade = new JLabel("Idade:");
		lblIdade.setBounds(427, 73, 65, 14);
		contentPane.add(lblIdade);
		
		JLabel lblTitulo = new JLabel("Sistema para Controle de Alunos");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 27));
		lblTitulo.setBounds(107, 11, 424, 33);
		contentPane.add(lblTitulo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 198, 567, 184);
		contentPane.add(scrollPane);
		
		tblResultado = new JTable();
		tblResultado.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				carregaInformacoes();
			}
		});
		tblResultado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tblResultado);
		tblResultado.setBackground(Color.LIGHT_GRAY);
		tblResultado.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nome", "E-mail", "Idade"
			}
		));
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisa(textNome.getText());
			}
		});
		btnPesquisar.setBounds(355, 143, 107, 23);
		contentPane.add(btnPesquisar);
		
		inicializarComponentes();	
	}
	
	private void inicializarComponentes() {
		aluno = new Aluno();
		dao = new DaoAluno();
		tableModel = new DefaultTableModel();
		
		idRef = 0;
		
		criaModeloTabela();
		atualizarTabela();
	} 
// define o modelo de dados da tabela
private void criaModeloTabela() {
	tableModel.addColumn("ID");
	tableModel.addColumn("Nome");
	tableModel.addColumn("E-Mail");
	tableModel.addColumn("Idade");
	
	tblResultado.setModel(tableModel);
}
// atualiza os dados da tabela
private void atualizarTabela() {
	listaAlunos = dao.select();
	montaTabela();
}

//atualiza o conteudo da tabela com base na pesquisa

private void pesquisa(String nome) {
	String idadetexto = textIdade.getText();
    String nometexto = textNome.getText();
    String emailtexto = textEmail.getText();

    if (!idadetexto.equals("")) {
        listaAlunos = dao.selectIdade(nometexto,emailtexto,Integer.parseInt(idadetexto));
    }else {
        listaAlunos = dao.selectNome(nometexto,emailtexto);
    }
    
	montaTabela();
}

// inclui as informações que estao na de objetos no tabelModel
private void montaTabela() {
	tableModel.setNumRows(0);
	
	Object[] coluna = new Object[4];
	
	for (Aluno alunoTMP : listaAlunos) {
	coluna[0] = alunoTMP.getId();
	coluna[1] = alunoTMP.getNome();
	coluna[2] = alunoTMP.getEmail();
	coluna[3] = alunoTMP.getIdade();
	
	tableModel.addRow(coluna);
	
	}
}

// grava e atualiza um registro no banco de dados 
private void gravar() {
	aluno.setNome(textNome.getText());
	aluno.setEmail(textEmail.getText());
	aluno.setIdade(Integer.parseInt(textIdade.getText()));
	
	if(idRef == 0) {
		dao.insert(aluno); 
		mensagem("Registro inserido com sucesso!");
	}else {
		aluno.setId(idRef);
		dao.update(aluno);
		mensagem("Registro atualizado com sucesso!");
	}	;
}

// limpa os campos apos a operacao gravar 
private void limpaCampos() {
	textNome.setText("");
	textEmail.setText("");
	textIdade.setText("");
	
	idRef = 0;
	tblResultado.clearSelection();
}

// obtem o id do registro do registro selecionado na tabela
@SuppressWarnings("finally")
private int getIdRegistroSelecionado() {
	int linha = 0;
	int id = 0;
	try {
		linha = tblResultado.getSelectedRow();
		id = (int) tblResultado.getValueAt(linha, 0);
	}catch (Exception e) {
		System.out.println("Selecione uma linha para excluir o Registro!");
		id = 0;
	}finally {
		return id;
	}
	
}

//carrega nos campos de texto as informacoes da linha selecionada da tabela
private void carregaInformacoes() {
    String nome;
    String email;
    String idade;
    
	int linha = tblResultado.getSelectedRow();
	
	nome = tblResultado.getValueAt(linha, 1).toString();
	email = tblResultado.getValueAt(linha, 2).toString();
	idade = tblResultado.getValueAt(linha, 3).toString();
	
	idRef = (int) tblResultado.getValueAt(linha, 0);
	
	textNome.setText(nome);
	textEmail.setText(email);
	textIdade.setText(idade);
}

public void mensagem(String x) {
    JOptionPane.showMessageDialog(null, x ,"caixa de mensagem", JOptionPane.INFORMATION_MESSAGE);

 }
 public void mensagemdelete(String y) {
     JOptionPane.showMessageDialog(null, y ,"deletado",JOptionPane.ERROR_MESSAGE );

 }


}



