package View;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DAO.AreaComumDAO;
import Model.AreaComum;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaGestaoAreas extends JFrame {
    private JTable tabelaAreas;
    private DefaultTableModel modeloTabela;
    private AreaComumDAO areaDAO;
    
    public TelaGestaoAreas() {
        areaDAO = new AreaComumDAO();
        initComponents();
        carregarAreas();
    }
    
    private void initComponents() {
        setTitle("Gestão de Áreas Comuns - Administrador");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Criar tabela
        String[] colunas = {"ID", "Nome", "Capacidade", "Taxa (R$)", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaAreas = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaAreas);
        
        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnNova = new JButton("Nova Área");
        JButton btnEditar = new JButton("Editar");
        JButton btnDesativar = new JButton("Desativar");
        JButton btnAtualizar = new JButton("Atualizar Lista");
        
        painelBotoes.add(btnNova);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnDesativar);
        painelBotoes.add(btnAtualizar);
        
        // Layout
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
        
        // Eventos dos botões
        btnNova.addActionListener(e -> abrirFormularioArea(null));
        btnEditar.addActionListener(e -> editarAreaSelecionada());
        btnDesativar.addActionListener(e -> desativarAreaSelecionada());
        btnAtualizar.addActionListener(e -> carregarAreas());
    }
    
    private void carregarAreas() {
        modeloTabela.setRowCount(0);
        List<AreaComum> areas = areaDAO.listarAreasAtivas();
        
        for (AreaComum area : areas) {
            Object[] linha = {
                area.getId(),
                area.getNome(),
                area.getCapacidadeMaxima(),
                String.format("%.2f", area.getTaxaReserva()),
                area.isAtiva() ? "Ativa" : "Inativa"
            };
            modeloTabela.addRow(linha);
        }
    }
    
    private void abrirFormularioArea(AreaComum area) {
        // Implementar formulário de cadastro/edição
    }
    
    private void editarAreaSelecionada() {
        int linhaSelecionada = tabelaAreas.getSelectedRow();
        if (linhaSelecionada >= 0) {
            int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
            // Buscar área e abrir formulário de edição
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma área para editar.");
        }
    }
    
    private void desativarAreaSelecionada() {
        int linhaSelecionada = tabelaAreas.getSelectedRow();
        if (linhaSelecionada >= 0) {
            int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
            int confirmacao = JOptionPane.showConfirmDialog(this, 
                "Deseja realmente desativar esta área?", 
                "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmacao == JOptionPane.YES_OPTION) {
                if (areaDAO.desativarArea(id)) {
                    JOptionPane.showMessageDialog(this, "Área desativada com sucesso!");
                    carregarAreas();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao desativar área.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma área para desativar.");
        }
    }
}