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
        // Componentes do formulário
        JTextField nomeField = new JTextField(area != null ? area.getNome() : "");
        JTextField capacidadeField = new JTextField(area != null ? String.valueOf(area.getCapacidadeMaxima()) : "");
        JTextField taxaField = new JTextField(area != null ? String.format("%.2f", area.getTaxaReserva()) : "");
        JTextArea descricaoArea = new JTextArea(area != null ? area.getDescricao() : "", 5, 20);
        JTextArea regrasArea = new JTextArea(area != null ? area.getRegrasUso() : "", 5, 20);

        // Painel do formulário
        JPanel painelFormulario = new JPanel(new GridLayout(0, 2, 5, 5));
        painelFormulario.add(new JLabel("Nome:"));
        painelFormulario.add(nomeField);
        painelFormulario.add(new JLabel("Capacidade Máxima:"));
        painelFormulario.add(capacidadeField);
        painelFormulario.add(new JLabel("Taxa de Reserva (R$):"));
        painelFormulario.add(taxaField);
        painelFormulario.add(new JLabel("Descrição:"));
        painelFormulario.add(new JScrollPane(descricaoArea));
        painelFormulario.add(new JLabel("Regras de Uso:"));
        painelFormulario.add(new JScrollPane(regrasArea));

        String titulo = (area == null) ? "Cadastrar Nova Área" : "Editar Área";
        int resultado = JOptionPane.showConfirmDialog(this, painelFormulario, titulo,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                String nome = nomeField.getText();
                int capacidade = Integer.parseInt(capacidadeField.getText());
                double taxa = Double.parseDouble(taxaField.getText().replace(",", "."));
                String descricao = descricaoArea.getText();
                String regras = regrasArea.getText();

                if (nome.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "O nome da área não pode ser vazio.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                AreaComum novaArea = new AreaComum(0, nome, capacidade, regras, taxa, true, descricao);

                boolean sucesso;
                if (area == null) { // Modo de Cadastro
                    sucesso = areaDAO.cadastrarArea(novaArea);
                } else { // Modo de Edição
                    novaArea.setId(area.getId());
                    sucesso = areaDAO.atualizarArea(novaArea);
                }

                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Área salva com sucesso!");
                    carregarAreas(); // Atualiza a tabela
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar a área.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, insira valores numéricos válidos para capacidade e taxa.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarAreaSelecionada() {
        int linhaSelecionada = tabelaAreas.getSelectedRow();
        if (linhaSelecionada >= 0) {
            int idArea = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
            // Usando o novo método do DAO para buscar o objeto completo
            AreaComum areaParaEditar = areaDAO.buscarAreaPorId(idArea);
            if (areaParaEditar != null) {
                abrirFormularioArea(areaParaEditar);
            } else {
                JOptionPane.showMessageDialog(this, "Área não encontrada no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma área na tabela para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
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