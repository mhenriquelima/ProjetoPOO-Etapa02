package br.com.clinicaVidaPlena.model.pessoa;

import br.com.clinicaVidaPlena.model.Atendimento;

// classe fisioterapeuta herda da classe abstrata profissional
public class Fisioterapeuta extends Profissional {
    private int totalSessoesPrevistas;

    // SOBRECARGA: 3 construtores com o mesmo nome (Fisioterapeuta) - recebe
    // apenas o nome
    public Fisioterapeuta(String nome) {
        super(nome, "fisioterapia");
        this.totalSessoesPrevistas = 10;
    }
    
    // recebe nome, registro e valor
    public Fisioterapeuta(String nome, String registro, double valor) {
        super(nome, "fisioterapia", registro, valor);
        this.totalSessoesPrevistas = 10;
    }

    // recebe tudo
    public Fisioterapeuta(String nome, String registro, double valor, String[] dias, int totalDias) {
        super(nome, "fisioterapia", registro, valor, dias, totalDias);
        this.totalSessoesPrevistas = 10;
    }

    public void registrarSessao() {
        System.out.println("Sessão de fisioterapia registrada com sucesso!");
    }

    // SOBRESCRITA: implementa o metodo abstrato registrarEspecifico()
    // declarado em Profissional, adicionando ao atendimento a informacao
    // particular da fisioterapia (quantas sessoes ainda restam).
    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        totalSessoesPrevistas--;
        atendimento.adicionarProcedimento(
                "Sessao de fisioterapia - sessoes restantes: " + Math.max(0, totalSessoesPrevistas)
        );
    }

    // SOBRESCRITA: estende o exibirResumo() de Profissional, reaproveitando
    // (super.exibirResumo()) e completando com o dado especifico da classe.
    @Override
    public String exibirResumo() {
        String resumo = super.exibirResumo();
        resumo = resumo + " | Sessões previstas: " + totalSessoesPrevistas;
        return resumo;
    }
}