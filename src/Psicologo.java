// classe psicologo herda da classe abstrata profissional
public class Psicologo extends Profissional {

    private String totalSessoes;

    // recebe apenas o nome
    public Psicologo(String nome) {
        super(nome, "psicologia");
        this.totalSessoes = "Não informada";
    }

    // recebe nome, registro e valor
    public Psicologo(String nome, String registro, double valor) {
        super(nome, "psicologia", registro, valor);
        this.totalSessoes = "Não informada";
    }

    // recebe tudo
    public Psicologo(String nome, String registro, double valor, String[] dias, int totalDias) {
        super(nome, "psicologia", registro, valor, dias, totalDias);
        this.totalSessoes = "Não informada";
    }

    public void definirTotalSessoes(String totalSessoes) {
        this.totalSessoes = totalSessoes;
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
        resumo = resumo + " | total de sessões: " + totalSessoes;
        return resumo;
    }
}