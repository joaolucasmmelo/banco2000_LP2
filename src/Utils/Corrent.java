package Utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Corrent extends Conta {
    public Corrent(String cpf, String agencia, String nome, String idConta, String tipo, String saldo) {
        super(cpf, agencia, nome, idConta, tipo, saldo);
    }

    public void contaEscreve(){
        super.contaEscreve();
    }

// Getters and Setters
    public String getNome() {
        return super.getNome();
    }
    public void setNome(String nome) {
        super.setNome(nome);
    }

    public String getCpf() {
        return super.getCpf();
    }
    public void setCpf(String cpf) {
        super.setCpf(cpf);
    }

    public String getIdConta(){
        return super.getIdConta();
    }
    public void setIdConta(String idConta) {
        super.setIdConta(idConta);
    }

    public String getAgencia() {
        return super.getAgencia();
    }
    public void setAgencia(String agencia) {
        super.setAgencia(agencia);
    }

    public String getSaldo() {
        return super.getSaldo();
    }
    public void setSaldo(String saldo) {
        super.setSaldo(saldo);
    }

    public String getTipo() {
        return super.getTipo();
    }
    public void setTipo(String tipo) {
        super.setTipo(tipo);
    }
}