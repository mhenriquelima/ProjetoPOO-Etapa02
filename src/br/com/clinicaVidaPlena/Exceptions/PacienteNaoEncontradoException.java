package br.com.clinicaVidaPlena.Exceptions;

public class PacienteNaoEncontradoException extends Exception {
    public PacienteNaoEncontradoException() {
        super();
    }
    public PacienteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
public PacienteNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
    @Override
    public String toString() {
        return "a seguinte excecao ocorreu: " + this.getClass().getName() + "\n"
             + "Mensagem: " + this.getMessage() + "\n";
    }
}