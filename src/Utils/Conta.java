package Utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Conta {

    private String cpf;
    private String agencia;
    private String nome;
    private String idConta;
    private String tipo;
    private String saldo;

    public Conta(String cpf, String agencia, String nome, String  idConta, String tipo, String saldo) {
        this.cpf = cpf;
        this.agencia = agencia;
        this.nome = nome;
        this.idConta = idConta;
        this.tipo = tipo;
        this.saldo = saldo;
    }

    public Conta(String cpf, String agencia, String nome, String  idConta, String tipo) {
        this.cpf = cpf;
        this.agencia = agencia;
        this.nome = nome;
        this.idConta = idConta;
        this.tipo = tipo;
    }

    public void contaEscreve(){
        try {
            String cpfEscreve = ("CPF: " + cpf + "; ");
            String agenciaEscreve = ("Agencia: " + agencia + "; ");
            String nomeEscreve = (nome + "; ");
            String idEscreve = ("ID: " + idConta + "; ");
            String tipoEscreve = ("Tipo: " + tipo + "; ");
            String saldoEscreve = ("Saldo: " + saldo + "; ");

            String dataCriacao = String.valueOf(LocalDateTime.now());
            String dataCriacaoEscreve = ("Data: " + dataCriacao + "; ");

            FileWriter dados = new FileWriter("src/dados.txt", true);
            dados.write(cpfEscreve + agenciaEscreve + nomeEscreve + idEscreve + tipoEscreve + saldoEscreve + dataCriacaoEscreve + "\n");
            System.out.println("Sucesso ao criar a conta.");
            dados.close();
        }
        catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }


// Getters e Setters
    public String getNome() {
        return this.nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return this.cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getIdConta(){
        return this.idConta;
    }
    public void setIdConta(String idConta) {
        this.idConta = idConta;
    }

    public String getAgencia() {
        return agencia;
    }
    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getSaldo() {
        return this.saldo;
    }
    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getTipo() {
        return this.tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}