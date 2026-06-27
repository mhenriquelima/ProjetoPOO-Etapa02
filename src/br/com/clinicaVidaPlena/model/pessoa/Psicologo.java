package br.com.clinicaVidaPlena.model.pessoa;

// classe psicologo herda da classe abstrata profissional
public class Psicologo extends Profissional {
    private String abordagem;

    // recebe apenas o nome
    public Psicologo(String nome) {
        super(nome, "psicologia");
        this.abordagem = "Não informada";
    }

    // recebe nome, registro e valor
    public Psicologo(String nome, String registro, double valor) {
        super(nome, "psicologia", registro, valor);
        this.abordagem = "Não informada";
    }

    // recebe tudo
    public Psicologo(String nome, String registro, double valor, String[] dias, int totalDias) {
        super(nome, "psicologia", registro, valor, dias, totalDias);
        this.abordagem = "Não informada";
    }

    public void definirAbordagem(String abordagem) {
        this.abordagem = abordagem;
    }

    // sobrescrita do metodo abstrato da classe pai
    @Override
    public void registrarEspecifico(Object atendimento) {
        System.out.println("registro específico para psicologia");
    }

    // sobrescreve o metodo exibirResumo da classe pai
    @Override
    public String exibirResumo() {
        String resumo = super.exibirResumo();
        resumo = resumo + " | abordagem: " + abordagem;
        return resumo;
    }
}