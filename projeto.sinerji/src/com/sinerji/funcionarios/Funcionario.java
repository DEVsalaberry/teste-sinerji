package com.sinerji.funcionarios;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.sinerji.vendas.venda;

public class Funcionario {

    private String nome;
    private LocalDate dataContrato;
    private String cargo;
    private Double salarioBase;
    private Double valorRecebido;
    private List<venda> vendas = new ArrayList<>();

    public Funcionario() {
        super();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataContrato() {
        return dataContrato;
    }

    public void setDataContrato(LocalDate dataContrato) {
        this.dataContrato = dataContrato;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(Double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public Double getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(Double valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    public List<venda> getVendas() {
        return vendas;
    }

    public Double calcularSalarioTotal() {
        return salarioBase + valorRecebido;
    }

    public Double calcularBonificacao() {
        return salarioBase * 0.1;
    }

    @Override
    public String toString() {
        return "Funcionario [nome = " + nome + ", dataContrato = " + dataContrato + ", cargo = " + cargo
                + ", salarioBase = " + NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(salarioBase)
                + ", ValorRecebido = "
                + NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valorRecebido) + ", vendas = "
                + vendas + "]";
    }

}

