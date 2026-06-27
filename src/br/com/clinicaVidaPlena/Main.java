package br.com.clinicaVidaPlena;
import br.com.clinicaVidaPlena.Exceptions.ConvenioNaoCobreException;
import br.com.clinicaVidaPlena.Exceptions.EspecialidadeInvalidaException;
import br.com.clinicaVidaPlena.Exceptions.HorarioIndisponivelException;
import br.com.clinicaVidaPlena.Exceptions.PacienteInativoException;
import br.com.clinicaVidaPlena.Exceptions.PacienteNaoEncontradoException;
import br.com.clinicaVidaPlena.Exceptions.PagamentoInvalidoException;
import br.com.clinicaVidaPlena.Exceptions.ProfissionalNaoEncontradoException;
import br.com.clinicaVidaPlena.Exceptions.ValorInvalidoException;
import br.com.clinicaVidaPlena.model.Agendavel;
import br.com.clinicaVidaPlena.model.Atendimento;
import br.com.clinicaVidaPlena.model.Consulta;
import br.com.clinicaVidaPlena.model.Convenio;
import br.com.clinicaVidaPlena.model.Paciente;
import br.com.clinicaVidaPlena.model.Relatorio;
import br.com.clinicaVidaPlena.model.pagamento.Pagamento;
import br.com.clinicaVidaPlena.model.pagamento.PagamentoCartao;
import br.com.clinicaVidaPlena.model.pagamento.PagamentoConvenio;
import br.com.clinicaVidaPlena.model.pagamento.PagamentoDinheiro;
import br.com.clinicaVidaPlena.model.pessoa.ClinicoGeral;
import br.com.clinicaVidaPlena.model.pessoa.Fisioterapeuta;
import br.com.clinicaVidaPlena.model.pessoa.Nutricionista;
import br.com.clinicaVidaPlena.model.pessoa.Pessoa;
import br.com.clinicaVidaPlena.model.pessoa.Profissional;
import br.com.clinicaVidaPlena.model.pessoa.Psicologo;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Paciente[] pacientes = new Paciente[100];
    static int totalPacientes = 0;

    static Profissional[] profissionais = new Profissional[50];
    static int totalProfissionais = 0;

    static Consulta[] consultas = new Consulta[200];
    static int totalConsultas = 0;

    static Atendimento[] atendimentos = new Atendimento[200];
    static int totalAtendimentos = 0;

    // Lista polimorfica: armazena qualquer subclasse de Pagamento.
    static List<Pagamento> pagamentos = new ArrayList<>();

    static double[] multas = new double[100];
    static int totalMultas = 0;

    static List<Pessoa> pessoas = new ArrayList<Pessoa>();

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== CLINICA VIDAPLENA ===");
            System.out.println("1 - Pacientes");
            System.out.println("2 - Profissionais");
            System.out.println("3 - Consultas");
            System.out.println("4 - Atendimentos");
            System.out.println("5 - Pagamentos");
            System.out.println("6 - Relatorios");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1:
                    menuPacientes();
                    break;
                case 2:
                    menuProfissionais();
                    break;
                case 3:
                    menuConsultas();
                    break;
                case 4:
                    menuAtendimentos();
                    break;
                case 5:
                    menuPagamentos();
                    break;
                case 6:
                    menuRelatorios();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }

        sc.close();
        System.out.println("Sistema encerrado.");
    }

    // -------------------------------------------------------------------------
    // PACIENTES
    // -------------------------------------------------------------------------

    public static void menuPacientes() {
        int op = -1;

        while (op != 0) {
            System.out.println("\n--- PACIENTES ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Complementar cadastro");
            System.out.println("3 - Buscar por CPF");
            System.out.println("4 - Listar todos");
            System.out.println("5 - Desativar");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");

            op = Integer.parseInt(sc.nextLine());

            switch (op) {
                case 1:
                    cadastrarPaciente();
                    break;
                case 2:
                    complementarPaciente();
                    break;
                case 3:
                    buscarPaciente();
                    break;
                case 4:
                    listarPacientes();
                    break;
                case 5:
                    desativarPaciente();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }
    }

    public static void cadastrarPaciente() {
        if (totalPacientes >= pacientes.length) {
            System.out.println("Limite de pacientes atingido.");
            return;
        }

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        if (buscarIndicePaciente(cpf) != -1) {
            System.out.println("CPF ja cadastrado!");
            return;
        }

        System.out.print("Tipo (1-Minimo / 2-Com idade e tel / 3-Completo): ");
        int tipo = Integer.parseInt(sc.nextLine());

        if (tipo == 1) {
            pacientes[totalPacientes] = new Paciente(nome, cpf);

        } else if (tipo == 2) {
            System.out.print("Idade: ");
            int idade = Integer.parseInt(sc.nextLine());

            System.out.print("Telefone: ");
            String telefone = sc.nextLine();

            pacientes[totalPacientes] = new Paciente(
                    nome,
                    cpf,
                    idade,
                    telefone
            );

        } else if (tipo == 3) {
            System.out.print("Idade: ");
            int idade = Integer.parseInt(sc.nextLine());

            System.out.print("Telefone: ");
            String telefone = sc.nextLine();

            Convenio convenio = cadastrarConvenioDoPaciente();

            pacientes[totalPacientes] = new Paciente(
                    nome,
                    cpf,
                    idade,
                    telefone,
                    convenio
            );

        } else {
            System.out.println("Tipo de cadastro invalido.");
            return;
        }
        pessoas.add(pacientes[totalPacientes]);
        totalPacientes++;
        System.out.println("Paciente cadastrado com sucesso!");
    }

    public static void complementarPaciente() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        int idx = buscarIndicePaciente(cpf);

        if (idx == -1) {
            System.out.println("Paciente nao encontrado.");
            return;
        }

        System.out.print("Vai informar convenio? (1-Nao / 2-Sim): ");
        int tipo = Integer.parseInt(sc.nextLine());

        System.out.print("Idade: ");
        int idade = Integer.parseInt(sc.nextLine());

        System.out.print("Telefone: ");
        String telefone = sc.nextLine();

        if (tipo == 1) {
            pacientes[idx].complementar(idade, telefone);

        } else if (tipo == 2) {
            Convenio convenio = cadastrarConvenioDoPaciente();
            pacientes[idx].complementar(idade, telefone, convenio);

        } else {
            System.out.println("Opcao invalida.");
            return;
        }

        System.out.println("Cadastro atualizado!");
    }

    /**
     * Cria um convenio usando os percentuais definidos no enunciado.
     * Como o enunciado nao define exatamente quais especialidades cada plano
     * cobre, elas sao informadas durante o cadastro.
     */
    public static Convenio cadastrarConvenioDoPaciente() {
        System.out.println("\nConvenios disponiveis:");
        System.out.println("1 - SaudePlus (40%)");
        System.out.println("2 - VidaMais (30%)");
        System.out.println("3 - BemEstar (50%)");
        System.out.println("0 - Sem convenio");
        System.out.print("Escolha: ");

        int opcao = Integer.parseInt(sc.nextLine());

        String nome;
        double percentual;

        switch (opcao) {
            case 1:
                nome = "SaudePlus";
                percentual = 40.0;
                break;
            case 2:
                nome = "VidaMais";
                percentual = 30.0;
                break;
            case 3:
                nome = "BemEstar";
                percentual = 50.0;
                break;
            case 0:
                return null;
            default:
                System.out.println("Convenio invalido. Paciente ficara sem convenio.");
                return null;
        }

        Convenio convenio = new Convenio(nome, percentual);

        System.out.print("Quantas especialidades esse convenio cobre? ");
        int quantidade = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < quantidade; i++) {
            System.out.print("Especialidade " + (i + 1) + ": ");
            String especialidade = sc.nextLine().trim().toLowerCase();
            convenio.adicionarEspecialidade(especialidade);
        }

        return convenio;
    }

    public static void buscarPaciente() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        int idx = buscarIndicePaciente(cpf);

        if (idx == -1) {
            System.out.println("Paciente nao encontrado.");
        } else {
            System.out.println(pacientes[idx].exibirResumo());
        }
    }

    public static void listarPacientes() {
        if (totalPacientes == 0) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }

        for (int i = 0; i < totalPacientes; i++) {
            System.out.println(pacientes[i].exibirResumo());
        }
    }

    public static void desativarPaciente() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        int idx = buscarIndicePaciente(cpf);

        if (idx == -1) {
            System.out.println("Paciente nao encontrado.");
        } else {
            pacientes[idx].desativar();
            System.out.println("Paciente desativado.");
        }
    }

    public static int buscarIndicePaciente(String cpf) {
        for (int i = 0; i < totalPacientes; i++) {
            if (pacientes[i].getCpf().equals(cpf)) {
                return i;
            }
        }

        return -1;
    }

    // -------------------------------------------------------------------------
    // PROFISSIONAIS
    // -------------------------------------------------------------------------

    public static void menuProfissionais() {
        int op = -1;

        while (op != 0) {
            System.out.println("\n--- PROFISSIONAIS ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Atualizar cadastro");
            System.out.println("3 - Listar todos");
            System.out.println("4 - Filtrar por especialidade");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");

            op = Integer.parseInt(sc.nextLine());

            switch (op) {
                case 1:
                    cadastrarProfissional();
                    break;
                case 2:
                    atualizarProfissional();
                    break;
                case 3:
                    listarProfissionais();
                    break;
                case 4:
                    filtrarProfissionais();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }
    }

    public static Profissional criarProfissionalPorEspecialidade(
            String nome,
            String especialidade) {

        if (especialidade.equals("fisioterapia")) {
            return new Fisioterapeuta(nome);
        }

        if (especialidade.equals("psicologia")) {
            return new Psicologo(nome);
        }

        if (especialidade.equals("nutricao")) {
            return new Nutricionista(nome);
        }

        if (especialidade.equals("clinica geral")) {
            return new ClinicoGeral(nome);
        }

        return null;
    }

    public static Profissional criarProfissionalPorEspecialidade(
            String nome,
            String especialidade,
            String registro,
            double valor) {

        if (especialidade.equals("fisioterapia")) {
            return new Fisioterapeuta(nome, registro, valor);
        }

        if (especialidade.equals("psicologia")) {
            return new Psicologo(nome, registro, valor);
        }

        if (especialidade.equals("nutricao")) {
            return new Nutricionista(nome, registro, valor);
        }

        if (especialidade.equals("clinica geral")) {
            return new ClinicoGeral(nome, registro, valor);
        }

        return null;
    }

    public static Profissional criarProfissionalPorEspecialidade(
            String nome,
            String especialidade,
            String registro,
            double valor,
            String[] dias,
            int totalDias) {

        if (especialidade.equals("fisioterapia")) {
            return new Fisioterapeuta(nome, registro, valor, dias, totalDias);
        }

        if (especialidade.equals("psicologia")) {
            return new Psicologo(nome, registro, valor, dias, totalDias);
        }

        if (especialidade.equals("nutricao")) {
            return new Nutricionista(nome, registro, valor, dias, totalDias);
        }

        if (especialidade.equals("clinica geral")) {
            return new ClinicoGeral(nome, registro, valor, dias, totalDias);
        }

        return null;
    }

    public static void cadastrarProfissional() {
        if (totalProfissionais >= profissionais.length) {
            System.out.println("Limite de profissionais atingido.");
            return;
        }

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print(
                "Especialidade (clinica geral/fisioterapia/psicologia/nutricao): "
        );
        String especialidade = sc.nextLine().trim().toLowerCase();

        if (!Profissional.especialidadeValida(especialidade)) {
            System.out.println("Especialidade invalida!");
            return;
        }

        System.out.print("Tipo (1-Minimo / 2-Com registro e valor / 3-Completo): ");
        int tipo = Integer.parseInt(sc.nextLine());

        Profissional profissional;

        if (tipo == 1) {
            profissional = criarProfissionalPorEspecialidade(
                    nome,
                    especialidade
            );

        } else if (tipo == 2) {
            System.out.print("Registro: ");
            String registro = sc.nextLine();

            System.out.print("Valor consulta: ");
            double valor = Double.parseDouble(sc.nextLine());

            profissional = criarProfissionalPorEspecialidade(
                    nome,
                    especialidade,
                    registro,
                    valor
            );

        } else if (tipo == 3) {
            System.out.print("Registro: ");
            String registro = sc.nextLine();

            System.out.print("Valor consulta: ");
            double valor = Double.parseDouble(sc.nextLine());

            System.out.print("Quantos dias atende? ");
            int quantidade = Integer.parseInt(sc.nextLine());

            if (quantidade < 0) {
                quantidade = 0;
            }

            if (quantidade > 7) {
                quantidade = 7;
            }

            String[] dias = new String[7];

            for (int i = 0; i < quantidade; i++) {
                System.out.print("Dia " + (i + 1) + ": ");
                dias[i] = sc.nextLine().trim().toLowerCase();
            }

            profissional = criarProfissionalPorEspecialidade(
                    nome,
                    especialidade,
                    registro,
                    valor,
                    dias,
                    quantidade
            );

        } else {
            System.out.println("Tipo de cadastro invalido.");
            return;
        }

        if (profissional == null) {
            System.out.println("Especialidade ainda nao implementada.");
            return;
        }

        profissionais[totalProfissionais] = profissional;
        pessoas.add(profissionais[totalProfissionais]);
        totalProfissionais++;

        System.out.println("Profissional cadastrado!");
    }

    public static void atualizarProfissional() {
        System.out.print("Nome do profissional: ");
        String nome = sc.nextLine();

        int idx = buscarIndiceProfissional(nome);

        if (idx == -1) {
            System.out.println("Profissional nao encontrado.");
            return;
        }

        System.out.print("Vai informar dias? (1-Nao / 2-Sim): ");
        int tipo = Integer.parseInt(sc.nextLine());

        System.out.print("Registro: ");
        String registro = sc.nextLine();

        System.out.print("Valor consulta: ");
        double valor = Double.parseDouble(sc.nextLine());

        if (tipo == 1) {
            profissionais[idx].atualizar(registro, valor);

        } else if (tipo == 2) {
            System.out.print("Quantos dias? ");
            int quantidade = Integer.parseInt(sc.nextLine());

            if (quantidade < 0) {
                quantidade = 0;
            }

            if (quantidade > 7) {
                quantidade = 7;
            }

            String[] dias = new String[7];

            for (int i = 0; i < quantidade; i++) {
                System.out.print("Dia " + (i + 1) + ": ");
                dias[i] = sc.nextLine().trim().toLowerCase();
            }

            profissionais[idx].atualizar(
                    registro,
                    valor,
                    dias,
                    quantidade
            );

        } else {
            System.out.println("Opcao invalida.");
            return;
        }

        System.out.println("Profissional atualizado!");
    }

    public static void listarProfissionais() {
        if (totalProfissionais == 0) {
            System.out.println("Nenhum profissional cadastrado.");
            return;
        }

        for (int i = 0; i < totalProfissionais; i++) {
            System.out.println(profissionais[i].exibirResumo());
        }
    }

    public static void filtrarProfissionais() {
        System.out.print("Especialidade: ");
        String especialidade = sc.nextLine().trim().toLowerCase();

        boolean encontrou = false;

        for (int i = 0; i < totalProfissionais; i++) {
            if (profissionais[i].especialidade.equals(especialidade)) {
                System.out.println(profissionais[i].exibirResumo());
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum profissional com essa especialidade.");
        }
    }

    public static int buscarIndiceProfissional(String nome) {
        for (int i = 0; i < totalProfissionais; i++) {
            if (profissionais[i].getNome().equals(nome)) {
                return i;
            }
        }

        return -1;
    }

    // -------------------------------------------------------------------------
    // CONSULTAS
    // -------------------------------------------------------------------------

    public static void menuConsultas() {
        int op = -1;

        while (op != 0) {
            System.out.println("\n--- CONSULTAS ---");
            System.out.println("1 - Agendar (escolher profissional)");
            System.out.println("2 - Agendar (busca por especialidade)");
            System.out.println("3 - Cancelar");
            System.out.println("4 - Remarcar");
            System.out.println("5 - Listar todas");
            System.out.println("6 - Buscar por CPF");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");

            op = Integer.parseInt(sc.nextLine());

            switch (op) {
                case 1:
                    agendarComProfissional();
                    break;
                case 2:
                    agendarPorEspecialidade();
                    break;
                case 3:
                    cancelarConsulta();
                    break;
                case 4:
                    remarcarConsulta();
                    break;
                case 5:
                    listarConsultas();
                    break;
                case 6:
                    buscarConsultasPorPaciente();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }
    }

    public static void agendarComProfissional() {
        try {
            System.out.print("CPF do paciente: ");
            String cpf = sc.nextLine();
            validarPacienteAtivo(cpf);

            System.out.print("Nome do profissional: ");
            String nomeProf = sc.nextLine();
            int idxProf = validarProfissionalParaAgendamento(nomeProf);

            System.out.print("Data (DD/MM/AAAA): ");
            String data = sc.nextLine();
            System.out.print("Horario (HH:MM): ");
            String horario = sc.nextLine();

            validarDisponibilidadeProfissional(idxProf, data, horario);

            if (temConflito(nomeProf, data, horario)) {
                throw new HorarioIndisponivelException("Horario ocupado para esse profissional.");
            }

            System.out.print("Informar tipo? (1-Nao / 2-Sim): ");
            int infoTipo = Integer.parseInt(sc.nextLine());

            Agendavel agendamento;
            if (infoTipo == 1) {
                agendamento = new Consulta(cpf, nomeProf, data, horario);
            } else {
                System.out.print("Tipo (inicial/retorno/avaliacao): ");
                String tipo = sc.nextLine();
                agendamento = new Consulta(cpf, nomeProf, data, horario, tipo);
            }

            consultas[totalConsultas] = (Consulta) agendamento;
            totalConsultas++;
            System.out.println("Consulta agendada com sucesso!");
        } catch (PacienteNaoEncontradoException | PacienteInativoException
                | ProfissionalNaoEncontradoException | ValorInvalidoException
                | HorarioIndisponivelException e) {
            System.out.println(e.getMessage());
            tentarSugestaoHorario();
        }
    }

    public static void agendarPorEspecialidade() {
        try {
            System.out.print("CPF do paciente: ");
            String cpf = sc.nextLine();
            validarPacienteAtivo(cpf);

            System.out.print("Especialidade: ");
            String esp = sc.nextLine();
            System.out.print("Data (DD/MM/AAAA): ");
            String data = sc.nextLine();
            System.out.print("Horario (HH:MM): ");
            String horario = sc.nextLine();

            if (!Profissional.especialidadeValida(esp)) {
                throw new EspecialidadeInvalidaException("Especialidade invalida.");
            }

            int idxProf = buscarProfissionalPorEspecialidadeDisponivel(esp, data, horario);
            if (idxProf == -1) {
                throw new HorarioIndisponivelException("Nao existe profissional disponivel nesse horario.");
            }

            Agendavel agendamento = new Consulta(cpf, profissionais[idxProf].getNome(), data, horario);
            consultas[totalConsultas] = (Consulta) agendamento;
            totalConsultas++;
            System.out.println("Consulta agendada com " + profissionais[idxProf].getNome() + "!");
        } catch (PacienteNaoEncontradoException | PacienteInativoException
                | EspecialidadeInvalidaException | HorarioIndisponivelException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void validarPacienteAtivo(String cpf)
            throws PacienteNaoEncontradoException, PacienteInativoException {
        int idxPac = buscarIndicePaciente(cpf);
        if (idxPac == -1) {
            throw new PacienteNaoEncontradoException("Paciente nao encontrado.");
        }
        if (!pacientes[idxPac].ativo) {
            throw new PacienteInativoException("Paciente inativo. Nao e possivel agendar.");
        }
    }

    private static int validarProfissionalParaAgendamento(String nomeProf)
            throws ProfissionalNaoEncontradoException, ValorInvalidoException {
        int idxProf = buscarIndiceProfissional(nomeProf);
        if (idxProf == -1) {
            throw new ProfissionalNaoEncontradoException("Profissional nao encontrado.");
        }
        if (profissionais[idxProf].valorConsulta <= 0) {
            throw new ValorInvalidoException("Profissional sem valor definido. Nao pode agendar.");
        }
        return idxProf;
    }

    private static void validarDisponibilidadeProfissional(int idxProf, String data, String horario)
            throws HorarioIndisponivelException {
        String diaSemana = descobrirDiaSemana(data);
        if (!profissionais[idxProf].atendeNoHorario(diaSemana, horario)) {
            throw new HorarioIndisponivelException("Profissional nao atende nesse dia/horario.");
        }
    }

    private static int buscarProfissionalPorEspecialidadeDisponivel(String esp, String data, String horario)
            throws HorarioIndisponivelException {
        boolean encontrouEspecialidade = false;

        for (int i = 0; i < totalProfissionais; i++) {
            if (!profissionais[i].especialidade.equals(esp)) {
                continue;
            }
            encontrouEspecialidade = true;
            if (profissionais[i].valorConsulta <= 0) {
                continue;
            }

            try {
                validarDisponibilidadeProfissional(i, data, horario);
            } catch (HorarioIndisponivelException e) {
                continue;
            }

            if (!temConflito(profissionais[i].getNome(), data, horario)) {
                return i;
            }
        }

        if (!encontrouEspecialidade) {
            throw new HorarioIndisponivelException("Nao ha profissional cadastrado para essa especialidade.");
        }

        return -1;
    }

    private static void tentarSugestaoHorario() {
        System.out.print("Deseja tentar sugestao de horario? (1-Sim / 2-Nao): ");
        int escolha = Integer.parseInt(sc.nextLine());
        if (escolha != 1) {
            return;
        }

        System.out.print("Nome do profissional: ");
        String nomeProf = sc.nextLine();
        System.out.print("Data (DD/MM/AAAA): ");
        String data = sc.nextLine();

        String sugestao = sugerirHorario(nomeProf, data);
        if (sugestao.equals("")) {
            System.out.println("Nenhum horario disponivel nesse dia.");
            return;
        }

        System.out.println("Sugestao: " + sugestao);
    }

    public static void cancelarConsulta() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        System.out.print("Data (DD/MM/AAAA): ");
        String data = sc.nextLine();

        System.out.print("Horario (HH:MM): ");
        String horario = sc.nextLine();

        int idxConsulta = -1;

        for (int i = 0; i < totalConsultas; i++) {
            if (consultas[i].cpfPaciente.equals(cpf)
                    && consultas[i].data.equals(data)
                    && consultas[i].horario.equals(horario)) {

                idxConsulta = i;
                break;
            }
        }

        if (idxConsulta == -1) {
            System.out.println("Consulta nao encontrada.");
            return;
        }

        if (consultas[idxConsulta].status.equals("realizada")) {
            System.out.println("Consulta ja realizada. Nao pode cancelar.");
            return;
        }

        if (consultas[idxConsulta].status.equals("cancelada")) {
            System.out.println("Consulta ja cancelada.");
            return;
        }

        System.out.print("Horario atual (HH:MM): ");
        String horaAtual = sc.nextLine();

        int horaConsulta = Integer.parseInt(horario.substring(0, 2));
        int horaAgora = Integer.parseInt(horaAtual.substring(0, 2));
        int diferenca = horaConsulta - horaAgora;

        if (diferenca < 2 && totalMultas < multas.length) {
            System.out.println("Multa de R$50.00 aplicada!");
            multas[totalMultas] = 50.0;
            totalMultas++;
        }

        System.out.print("Informar motivo? (1-Nao / 2-Sim): ");
        int informarMotivo = Integer.parseInt(sc.nextLine());

        if (informarMotivo == 1) {
            consultas[idxConsulta].cancelar();
        } else {
            System.out.print("Motivo: ");
            String motivo = sc.nextLine();
            System.out.println(consultas[idxConsulta].cancelar(motivo));
        }

        System.out.println("Consulta cancelada.");
    }

    public static void remarcarConsulta() {
        if (totalConsultas >= consultas.length) {
            System.out.println("Limite de consultas atingido.");
            return;
        }

        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        System.out.print("Data original (DD/MM/AAAA): ");
        String dataOriginal = sc.nextLine();

        System.out.print("Horario original (HH:MM): ");
        String horarioOriginal = sc.nextLine();

        int idxConsulta = -1;

        for (int i = 0; i < totalConsultas; i++) {
            if (consultas[i].cpfPaciente.equals(cpf)
                    && consultas[i].data.equals(dataOriginal)
                    && consultas[i].horario.equals(horarioOriginal)
                    && consultas[i].status.equals("agendada")) {

                idxConsulta = i;
                break;
            }
        }

        if (idxConsulta == -1) {
            System.out.println("Consulta agendada nao encontrada.");
            return;
        }

        System.out.print("Apenas trocar horario no mesmo dia? (1-Sim / 2-Nao): ");
        int tipo = Integer.parseInt(sc.nextLine());

        String novaData;
        String novoHorario;

        if (tipo == 1) {
            novaData = dataOriginal;

            System.out.print("Novo horario: ");
            novoHorario = sc.nextLine();

        } else {
            System.out.print("Nova data (DD/MM/AAAA): ");
            novaData = sc.nextLine();

            System.out.print("Novo horario (HH:MM): ");
            novoHorario = sc.nextLine();
        }

        String nomeProf = consultas[idxConsulta].nomeProfissional;
        int idxProfissional = buscarIndiceProfissional(nomeProf);

        if (idxProfissional == -1) {
            System.out.println("Profissional nao encontrado.");
            return;
        }

        if (tipo == 2) {
            String diaSemana = descobrirDiaSemana(novaData);

            if (!profissionais[idxProfissional].atendeNoDia(diaSemana)) {
                System.out.println("Profissional nao atende nesse dia.");
                return;
            }
        }

        if (temConflito(nomeProf, novaData, novoHorario)) {
            System.out.println("Horario ocupado. Nao foi possivel remarcar.");
            return;
        }

        consultas[idxConsulta].remarcar();

        consultas[totalConsultas] = new Consulta(
                cpf,
                nomeProf,
                novaData,
                novoHorario,
                consultas[idxConsulta].tipo
        );

        totalConsultas++;
        System.out.println("Consulta remarcada com sucesso!");
    }

    public static void listarConsultas() {
        if (totalConsultas == 0) {
            System.out.println("Nenhuma consulta.");
            return;
        }

        for (int i = 0; i < totalConsultas; i++) {
            System.out.println("[" + i + "] " + consultas[i].exibirResumo());
        }
    }

    public static void buscarConsultasPorPaciente() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        boolean encontrou = false;

        for (int i = 0; i < totalConsultas; i++) {
            if (consultas[i].cpfPaciente.equals(cpf)) {
                System.out.println("[" + i + "] " + consultas[i].exibirResumo());
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhuma consulta encontrada.");
        }
    }

     // verifica se ja tem consulta nesse horario com esse profissional
    public static boolean temConflito(String nomeProf, String data, String horario) {
        int idxProf = buscarIndiceProfissional(nomeProf);
        if (idxProf == -1) {
            return true;
        }

        String diaSemana = descobrirDiaSemana(data);
        if (!profissionais[idxProf].atendeNoHorario(diaSemana, horario)) {
            return true;
        }

        for (int i = 0; i < totalConsultas; i++) {
            if (consultas[i].nomeProfissional.equals(nomeProf)
                    && consultas[i].data.equals(data)
                    && consultas[i].horario.equals(horario)
                    && consultas[i].status.equals("agendada")) {

                return true;
            }
        }

        return false;
    }

    // sugere proximo horario livre (de hora em hora, 08h ate 18h)
    public static String sugerirHorario(String nomeProf, String data) {
        int idxProf = buscarIndiceProfissional(nomeProf);
        if (idxProf == -1) {
            return "";
        }

        String diaSemana = descobrirDiaSemana(data);

        for (int h = 8; h <= 21; h++) {
            String teste;
            if (h < 10) {
                teste = "0" + h + ":00";
            } else {
                teste = h + ":00";
            }

            if (!profissionais[idxProf].atendeNoHorario(diaSemana, teste)) {
                continue;
            }

            if (!temConflito(nomeProf, data, teste)) {
                return teste;
            }
        }

        return "";
    }

    public static String descobrirDiaSemana(String data) {
        String dataLimpa = data.trim();

        int dia = Integer.parseInt(dataLimpa.substring(0, 2));
        int mes = Integer.parseInt(dataLimpa.substring(3, 5));
        int ano = Integer.parseInt(dataLimpa.substring(6, 10));

        if (mes < 3) {
            mes += 12;
            ano--;
        }

        int k = ano % 100;
        int j = ano / 100;

        int resultado = (
                dia
                        + (13 * (mes + 1)) / 5
                        + k
                        + k / 4
                        + j / 4
                        - 2 * j
        ) % 7;

        if (resultado < 0) {
            resultado += 7;
        }

        String[] nomes = {
                "sabado",
                "domingo",
                "segunda",
                "terca",
                "quarta",
                "quinta",
                "sexta"
        };

        return nomes[resultado];
    }

    // -------------------------------------------------------------------------
    // ATENDIMENTOS
    // -------------------------------------------------------------------------

    public static void menuAtendimentos() {
        int op = -1;

        while (op != 0) {
            System.out.println("\n--- ATENDIMENTOS ---");
            System.out.println("1 - Registrar atendimento");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");

            op = Integer.parseInt(sc.nextLine());

            if (op == 1) {
                registrarAtendimento();
            } else if (op != 0) {
                System.out.println("Opcao invalida!");
            }
        }
    }

    public static void registrarAtendimento() {
        if (totalAtendimentos >= atendimentos.length) {
            System.out.println("Limite de atendimentos atingido.");
            return;
        }

        System.out.print("Indice da consulta: ");
        int idxConsulta = Integer.parseInt(sc.nextLine());

        if (idxConsulta < 0 || idxConsulta >= totalConsultas) {
            System.out.println("Indice invalido.");
            return;
        }

        if (!consultas[idxConsulta].status.equals("agendada")) {
            System.out.println("So pode registrar atendimento em consulta agendada.");
            return;
        }

        System.out.print("Observacoes: ");
        String observacoes = sc.nextLine();

        System.out.print("Tipo de registro (1-So obs / 2-Com diagnostico / 3-Completo): ");
        int tipo = Integer.parseInt(sc.nextLine());

        if (tipo == 1) {
            atendimentos[totalAtendimentos] = new Atendimento(
                    idxConsulta,
                    observacoes
            );

        } else if (tipo == 2) {
            System.out.print("Diagnostico: ");
            String diagnostico = sc.nextLine();

            atendimentos[totalAtendimentos] = new Atendimento(
                    idxConsulta,
                    observacoes,
                    diagnostico
            );

        } else if (tipo == 3) {
            System.out.print("Diagnostico: ");
            String diagnostico = sc.nextLine();

            System.out.print(
                    "Como informar procedimentos? (1-Um por vez / 2-Todos de uma vez): "
            );
            int forma = Integer.parseInt(sc.nextLine());

            String[] procedimentos = new String[10];
            int quantidadeProcedimentos = 0;

            if (forma == 1) {
                String procedimento = "";

                while (!procedimento.equals("fim")
                        && quantidadeProcedimentos < procedimentos.length) {

                    System.out.print("Procedimento (ou 'fim'): ");
                    procedimento = sc.nextLine();

                    if (!procedimento.equals("fim")) {
                        procedimentos[quantidadeProcedimentos] = procedimento;
                        quantidadeProcedimentos++;
                    }
                }

            } else if (forma == 2) {
                System.out.print("Quantos? ");
                quantidadeProcedimentos = Integer.parseInt(sc.nextLine());

                if (quantidadeProcedimentos < 0) {
                    quantidadeProcedimentos = 0;
                }

                if (quantidadeProcedimentos > procedimentos.length) {
                    quantidadeProcedimentos = procedimentos.length;
                }

                for (int i = 0; i < quantidadeProcedimentos; i++) {
                    System.out.print("Proc " + (i + 1) + ": ");
                    procedimentos[i] = sc.nextLine();
                }

            } else {
                System.out.println("Forma invalida.");
                return;
            }

            atendimentos[totalAtendimentos] = new Atendimento(
                    idxConsulta,
                    observacoes,
                    diagnostico,
                    procedimentos,
                    quantidadeProcedimentos
            );

        } else {
            System.out.println("Tipo de registro invalido.");
            return;
        }
        String nomeProfissional = consultas[idxConsulta].nomeProfissional;
        int idxProf = buscarIndiceProfissional(nomeProfissional);
        
        if (idxProf != -1 && profissionais[idxProf] instanceof Psicologo) {
            profissionais[idxProf].registrarEspecifico(atendimentos[totalAtendimentos]);
        }
        consultas[idxConsulta].realizar();
        totalAtendimentos++;

        System.out.println("\n--- RESUMO ---");
        System.out.println(atendimentos[totalAtendimentos - 1].exibirResumo());
        System.out.println("Consulta marcada como realizada.");
    }

    // -------------------------------------------------------------------------
    // PAGAMENTOS
    // -------------------------------------------------------------------------

    public static void menuPagamentos() {
        int op = -1;

        while (op != 0) {
            System.out.println("\n--- PAGAMENTOS ---");
            System.out.println("1 - Pagamento direto");
            System.out.println("2 - Pagamento automatico");
            System.out.println("3 - Listar pagamentos");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");

            op = Integer.parseInt(sc.nextLine());

            switch (op) {
                case 1:
                    pagamentoDireto();
                    break;
                case 2:
                    pagamentoAutomatico();
                    break;
                case 3:
                    listarPagamentos();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }
    }

    public static void pagamentoDireto() {
        System.out.print("Indice da consulta: ");
        int idxConsulta = Integer.parseInt(sc.nextLine());

        if (idxConsulta < 0 || idxConsulta >= totalConsultas) {
            System.out.println("Indice invalido.");
            return;
        }

        System.out.print("Valor base: ");
        double valorBase = Double.parseDouble(sc.nextLine());

        if (valorBase < 0) {
            System.out.println("Valor invalido.");
            return;
        }

        System.out.print("Tipo (dinheiro/cartao): ");
        String tipoPagamento = sc.nextLine().trim().toLowerCase();

        try {
            Pagamento pagamento;

            if (tipoPagamento.equals("dinheiro")) {
                pagamento = new PagamentoDinheiro(
                        idxConsulta,
                        valorBase
                );

            } else if (tipoPagamento.equals("cartao")) {
                System.out.print("Parcelas (1 a 6): ");
                int parcelas = Integer.parseInt(sc.nextLine());

                pagamento = new PagamentoCartao(
                        idxConsulta,
                        valorBase,
                        parcelas
                );

            } else {
                System.out.println("Tipo de pagamento invalido.");
                return;
            }

            adicionarPagamento(pagamento);

            System.out.println("Pagamento registrado!");
            System.out.println(pagamento.exibirResumo());

        } catch (PagamentoInvalidoException e) {
            System.out.println(
                    "Erro ao registrar pagamento: "
                            + e.getMessage()
            );
        }
    }

    public static void pagamentoAutomatico() {
        System.out.print("Indice da consulta: ");
        int idxConsulta = Integer.parseInt(sc.nextLine());

        if (idxConsulta < 0 || idxConsulta >= totalConsultas) {
            System.out.println("Indice invalido.");
            return;
        }

        Consulta consulta = consultas[idxConsulta];

        int idxProfissional = buscarIndiceProfissional(
                consulta.nomeProfissional
        );

        if (idxProfissional == -1) {
            System.out.println("Profissional nao encontrado.");
            return;
        }

        int idxPaciente = buscarIndicePaciente(
                consulta.cpfPaciente
        );

        if (idxPaciente == -1) {
            System.out.println("Paciente nao encontrado.");
            return;
        }

        Profissional profissional = profissionais[idxProfissional];
        Paciente paciente = pacientes[idxPaciente];

        double valorBase = profissional.valorConsulta;

        if (valorBase < 0) {
            System.out.println("Valor da consulta invalido.");
            return;
        }

        System.out.print("Tem multa pendente? (1-Nao / 2-Sim): ");
        int possuiMulta = Integer.parseInt(sc.nextLine());

        double valorMulta = 0.0;

        if (possuiMulta == 2) {
            System.out.print("Valor da multa: ");
            valorMulta = Double.parseDouble(sc.nextLine());

            if (valorMulta < 0) {
                System.out.println("Valor da multa invalido.");
                return;
            }
        }

        System.out.println("Valor base: R$" + valorBase);

        System.out.print("Tipo (dinheiro/cartao/convenio): ");
        String tipoPagamento = sc.nextLine().trim().toLowerCase();

        try {
            Pagamento pagamento;

            switch (tipoPagamento) {
                case "dinheiro":
                    pagamento = new PagamentoDinheiro(
                            idxConsulta,
                            valorBase,
                            valorMulta
                    );
                    break;

                case "cartao":
                    System.out.print("Parcelas (1 a 6): ");
                    int parcelas = Integer.parseInt(sc.nextLine());

                    pagamento = new PagamentoCartao(
                            idxConsulta,
                            valorBase,
                            parcelas,
                            valorMulta
                    );
                    break;

                case "convenio":
                    pagamento = new PagamentoConvenio(
                            idxConsulta,
                            valorBase,
                            paciente.convenio,
                            profissional.especialidade,
                            valorMulta
                    );
                    break;

                default:
                    System.out.println("Tipo de pagamento invalido.");
                    return;
            }

            adicionarPagamento(pagamento);

            System.out.println("Pagamento registrado!");
            System.out.println(pagamento.exibirResumo());

        } catch (ConvenioNaoCobreException e) {
            System.out.println(e);

        } catch (PagamentoInvalidoException e) {
            System.out.println(e);
        }
    }

    public static void adicionarPagamento(Pagamento pagamento) {
        // LIGACAO DINAMICA conforme R5:
        // o metodo executado depende da subclasse real do objeto.
        pagamento.valorFinal = pagamento.calcularValorFinal();
        pagamentos.add(pagamento);
    }

    public static void listarPagamentos() {
        if (pagamentos.isEmpty()) {
            System.out.println("Nenhum pagamento registrado.");
            return;
        }

        System.out.println("\n=== PAGAMENTOS REGISTRADOS ===");

        for (Pagamento pagamento : pagamentos) {
            // LIGACAO DINAMICA conforme R5.
            pagamento.valorFinal = pagamento.calcularValorFinal();

            System.out.println(
                    pagamento.getClass().getSimpleName()
                            + " -> "
                            + pagamento.exibirResumo()
            );
        }
    }

    public static void relatorioUnificadoPessoas() {
        System.out.println("\nRELATORIO UNIFICADO DE PESSOAS");

        if (pessoas.isEmpty()) {
            System.out.println("Nenhuma pessoa cadastrada.");
            return;
        }

        int totalPacientesRelatorio = 0;
        int totalProfissionaisRelatorio = 0;

        for (Pessoa pessoa : pessoas) {
            // LIGAÇÃO DINÂMICA: o método chamado depende do tipo REAL do objeto, não do tipo da referência
            System.out.println(pessoa.exibirResumo());

            if (pessoa instanceof Paciente) {
                totalPacientesRelatorio++;
            }

            if (pessoa instanceof Profissional) {
                totalProfissionaisRelatorio++;

                Profissional prof = (Profissional) pessoa;

                if (prof instanceof Fisioterapeuta) {
                    System.out.println("Tipo real: Fisioterapeuta");
                } else if (prof instanceof Psicologo) {
                    System.out.println("Tipo real: Psicologo");
                } else if (prof instanceof Nutricionista) {
                    System.out.println("Tipo real: Nutricionista");
                } else if (prof instanceof ClinicoGeral) {
                    System.out.println("Tipo real: Clinico Geral");
                }
            }

            System.out.println("---");
        }

        System.out.println("Total de pacientes: " + totalPacientesRelatorio);
        System.out.println("Total de profissionais: " + totalProfissionaisRelatorio);
    }

    // -------------------------------------------------------------------------
    // RELATORIOS
    // -------------------------------------------------------------------------

    public static void menuRelatorios() {
        int op = -1;

        while (op != 0) {
            System.out.println("\n--- RELATORIOS ---");
            System.out.println("1 - Geral");
            System.out.println("2 - Por profissional");
            System.out.println("3 - Por periodo");
            System.out.println("4 - Resumo financeiro");
            System.out.println("5 - Relatorio de pagamentos");
            System.out.println("6 - Relatorio unificado de pessoas");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");

            op = Integer.parseInt(sc.nextLine());

            switch (op) {
                case 1:
                    Relatorio.gerarRelatorio(
                            consultas,
                            totalConsultas,
                            atendimentos,
                            totalAtendimentos
                    );
                    break;

                case 2:
                    System.out.print("Nome do profissional: ");
                    String nome = sc.nextLine();

                    Relatorio.gerarRelatorio(
                            consultas,
                            totalConsultas,
                            atendimentos,
                            totalAtendimentos,
                            nome
                    );
                    break;

                case 3:
                    System.out.print("Data inicio (DD/MM/AAAA): ");
                    String inicio = sc.nextLine();

                    System.out.print("Data fim (DD/MM/AAAA): ");
                    String fim = sc.nextLine();

                    Relatorio.gerarRelatorio(
                            consultas,
                            totalConsultas,
                            atendimentos,
                            totalAtendimentos,
                            inicio,
                            fim
                    );
                    break;

                case 4:
                    Relatorio.gerarResumoFinanceiro(
                            consultas,
                            totalConsultas,
                            pagamentos,
                            multas,
                            totalMultas
                    );
                    break;

                case 5:
                    Relatorio.gerarRelatorioPagamentos(pagamentos);
                    break;

                case 6:
                    relatorioUnificadoPessoas();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }
    }
}
