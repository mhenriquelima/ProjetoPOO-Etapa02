public class ValorInvalidoException extends Exception {
    public ValorInvalidoException() {
        super();
    }
    public ValorInvalidoException(String mensagem) {
        super(mensagem);
    }
    public String toString() {
        return "a seguinte excecao ocorreu: " + this.getClass().getName() + "\n"
             + "Mensagem: " + this.getMessage() + "\n";
    }
}