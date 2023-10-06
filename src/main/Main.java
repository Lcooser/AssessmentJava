package main;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cliente.Cliente;
import cliente.Endereco;
import cliente.PessoaFisica;
import cliente.PessoaJuridica;
import imovel.ContratoAluguel;
import imovel.Imovel;

public class Main {
	public static void main(String[] args) {
		List<Cliente> clientesCadastrados = new ArrayList<>();
		List<Imovel> imoveisCadastrados = new ArrayList<>();
		List<ContratoAluguel> contratos = new ArrayList<>();

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("Escolha uma opção:");
			System.out.println("1. Adicionar Cliente Pessoa Física");
			System.out.println("2. Adicionar Cliente Pessoa Jurídica");
			System.out.println("3. Adicionar Contrato de Aluguel");
			System.out.println("4. Buscar Cliente por CPF/CNPJ");
			System.out.println("5. Buscar Imóvel por Registro");
			System.out.println("6. Exportar Contratos para CSV");
			System.out.println("7. Registrar Imóvel");
			System.out.println("8. Sair");

			int opcao = scanner.nextInt();
			scanner.nextLine();

			switch (opcao) {
			case 1:
				adicionarClientePessoaFisica(clientesCadastrados, scanner);
				break;

			case 2:
				adicionarClientePessoaJuridica(clientesCadastrados, scanner);
				break;

			case 3:
				adicionarContratoAluguel(clientesCadastrados, imoveisCadastrados, contratos, scanner);
				break;

			case 4:
				Scanner cpfCnpjScanner = new Scanner(System.in);
				System.out.print("CPF (Pessoa Física) ou CNPJ (Pessoa Jurídica) do Cliente: ");
				String cpfCnpjBusca = cpfCnpjScanner.nextLine();
				buscarClientePorCpfCnpj(clientesCadastrados, cpfCnpjBusca);
				break;

			case 5:
				buscarImovelPorRegistro(imoveisCadastrados, scanner);
				break;

			case 6:
				exportarContratosParaCSV(contratos, scanner);
				break;

			case 7:
				registrarImovel(imoveisCadastrados, scanner);
				break;

			case 8:
				sairDoPrograma(scanner);
				break;

			default:
				System.out.println("Opção inválida. Escolha uma opção válida.");
			}
		}
	}

	private static void adicionarClientePessoaFisica(List<Cliente> clientesCadastrados, Scanner scanner) {
		try {
			System.out.print("Nome do Cliente Pessoa Física: ");
			String nomeClientePF = scanner.nextLine();
			System.out.print("Telefone do Cliente (com DDD): ");
			String telefoneClientePF = scanner.nextLine();
			System.out.print("Email do Cliente: ");
			String emailClientePF = scanner.nextLine();
			System.out.print("CPF do Cliente: ");
			String cpfCliente = scanner.nextLine();

			if (isCpfValid(cpfCliente)) {
				clientesCadastrados.add(new PessoaFisica(nomeClientePF, telefoneClientePF, emailClientePF, cpfCliente));
				System.out.println("Cliente Pessoa Física cadastrado com sucesso!");
			} else {
				System.out.println("Erro ao cadastrar cliente: CPF inválido.");
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
		}
	}

	private static boolean isCpfValid(String cpf) {

		cpf = cpf.replaceAll("[^0-9]", "");

		if (cpf.length() != 11) {
			return false;
		}

		if (cpf.matches("(\\d)\\1{10}")) {
			return false; // Todos os digitos sao iguais
		}

		// Calcula e verifica os digitos do cpf
		int[] digits = new int[11];
		for (int i = 0; i < 11; i++) {
			digits[i] = cpf.charAt(i) - '0';
		}

		int sum1 = 0;
		for (int i = 0; i < 9; i++) {
			sum1 += digits[i] * (10 - i);
		}

		int remainder1 = sum1 % 11;
		int checkDigit1 = (remainder1 < 2) ? 0 : (11 - remainder1);

		if (digits[9] != checkDigit1) {
			return false;
		}

		int sum2 = 0;
		for (int i = 0; i < 10; i++) {
			sum2 += digits[i] * (11 - i);
		}

		int remainder2 = sum2 % 11;
		int checkDigit2 = (remainder2 < 2) ? 0 : (11 - remainder2);

		return digits[10] == checkDigit2;
	}

	private static void adicionarClientePessoaJuridica(List<Cliente> clientesCadastrados, Scanner scanner) {
		try {
			System.out.print("Nome do Cliente Pessoa Jurídica: ");
			String nomeClientePJ = scanner.nextLine();
			System.out.print("Telefone do Cliente Pessoa Jurídica (com DDD): ");
			String telefoneClientePJ = scanner.nextLine();
			System.out.print("Email do Cliente Pessoa Jurídica: ");
			String emailClientePJ = scanner.nextLine();
			System.out.print("CNPJ do Cliente Pessoa Jurídica: ");
			String cnpjCliente = scanner.nextLine();

			// Validação do CNPJ (exemplo simples)
			if (!validarCnpj(cnpjCliente)) {
				System.out.println("Erro ao cadastrar cliente: CNPJ inválido.");
				return;
			}

			PessoaJuridica clientePJ = new PessoaJuridica(nomeClientePJ, telefoneClientePJ, emailClientePJ,
					cnpjCliente);

			clientesCadastrados.add(clientePJ);
			System.out.println("Cliente Pessoa Jurídica cadastrado com sucesso!");
		} catch (IllegalArgumentException e) {
			System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
		}
	}

	private static boolean validarCnpj(String cnpj) {

		cnpj = cnpj.replaceAll("[^0-9]", "");

		if (cnpj.length() != 14) {
			return false;
		}

		int soma = 0;
		for (int i = 0; i < 12; i++) {
			int digit = Character.getNumericValue(cnpj.charAt(i));
			soma += digit * (13 - i);
		}
		int remainder = soma % 11;
		int digitoVerificador1 = (remainder < 2) ? 0 : (11 - remainder);

		if (Character.getNumericValue(cnpj.charAt(12)) != digitoVerificador1) {
			return false;
		}

		soma = 0;
		for (int i = 0; i < 13; i++) {
			int digit = Character.getNumericValue(cnpj.charAt(i));
			soma += digit * (14 - i);
		}
		remainder = soma % 11;
		int digitoVerificador2 = (remainder < 2) ? 0 : (11 - remainder);

		return Character.getNumericValue(cnpj.charAt(13)) == digitoVerificador2;
	}

	private static boolean temSobreposicaoDeContrato(Imovel imovel, LocalDate dataInicioNovoContrato,
			LocalDate dataTerminoNovoContrato) {
		for (ContratoAluguel contrato : imovel.getListaContratos()) {
			LocalDate dataInicioExistente = contrato.getDataInicio();
			LocalDate dataTerminoExistente = contrato.getDataTermino();

			// Verifique se as datas do novo contrato se sobrepõem com um contrato existente
			if (dataInicioNovoContrato.isBefore(dataTerminoExistente)
					&& dataTerminoNovoContrato.isAfter(dataInicioExistente)) {
				return true; // Sobreposição detectada
			}
		}
		return false; // Não há sobreposição
	}

	private static void adicionarContratoAluguel(List<Cliente> clientesCadastrados, List<Imovel> imoveisCadastrados,
			List<ContratoAluguel> contratos, Scanner scanner) {
		try {
			System.out.print("Registro do Imóvel: ");
			String registroImovelContrato = scanner.nextLine();
			System.out.print("CPF/CNPJ do Cliente: ");
			String cpfCnpjCliente = scanner.nextLine();
			System.out.print("Data de Início do Contrato (AAAA-MM-DD): ");
			String dataInicioContratoStr = scanner.nextLine();
			LocalDate dataInicioContrato = LocalDate.parse(dataInicioContratoStr);
			System.out.print("Data de Término do Contrato (AAAA-MM-DD): ");
			String dataTerminoContratoStr = scanner.nextLine();
			LocalDate dataTerminoContrato = LocalDate.parse(dataTerminoContratoStr);

			Cliente clienteEncontrado = buscarClientePorCpfCnpj(clientesCadastrados, cpfCnpjCliente);
			Imovel imovelEncontrado = buscarImovelPorRegistro(imoveisCadastrados, registroImovelContrato);

			if (clienteEncontrado != null && imovelEncontrado != null) {
				// Verifique a sobreposição de contratos aqui antes de adicionar um novo
				// contrato
				boolean sobreposicao = temSobreposicaoDeContrato(imovelEncontrado, dataInicioContrato,
						dataTerminoContrato);
				if (!sobreposicao) {
					// Adicione o novo contrato se não houver sobreposição
					ContratoAluguel novoContrato = new ContratoAluguel(imovelEncontrado, clienteEncontrado,
							dataInicioContrato, dataTerminoContrato);
					contratos.add(novoContrato);
					clienteEncontrado.adicionarContrato(novoContrato);
					System.out.println("Contrato de aluguel adicionado com sucesso!");
				} else {
					System.out.println("Sobreposição de contrato detectada. Não é possível criar o contrato.");
				}
			} else {
				System.out.println(
						"Cliente ou imóvel não encontrado. Certifique-se de que CPF/CNPJ e registro do imóvel estejam corretos.");
			}
		} catch (DateTimeParseException e) {
			System.out.println("Erro ao adicionar contrato de aluguel: Formato de data inválido.");
		} catch (IllegalArgumentException e) {
			System.out.println("Erro ao adicionar contrato de aluguel: " + e.getMessage());
		}
	}

	private static Cliente buscarClientePorCpfCnpj(List<Cliente> clientesCadastrados, String cpfCnpjCliente) {
		for (Cliente cliente : clientesCadastrados) {
			if (cliente instanceof PessoaFisica) {
				PessoaFisica pessoaFisica = (PessoaFisica) cliente;
				if (pessoaFisica.getCpf().equals(cpfCnpjCliente)) {
					System.out.println("Cliente Pessoa Física encontrado com sucesso!");
					exibirInformacoesCliente(cliente);
					return cliente;
				}
			} else if (cliente instanceof PessoaJuridica) {
				PessoaJuridica pessoaJuridica = (PessoaJuridica) cliente;
				if (pessoaJuridica.getCnpj().equals(cpfCnpjCliente)) {
					System.out.println("Cliente Pessoa Jurídica encontrado com sucesso!");
					exibirInformacoesCliente(cliente);
					return cliente;
				}
			}
		}

		System.out.println("Cliente não encontrado.");
		return null;
	}

	private static void exibirInformacoesCliente(Cliente cliente) {
		// Exiba as informações do cliente aqui
		System.out.println("Nome: " + cliente.getNome());
		System.out.println("Telefone: " + cliente.getTelefone());
		System.out.println("Email: " + cliente.getEmail());

		List<ContratoAluguel> contratosAtivos = cliente.getContratosAtivos();
		double valorTotalContratos = cliente.calcularValorTotalContratos();

		System.out.println("Número de Contratos Ativos: " + contratosAtivos.size());
		System.out.println("Valor Total dos Contratos: " + valorTotalContratos);

		if (!contratosAtivos.isEmpty()) {
			System.out.println("Contratos Ativos:");
			for (ContratoAluguel contrato : contratosAtivos) {
				System.out.println("  - Registro do Imóvel: " + contrato.getImovel().getRegistro());
				System.out.println("  - Data de Início: " + contrato.getDataInicio());
				System.out.println("  - Data de Término: " + contrato.getDataTermino());
				System.out.println("  - Valor do Contrato: " + contrato.calcularValorContrato());
			}
		} else {
			System.out.println("O cliente não possui contratos ativos.");
		}
	}

	private static Imovel buscarImovelPorRegistro(List<Imovel> imoveisCadastrados, Scanner scanner) {
		System.out.print("Registro do Imóvel: ");
		String registroBusca = scanner.nextLine();

		for (Imovel imovel : imoveisCadastrados) {
			if (imovel.getRegistro().equals(registroBusca)) {
				System.out.println("Imóvel encontrado com sucesso!");
				exibirInformacoesImovel(imovel);
				return imovel;
			}
		}

		System.out.println("Imóvel não encontrado.");
		return null;
	}

	private static void exibirInformacoesImovel(Imovel imovel) {
		System.out.println("Informações do Imóvel:");
		System.out.println("Registro: " + imovel.getRegistro());
		System.out.println("Nome: " + imovel.getNome());
		System.out.println("Valor do Aluguel Mensal: " + imovel.getValorAluguel());
		// Adicione outras informações relevantes do imóvel aqui
	}

	private static Imovel buscarImovelPorRegistro(List<Imovel> imoveisCadastrados, String registroBusca) {
		for (Imovel imovel : imoveisCadastrados) {
			if (imovel.getRegistro().equals(registroBusca)) {
				exibirInformacoesImovel(imovel);
				return imovel;
			}
		}
		return null;
	}

	private static void exportarContratosParaCSV(List<ContratoAluguel> contratos, Scanner scanner) {
		System.out.print("Nome do arquivo CSV: ");
		String nomeArquivoCSV = scanner.nextLine();

		try (FileWriter writer = new FileWriter(nomeArquivoCSV)) {
			writer.write(
					"Registro do Imóvel, Nome do Imóvel, Endereço do Imóvel, Valor do Aluguel Mensal, Nome do Cliente, Telefone do Cliente, Data de Início, Data de Término, Contrato Vencido\n");

			for (ContratoAluguel contrato : contratos) {
				String csvLine = String.format("\"%s\", \"%s\", \"%s\", %.2f, \"%s\", \"%s\", %s, %s, %s\n",
						contrato.getImovel().getRegistro(), contrato.getImovel().getNome(),
						contrato.getImovel().getEndereco().toString(), contrato.getImovel().getValorAluguel(),
						contrato.getCliente().getNome(), contrato.getCliente().getTelefone(),
						contrato.getDataInicio().toString(), contrato.getDataTermino().toString(),
						contrato.contratoVencido());
				writer.write(csvLine);
			}

			System.out.println("Contratos de aluguel exportados para " + nomeArquivoCSV);
		} catch (IOException e) {
			System.err.println("Erro ao exportar contratos de aluguel para CSV: " + e.getMessage());
		}
	}

	private static void registrarImovel(List<Imovel> imoveisCadastrados, Scanner scanner) {
		System.out.println("Cadastro de Imóvel:");

		System.out.print("Registro do Imóvel: ");
		String registroImovel = scanner.nextLine();

		System.out.print("Nome do Imóvel: ");
		String nomeImovel = scanner.nextLine();

		System.out.print("Valor do Aluguel Mensal: ");
		float valorAluguelImovel = scanner.nextFloat();
		scanner.nextLine();

		System.out.print("Endereço do Imóvel - Logradouro: ");
		String logradouroImovel = scanner.nextLine();
		System.out.print("Endereço do Imóvel - Tipo de Logradouro: ");
		String tipoLogradouroImovel = scanner.nextLine();
		System.out.print("Endereço do Imóvel - Número: ");
		String numeroImovel = scanner.nextLine();
		System.out.print("Endereço do Imóvel - Complemento: ");
		String complementoImovel = scanner.nextLine();
		System.out.print("Endereço do Imóvel - Cidade: ");
		String cidadeImovel = scanner.nextLine();
		System.out.print("Endereço do Imóvel - Estado: ");
		String estadoImovel = scanner.nextLine();
		System.out.print("Endereço do Imóvel - CEP: ");
		String cepImovel = scanner.nextLine();

		Endereco enderecoImovel = new Endereco(logradouroImovel, tipoLogradouroImovel, numeroImovel, complementoImovel,
				cidadeImovel, estadoImovel, cepImovel);

		System.out.print("Tipo do Imóvel (Residencial/Comercial): ");
		String tipoImovelStr = scanner.nextLine();
		TipoImovelEnum tipoImovel = TipoImovelEnum.RESIDENCIAL;

		if ("Comercial".equalsIgnoreCase(tipoImovelStr)) {
			tipoImovel = TipoImovelEnum.COMERCIAL;
		}

		Imovel novoImovel = new Imovel(registroImovel, nomeImovel, valorAluguelImovel, enderecoImovel, tipoImovel,
				new ArrayList<>());
		imoveisCadastrados.add(novoImovel);

		System.out.println("Imóvel cadastrado com sucesso!");
	}

	private static void sairDoPrograma(Scanner scanner) {
		scanner.close();
		System.out.println("Saindo do programa.");
		System.exit(0);
	}

}
