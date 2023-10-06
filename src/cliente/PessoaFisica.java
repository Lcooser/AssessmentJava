package cliente;

import java.util.ArrayList;
import java.util.List;

import imovel.ContratoAluguel;

public class PessoaFisica extends Cliente {
    private String cpf;

    public PessoaFisica(String nome, String telefone, String email, String cpf) {
        super(nome, telefone, email, new ArrayList<>()); // Inicialize a lista de contratos vazia
        setCpf(cpf);
    }

    public float calcularValorTotalContratos() {
        List<ContratoAluguel> contratosAtivos = getContratosAtivos();
        double valorTotal = contratosAtivos.stream().mapToDouble(ContratoAluguel::calcularValorContrato).sum();

        // Aplicar descontos com base no número de contratos ativos
        int numeroContratosAtivos = contratosAtivos.size();
        if (numeroContratosAtivos >= 3 && numeroContratosAtivos < 5) {
            valorTotal *= 0.95; // Aplica desconto de 5%
        } else if (numeroContratosAtivos >= 5) {
            valorTotal *= 0.9; // Aplica desconto de 10%
        }

        return (float) valorTotal;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf != null && !cpf.isEmpty()) {
            this.cpf = cpf;
        } else {
            throw new IllegalArgumentException("CPF do cliente não pode ser nulo ou vazio.");
        }
    }

    @Override
    protected String getCpfOuCnpj() {
        return getCpf(); // Retorna o CPF da pessoa física
    }

    @Override
    public void adicionarContrato(ContratoAluguel contrato) {
        if (getListaContratos() == null) {
            setListaContratos(new ArrayList<>());
        }
        super.adicionarContrato(contrato);
        contrato.setCliente(this);
    }
}
