public class Convenio {
    private String nome;

    public Convenio(String nome) {
        this.nome = validarNome(nome);
    }

    private String validarNome(String nome) {
        if (nome == null) {
            return "";
        }
        return nome.trim();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = validarNome(nome);
    }
}
