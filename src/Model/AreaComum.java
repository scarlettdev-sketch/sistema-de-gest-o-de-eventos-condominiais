package Model;
public class AreaComum {
    private int id;
    private String nome;
    private int capacidadeMaxima;
    private String regrasUso;
    private double taxaReserva;
    private boolean ativa;
    private String descricao;
    
    // Construtores
    public AreaComum(String nome, int capacidadeMaxima, String regrasUso, double taxaReserva) {
        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
        this.regrasUso = regrasUso;
        this.taxaReserva = taxaReserva;
        this.ativa = true;
    }
    
    // Construtor completo para uso do DAO
    public AreaComum(int id, String nome, int capacidadeMaxima, String regrasUso, 
                     double taxaReserva, boolean ativa, String descricao) {
        this.id = id;
        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
        this.regrasUso = regrasUso;
        this.taxaReserva = taxaReserva;
        this.ativa = ativa;
        this.descricao = descricao;
    }
    
    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getCapacidadeMaxima() { return capacidadeMaxima; }
    public String getRegrasUso() { return regrasUso; }
    public double getTaxaReserva() { return taxaReserva; }
    public boolean isAtiva() { return ativa; }
    public String getDescricao() { return descricao; }
    
    // Setters
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCapacidadeMaxima(int capacidadeMaxima) { this.capacidadeMaxima = capacidadeMaxima; }
    public void setRegrasUso(String regrasUso) { this.regrasUso = regrasUso; }
    public void setTaxaReserva(double taxaReserva) { this.taxaReserva = taxaReserva; }
    public void setAtiva(boolean ativa) { this.ativa = ativa; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}