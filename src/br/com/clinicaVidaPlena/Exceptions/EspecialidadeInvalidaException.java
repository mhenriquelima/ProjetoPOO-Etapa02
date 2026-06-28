package br.com.clinicaVidaPlena.Exceptions;

public class EspecialidadeInvalidaException extends Exception {
    public EspecialidadeInvalidaException() {
        super();
    }
    public EspecialidadeInvalidaException(String mensagem) {
        super(mensagem);
    }
public EspecialidadeInvalidaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
    @Override
    public String toString() {
        return "a seguinte excecao ocorreu: " + this.getClass().getName() + "\n"
             + "Mensagem: " + this.getMessage() + "\n";
    }
}