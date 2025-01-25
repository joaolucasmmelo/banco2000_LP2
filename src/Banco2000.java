import Utils.Corrent;
import Utils.Poupanca;
import Utils.Salario;
import Utils.Utils;
import Utils.Menu;
import Utils.Conta;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;
import java.io.*;

public class Banco2000 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Menu menu = new Menu();
        Utils utils = new Utils();
        Corrent corrent = new Corrent(null, null, null, null, null, null);
        Poupanca poupanca = new Poupanca(null, null, null, null, null, null);
        Salario salario = new Salario(null, null, null, null, null, null, null, null);

        int n = 1;
        int verConta = 0;
        boolean verLogin = false;
        String seuId = "";

        while (n != 0){
        // Verificacao de taxas e rendimentos das contas
            utils.verificaSaldo();

            if (!verLogin){
                menu.menuImprime();
            }
            else{
                if (verConta == 1){
                    menu.menuCorrentLoginImprime(corrent);
                    menu.menuImprime();
                }
                if (verConta == 2){
                    menu.menuPoupancaLoginImprime(poupanca);
                    menu.menuImprime();
                }
                if (verConta == 3){
                    menu.menuSalarioLoginImprime(salario);
                    menu.menuImprime();
                }
            }

            n = input.nextInt();
            if ((n != 1) && (n != 2) && (n != 3) && (n != 4) && (n != 0) && (n != 5)){
                System.out.println("Por favor digite uma opcao valida!");
            }
            else{
            // OPCAO 1

                if (n == 1){
                    if (verLogin){
                        System.out.println("Voce precisa estar deslogado da qualquer conta para realizar um cadastro de usuario.");
                    }
                    else {
                        input.nextLine();

                        System.out.print("\nDigite o nome do proprietario do CPF: ");
                        String nome = input.nextLine();

                        System.out.print("Digite o CPF para a realizacao do cadastro no sistema de bancos: ");
                        String cpf = input.nextLine();
                        //verificacao de cadastro

                        try (BufferedReader cadastrosLer = new BufferedReader(new FileReader("src/cadastros.txt"))) {
                            String linha;
                            boolean encontrado = false;

                            linha = cadastrosLer.readLine();
                            while (linha != null) {
                                if (linha.contains(("CPF: " + cpf + "; "))) {
                                    encontrado = true;
                                    break;
                                }
                                linha = cadastrosLer.readLine();
                            }
                            cadastrosLer.close();

                            if (encontrado) {
                                System.out.println("\nNao foi possivel concluir o cadastro. CPF ja cadastrado em nosso banco de dados!");
                            }
                            else {
                                try {
                                    // Criacao senha
                                    System.out.print("Crie uma senha de 3 a 12 caracteres para finalizar o cadastro: ");
                                    String senha = input.nextLine();

                                    // Verificacao senha
                                    while (senha.length() < 3 || senha.length() > 12){
                                        System.out.print("Por favor digite uma senha de 3 a 12 caracteres: ");
                                        senha = input.nextLine();
                                    }

                                    try {
                                        FileWriter cadastrosEscreve = new FileWriter("src/cadastros.txt", true);
                                        cadastrosEscreve.write("CPF: " + cpf + "; Nome: " + nome + "; Senha: " + senha + "; \n");
                                        cadastrosEscreve.close();
                                    }
                                    catch (IOException e){
                                        System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                    }
                                }
                                catch (NumberFormatException e) {
                                    System.out.println("CPF invalido! Por favor digite apenas numeros!");
                                }
                            }
                        }
                        catch (IOException e) {
                            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                        }
                    }
                }

            // OPCAO 2
                if (n == 2){
                    if (verLogin){
                        System.out.println("Voce precisa estar deslogado para a criacao de uma conta.");
                    }
                    else {
                        boolean verWhile = false;

                        while (!verWhile){
                            System.out.println("Digite os dados necessarios para a criacao da conta.");

                            input.nextLine();
                            System.out.print("\nDigite o CPF do titular da conta: ");
                            String cpf = input.nextLine();

                            String nome = null;
                            try (BufferedReader cadastrosLer = new BufferedReader(new FileReader("src/cadastros.txt"))) {
                                String linha;
                                boolean encontrado = false;
                                linha = cadastrosLer.readLine();

                                while (linha != null) {
                                    if (linha.contains(("CPF: " + cpf + "; "))) {
                                        int i = 0;
                                        while (i < 3){
                                            System.out.print("\nDigite a senha do CPF cadastrado para prosseguir: ");
                                            String senha = input.nextLine();
                                            if (linha.contains(("Senha: " + senha + "; "))){

                                                String[] partes = linha.split("; ");
                                                for (String parte : partes) {
                                                    if (parte.startsWith("Nome: ")) {
                                                        nome = parte;
                                                        break;
                                                    }
                                                }
                                                encontrado = true;
                                                linha = null;
                                                break;
                                            }
                                            else {
                                                System.out.println("Senha incorreta! Voce tem mais " + (2-i) + " tentativas.");
                                            }
                                            i++;
                                        }
                                    }
                                    linha = cadastrosLer.readLine();
                                }
                                cadastrosLer.close();

                                if (!encontrado){
                                    System.out.println("\nNao foi possivel prosseguir pois o CPF nao esta cadastrado ou a Senha esta incorreta.");
                                    verWhile = true;
                                    break;
                                }
                            }
                            catch (IOException e){
                                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                            }

                            String id = null;
                            boolean verId = false;
                            while (!verId){
                                System.out.print("\nDigite um ID unico para a conta: ");
                                id = input.nextLine();
                                boolean verr = true;

                                try (BufferedReader dadosLer = new BufferedReader(new FileReader("src/dados.txt"))) {
                                    String linha = dadosLer.readLine();

                                    while (linha != null){
                                        if (linha.contains(("ID: " + id + "; "))){
                                            System.out.println("ID de conta ja existente, Por favor digite um ID unico!");
                                            verr = false;
                                            break;
                                        }
                                        linha = dadosLer.readLine();
                                    }
                                    dadosLer.close();

                                    if (verr){
                                        verId = true;
                                    }
                                }
                                catch (IOException e){
                                    System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                }
                            }

                            menu.opcoesAgenciasImprime();
                            System.out.print("Selecione uma das agencias disponiveis para prosseguir: ");
                            String agencia = input.nextLine();

                            while (!agencia.equals("1") && !agencia.equals("2") && !agencia.equals("3") && !agencia.equals("4") && !agencia.equals("5")) {
                                System.out.print("\nPor favor digite uma opcao de agencia valida!");
                                menu.opcoesAgenciasImprime();
                                System.out.print("Selecione uma das agencias disponiveis para prosseguir: ");
                                agencia = input.next();
                            }
                            if (agencia.equals("1")){agencia = "Banco do Brasil";}
                            if (agencia.equals("2")){agencia = "Bradesco";}
                            if (agencia.equals("3")){agencia = "Itau";}
                            if (agencia.equals("4")){agencia = "Nubank";}
                            if (agencia.equals("5")){agencia = "Santander";}

                            String tipo = null;
                            boolean verTipo = false;
                            while (!verTipo){
                                menu.tiposContaImprime();
                                System.out.print("\nDigite o tipo da conta: ");
                                tipo = input.nextLine();

                                if (tipo.equals("1") || tipo.equals("2") || tipo.equals("3")){verTipo = true;}
                                else {System.out.println("Digite um numero correspondente ao tipo de conta que deseja!");}
                            }

                            String saldo = null;
                            double saldoN = 0;
                            boolean v = false;
                            while (!v){
                                try {
                                    System.out.print("\nDigite o saldo inicial da conta: ");
                                    saldoN = input.nextDouble();
                                    while (saldoN < 0){
                                        System.out.println("\nO saldo nao pode ser negativo!");
                                        System.out.print("Por favor digite um saldo nulo ou positivo: ");
                                        saldoN = input.nextDouble();
                                    }
                                    saldo = Double.toString(saldoN);
                                    v = true;
                                }
                                catch (InputMismatchException e){
                                    System.out.print("\nPreencha o saldo apenas com numeros!!!");
                                    input.nextLine();
                                }
                            }

                            if (tipo.equals("1")){
                                tipo = "Corrente";
                                corrent = new Corrent(cpf, agencia, nome, id, tipo, saldo);
                                corrent.contaEscreve();
                            }
                            if (tipo.equals("2")){
                                tipo = "Poupanca";
                                poupanca = new Poupanca(cpf, agencia, nome, id, tipo, saldo);
                                poupanca.contaEscreve();
                            }
                            if (tipo.equals("3")){
                                try (BufferedReader empregadorVer = new BufferedReader(new FileReader("src/dados.txt"))) {
                                    String linha = empregadorVer.readLine();
                                    boolean verEmp = false;

                                    System.out.print("Digite o ID da conta que sera o empregador desta: ");
                                    input.nextLine();
                                    String idEmp = input.nextLine();
                                    while (linha != null){
                                        if (linha.contains("; ID: " + idEmp + "; ") && !linha.contains("; Tipo: Salario; ")){
                                            tipo = "Salario";
                                            String status = "V";
                                            salario = new Salario(cpf, agencia, nome, id, tipo, saldo, status, idEmp);
                                            salario.contaEscreve(cpf, agencia, nome, id, tipo, saldo, status, idEmp);
                                            verEmp = true;
                                            break;
                                        }
                                        linha = empregadorVer.readLine();
                                    }
                                    if (!verEmp){
                                        System.out.println("\nID de conta invalido! Operacao cancelada.");
                                    }
                                }
                                catch (IOException e){
                                    System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                }
                            }
                            verWhile = true;
                        }
                    }
                }

            // OPCAO 3
                if (n == 3){
                    if (verLogin){
                        Conta contaDados = new Conta(null, null, null, null, null);
                        int nn = 1;
                        while (nn != 0){
                            if (utils.verificaSaldo() == 1){
                                salario.setStatus("Status: V");
                            }

                        // CORRENTE
                            if (verConta == 1) {
                                menu.menuCorrentLoginImprime(corrent);
                                menu.menuOperacoesCorrentImprime();
                                nn = input.nextInt();
                                if ((nn != 1) && (nn != 2) && (nn != 3) && (nn != 4) && (nn != 0) && (nn != 5)) {
                                    System.out.println("Por favor digite uma opcao valida!");
                                } else {
                                    if (nn == 1){
                                        String data = LocalDateTime.now().toString();
                                        String saldo = corrent.getSaldo();
                                        data = data.substring(0, 10);
                                        saldo = saldo.substring(7);
                                        System.out.println("\nData e hora da operacao: " + data);
                                        System.out.println("\nSaldo: R$ " + saldo);
                                    }
                                    if (nn == 2){
                                        double saque;
                                        String saqueStr;
                                        String saldoStr = corrent.getSaldo();
                                        saldoStr = saldoStr.substring(7);
                                        double saldo = Double.parseDouble(saldoStr);
                                        System.out.println("\nSaldo atual: R$ " + saldo);
                                        System.out.print("\nDigite a quantia que deseja sacar: ");
                                        input.nextLine();
                                        saqueStr = input.nextLine();
                                        saqueStr = saqueStr.replaceFirst(",", ".");
                                        saque = Double.parseDouble(saqueStr);

                                        if (saque > saldo){
                                            System.out.println("Operacao cancelada. Saldo insuficiente.");
                                        }
                                        else {
                                            System.out.println("\nSaque bem sucedido.");
                                            String data = LocalDateTime.now().toString();
                                            data = data.substring(0, 10);
                                            System.out.println("\nData e hora da operacao: " + data);
                                            String novoSaldo = Double.toString(((saldo - saque) * 100) / 100);
                                            System.out.println("Novo saldo: R$ " + novoSaldo);
                                            corrent.setSaldo("Saldo: " + novoSaldo);

                                            String linha1 = null;
                                            try (BufferedReader dadosChecker = new BufferedReader(new FileReader("src/dados.txt"))) {
                                                linha1 = dadosChecker.readLine();
                                                if (linha1 != null){
                                                    File criaNovosDados = new File("src/dadosNovos.txt");
                                                    try {
                                                        FileWriter novosDados = new FileWriter("src/dadosNovos.txt", false);
                                                        while (linha1 != null){

                                                            if (linha1.contains(corrent.getIdConta())){
                                                                String[] partes = linha1.split("; ");
                                                                for (String parte : partes) {
                                                                    if (parte.startsWith("Saldo: ")) {
                                                                        linha1 = linha1.replaceFirst(parte, (corrent.getSaldo()));
                                                                        break;
                                                                    }
                                                                }
                                                                novosDados.write(linha1 + "\n");
                                                            }
                                                            else {
                                                                novosDados.write(linha1 + "\n");
                                                            }
                                                            linha1 = dadosChecker.readLine();
                                                        }
                                                        dadosChecker.close();
                                                        novosDados.close();

                                                        try (FileWriter dadosEscreve = new FileWriter("src/dados.txt", false)) {
                                                            try (BufferedReader dadosNovosLer = new BufferedReader(new FileReader("src/dadosNovos.txt"))){
                                                                String linha = dadosNovosLer.readLine();
                                                                while (linha != null){
                                                                    dadosEscreve.write(linha + "\n");
                                                                    linha = dadosNovosLer.readLine();
                                                                }
                                                                dadosNovosLer.close();
                                                                Files.deleteIfExists(Path.of("src/dadosNovos.txt"));
                                                                dadosEscreve.close();
                                                            }
                                                            catch (IOException e){
                                                                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                                            }
                                                        }
                                                        catch (IOException e){
                                                            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                                        }
                                                    }
                                                    catch (IOException e){
                                                        System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                                    }
                                                }
                                            }
                                            catch (IOException e){
                                                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                            }
                                        }
                                    }
                                    if (nn == 3){
                                        double deposito;
                                        String depositoStr;
                                        String saldoStr = corrent.getSaldo();
                                        saldoStr = saldoStr.substring(7);
                                        double saldo = Double.parseDouble(saldoStr);
                                        System.out.println("\nSaldo atual: R$ " + saldo);
                                        System.out.print("\nDigite a quantia que deseja depositar: ");
                                        input.nextLine();
                                        depositoStr = input.nextLine();
                                        depositoStr = depositoStr.replaceFirst(",", ".");
                                        deposito = Double.parseDouble(depositoStr);

                                        if (deposito <= 0){
                                            System.out.println("Operacao cancelada. Deposito nao pode ser nulo ou negativo.");
                                        }
                                        else {
                                            System.out.println("\nDeposito bem sucedido.");
                                            String data = LocalDateTime.now().toString();
                                            data = data.substring(0, 10);
                                            System.out.println("\nData e hora da operacao: " + data);
                                            String novoSaldo = Double.toString(((saldo + deposito) * 100) / 100);
                                            System.out.println("Novo saldo: R$ " + novoSaldo);
                                            corrent.setSaldo("Saldo: " + novoSaldo);

                                            String linha1 = null;
                                            try (BufferedReader dadosChecker = new BufferedReader(new FileReader("src/dados.txt"))) {
                                                linha1 = dadosChecker.readLine();
                                                if (linha1 != null){
                                                    File criaNovosDados = new File("src/dadosNovos.txt");
                                                    try {
                                                        FileWriter novosDados = new FileWriter("src/dadosNovos.txt", false);
                                                        while (linha1 != null){

                                                            if (linha1.contains(corrent.getIdConta())){
                                                                String[] partes = linha1.split("; ");
                                                                for (String parte : partes) {
                                                                    if (parte.startsWith("Saldo: ")) {
                                                                        linha1 = linha1.replaceFirst(parte, (corrent.getSaldo()));
                                                                        break;
                                                                    }
                                                                }
                                                                novosDados.write(linha1 + "\n");
                                                            }
                                                            else {
                                                                novosDados.write(linha1 + "\n");
                                                            }
                                                            linha1 = dadosChecker.readLine();
                                                        }
                                                        dadosChecker.close();
                                                        novosDados.close();

                                                        try (FileWriter dadosEscreve = new FileWriter("src/dados.txt", false)) {
                                                            try (BufferedReader dadosNovosLer = new BufferedReader(new FileReader("src/dadosNovos.txt"))){
                                                                String linha = dadosNovosLer.readLine();
                                                                while (linha != null){
                                                                    dadosEscreve.write(linha + "\n");
                                                                    linha = dadosNovosLer.readLine();
                                                                }
                                                                dadosNovosLer.close();
                                                                Files.deleteIfExists(Path.of("src/dadosNovos.txt"));
                                                                dadosEscreve.close();
                                                            }
                                                            catch (IOException e){
                                                                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                                            }
                                                        }
                                                        catch (IOException e){
                                                            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                                        }
                                                    }
                                                    catch (IOException e){
                                                        System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                                    }
                                                }
                                            }
                                            catch (IOException e){
                                                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                            }
                                        }
                                    }
                                    if (nn == 4){
                                        System.out.print("Digite o Id da conta para qual deseja fazer a transferencia: ");
                                        input.nextLine();
                                        String id = "; ID: " + input.nextLine() + "; ";
                                        if (id.contains(corrent.getIdConta())){
                                            System.out.println("Operacao cancelada. Impossivel de transferir a si mesmo.");
                                        }
                                        else {
                                            boolean encontrada = false;
                                            String linha1 = null;
                                            String SaldoResto = "";
                                            try (BufferedReader dadosChecker = new BufferedReader(new FileReader("src/dados.txt"))) {
                                                linha1 = dadosChecker.readLine();
                                                File criaNovosDados = new File("src/dadosNovos.txt");
                                                try {
                                                    FileWriter novosDados = new FileWriter("src/dadosNovos.txt", false);
                                                    boolean verTudo = true;
                                                    double transf;
                                                    String transfStr;
                                                    String saldoStr = corrent.getSaldo();
                                                    saldoStr = saldoStr.substring(7);
                                                    double saldo = Double.parseDouble(saldoStr);
                                                    System.out.println("\nSaldo atual: R$ " + saldo);
                                                    System.out.print("\nDigite a quantia que deseja transferir: ");
                                                    transfStr = input.nextLine();
                                                    transfStr = transfStr.replaceFirst(",", ".");
                                                    transf = Double.parseDouble(transfStr);

                                                    if (transf > saldo){
                                                        System.out.println("Operacao cancelada. Saldo insuficiente.");
                                                        break;
                                                    }
                                                    else {
                                                        while (linha1 != null) {
                                                            if (linha1.contains(id)) {
                                                                String[] contaPartes = linha1.split("; ");
                                                                for (String contaParte : contaPartes){
                                                                    if (contaParte.startsWith("CPF: ")){contaDados.setCpf(contaParte);}
                                                                    if (contaParte.startsWith("Agencia: ")){contaDados.setAgencia(contaParte);}
                                                                    if (contaParte.startsWith("Nome: ")){contaDados.setNome(contaParte);}
                                                                    if (contaParte.startsWith("ID: ")){contaDados.setIdConta(contaParte);}
                                                                    if (contaParte.startsWith("Tipo: ")){contaDados.setTipo(contaParte);}
                                                                }

                                                                encontrada = true;

                                                                if (linha1.contains("; Tipo: Salario; ")){
                                                                    String idCheck = corrent.getIdConta().substring(4);

                                                                    if (linha1.contains("; ID Empregador: " + idCheck + "; ")){
                                                                        SaldoResto = Double.toString((saldo - transf)* 100 / 100);
                                                                        String[] partes = linha1.split("; ");
                                                                        for (String parte : partes){
                                                                            if (parte.startsWith("Saldo: ")){
                                                                                double somaSaldo = (Double.parseDouble(parte.substring(7)) + transf);
                                                                                linha1 = linha1.replaceFirst(parte, "Saldo: " + Double.toString((somaSaldo * 100) / 100));
                                                                                novosDados.write(linha1 + "\n");
                                                                                corrent.setSaldo("Saldo: " + SaldoResto);
                                                                                break;
                                                                            }
                                                                        }
                                                                    }
                                                                    else {
                                                                        verTudo = false;
                                                                        System.out.println("Impossivel realizar a transacao a esta conta pois voce nao e o Empregador dela.");
                                                                        corrent.setSaldo("Saldo: " + saldoStr);
                                                                        novosDados.write(linha1 + "\n");
                                                                    }
                                                                }
                                                                if (linha1.contains("; Tipo: Corrente; ")){
                                                                    SaldoResto = Double.toString((saldo - transf)* 100 / 100);
                                                                    String[] partes = linha1.split("; ");
                                                                    for (String parte : partes){
                                                                        if (parte.startsWith("Saldo: ")){
                                                                            double somaSaldo = (Double.parseDouble(parte.substring(7)) + transf);
                                                                            linha1 = linha1.replaceFirst(parte, "Saldo: " + Double.toString((somaSaldo * 100) / 100));
                                                                            novosDados.write(linha1 + "\n");
                                                                            corrent.setSaldo("Saldo: " + SaldoResto);
                                                                            break;
                                                                        }
                                                                    }
                                                                }
                                                                if (linha1.contains("; Tipo: Poupanca; ")){
                                                                    SaldoResto = Double.toString((saldo - transf)* 100 / 100);
                                                                    String[] partes = linha1.split("; ");
                                                                    for (String parte : partes){
                                                                        if (parte.startsWith("Saldo: ")){
                                                                            double somaSaldo = (Double.parseDouble(parte.substring(7)) + transf);
                                                                            linha1 = linha1.replaceFirst(parte, "Saldo: " + Double.toString((somaSaldo * 100) / 100));
                                                                            novosDados.write(linha1 + "\n");
                                                                            corrent.setSaldo("Saldo: " + SaldoResto);
                                                                            break;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            else if (linha1.contains("; ID: "+ seuId + "; ")){
                                                                String[] partes = linha1.split("; ");
                                                                for (String parte : partes){
                                                                    if (parte.startsWith("Saldo: ")){
                                                                        double saldoResto = ((Double.parseDouble(parte.substring(7)) - transf)* 100 / 100);
                                                                        linha1 = linha1.replaceFirst(parte, "Saldo: " + Double.toString((saldoResto * 100) / 100));
                                                                        novosDados.write(linha1 + "\n");
                                                                        corrent.setSaldo("Saldo: " + saldoResto);
                                                                        break;
                                                                    }
                                                                }
                                                            }
                                                            else {
                                                                novosDados.write(linha1 + "\n");
                                                            }

                                                            linha1 = dadosChecker.readLine();
                                                        }
                                                    }
                                                    dadosChecker.close();
                                                    novosDados.close();

                                                    if (!encontrada){
                                                        Files.deleteIfExists(Path.of("src/dadosNovos.txt"));
                                                        System.out.println("O ID digitado nao existe.");
                                                    }
                                                    else {
                                                        if (verTudo) {
                                                            System.out.println("\nTransacao bem sucedida para:");
                                                            System.out.println(contaDados.getNome());
                                                            System.out.println(contaDados.getCpf());
                                                            System.out.println(contaDados.getAgencia());
                                                            System.out.println(contaDados.getIdConta());
                                                            System.out.println(contaDados.getTipo());

                                                            String data = LocalDateTime.now().toString();
                                                            data = data.substring(0, 10);
                                                            System.out.println("\nData e hora da operacao: " + data);
                                                            System.out.println("Novo saldo: R$ " + SaldoResto);
                                                            corrent.setSaldo("Saldo: " + SaldoResto);

                                                            try (FileWriter dadosEscreve = new FileWriter("src/dados.txt", false)) {
                                                                try (BufferedReader dadosNovosLer = new BufferedReader(new FileReader("src/dadosNovos.txt"))){
                                                                    String linha = dadosNovosLer.readLine();
                                                                    while (linha != null){

                                                                        dadosEscreve.write(linha + "\n");
                                                                        linha = dadosNovosLer.readLine();
                                                                    }
                                                                    dadosNovosLer.close();
                                                                    dadosEscreve.close();
                                                                    Files.deleteIfExists(Path.of("src/dadosNovos.txt"));
                                                                }
                                                                catch (IOException e){
                                                                    System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                                                }
                                                            }
                                                            catch (IOException e){
                                                                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                                            }
                                                        }
                                                    }
                                                }
                                                catch (IOException e){
                                                    System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                                }
                                            }
                                            catch (IOException e){
                                                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                            }
                                        }
                                    }
                                }
                            }
                        // POUPANCA
                            if (verConta == 2){
                                menu.menuPoupancaLoginImprime(poupanca);
                                menu.menuOperacoesPoupancaImprime();
                                nn = input.nextInt();
                                if ((nn != 1) && (nn != 2) && (nn != 3) && (nn != 4) && (nn != 0) && (nn != 5)){
                                    System.out.println("Por favor digite uma opcao valida!");
                                }
                                else{
                                    if (nn == 1){
                                        String data = LocalDateTime.now().toString();
                                        String saldo = poupanca.getSaldo();
                                        data = data.substring(0, 10);
                                        saldo = saldo.substring(7);
                                        System.out.println("\nData e hora da operacao: " + data);
                                        System.out.println("\nSaldo: R$ " + saldo);
                                    }
                                    if (nn == 2){
                                        double saque;
                                        String saqueStr;
                                        String saldoStr = poupanca.getSaldo();
                                        saldoStr = saldoStr.substring(7);
                                        double saldo = Double.parseDouble(saldoStr);
                                        System.out.println("\nSaldo atual: R$ " + saldo);
                                        System.out.print("\nDigite a quantia que deseja sacar: ");
                                        input.nextLine();
                                        saqueStr = input.nextLine();
                                        saqueStr = saqueStr.replaceFirst(",", ".");
                                        saque = Double.parseDouble(saqueStr);

                                        if (saque > saldo){
                                            System.out.println("Operacao cancelada. Saldo insuficiente.");
                                        }
                                        else {
                                            System.out.println("Saque bem sucedido.");
                                            String data = LocalDateTime.now().toString();
                                            data = data.substring(0, 10);
                                            System.out.println("\nData e hora da operacao: " + data);
                                            String novoSaldo = Double.toString(((saldo - saque) * 100) / 100);
                                            System.out.println("Novo saldo: R$ " + novoSaldo);
                                            poupanca.setSaldo("Saldo: " + novoSaldo);

                                            String linha1 = null;
                                            try (BufferedReader dadosChecker = new BufferedReader(new FileReader("src/dados.txt"))) {
                                                linha1 = dadosChecker.readLine();
                                                if (linha1 != null){
                                                    File criaNovosDados = new File("src/dadosNovos.txt");
                                                    try {
                                                        FileWriter novosDados = new FileWriter("src/dadosNovos.txt", false);
                                                        while (linha1 != null){

                                                            if (linha1.contains(poupanca.getIdConta())){
                                                                String[] partes = linha1.split("; ");
                                                                for (String parte : partes) {
                                                                    if (parte.startsWith("Saldo: ")) {
                                                                        linha1 = linha1.replaceFirst(parte, (poupanca.getSaldo()));
                                                                        break;
                                                                    }
                                                                }
                                                                novosDados.write(linha1 + "\n");
                                                            }
                                                            else {
                                                                novosDados.write(linha1 + "\n");
                                                            }
                                                            linha1 = dadosChecker.readLine();
                                                        }
                                                        dadosChecker.close();
                                                        novosDados.close();

                                                        try (FileWriter dadosEscreve = new FileWriter("src/dados.txt", false)) {
                                                            try (BufferedReader dadosNovosLer = new BufferedReader(new FileReader("src/dadosNovos.txt"))){
                                                                String linha = dadosNovosLer.readLine();
                                                                while (linha != null){
                                                                    dadosEscreve.write(linha + "\n");
                                                                    linha = dadosNovosLer.readLine();
                                                                }
                                                                dadosNovosLer.close();
                                                                Files.deleteIfExists(Path.of("src/dadosNovos.txt"));
                                                                dadosEscreve.close();
                                                            }
                                                            catch (IOException e){
                                                                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                                            }
                                                        }
                                                        catch (IOException e){
                                                            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                                        }
                                                    }
                                                    catch (IOException e){
                                                        System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                                    }
                                                }
                                            }
                                            catch (IOException e){
                                                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                            }
                                        }
                                    }
                                    if (nn == 3){
                                        double deposito;
                                        String depositoStr;
                                        String saldoStr = poupanca.getSaldo();
                                        saldoStr = saldoStr.substring(7);
                                        double saldo = Double.parseDouble(saldoStr);
                                        System.out.println("\nSaldo atual: R$ " + saldo);
                                        System.out.print("\nDigite a quantia que deseja depositar: ");
                                        input.nextLine();
                                        depositoStr = input.nextLine();
                                        depositoStr = depositoStr.replaceFirst(",", ".");
                                        deposito = Double.parseDouble(depositoStr);

                                        if (deposito <= 0){
                                            System.out.println("Operacao cancelada. Deposito nao pode ser nulo ou negativo.");
                                        }
                                        else {
                                            System.out.println("\nDeposito bem sucedido.");
                                            String data = LocalDateTime.now().toString();
                                            data = data.substring(0, 10);
                                            System.out.println("\nData e hora da operacao: " + data);
                                            String novoSaldo = Double.toString(((saldo + deposito) * 100) / 100);
                                            System.out.println("Novo saldo: R$ " + novoSaldo);
                                            poupanca.setSaldo("Saldo: " + novoSaldo);

                                            String linha1 = null;
                                            try (BufferedReader dadosChecker = new BufferedReader(new FileReader("src/dados.txt"))) {
                                                linha1 = dadosChecker.readLine();
                                                if (linha1 != null){
                                                    File criaNovosDados = new File("src/dadosNovos.txt");
                                                    try {
                                                        FileWriter novosDados = new FileWriter("src/dadosNovos.txt", false);
                                                        while (linha1 != null){

                                                            if (linha1.contains(poupanca.getIdConta())){
                                                                String[] partes = linha1.split("; ");
                                                                for (String parte : partes) {
                                                                    if (parte.startsWith("Saldo: ")) {
                                                                        linha1 = linha1.replaceFirst(parte, (poupanca.getSaldo()));
                                                                        break;
                                                                    }
                                                                }
                                                                novosDados.write(linha1 + "\n");
                                                            }
                                                            else {
                                                                novosDados.write(linha1 + "\n");
                                                            }
                                                            linha1 = dadosChecker.readLine();
                                                        }
                                                        dadosChecker.close();
                                                        novosDados.close();

                                                        try (FileWriter dadosEscreve = new FileWriter("src/dados.txt", false)) {
                                                            try (BufferedReader dadosNovosLer = new BufferedReader(new FileReader("src/dadosNovos.txt"))){
                                                                String linha = dadosNovosLer.readLine();
                                                                while (linha != null){
                                                                    dadosEscreve.write(linha + "\n");
                                                                    linha = dadosNovosLer.readLine();
                                                                }
                                                                dadosNovosLer.close();
                                                                Files.deleteIfExists(Path.of("src/dadosNovos.txt"));
                                                                dadosEscreve.close();
                                                            }
                                                            catch (IOException e){
                                                                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                                            }
                                                        }
                                                        catch (IOException e){
                                                            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                                        }
                                                    }
                                                    catch (IOException e){
                                                        System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                                    }
                                                }
                                            }
                                            catch (IOException e){
                                                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                            }
                                        }
                                    }
                                    if (nn == 4){
                                        System.out.print("Digite o Id da conta para qual deseja fazer a transferencia: ");
                                        input.nextLine();
                                        String id = "; ID: " + input.nextLine() + "; ";
                                        if (id.contains(poupanca.getIdConta())){
                                            System.out.println("Operacao cancelada. Impossivel de transferir a si mesmo.");
                                        }
                                        else {
                                            boolean encontrada = false;
                                            String linha1 = null;
                                            String SaldoResto = "";
                                            try (BufferedReader dadosChecker = new BufferedReader(new FileReader("src/dados.txt"))) {
                                                linha1 = dadosChecker.readLine();
                                                File criaNovosDados = new File("src/dadosNovos.txt");
                                                try {
                                                    FileWriter novosDados = new FileWriter("src/dadosNovos.txt", false);
                                                    boolean verTudo = true;

                                                    double transf;
                                                    String transfStr;
                                                    String saldoStr = poupanca.getSaldo();
                                                    saldoStr = saldoStr.substring(7);
                                                    double saldo = Double.parseDouble(saldoStr);
                                                    System.out.println("\nSaldo atual: R$ " + saldo);
                                                    System.out.print("\nDigite a quantia que deseja transferir: ");
                                                    transfStr = input.nextLine();
                                                    transfStr = transfStr.replaceFirst(",", ".");
                                                    transf = Double.parseDouble(transfStr);

                                                    if (transf > saldo){
                                                        System.out.println("Operacao cancelada. Saldo insuficiente.");
                                                        break;
                                                    }
                                                    else {
                                                        while (linha1 != null) {
                                                            if (linha1.contains(id)) {
                                                                encontrada = true;

                                                                String[] contaPartes = linha1.split("; ");
                                                                for (String contaParte : contaPartes){
                                                                    if (contaParte.startsWith("CPF: ")){contaDados.setCpf(contaParte);}
                                                                    if (contaParte.startsWith("Agencia: ")){contaDados.setAgencia(contaParte);}
                                                                    if (contaParte.startsWith("Nome: ")){contaDados.setNome(contaParte);}
                                                                    if (contaParte.startsWith("ID: ")){contaDados.setIdConta(contaParte);}
                                                                    if (contaParte.startsWith("Tipo: ")){contaDados.setTipo(contaParte);}
                                                                }

                                                                if (linha1.contains("; Tipo: Salario; ")){
                                                                    String idCheck = poupanca.getIdConta().substring(4);
                                                                    if (linha1.contains("; ID Empregador: " + idCheck + "; ")){
                                                                        SaldoResto = Double.toString((saldo - transf)* 100 / 100);
                                                                        String[] partes = linha1.split("; ");
                                                                        for (String parte : partes){
                                                                            if (parte.startsWith("Saldo: ")){
                                                                                double somaSaldo = (Double.parseDouble(parte.substring(7)) + transf);
                                                                                linha1 = linha1.replaceFirst(parte, "Saldo: " + Double.toString((somaSaldo * 100) / 100));
                                                                                novosDados.write(linha1 + "\n");
                                                                                poupanca.setSaldo("Saldo: " + SaldoResto);
                                                                                break;
                                                                            }
                                                                        }
                                                                    }
                                                                    else {
                                                                        verTudo = false;
                                                                        System.out.println("Impossivel realizar a transacao a esta conta pois voce nao e o Empregador dela.");
                                                                        poupanca.setSaldo("Saldo: " + saldoStr);
                                                                        novosDados.write(linha1 + "\n");
                                                                    }
                                                                }
                                                                if (linha1.contains("; Tipo: Corrente; ")){
                                                                    SaldoResto = Double.toString((saldo - transf)* 100 / 100);
                                                                    String[] partes = linha1.split("; ");
                                                                    for (String parte : partes){
                                                                        if (parte.startsWith("Saldo: ")){
                                                                            double somaSaldo = (Double.parseDouble(parte.substring(7)) + transf);
                                                                            linha1 = linha1.replaceFirst(parte, "Saldo: " + Double.toString((somaSaldo * 100) / 100));
                                                                            novosDados.write(linha1 + "\n");
                                                                            poupanca.setSaldo("Saldo: " + SaldoResto);
                                                                            break;
                                                                        }
                                                                    }
                                                                }
                                                                if (linha1.contains("; Tipo: Poupanca; ")){
                                                                    SaldoResto = Double.toString((saldo - transf)* 100 / 100);
                                                                    String[] partes = linha1.split("; ");
                                                                    for (String parte : partes){
                                                                        if (parte.startsWith("Saldo: ")){
                                                                            double somaSaldo = (Double.parseDouble(parte.substring(7)) + transf);
                                                                            linha1 = linha1.replaceFirst(parte, "Saldo: " + Double.toString((somaSaldo * 100) / 100));
                                                                            novosDados.write(linha1 + "\n");
                                                                            poupanca.setSaldo("Saldo: " + SaldoResto);
                                                                            break;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            else if (linha1.contains("; ID: "+ seuId + "; ")){
                                                                String[] partes = linha1.split("; ");
                                                                for (String parte : partes){
                                                                    if (parte.startsWith("Saldo: ")){
                                                                        double saldoResto = ((Double.parseDouble(parte.substring(7)) - transf)* 100 / 100);
                                                                        linha1 = linha1.replaceFirst(parte, "Saldo: " + Double.toString(saldoResto));
                                                                        novosDados.write(linha1 + "\n");
                                                                        poupanca.setSaldo("Saldo: " + saldoResto);
                                                                        break;
                                                                    }
                                                                }
                                                            }
                                                            else {
                                                                novosDados.write(linha1 + "\n");
                                                            }

                                                            linha1 = dadosChecker.readLine();
                                                        }
                                                    }
                                                    dadosChecker.close();
                                                    novosDados.close();

                                                    if (!encontrada){
                                                        Files.deleteIfExists(Path.of("src/dadosNovos.txt"));
                                                        System.out.println("O ID digitado nao existe.");
                                                    }
                                                    else {
                                                        if (verTudo){
                                                            System.out.println("\nTransacao bem sucedida para:");
                                                            System.out.println(contaDados.getNome());
                                                            System.out.println(contaDados.getCpf());
                                                            System.out.println(contaDados.getAgencia());
                                                            System.out.println(contaDados.getIdConta());
                                                            System.out.println(contaDados.getTipo());

                                                            String data = LocalDateTime.now().toString();
                                                            data = data.substring(0, 10);
                                                            System.out.println("\nData e hora da operacao: " + data);
                                                            System.out.println("Novo saldo: R$ " + SaldoResto);
                                                            poupanca.setSaldo("Saldo: " + SaldoResto);

                                                            try (FileWriter dadosEscreve = new FileWriter("src/dados.txt", false)) {
                                                                try (BufferedReader dadosNovosLer = new BufferedReader(new FileReader("src/dadosNovos.txt"))){
                                                                    String linha = dadosNovosLer.readLine();
                                                                    while (linha != null){

                                                                        dadosEscreve.write(linha + "\n");
                                                                        linha = dadosNovosLer.readLine();
                                                                    }
                                                                    dadosNovosLer.close();
                                                                    dadosEscreve.close();
                                                                    Files.deleteIfExists(Path.of("src/dadosNovos.txt"));
                                                                }
                                                                catch (IOException e){
                                                                    System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                                                }
                                                            }
                                                            catch (IOException e){
                                                                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                                            }
                                                        }

                                                    }
                                                }
                                                catch (IOException e){
                                                    System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                                }
                                            }
                                            catch (IOException e){
                                                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                            }
                                        }
                                    }
                                }
                            }
                        // SALARIO
                            if (verConta == 3){
                                menu.menuSalarioLoginImprime(salario);
                                menu.menuOperacoesSalarioImprime();
                                nn = input.nextInt();
                                if ((nn != 1) && (nn != 2) && (nn != 0)){
                                    System.out.println("Por favor digite uma opcao valida!");
                                }
                                else{
                                    if (nn == 1){
                                        String data = LocalDateTime.now().toString();
                                        String saldo = salario.getSaldo();
                                        data = data.substring(0, 10);
                                        saldo = saldo.substring(7);
                                        System.out.println("\nData e hora da operacao: " + data);
                                        System.out.println("\nSaldo: R$ " + saldo);
                                    }
                                    if (nn == 2){
                                        if (salario.getStatus().equals("Status: V")){
                                            double saque;
                                            String saqueStr;
                                            String saldoStr = salario.getSaldo();
                                            saldoStr = saldoStr.substring(7);
                                            double saldo = Double.parseDouble(saldoStr);
                                            System.out.println("\nSaldo atual: R$ " + saldo);
                                            System.out.print("\nDigite a quantia que deseja sacar: ");
                                            input.nextLine();
                                            saqueStr = input.nextLine();
                                            saqueStr = saqueStr.replaceFirst(",", ".");
                                            saque = Double.parseDouble(saqueStr);

                                            if (saque > saldo){
                                                System.out.println("Operacao cancelada. Saldo insuficiente.");
                                            }
                                            else {
                                                System.out.println("Saque bem sucedido.");
                                                String data = LocalDateTime.now().toString();
                                                data = data.substring(0, 10);
                                                System.out.println("\nData e hora da operacao: " + data);
                                                String novoSaldo = Double.toString(((saldo - saque) * 100) / 100);
                                                System.out.println("Novo saldo: R$ " + novoSaldo);

                                                salario.setStatus("Status: X");
                                                salario.setSaldo("Saldo: " + novoSaldo);

                                                String linha1 = null;
                                                try (BufferedReader dadosChecker = new BufferedReader(new FileReader("src/dados.txt"))) {
                                                    linha1 = dadosChecker.readLine();
                                                    if (linha1 != null){
                                                        File criaNovosDados = new File("src/dadosNovos.txt");
                                                        try {
                                                            FileWriter novosDados = new FileWriter("src/dadosNovos.txt", false);

                                                            while (linha1 != null){

                                                                if (linha1.contains(salario.getIdConta())){
                                                                    String[] partes = linha1.split("; ");
                                                                    for (String parte : partes) {
                                                                        if (parte.startsWith("Data: ")) {
                                                                            linha1 = linha1.replaceFirst(parte, ("Data: " + LocalDateTime.now().toString()));
                                                                        }
                                                                        if (parte.startsWith("Saldo: ")) {
                                                                            linha1 = linha1.replaceFirst(parte, (salario.getSaldo()));
                                                                        }
                                                                        if (parte.startsWith("Status: ")){
                                                                            linha1 = linha1.replaceFirst(parte, "Status: X");
                                                                        }
                                                                    }
                                                                    novosDados.write(linha1 + "\n");
                                                                }
                                                                else {
                                                                    novosDados.write(linha1 + "\n");
                                                                }

                                                                linha1 = dadosChecker.readLine();
                                                            }
                                                            dadosChecker.close();
                                                            novosDados.close();

                                                            try (FileWriter dadosEscreve = new FileWriter("src/dados.txt", false)) {
                                                                try (BufferedReader dadosNovosLer = new BufferedReader(new FileReader("src/dadosNovos.txt"))){
                                                                    String linha = dadosNovosLer.readLine();
                                                                    while (linha != null){
                                                                        dadosEscreve.write(linha + "\n");
                                                                        linha = dadosNovosLer.readLine();
                                                                    }
                                                                    dadosNovosLer.close();
                                                                    Files.deleteIfExists(Path.of("src/dadosNovos.txt"));
                                                                    dadosEscreve.close();
                                                                }
                                                                catch (IOException e){
                                                                    System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                                                }
                                                            }
                                                            catch (IOException e){
                                                                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                                            }
                                                        }
                                                        catch (IOException e){
                                                            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                                        }
                                                    }
                                                }
                                                catch (IOException e){
                                                    System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                                }
                                            }
                                        }
                                        else {
                                            System.out.println("Saque bloqueado. Aguarde ate que seu Status esteja aprovado novamente.");
                                        }
                                    }
                                }
                            }
                        }

                    }
                    else {
                        System.out.println("Voce precisa estar logado em uma conta para realizar operacoes bancarias.");
                    }
                }

            // OPCAO 4
                if (n == 4){
                    if (verLogin){
                        System.out.println("Opcao indisponivel. Voce ja esta logado em uma conta no momento!");
                    }
                    else {
                        System.out.print("Digite o ID para a realizacao do Login em uma conta: ");
                        input.nextLine();
                        String id = input.nextLine();

                        try (BufferedReader dadosLer = new BufferedReader(new FileReader("src/dados.txt"))) {
                            String linha;
                            boolean encontrado = false;
                            linha = dadosLer.readLine();

                            while (linha != null) {
                                if (linha.contains((" ID: " + id + "; "))) {
                                    encontrado = true;
                                    break;
                                }
                                linha = dadosLer.readLine();
                            }
                            dadosLer.close();

                            if (encontrado) {
                                String[] partes = linha.split("; ");
                                for (String parte : partes) {
                                    if (parte.startsWith("CPF: ")) {
                                        try (BufferedReader cadastrosLer = new BufferedReader(new FileReader("src/cadastros.txt"))) {
                                            String linhaCadastro = cadastrosLer.readLine();
                                            while (linhaCadastro != null) {
                                                if (linhaCadastro.contains(parte)) {

                                                    for (int i = 1; i<4; i++){
                                                        System.out.print("\nDigite a senha do CPF titular da conta para prosseguir: ");
                                                        String senha = input.nextLine();
                                                        if (linhaCadastro.contains(("Senha: " + senha + "; "))){
                                                            seuId = id;
                                                            if (linha.contains("; Tipo: Corrente; ")){
                                                                verConta = 1;
                                                                String[] partes2 = linha.split("; ");
                                                                for (String parte2 : partes2) {
                                                                    if (parte2.startsWith("CPF: ")) {corrent.setCpf(parte2);}
                                                                    if (parte2.startsWith("Agencia: ")) {corrent.setAgencia(parte2);}
                                                                    if (parte2.startsWith("Nome: ")) {corrent.setNome(parte2);}
                                                                    if (parte2.startsWith("ID: ")) {corrent.setIdConta(parte2);}
                                                                    if (parte2.startsWith("Tipo: ")) {corrent.setTipo(parte2);}
                                                                    if (parte2.startsWith("Saldo: ")) {corrent.setSaldo(parte2);}
                                                                }
                                                                System.out.println("Sucesso ao realizar login.");
                                                                verLogin = true;
                                                                break;
                                                            }
                                                            if (linha.contains("; Tipo: Poupanca; ")){
                                                                verConta = 2;
                                                                String[] partes2 = linha.split("; ");
                                                                for (String parte2 : partes2) {
                                                                    if (parte2.startsWith("CPF: ")) {poupanca.setCpf(parte2);}
                                                                    if (parte2.startsWith("Agencia: ")) {poupanca.setAgencia(parte2);}
                                                                    if (parte2.startsWith("Nome: ")) {poupanca.setNome(parte2);}
                                                                    if (parte2.startsWith("ID: ")) {poupanca.setIdConta(parte2);}
                                                                    if (parte2.startsWith("Tipo: ")) {poupanca.setTipo(parte2);}
                                                                    if (parte2.startsWith("Saldo: ")) {poupanca.setSaldo(parte2);}
                                                                }
                                                                System.out.println("Sucesso ao realizar login.");
                                                                verLogin = true;
                                                                break;
                                                            }
                                                            if (linha.contains("; Tipo: Salario; ")){
                                                                verConta = 3;
                                                                String[] partes2 = linha.split("; ");
                                                                for (String parte2 : partes2) {
                                                                    if (parte2.startsWith("CPF: ")) {salario.setCpf(parte2);}
                                                                    if (parte2.startsWith("Agencia: ")) {salario.setAgencia(parte2);}
                                                                    if (parte2.startsWith("Nome: ")) {salario.setNome(parte2);}
                                                                    if (parte2.startsWith("ID: ")) {salario.setIdConta(parte2);}
                                                                    if (parte2.startsWith("Tipo: ")) {salario.setTipo(parte2);}
                                                                    if (parte2.startsWith("Saldo: ")) {salario.setSaldo(parte2);}
                                                                    if (parte2.startsWith("Status: ")) {salario.setStatus(parte2);}
                                                                    if (parte2.startsWith("ID Empregador: ")) {salario.setEmpregador(parte2);}
                                                                }
                                                                System.out.println("Sucesso ao realizar login.");
                                                                verLogin = true;
                                                                break;
                                                            }
                                                        }
                                                        else {
                                                            System.out.println("Senha incorreta! Voce tem mais " + (3-i) + " tentativas.");
                                                        }
                                                    }
                                                    cadastrosLer.close();
                                                    break;
                                                }
                                                linhaCadastro = cadastrosLer.readLine();
                                            }
                                            break;
                                        }
                                        catch (IOException e){
                                            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                                        }
                                    }
                                }
                            }
                            else {System.out.println("ID de conta inexistente.");}
                        }
                        catch (IOException e){System.err.println("Erro ao ler o arquivo: " + e.getMessage());}
                    }
                }

            // OPCAO 5
                if (n == 5){
                    if (verLogin){
                        System.out.println("Saindo da conta.");
                        verLogin = false;
                    }
                    else {
                        System.out.println("Voce nao esta logado em nenhuma conta no momento.");
                    }
                }

            // OPCAO 0
                if (n == 0){
                    System.out.println("Saindo do programa.");
                }
            }
        }
    }
}