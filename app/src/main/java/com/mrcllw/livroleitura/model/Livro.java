package com.mrcllw.livroleitura.model;

/**
 * Created by Marcello on 20/11/2017.
 */

public class Livro {
    private String id;
    private String nome;
    private String paginas;
    private String foto;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getPaginas() {
        return paginas;
    }
    public void setPaginas(String paginas) {
        this.paginas = paginas;
    }
    public String getFoto() {
        return foto;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }
}
