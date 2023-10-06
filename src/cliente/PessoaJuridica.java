package cliente;

import java.util.ArrayList;
import java.util.List;

import imovel.ContratoAluguel;

public class PessoaJuridica extends Cliente {
    private String cnpj;

    public PessoaJuridica(String nome, String telefone, String email, String cnpj) {
        super(nome, telefone, email, new ArrayList<>()); 
        setCnpj(cnpj);
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        if (cnpj != null && !cnpj.isEmpty()) {
            this.cnpj = cnpj;
        } else {
            throw new IllegalArgumentException("CNPJ do cliente não pode ser nulo ou vazio.");
        }
    }

    @Override
    protected String getCpfOuCnpj() {
        return getCnpj(); 
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
