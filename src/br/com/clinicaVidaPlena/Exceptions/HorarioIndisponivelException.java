package br.com.clinicaVidaPlena.Exceptions;

public class HorarioIndisponivelException extends Exception {
    public HorarioIndisponivelException() {
        super();
    }

    public HorarioIndisponivelException(String mensagem) {
        super(mensagem);
    }
public HorarioIndisponivelException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
    @Override
    public String toString() {
        return "a seguinte excecao ocorreu: " + this.getClass().getName() + "\n"
             + "Mensagem: " + this.getMessage() + "\n";
    }
}
