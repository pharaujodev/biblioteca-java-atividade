package biblioteca.dominio;
public class Livro {
    private Long id;
    private String titulo;
    private String isbn;
    private int quantidadeDisponivel;
    private String autor;

 public Livro(Long id, String titulo, String autor, String isbn, int quantidadeDisponivel) {
    this.id = id;
    this.titulo = titulo;
    this.autor = autor;
    this.isbn = isbn;
    this.quantidadeDisponivel = quantidadeDisponivel;
}

    public void realizarEmprestimo() {
        if (quantidadeDisponivel > 0) {
            quantidadeDisponivel--;
        } else {
            throw new IllegalStateException("Livro indisponível para empréstimo.");
        }
    }

    public void registrarDevolucao() {
        quantidadeDisponivel++;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }
}