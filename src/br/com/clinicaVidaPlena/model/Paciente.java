package br.com.clinicaVidaPlena.model;

import br.com.clinicaVidaPlena.model.pessoa.Pessoa;

public class Paciente extends Pessoa {
    public int idade;
    public String telefone;
    // ASSOCIAÇÃO (R8)
    public Convenio convenio;
    public boolean ativo;

    public Paciente(String nome, String cpf) {
        super(nome, cpf, "Não Informado", "01/01/2000"); 
        this.idade = 0;
        this.telefone = "";
        this.convenio = null;
        this.ativo = true;
    }

    public Paciente(String nome, String cpf, int idade, String telefone) {
        super(nome, cpf, telefone, "01/01/2000");
        this.idade = idade;
        this.telefone = telefone;
        this.convenio = null;
        this.ativo = true;
    }

    // construtor com todos os dados
    public Paciente(String nome, String cpf, int idade, String telefone, Convenio convenio) {
        super(nome, cpf, telefone, "01/01/2000");
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.telefone = telefone;
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

    @Override
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