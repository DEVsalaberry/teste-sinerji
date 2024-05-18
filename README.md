Sinerji Sistema de Folha de Pagamento

Visão Geral

O Sistema de Folha de Pagamento Sinerji é uma aplicação Java que gerencia os salários e benefícios dos funcionários com base em suas posições e desempenho de vendas. 
Ele calcula pagamentos totais, benefícios e bônus para diferentes situações e fornece insights sobre o desempenho dos funcionários.

Funcionalidades
Calcula o valor total pago com bônus para todos os funcionários em um determinado mês.

Calcula o valor total pago sem benefícios para todos os funcionários em um determinado mês.

Calcula o valor total dos benefícios pagos em um mês.

Identifica o funcionário com o maior salário em um determinado mês.

Encontra o funcionário com o maior benefício em um determinado mês.

Determina o vendedor com maior desempenho em um determinado mês.

Uso
Dados de Entrada: Informações dos funcionários, incluindo nome, cargo, data de contratação, salário base e dados de vendas.

Cálculo de Datas: O sistema calcula salários, bônus e benefícios com base na data fornecida.

Chamadas de Funções: Use as funções fornecidas para obter informações específicas sobre pagamentos, benefícios e desempenho dos funcionários.

Exibição de Resultados: O sistema exibe informações como pagamentos totais, maior salário, maior benefício e vendedor com melhor desempenho.

Estrutura do Código

Main.java: Contém a lógica principal para calcular pagamentos, benefícios e bônus.

Funcionario.java: Define a classe Funcionario para representar os funcionários.

Venda.java: Define a classe Venda para representar as transações de vendas.

Pacote com.sinerji.vendas: Contém classes relacionadas às transações de vendas.
Funções Principais
valorTotalPagoComBonus: Calcula o valor total pago com bônus para todos os funcionários em um determinado mês.
valorTotalPagoSemBeneficios: Calcula o valor total pago sem benefícios para todos os funcionários em um determinado mês.
valorTotalBeneficiosPagos: Calcula o valor total dos benefícios pagos em um mês.
salarioMaisAltoDoMes: Encontra o funcionário com o maior salário em um determinado mês.
nomeFuncionarioMaiorBeneficioMes: Identifica o funcionário com o maior benefício em um determinado mês.
vendedorQueMaisVendeu: Determina o vendedor com maior desempenho em um determinado mês.
