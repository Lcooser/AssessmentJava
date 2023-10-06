package cliente;

public class Endereco {
    private String logradouro;
    private String tipoLogradouro;
    private String numero;
    private String complemento;
    private String cidade;
    private String estado;
    private String cep;

    public Endereco(String logradouro, String tipoLogradouro, String numero, String complemento, String cidade,
                    String estado, String cep) {
        setLogradouro(logradouro);
        setTipoLogradouro(tipoLogradouro);
        setNumero(numero);
        this.complemento = complemento;
        setCidade(cidade);
        setEstado(estado);
        setCep(cep);
    }

    private void validarCampoNaoNuloOuVazio(String valor, String mensagem) {
        if (valor == null || valor.isEmpty()) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        validarCampoNaoNuloOuVazio(logradouro, "Logradouro do endereço não pode ser nulo ou vazio.");
        this.logradouro = logradouro;
    }

    public String getTipoLogradouro() {
        return tipoLogradouro;
    }

    public void setTipoLogradouro(String tipoLogradouro) {
        validarCampoNaoNuloOuVazio(tipoLogradouro, "Tipo de logradouro do endereço não pode ser nulo ou vazio.");
        this.tipoLogradouro = tipoLogradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        validarCampoNaoNuloOuVazio(numero, "Número do endereço não pode ser nulo ou vazio.");
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        validarCampoNaoNuloOuVazio(cidade, "Cidade do endereço não pode ser nula ou vazia.");
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        validarCampoNaoNuloOuVazio(estado, "Estado do endereço não pode ser nulo ou vazio.");
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        validarCampoNaoNuloOuVazio(cep, "CEP do endereço não pode ser nulo ou vazio.");
        this.cep = cep;
    }
}
