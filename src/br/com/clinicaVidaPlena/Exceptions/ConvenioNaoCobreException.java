package br.com.clinicaVidaPlena.Exceptions;

public class ConvenioNaoCobreException extends Exception {

    public ConvenioNaoCobreException() {
        super();
    }

    public ConvenioNaoCobreException(String mensagem) {
        super(mensagem);
    }
public ConvenioNaoCobreException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
    @Override
    public String toString() {
        return "A seguinte excecao ocorreu: "
                + getClass().getName()
                + "\nMensagem: "
                + getMessage()
                + "\n";
    }
}