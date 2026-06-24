public class ClinicoGeral extends Profissional {

    private int totalConsultas;

    public ClinicoGeral(String nome) {
        super(nome, "clinica geral");
        this.totalConsultas = 0;
    }

    public ClinicoGeral(String nome, String registro, double valor) {
        super(nome, "clinica geral", registro, valor);
        this.totalConsultas = 0;
    }

    public ClinicoGeral(String nome, String registro, double valor, String[] dias, int totalDias) {
        super(nome, "clinica geral", registro, valor, dias, totalDias);
        this.totalConsultas = 0;
    }

    public void registrarConsulta() {
        totalConsultas++;
    }

    @Override
    public void registrarEspecifico(Object atendimento) {
        System.out.println("Registro específico para clínica geral");
    }

    @Override
    public String exibirResumo() {
        return super.exibirResumo()
                + " | Consultas: " + totalConsultas;
    }
}