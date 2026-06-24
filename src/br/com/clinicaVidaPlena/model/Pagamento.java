package br.com.clinicaVidaPlena.model;

public abstract class Pagamento implements Exportavel {

    public int indiceConsulta;
    public double valorFinal;
    public String tipoPagamento;
    public int parcelas;

    protected Pagamento(int indiceConsulta, double valorFinal, String tipoPagamento) {
        this.indiceConsulta = indiceConsulta;
        this.valorFinal = valorFinal;
        this.tipoPagamento = tipoPagamento;
        this.parcelas = 1;
    }

    protected Pagamento(int indiceConsulta, double valorFinal,
                        String tipoPagamento, int parcelas) {
        this.indiceConsulta = indiceConsulta;
        this.valorFinal = valorFinal;
        this.tipoPagamento = tipoPagamento;
        this.parcelas = parcelas;
    }

    public abstract double calcularValorFinal();

    public String exibirResumo() {
        double valorArredondado =
                Math.round(valorFinal * 100.0) / 100.0;

        String resumo = "Consulta #" + indiceConsulta
                + " | Valor: R$" + valorArredondado
                + " | Tipo: " + tipoPagamento
                + " | Parcelas: " + parcelas;

        if (parcelas > 1) {
            double valorParcela =
                    Math.round((valorFinal / parcelas) * 100.0) / 100.0;

            resumo += " (R$" + valorParcela + " cada)";
        }

        return resumo;
    }
}