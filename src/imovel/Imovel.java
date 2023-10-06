package imovel;

import cliente.Endereco;
import excpetion.EnderecoNuloOuVazioException;
import excpetion.NomeNuloOuVazioException;
import excpetion.RegistroNuloOuVazioException;
import excpetion.TipoImovelNuloOuVazioException;
import excpetion.ValorAluguelInvalidoException;
import main.TipoImovelEnum;

import java.time.LocalDate;
import java.util.ArrayList;

public class Imovel {
	private String registro;
	private String nome;
	private float valorAluguel;
	private Endereco endereco;
	private TipoImovelEnum tipo;
	private ArrayList<ContratoAluguel> listaContratos;

	public Imovel(String registro, String nome, float valorAluguel, Endereco endereco, TipoImovelEnum tipo,
			ArrayList<ContratoAluguel> listaContratos) {
		setRegistro(registro);
		setNome(nome);
		setValorAluguel(valorAluguel);
		setEndereco(endereco);
		setTipo(tipo);
		setListaContratos(listaContratos);
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		if (registro != null && !registro.isEmpty()) {
			this.registro = registro;
		} else {
			throw new RegistroNuloOuVazioException("Registro do imóvel não pode ser nulo ou vazio.");
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (nome != null && !nome.isEmpty()) {
			this.nome = nome;
		} else {
			throw new NomeNuloOuVazioException("Nome do imóvel não pode ser nulo ou vazio.");
		}
	}

	public float getValorAluguel() {
		return valorAluguel;
	}

	public void setValorAluguel(float valorAluguel) {
		if (valorAluguel >= 0) {
			this.valorAluguel = valorAluguel;
		} else {
			throw new ValorAluguelInvalidoException("Valor do aluguel não pode ser menor do que zero.");
		}
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		if (endereco != null) {
			this.endereco = endereco;
		} else {
			throw new EnderecoNuloOuVazioException("Endereço do imóvel não pode ser nulo.");
		}
	}

	public TipoImovelEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoImovelEnum tipo) {
		if (tipo != null) {
			this.tipo = tipo;
		} else {
			throw new TipoImovelNuloOuVazioException("Tipo do imóvel não pode ser nulo.");
		}
	}

	public ArrayList<ContratoAluguel> getListaContratos() {
		return listaContratos;
	}

	public void setListaContratos(ArrayList<ContratoAluguel> listaContratos) {
		this.listaContratos = listaContratos;
	}

	public boolean possuiSobreposicaoDeContrato(LocalDate dataInicio, LocalDate dataTermino) {
		for (ContratoAluguel contrato : getListaContratos()) {
			if (!contrato.contratoVencido()) {
				LocalDate inicioContrato = contrato.getDataInicio();
				LocalDate terminoContrato = contrato.getDataTermino();

				if ((dataInicio.isEqual(inicioContrato) || dataInicio.isAfter(inicioContrato))
						&& (dataInicio.isEqual(terminoContrato) || dataInicio.isBefore(terminoContrato))) {
					return true;
				}
				if ((dataTermino.isEqual(inicioContrato) || dataTermino.isAfter(inicioContrato))
						&& (dataTermino.isEqual(terminoContrato) || dataTermino.isBefore(terminoContrato))) {
					return true;
				}
			}
		}
		return false;
	}
}
