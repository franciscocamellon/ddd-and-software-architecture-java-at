package com.camelloncase.transporte.domain;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class EnderecoEntrega {

    private String logradouro;
    private String numero;
    private String cidade;
    private String uf;
    private String cep;

    protected EnderecoEntrega() {
    }

    public EnderecoEntrega(String logradouro, String numero, String cidade, String uf, String cep) {
        if (isBlank(logradouro) || isBlank(cidade) || isBlank(uf) || isBlank(cep)) {
            throw new IllegalArgumentException("Endereço inválido (campos obrigatórios em branco)");
        }
        String ufNorm = uf.trim().toUpperCase();
        if (ufNorm.length() != 2) {
            throw new IllegalArgumentException("UF inválida: " + uf);
        }
        String cepNorm = cep.replaceAll("\\D", "");
        if (cepNorm.length() != 8) {
            throw new IllegalArgumentException("CEP inválido: " + cep);
        }

        this.logradouro = logradouro.trim();
        this.numero = numero == null ? "" : numero.trim();
        this.cidade = cidade.trim();
        this.uf = ufNorm;
        this.cep = cepNorm;
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getCidade() {
        return cidade;
    }

    public String getUf() {
        return uf;
    }

    public String getCep() {
        return cep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnderecoEntrega that = (EnderecoEntrega) o;
        return Objects.equals(logradouro, that.logradouro)
                && Objects.equals(numero, that.numero)
                && Objects.equals(cidade, that.cidade)
                && Objects.equals(uf, that.uf)
                && Objects.equals(cep, that.cep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logradouro, numero, cidade, uf, cep);
    }
}
