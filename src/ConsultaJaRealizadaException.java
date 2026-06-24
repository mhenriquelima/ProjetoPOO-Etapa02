public class ConsultaJaRealizadaException extends Exception {
    public ConsultaJaRealizadaException() {
        super();
    }
    public ConsultaJaRealizadaException(String mensagem) {
        super(mensagem);
    }
    public String toString() {
        return "a seguinte excecao ocorreu: " + this.getClass().getName() + "\n"
             + "Mensagem: " + this.getMessage() + "\n";
    }
}