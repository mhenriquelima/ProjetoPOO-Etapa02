package br.com.clinicaVidaPlena.Exceptions;

public class ProfissionalNaoEncontradoException extends Exception {
    public ProfissionalNaoEncontradoException() {
        super();
    }
    public ProfissionalNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
public ProfissionalNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
    @Override    
    public String toString() {
        return "a seguinte excecao ocorreu: " + this.getClass().getName() + "\n"
             + "Mensagem: " + this.getMessage() + "\n";
    }
}