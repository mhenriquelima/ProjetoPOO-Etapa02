package br.com.clinicaVidaPlena.model.pagamento;

import br.com.clinicaVidaPlena.model.Exportavel;
import br.com.clinicaVidaPlena.Exceptions.ConvenioNaoCobreException;
import br.com.clinicaVidaPlena.model.Convenio;

public class PagamentoConvenio extends Pagamento implements Exportavel{

    private Convenio convenio;
    private String especialidade;

    public PagamentoConvenio(int indiceConsulta,
                             double valorBase,
                             Convenio convenio,
                             String especialidade)
            throws ConvenioNaoCobreException {

        this(
                indiceConsulta,
                valorBase,
                convenio,
                especialidade,
                0.0
        );
    }

    public PagamentoConvenio(int indiceConsulta,
                             double valorBase,
                             Convenio convenio,
                             String especialidade,
                             double multa)
            throws ConvenioNaoCobreException {

        super(
                indiceConsulta,
                valorBase,
                "convenio",
                1,
                multa
        );

        validarConvenio(convenio, especialidade);

        this.convenio = convenio;
        this.especialidade = especialidade;
        this.valorFinal = calcularValorFinal();
    }

    private void validarConvenio(Convenio convenio,
                                 String especialidade)
            throws ConvenioNaoCobreException {

        if (convenio == null) {
            throw new ConvenioNaoCobreException(
                    "O paciente nao possui convenio."
            );
        }

        if (!convenio.cobreEspecialidade(especialidade)) {
            throw new ConvenioNaoCobreException(
                    "O convenio "
                            + convenio.getNome()
                            + " nao cobre a especialidade "
                            + especialidade
                            + "."
            );
        }
    }

    @Override
    public double calcularValorFinal() {
        double percentual =
                convenio.getPercentualCobertura() / 100.0;

        double valorCoberto =
                valorBase * percentual;

        double valorPaciente =
                valorBase - valorCoberto + multa;

        return arredondar(valorPaciente);
    }

    @Override
    public String exibirResumo() {
        return super.exibirResumo()
                + " | Convenio: " + convenio.getNome()
                + " | Cobertura: "
                + convenio.getPercentualCobertura()
                + "%"
                + " | Especialidade: "
                + especialidade;
    }

    @Override
    public String exportarDados() {
        return exibirResumo();
    }
}