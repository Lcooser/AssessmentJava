package imovel;

import cliente.Cliente;


import java.time.LocalDate;
import java.time.Period;

public class ContratoAluguel {
	private Imovel imovel;
	private Cliente cliente;
	private LocalDate dataInicio;
	private LocalDate dataTermino;
	

	public ContratoAluguel(Imovel imovel, Cliente cliente, LocalDate dataInicio, LocalDate dataTermino) {
		// Validações de entrada
		if (imovel == null) {
			throw new IllegalArgumentException("Imóvel do contrato não pode ser nulo.");
		}

		if (cliente == null) {
			throw new IllegalArgumentException("Cliente do contrato não pode ser nulo.");
		}

		if (dataInicio == null || dataTermino == null) {
			throw new IllegalArgumentException("As datas de início e término não podem ser nulas.");
		}

		if (dataTermino.isBefore(dataInicio)) {
			throw new IllegalArgumentException("A data de término não pode ser anterior à data de início.");
		}

		LocalDate dataAtual = LocalDate.now();

		if (dataInicio.isBefore(dataAtual) || dataTermino.isBefore(dataAtual)) {
			throw new IllegalArgumentException("As datas não podem ser no passado.");
		}

		this.imovel = imovel;
		this.cliente = cliente;
		this.dataInicio = dataInicio;
		this.dataTermino = dataTermino;
	}

	public double calcularValorContrato() {
	    int meses = Period.between(dataInicio, dataTermino).getMonths() + 1; // +1 para incluir o mês de início
	    
	    double valorMensal = imovel.getValorAluguel();

	    double valorTotal = valorMensal * meses;

	    return valorTotal;
	}


	public boolean contratoVencido() {
		LocalDate dataAtual = LocalDate.now();
		return dataTermino.isBefore(dataAtual);
	}

	@Override
	public String toString() {
		String contratoVencidoStr = contratoVencido() ? "Sim" : "Não";

		return String.format(
				"Registro: %s, Nome: %s, Endereço: %s, Valor Aluguel: %.2f, Cliente: %s, Telefone: %s, Início: %s, Término: %s, Vencido: %s\n",
				getImovel().getRegistro(), getImovel().getNome(), getImovel().getEndereco().toString(),
				getImovel().getValorAluguel(), getCliente().getNome(), getCliente().getTelefone(),
				getDataInicio().toString(), getDataTermino().toString(), contratoVencidoStr);
	}

	public Imovel getImovel() {
		return imovel;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public LocalDate getDataTermino() {
		return dataTermino;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public boolean verificaConflitoDeDatas() {
		for (ContratoAluguel contrato : imovel.getListaContratos()) {
			if (!this.equals(contrato) && !dataTermino.isBefore(contrato.getDataInicio())
					&& !dataInicio.isAfter(contrato.getDataTermino())) {

				return true;
			}
		}
		return false;
	}
}
