package br.com.clinicaVidaPlena.model.pagamento;
import br.com.clinicaVidaPlena.model.Exportavel;
public abstract class Pagamento implements Exportavel {

    private int indiceConsulta;
    protected double valorBase;
    protected double valorFinal;
    private String tipoPagamento;
    protected int parcelas;
    protected double multa;

    protected Pagamento(int indiceConsulta,
                        double valorBase,
                        String tipoPagamento) {

        this(indiceConsulta, valorBase, tipoPagamento, 1, 0.0);
    }

    protected Pagamento(int indiceConsulta,
                        double valorBase,
                        String tipoPagamento,
                        int parcelas) {

        this(indiceConsulta, valorBase, tipoPagamento, parcelas, 0.0);
    }

    protected Pagamento(int indiceConsulta,
                        double valorBase,
                        String tipoPagamento,
                        int parcelas,
                        double multa) {

        this.indiceConsulta = indiceConsulta;
        this.valorBase = valorBase;
        this.valorFinal = valorBase;
        this.tipoPagamento = tipoPagamento;
        this.parcelas = parcelas;
        this.multa = multa;
    }

    public abstract double calcularValorFinal();

    protected double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    public double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public int getIndiceConsulta() {
        return indiceConsulta;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public int getParcelas() {
        return parcelas;
    }

    @Override
    public String exportarDados() {
        return indiceConsulta + ";"
                + tipoPagamento + ";"
                + arredondar(valorFinal) + ";"
                + parcelas;
    }

    public String exibirResumo() {
        String resumo = "Consulta #" + indiceConsulta
                + " | Valor: R$" + arredondar(valorFinal)
                + " | Tipo: " + tipoPagamento
                + " | Parcelas: " + parcelas;

        if (parcelas > 1) {
            resumo += " (R$"
                    + arredondar(valorFinal / parcelas)
                    + " cada)";
        }

        return resumo;
    }
}