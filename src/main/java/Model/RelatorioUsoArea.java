package Model;

// Esta classe é um "POJO" (Plain Old Java Object).
// Ela não tem lógica complexa, apenas serve para guardar os dados agregados do nosso relatório.
public class RelatorioUsoArea {

    private String nomeArea;
    private int totalReservas;
    private double valorArrecadado;

    public RelatorioUsoArea(String nomeArea, int totalReservas, double valorArrecadado) {
        this.nomeArea = nomeArea;
        this.totalReservas = totalReservas;
        this.valorArrecadado = valorArrecadado;
    }

    // Apenas Getters, pois os dados vêm prontos do banco e não serão alterados.
    public String getNomeArea() {
        return nomeArea;
    }

    public int getTotalReservas() {
        return totalReservas;
    }

    public double getValorArrecadado() {
        return valorArrecadado;
    }
}