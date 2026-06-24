public class Paciente {
    public String nome;
    public String cpf;
    public int idade;
    public String telefone;
    // ASSOCIAÇÃO (R8)
    public Convenio convenio;
    public boolean ativo;

    public Paciente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.idade = 0;
        this.telefone = "";
        this.convenio = null;
        this.ativo = true;
    }

    public Paciente(String nome, String cpf, int idade, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.telefone = telefone;
        this.convenio = null;
        this.ativo = true;
    }

    // construtor com todos os dados
    public Paciente(String nome, String cpf, int idade, String telefone, Convenio convenio) {
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.telefone = telefone;
        this.convenio = convenio;
        this.ativo = true;
    }

    // atualiza so idade e telefone
    public void complementar(int idade, String telefone) {
        this.idade = idade;
        this.telefone = telefone;
    }

    // atualiza tudo incluindo convenio
    public void complementar(int idade, String telefone, Convenio convenio) {
        this.idade = idade;
        this.telefone = telefone;
        this.convenio = convenio;
    }

    public void desativar() {
        this.ativo = false;
    }

    public String exibirResumo() {
        String status = "Sim";
        if (!ativo) {
            status = "Nao";
        }
        return "Nome: " + nome + " | CPF: " + cpf + " | Idade: " + idade
                + " | Tel: " + telefone + " | Convenio: " + (convenio == null ? "" : convenio.getNome())
                + " | Ativo: " + status;
    }
}
