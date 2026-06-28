package br.com.clinicaVidaPlena.model.pagamento;
import br.com.clinicaVidaPlena.model.Exportavel;
import br.com.clinicaVidaPlena.Exceptions.PagamentoInvalidoException;


public class PagamentoCartao extends Pagamento implements Exportavel{

    private static final int MINIMO_PARCELAS = 1;
    private static final int MAXIMO_PARCELAS = 6;

    private static final double TAXA_POR_PARCELA_EXTRA = 0.025;

    public PagamentoCartao(int indiceConsulta,
                           double valorBase,
                           int parcelas)
            throws PagamentoInvalidoException {

        this(indiceConsulta, valorBase, parcelas, 0.0);
    }

    public PagamentoCartao(int indiceConsulta,
                           double valorBase,
                           int parcelas,
                           double multa)
            throws PagamentoInvalidoException {

        super(
                indiceConsulta,
                valorBase,
                "cartao",
                parcelas,
                multa
        );

        validarParcelas(parcelas);
        this.valorFinal = calcularValorFinal();
    }

    private void validarParcelas(int parcelas)
            throws PagamentoInvalidoException {

        if (parcelas < MINIMO_PARCELAS
                || parcelas > MAXIMO_PARCELAS) {

            throw new PagamentoInvalidoException(
                    "Quantidade de parcelas invalida. "
                            + "Informe um valor entre 1 e 6."
            );
        }
    }

    @Override
    public double calcularValorFinal() {
        int parcelasAcimaDeTres =
                Math.max(0, parcelas - 3);

        double percentualTaxa =
                parcelasAcimaDeTres
                        * TAXA_POR_PARCELA_EXTRA;

        double valorComTaxa =
                valorBase * (1.0 + percentualTaxa);

        return arredondar(valorComTaxa + multa);
    }
    @Override
    public String exportarDados() {
        return exibirResumo();
    }
}