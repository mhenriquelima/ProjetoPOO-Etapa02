public class Paciente extends Pessoa {
    private int idade;
    private Convenio convenio;
    private boolean ativo;

    public Paciente(String nome, String cpf) {
        super(nome, cpf, "Não Informado", "01/01/2000");
        this.idade = 0;
        this.convenio = null;
        this.ativo = true;
    }

    public Paciente(String nome, String cpf, int idade, String telefone) {
        super(nome, cpf, telefone, "01/01/2000");
        this.idade = idade;
        this.convenio = null;
        this.ativo = true;
    }

    public Paciente(String nome, String cpf, int idade, String telefone, Convenio convenio) {
        super(nome, cpf, telefone, "01/01/2000");
        this.idade = idade;
        this.convenio = convenio;
        this.ativo = true;
    }

    public void complementar(int idade, String telefone) {
        this.idade = idade;
        setTelefone(telefone);
    }

    public void complementar(int idade, String telefone, Convenio convenio) {
        this.idade = idade;
        setTelefone(telefone);
        this.convenio = convenio;
    }

    public void desativar() {
        this.ativo = false;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getConvenio() {
        return convenio == null ? "" : convenio.getNome();
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public boolean hasConvenio() {
        return convenio != null && !convenio.getNome().trim().isEmpty();
    }

    public boolean isAtivo() {
        return ativo;
    }

    @Override
    public String exibirResumo() {
        String status = ativo ? "Sim" : "Nao";
        return "Nome: " + getNome() + " | CPF: " + getCpf() + " | Idade: " + idade
                + " | Tel: " + getTelefone() + " | Convenio: " + getConvenio()
                + " | Ativo: " + status;
    }
}
