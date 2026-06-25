package br.com.clinicaVidaPlena.model.pessoa;

// classe fisioterapeuta herda da classe abstrata profissional
public class Fisioterapeuta extends Profissional {
    private int totalSessoesPrevistas;

    // recebe apenas o nome
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

    // sobreescrita do metodo abstrato da classe pai
    @Override
    public void registrarEspecifico(Object atendimento) {
        System.out.println("Registro específico para fisioterapia"); 

    }

    //sobreescreve o metodo exibirResumo da classe pai
    @Override
    public String exibirResumo() {
        String resumo = super.exibirResumo();
        resumo = resumo + " | Sessões previstas: " + totalSessoesPrevistas;
        return resumo;
    }
}
