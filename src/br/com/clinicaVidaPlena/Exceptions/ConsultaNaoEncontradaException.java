public class ConsultaNaoEncontradaException extends Exception {
    public ConsultaNaoEncontradaException() {
        super();
    }
    public ConsultaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
    public String toString() {
        return "a seguinte excecao ocorreu: " + this.getClass().getName() + "\n"
             + "Mensagem: " + this.getMessage() + "\n";
    }
}