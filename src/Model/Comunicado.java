package Model;

import java.time.LocalDateTime;

public class Comunicado {

    private int id;
    private String titulo;
    private String conteudo;
    private LocalDateTime dataPublicacao;
    private int usuarioId; // FK que referencia o usuário que publicou

    public Comunicado(int id, String titulo, String conteudo, LocalDateTime dataPublicacao, int usuarioId) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.dataPublicacao = dataPublicacao;
        this.usuarioId = usuarioId;
    }

    public Comunicado(String titulo, String conteudo, LocalDateTime dataPublicacao, int usuarioId) {
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.dataPublicacao = dataPublicacao;
        this.usuarioId = usuarioId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public LocalDateTime getDataPublicacao() {
        return dataPublicacao;
    }

    public int getUsuarioId() {
        return usuarioId;
    }
}