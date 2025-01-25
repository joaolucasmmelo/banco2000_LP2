package Utils;

public class Poupanca extends Conta {
    public Poupanca(String cpf, String agencia, String nome, String idConta, String tipo, String saldo) {
        super(cpf, agencia, nome, idConta, tipo, saldo);
    }

    public void contaEscreve(){
        super.contaEscreve();
    }

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
