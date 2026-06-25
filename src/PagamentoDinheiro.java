public class PagamentoDinheiro extends Pagamento {

    private static final double DESCONTO_FIXO = 0.05;

    public PagamentoDinheiro(int indiceConsulta, double valorBase) {
        this(indiceConsulta, valorBase, 0.0);
    }

    public PagamentoDinheiro(int indiceConsulta,
                             double valorBase,
                             double multa) {

        super(indiceConsulta, valorBase, "dinheiro", 1, multa);
        this.valorFinal = calcularValorFinal();
    }

    @Override
    public double calcularValorFinal() {
        double valorComDesconto =
                valorBase * (1.0 - DESCONTO_FIXO);

        return arredondar(valorComDesconto + multa);
    }
}