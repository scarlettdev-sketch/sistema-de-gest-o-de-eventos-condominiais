package View;

import DAO.ComunicadoDAO;
import Model.Comunicado;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VerComunicadosGUI extends JFrame {

    private JTable tabelaComunicados;
    private DefaultTableModel tableModel;
    private JButton btnAtualizar;

    public VerComunicadosGUI(){
        setTitle("Visualizar Comunicados");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        //Criação da Tabela
        String[] colunas = {"ID", "Título", "Conteúdo", "Data de Publicação"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabelaComunicados = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaComunicados);

        // Adiciona a tabela ao painel central
        add(scrollPane, BorderLayout.CENTER);

        //Painel de Botões
        JLabel painelBotoes = new JLabel();
        btnAtualizar = new JButton("Atualizar Lista");
        painelBotoes.add(btnAtualizar);

        add(painelBotoes, BorderLayout.SOUTH);

        //Adiciona a ação ao botão de atualizar
        btnAtualizar.addActionListener(e -> carregarComunicados());

        //Carrega os comunicados ao iniciar a tela
        carregarComunicados();
    }

    private void carregarComunicados(){
        tableModel.setRowCount(0); //Limpa a tabela antes de carregar
        ComunicadoDAO comunicadoDAO = new ComunicadoDAO();
        List<Comunicado> comunicados = comunicadoDAO.listarTodosComunicados();

        for (Comunicado comunicado : comunicados){
            Object[] linha = {
                    comunicado.getId(),
                    comunicado.getTitulo(),
                    comunicado.getConteudo(),
                    comunicado.getDataPublicacao()
            };
            tableModel.addRow(linha);
        }
    }
}
