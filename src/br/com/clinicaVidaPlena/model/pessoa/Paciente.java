package br.com.clinicaVidaPlena.model.pessoa;

import br.com.clinicaVidaPlena.model.Convenio;

public class Paciente extends Pessoa {
    private int idade;
    // ASSOCIAÇÃO (R8)
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

    // construtor com todos os dados
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

    public void complementar(int idade, String telefone, Convenio convenioNome) {
        this.idade = idade;
        setTelefone(telefone);
        this.convenio = convenioNome;
    }

    public void desativar() {
        this.ativo = false;
    }

    public int getIdade() {
        return idade;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public boolean isAtivo() {
        return ativo;
    }

    @Override
    public String exibirResumo() {
        String status = "Sim";
        if (!ativo) {
            status = "Nao";
        }
        return "Nome: " + getNome() + " | CPF: " + getCpf() + " | Idade: " + idade
                + " | Tel: " + getTelefone() + " | Convenio: " + (convenio == null ? "" : convenio.getNome())
                + " | Ativo: " + status;
    }
}