package cliente;

import java.util.ArrayList;
import java.util.List;

import excpetion.EmailNuloOuVazioException;
import excpetion.NomeNuloOuVazioException;
import excpetion.TelefoneNuloOuVazioException;
import imovel.ContratoAluguel;
import main.Contabil;

public class Cliente implements Contabil {
	private String nome;
	private String telefone;
	private String email;
	private List<ContratoAluguel> listaContratos;

	public Cliente(String nome, String telefone, String email, List<ContratoAluguel> listaContratos) {
		setNome(nome);
		setTelefone(telefone);
		setEmail(email);
		setListaContratos(listaContratos);
	}

	protected String getCpfOuCnpj() {
		return null;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (nome != null && !nome.isEmpty()) {
			this.nome = nome;
		} else {
			throw new NomeNuloOuVazioException("Nome do cliente não pode ser nulo ou vazio.");
		}
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		if (telefone != null && !telefone.isEmpty()) {
			this.telefone = telefone;
		} else {
			throw new TelefoneNuloOuVazioException("Telefone do cliente não pode ser nulo ou vazio.");
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email != null && !email.isEmpty()) {
			this.email = email;
		} else {
			throw new EmailNuloOuVazioException("Email do cliente não pode ser nulo ou vazio.");
		}
	}

	public List<ContratoAluguel> getListaContratos() {
		return listaContratos;
	}

	public void setListaContratos(List<ContratoAluguel> listaContratos) {
		this.listaContratos = listaContratos;
	}

	public void adicionarContrato(ContratoAluguel contrato) {
		if (contrato != null) {
			listaContratos.add(contrato);
		}
	}

	public float calcularDesconto() {
		int numContratos = getListaContratos().size();
		if (numContratos >= 5) {
			return (float) 0.10; // 10% de desconto
		} else if (numContratos >= 3) {
			return (float) 0.05; // 5% de desconto
		}
		return (float) 0.0; // Sem desconto
	}

	@Override
	public float calcularValorTotalContratos() {
		double valorTotal = 0.0;
		double desconto = calcularDesconto();

		for (ContratoAluguel contrato : getContratosAtivos()) {
			if (!contrato.contratoVencido()) {
				valorTotal += contrato.calcularValorContrato();
			}
		}

		return (float) (valorTotal * (1 - desconto)); // Aplicar o desconto
	}

	@Override
	public String toString() {
		StringBuilder info = new StringBuilder();
		info.append("Nome: ").append(getNome()).append("\n");
		info.append("Telefone: ").append(getTelefone()).append("\n");
		info.append("Email: ").append(getEmail()).append("\n");

		if (getListaContratos() != null && !getListaContratos().isEmpty()) {
			info.append("Contratos Ativos:\n");
			for (ContratoAluguel contrato : getListaContratos()) {
				if (!contrato.contratoVencido()) {
					info.append("  - ").append(contrato.toString()).append("\n");
				}
			}
		} else {
			info.append("Não há contratos ativos.\n");
		}

		return info.toString();
	}

	public List<ContratoAluguel> getContratosAtivos() {
		List<ContratoAluguel> contratosAtivos = new ArrayList<>();

		for (ContratoAluguel contrato : listaContratos) {
			if (!contrato.contratoVencido()) {
				contratosAtivos.add(contrato);
			}
		}

		return contratosAtivos;
	}
}
