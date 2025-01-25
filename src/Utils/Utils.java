package Utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class Utils {
    public int verificaSaldo(){
        boolean verStatusUtils = false;
        Corrent corrent = new Corrent(null, null, null, null, null, null);
        Poupanca poupanca = new Poupanca(null, null, null, null, null, null);

        String linha1 = null;
        try (BufferedReader dadosChecker = new BufferedReader(new FileReader("src/dados.txt"))) {
            linha1 = dadosChecker.readLine();
            if (linha1 != null){
                File criaNovosDados = new File("src/dadosNovos.txt");
                try {
                    FileWriter novosDados = new FileWriter("src/dadosNovos.txt", false);

                    while (linha1 != null){
                        // CORRENTE
                        if (linha1.contains("; Tipo: Corrente; ")){
                            String[] partes = linha1.split("; ");
                            for (String parte : partes) {
                                if (parte.startsWith("Data: ")) {
                                    String dataStr = parte.replaceFirst("Data: ", "");
                                    String newLinha = null;

                                    if (LocalDateTime.now().isAfter(LocalDateTime.parse(dataStr).plusMinutes(5))) {
                                        String[] partes2 = linha1.split("; ");
                                        for (String parte2 : partes2) {
                                            if (parte2.startsWith("Saldo: ")) {
                                                String saldoStr = parte2.replaceFirst("Saldo: ", "");
                                                String novoSaldo = Double.toString(Math.round((Double.parseDouble(saldoStr) - 0.01) * 100.0) / 100.0);

                                                newLinha = linha1.replaceFirst(parte, ("Data: " + LocalDateTime.now().toString()));
                                                newLinha = newLinha.replaceFirst(parte2, ("Saldo: " + novoSaldo));

                                                novosDados.write(newLinha + "\n");
                                            }
                                        }
                                    }
                                    else {
                                        novosDados.write(linha1 + "\n");
                                    }
                                }
                            }
                        }
                        //  POUPANCA
                        if (linha1.contains("; Tipo: Poupanca; ")){
                            String[] partes = linha1.split("; ");
                            for (String parte : partes) {
                                if (parte.startsWith("Data: ")) {
                                    String dataStr = parte.replaceFirst("Data: ", "");
                                    String newLinha = null;

                                    if (LocalDateTime.now().isAfter(LocalDateTime.parse(dataStr).plusMinutes(5))) {
                                        String[] partes2 = linha1.split("; ");
                                        for (String parte2 : partes2) {
                                            if (parte2.startsWith("Saldo: ")) {
                                                String saldoStr = parte2.replaceFirst("Saldo: ", "");
                                                String novoSaldo = Double.toString(Math.round((Double.parseDouble(saldoStr) * 1.01) * 100.0) / 100.0);

                                                newLinha = linha1.replaceFirst(parte, ("Data: " + LocalDateTime.now().toString()));
                                                newLinha = newLinha.replaceFirst(parte2, ("Saldo: " + novoSaldo));
                                                novosDados.write(newLinha + "\n");
                                            }
                                        }
                                    }
                                    else {
                                        novosDados.write(linha1 + "\n");
                                    }
                                }
                            }
                        }
                        //  SALARIO
                        if (linha1.contains("; Tipo: Salario; ")){
                            if (linha1.contains("; Status: V; ")){
                                novosDados.write(linha1 + "\n");
                            }
                            else {
                                String[] partes = linha1.split("; ");
                                for (String parte : partes) {
                                    if (parte.startsWith("Data: ")) {
                                        String dataStr = parte.replaceFirst("Data: ", "");
                                        String newLinha = null;

                                        if (LocalDateTime.now().isAfter(LocalDateTime.parse(dataStr).plusMinutes(5))) {
                                            Salario salario = new Salario(null, null, null, null, null, null, null, null);

                                            String[] partes2 = linha1.split("; ");
                                            for (String parte2 : partes2) {
                                                if (parte2.startsWith("Status: ")) {

                                                    verStatusUtils = true;
                                                    newLinha = linha1.replaceFirst(parte, ("Data: " + LocalDateTime.now().toString()));
                                                    newLinha = newLinha.replaceFirst(parte2, ("Status: V"));
                                                    novosDados.write(newLinha + "\n");
                                                }
                                            }
                                        }
                                        else {
                                            novosDados.write(linha1 + "\n");
                                        }
                                    }
                                }
                            }
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
        if (verStatusUtils){
            return 1;
        }
        return 0;
    }
}
