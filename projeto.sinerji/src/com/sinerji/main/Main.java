package com.sinerji.main;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.sinerji.funcionarios.Funcionario;
import com.sinerji.vendas.venda;

public class Main {

	public static String valorTotalPagoComBonus(List<Funcionario> funcionarios, LocalDate data) {
		funcionarios.forEach(funcionario -> {
			calcularSalario(funcionario, data);
		});

		Double totalPago = 0.0;
		for (Funcionario funcionario : funcionarios) {
			totalPago += funcionario.getValorRecebido();
		}
		return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(totalPago);
	}

	public static String valorTotalPagoSemBeneficios(List<Funcionario> funcionarios, LocalDate data) {
		funcionarios.forEach(funcionario -> {
			calcularSalarioSemBeneficio(funcionario, data);
		});

		Double totalPago = 0.0;
		for (Funcionario funcionario : funcionarios) {
			totalPago += funcionario.getValorRecebido();
		}
		return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(totalPago);
	}

	public static String valorTotalBeneficiosPagos(List<Funcionario> funcionarios, LocalDate data) {
		funcionarios.forEach(funcionario -> {
			calcularSalarioSemBonus(funcionario, data);
		});

		Double totalPago = 0.0;
		Double salarioBase = 0.0;
		Double valorRecebido = 0.0;

		for (Funcionario funcionario : funcionarios) {
			if (!funcionario.getCargo().equalsIgnoreCase("gerente")) {
				salarioBase += funcionario.getSalarioBase();
				valorRecebido += funcionario.getValorRecebido();
			}
		}
		totalPago = valorRecebido - salarioBase;
		return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(totalPago);
	}

	public static String salarioMaisAltoDoMes(List<Funcionario> funcionarios, LocalDate data) {
		funcionarios.forEach(funcionario -> {
			calcularSalario(funcionario, data);
		});

		Double totalPago = 0.0;

		for (Funcionario funcionario : funcionarios) {
			totalPago += funcionario.getValorRecebido();
		}

		List<Double> valores = funcionarios.stream().map(func -> func.getValorRecebido()).collect(Collectors.toList());

		Double maiorValor = valores.stream().reduce(0.0, (acc, valor) -> {
			if (acc < valor) {
				acc = valor;
			}
			return acc;
		});

		totalPago = maiorValor;

		return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(totalPago);
	}

	public static String nomeFuncionarioMaiorBeneficioMes(List<Funcionario> funcionarios, LocalDate data) {
		funcionarios.forEach(funcionario -> {
			calcularSalarioSemBonus(funcionario, data);
		});

		List<Funcionario> funcionariosComBeneficio = new ArrayList<>();

		funcionarios.stream().filter(f -> !f.getCargo().equalsIgnoreCase("gerente")).forEach(f -> {
			funcionariosComBeneficio.add(f);
		});

		funcionariosComBeneficio.stream()
				.forEach(func -> func.setValorRecebido(func.getValorRecebido() - func.getSalarioBase()));

		List<Double> valores = funcionariosComBeneficio.stream().map(func -> func.getValorRecebido())
				.collect(Collectors.toList());

		Double maiorValor = valores.stream().reduce(0.0, (acc, valor) -> {
			if (acc < valor) {
				acc = valor;
			}
			return acc;
		});

		String nome = funcionarios.stream().map(func -> {
			if (func.getValorRecebido() != maiorValor) {
				return "";
			}
			return func.getNome();
		}).reduce("", (acc, func) -> func + acc);

		return nome;
	}

	public static Funcionario vendedorQueMaisVendeu(List<Funcionario> funcionarios, LocalDate data) {
		funcionarios.forEach(funcionario -> {
			calcularSalario(funcionario, data);
		});

		List<Funcionario> vendedores = funcionarios.stream()
				.filter(func -> func.getCargo().equalsIgnoreCase("vendedor")).toList();

		List<venda> vendas = new ArrayList<>();
		for (Funcionario vendedor : vendedores) {
			vendedor.getVendas().stream().filter(v -> v.getMes().getMonthValue() == data.getMonthValue())
					.filter(v -> v.getMes().getYear() == data.getYear()).forEach(v -> vendas.add(v));
		}

		Double resultado = vendas.stream().map(venda -> venda.getValor()).reduce(0.0,
				(acc, valor) -> Double.max(acc, valor));

		List<venda> maioresVendas = vendas.stream().filter(v -> v.getValor().equals(resultado)).toList();

		Funcionario funcionario = new Funcionario();
		for (Funcionario vendedor : vendedores) {
			for (venda v : maioresVendas) {
				if (vendedor.getVendas().contains(v)) {
					funcionario = vendedor;
				}
			}
		}
		return funcionario;
	}

	public static void calcularSalario(Funcionario funcionario, LocalDate data) {
		Period anosServico = funcionario.getDataContrato().until(data);
		int bonus = anosServico.getYears() * 0;

		switch (funcionario.getCargo().toLowerCase()) {
		case "secretario":
			bonus = 1000;
			double beneficioSecretario = funcionario.getSalarioBase() * 0.2;
			funcionario.setValorRecebido(funcionario.getSalarioBase() + bonus + beneficioSecretario);
			break;
		case "gerente":
			bonus = 3000;
			funcionario.setValorRecebido(funcionario.getSalarioBase() + bonus);
			break;
		case "vendedor":
			bonus = 1800;
			double beneficioVendedor = funcionario.getVendas().stream()
					.filter(venda -> venda.getMes().getYear() == data.getYear())
					.filter(venda -> venda.getMes().getMonthValue() == data.getMonthValue())
					.mapToDouble(venda -> venda.getValor() * 0.3).sum();
			funcionario.setValorRecebido(funcionario.getSalarioBase() + beneficioVendedor + bonus);
			break;
		default:
			System.out.println("Cargo inválido.");
		}
	}

	public static void calcularSalarioSemBeneficio(Funcionario funcionario, LocalDate data) {
		String cargo = funcionario.getCargo().toLowerCase();
		Period anosServico = funcionario.getDataContrato().until(data);
		int bonus = anosServico.getYears() * 1000;

		switch (cargo) {
		case "secretario":
			funcionario.setValorRecebido(funcionario.getSalarioBase() + bonus);
			break;
		case "gerente":
			bonus = anosServico.getYears() * 3000;
			funcionario.setValorRecebido(funcionario.getSalarioBase() + bonus);
			break;
		case "vendedor":
			bonus = anosServico.getYears() * 1800;
			double salario = funcionario.getSalarioBase() + bonus;
			funcionario.getVendas().stream().filter(venda -> venda.getMes().getYear() == data.getYear())
					.filter(venda -> venda.getMes().getMonthValue() == data.getMonthValue())
					.forEach(venda -> funcionario.setValorRecebido(salario));
			break;
		default:
			// Padrão caso não corresponda a nenhum cargo
			System.out.println("Cargo não identificado.");
			break;
		}

	}

	public static void calcularSalarioSemBonus(Funcionario funcionario, LocalDate data) {

		if (funcionario.getCargo().equalsIgnoreCase("Secretario")) {
			double beneficio = funcionario.getSalarioBase() * 0.2;
			Double salario = funcionario.getSalarioBase() + beneficio;
			funcionario.setValorRecebido(salario);
		}

		if (funcionario.getCargo().equalsIgnoreCase("Vendedor")) {
			funcionario.getVendas().stream().filter(venda -> venda.getMes().getYear() == data.getYear())
					.filter(venda -> venda.getMes().getMonthValue() == data.getMonthValue()).forEach(venda -> {
						double beneficio = venda.getValor() * 0.3;
						double salario = funcionario.getSalarioBase() + beneficio;
						funcionario.setValorRecebido(salario);
					});
		}

	}

	public static void main(String[] args) {

		List<Funcionario> funcionarios = new ArrayList<>();

		Funcionario felipe = new Funcionario();
		felipe.setNome("Felipe Salaberry");
		felipe.setCargo("secretario");
		felipe.setDataContrato(LocalDate.parse("2018-01-01"));
		felipe.setSalarioBase(7000.0);

		Funcionario milena = new Funcionario();
		milena.setNome("Milena Rodrigues");
		milena.setCargo("secretario");
		milena.setDataContrato(LocalDate.parse("2014-09-05"));
		milena.setSalarioBase(7000.0);

		Funcionario carla = new Funcionario();
		carla.setNome("Carla Rosana");
		carla.setCargo("vendedor");
		carla.setDataContrato(LocalDate.parse("2022-10-04"));
		carla.setSalarioBase(12000.0);

		Funcionario lucas = new Funcionario();
		lucas.setNome("Lucas Lopes");
		lucas.setCargo("vendedor");
		lucas.setDataContrato(LocalDate.parse("2022-10-04"));
		lucas.setSalarioBase(12000.0);

		Funcionario julia = new Funcionario();
		julia.setNome("Julia Morse");
		julia.setCargo("gerente");
		julia.setDataContrato(LocalDate.parse("2018-05-04"));
		julia.setSalarioBase(20000.0);

		Funcionario leo = new Funcionario();
		leo.setNome("Leo Madruga");
		leo.setCargo("gerente");
		leo.setDataContrato(LocalDate.parse("2010-02-03"));
		leo.setSalarioBase(20000.0);

		venda lucas1 = new venda("Lucas", 3400.0, LocalDate.parse("2021-10-05"));
		venda lucas2 = new venda("Lucas", 7700.0, LocalDate.parse("2022-01-01"));
		venda lucas3 = new venda("Lucas", 5000.0, LocalDate.parse("2022-02-01"));
		venda lucas4 = new venda("Lucas", 5900.0, LocalDate.parse("2022-03-01"));
		venda lucas5 = new venda("Lucas", 6500.0, LocalDate.parse("2022-04-01"));

		venda carla1 = new venda("Carla", 5200.0, LocalDate.parse("2022-10-04"));
		venda carla2 = new venda("Carla", 4000.0, LocalDate.parse("2022-01-01"));
		venda carla3 = new venda("Carla", 4200.0, LocalDate.parse("2022-02-01"));
		venda carla4 = new venda("Carla", 5850.0, LocalDate.parse("2022-03-01"));
		venda carla5 = new venda("Carla", 7000.0, LocalDate.parse("2022-04-01"));

		lucas.getVendas().add(lucas1);
		lucas.getVendas().add(lucas2);
		lucas.getVendas().add(lucas3);
		lucas.getVendas().add(lucas4);
		lucas.getVendas().add(lucas5);

		carla.getVendas().add(carla1);
		carla.getVendas().add(carla2);
		carla.getVendas().add(carla3);
		carla.getVendas().add(carla4);
		carla.getVendas().add(carla5);

		funcionarios.add(felipe);
		funcionarios.add(milena);
		funcionarios.add(carla);
		funcionarios.add(lucas);
		funcionarios.add(julia);
		funcionarios.add(leo);

		System.out.println("Valor total pago com todos os beneficios e bônus do mês à todos os funcionários: "
				+ valorTotalPagoComBonus(funcionarios, LocalDate.parse("2022-03-01")));

		System.out.println();
		funcionarios.forEach(funcionario -> System.out.println(funcionario));
		System.out.println();

		System.out.println("Valor pago sem benefícios: "
				+ valorTotalPagoSemBeneficios(funcionarios, LocalDate.parse("2022-03-01")));

		System.out.println();
		funcionarios.forEach(funcionario -> System.out.println(funcionario));
		System.out.println();

		System.out.println("Valor total de benefícios pagos no mês: "
				+ valorTotalBeneficiosPagos(funcionarios, LocalDate.parse("2022-03-01")));

		System.out.println();
		funcionarios.forEach(funcionario -> System.out.println(funcionario));
		System.out.println();

		System.out.println(
				"Salário mais alto no mês: " + salarioMaisAltoDoMes(funcionarios, LocalDate.parse("2022-03-01")));

		System.out.println();
		funcionarios.forEach(funcionario -> System.out.println(funcionario));
		System.out.println();

		System.out.println("Nome do Funcionário com o maior benefício: "
				+ nomeFuncionarioMaiorBeneficioMes(funcionarios, LocalDate.parse("2022-01-01")));

		System.out.println();
		funcionarios.stream().filter(funcionario -> !funcionario.getCargo().equalsIgnoreCase("gerente"))
				.forEach(funcionario -> System.out.println(funcionario));
		System.out.println();

		System.out.println("Vendedor que mais vendeu no mês: "
				+ vendedorQueMaisVendeu(funcionarios, LocalDate.parse("2022-04-01")));

		System.out.println();
		funcionarios.stream().filter(funcionario -> funcionario.getCargo().equalsIgnoreCase("Vendedor"))
				.forEach(funcionario -> System.out.println(funcionario));
		System.out.println();

	}
}
