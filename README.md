# Desafio Consulta Vendas - DevSuperior (Projeto DSMeta)

Este projeto foi desenvolvido como um desafio prático para consolidar conhecimentos em **Spring Data JPA**, focando em consultas otimizadas (JPQL), tratamento de filtros dinâmicos e resolução de problemas de performance.

## 🚀 Desafios Implementados

O sistema gerencia um domínio de Vendas (`Sale`) e Vendedores (`Seller`), onde implementei os seguintes casos de uso:

### 1. Relatório de Vendas (Report)
* **Problema N+1 Solucionado**: Para evitar que o Hibernate disparasse uma consulta extra para cada vendedor ao listar as vendas, utilizei a cláusula `JOIN FETCH` na JPQL. Isso permite carregar os dados da venda e do vendedor em uma única ida ao banco de dados.
* **Busca por Entidade**: Nesta consulta, utilizei `SELECT obj FROM Sale obj`, retornando a entidade gerenciada pelo JPA. Isso foi fundamental para manter a flexibilidade de manipulação do objeto antes da conversão para DTO no Service.
* **Filtros e Paginação**: Implementação de filtros opcionais por data e trecho de nome (Case Insensitive), com retorno paginado.

### 2. Sumário de Vendas por Vendedor (Summary)
* **Agregação e GROUP BY**: Implementação de consulta de agrupamento para somar o total de vendas por cada vendedor dentro de um período.
* **Constructor Expression**: Como o resultado de uma soma (`SUM`) não representa uma entidade completa, utilizei a sintaxe `SELECT new ...`, passando o caminho completo da classe DTO. Isso instrui o JPA a instanciar o `SaleSummaryDTO` diretamente durante a execução da query.

---

## 🧠 Raciocínio Técnico e Aprendizados

### Diferença entre Projeção e Entidade
Um ponto crucial do aprendizado foi entender quando usar cada abordagem no Repository:
* **`SELECT obj`**: Usado quando precisamos da entidade completa. Ideal para o Relatório, onde a conversão para DTO é feita após a recuperação dos dados, aproveitando o `JOIN FETCH`.
* **`SELECT new com.pacote.DTO(...)`**: Usado quando queremos apenas dados específicos ou agregados (como somas). É mais performático para o Sumário, pois o JPA não precisa monitorar essas instâncias no contexto de persistência.



### Tratamento Dinâmico de Datas
A lógica de negócio exigia que, na ausência de parâmetros, o sistema calculasse automaticamente o intervalo de tempo:
1.  A data final (`maxDate`) assume o dia atual do sistema.
2.  A data inicial (`minDate`) é calculada como 12 meses antes da data final definida.
Essa lógica foi centralizada na camada de **Service**, garantindo que o **Controller** receba apenas Strings e a **Repository** receba objetos `LocalDate` prontos para a consulta.

---

## 🛠️ Tecnologias e Padrões
* **Java 17 / Spring Boot 3**
* **JPQL** para consultas customizadas e otimizadas.
* **Padrão DTO** para transferência de dados entre camadas.
* **H2 Database** com carregamento via `import.sql` devidamente configurado (resolvendo restrições de integridade e associações).
