import java.util.Objects;

public class HorarioDisponivel {

    private String diaDaSemana;
    private String turno;

    public HorarioDisponivel(String diaDaSemana, String turno) {
        this.diaDaSemana = diaDaSemana;
        this.turno = turno;
    }

    public String getDiaDaSemana() {
        return diaDaSemana;
    }

    public String getTurno() {
        return turno;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof HorarioDisponivel)) return false;

        HorarioDisponivel h = (HorarioDisponivel) obj;

        return diaDaSemana.equals(h.diaDaSemana)
                && turno.equals(h.turno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diaDaSemana, turno);
    }
}