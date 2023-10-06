package main;

import imovel.ContratoAluguel;


import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class CSVExporter {

    public static void exportContractsToCSV(List<ContratoAluguel> contratos, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
          
            writer.append(
                    "Registro Imóvel, Nome Imóvel, Endereço, Valor Aluguel Mensal, Nome Cliente, Telefone Cliente, Data Início, Data Término, Contrato Vencido, Valor Total do Contrato");
            writer.append("\n");

            // Formato para exibir o valor com duas casas decimais
            DecimalFormat decimalFormat = new DecimalFormat("0.00");

            // Escrever os dados dos contratos no arquivo CSV
            for (ContratoAluguel contrato : contratos) {
                writer.append(contrato.getImovel().getRegistro());
                writer.append(",");
                writer.append(contrato.getImovel().getNome());
                writer.append(",");
                writer.append(contrato.getImovel().getEndereco().toString());
                writer.append(",");
                writer.append(String.valueOf(contrato.getImovel().getValorAluguel()));
                writer.append(",");
                writer.append(contrato.getCliente().getNome());
                writer.append(",");
                writer.append(contrato.getCliente().getTelefone());
                writer.append(",");
                writer.append(contrato.getDataInicio().toString());
                writer.append(",");
                writer.append(contrato.getDataTermino().toString());
                writer.append(",");
                writer.append(String.valueOf(contrato.contratoVencido()));
                writer.append(",");
                double valorContrato = contrato.calcularValorContrato();
                writer.append(decimalFormat.format(valorContrato)); 
                writer.append("\n");
            }

            
            double valorTotalContratos = contratos.stream().mapToDouble(ContratoAluguel::calcularValorContrato).sum();
            writer.append(",,,," + ",,,,Valor Total de Todos os Contratos," + decimalFormat.format(valorTotalContratos));
            writer.append("\n");

            System.out.println("Contratos exportados com sucesso para o arquivo " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao exportar contratos para o arquivo CSV.");
        }
    }
}
