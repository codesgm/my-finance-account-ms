# Diretrizes de Desenvolvimento

> Para comandos de compilação, execução e configuração geral, consulte o [README.md](../../README.md).

### BOAS PRATICAS:
SEMPRE APLICAR CLEAN CODE
SEMPRE APLICAR METODOLOGIA SOLID
DAR PREFERÊNCIAS PARA ANOTAÇÕES PARA DIMINUIR O CÓDIGO ESCRITO 

### VALIDAÇÕES OBRIGATÓRIAS:
- @Valid em todos os DTOs
- @NotNull, @NotBlank, @Email onde apropriado
- @Positive para valores monetários
- @PastOrPresent para datas
- Validação de propriedade (user só acessa seus dados)

## Testes
- JaCoCo configurado para relatórios de cobertura em controllers e services
- Builders de teste disponíveis para entidades complexas (ex: `BudgetBuilder`, `ProductBuilder`)
- Testes unitários seguir padrão: given<Condição>_when<Método>_then<Resultado>

## Mudanças no Banco de Dados
- Todas as mudanças de schema via migrações Flyway
- Versão baseline: 202501301325
- Migrações organizadas por pastas ano/mês
- Migrações: V{YYYYMMDDHHII}_{descrição}.sql com script de reversão ao final
- Ao criar migrações, SEMPRE incluir comentário de reversão:
  -- Para reverter essa migration executar o script abaixo:
  -- [SQL de reversão]
  -- DELETE FROM erp.flyway_schema_history WHERE version = 'XXX';


## Tratamento de Erros
- Tratamento global de exceções via `RestExceptionHandler`
- Exceções de negócio customizadas no pacote `common.exception`
- Integração Rollbar para monitoramento de erros

## Commit
- Commits Semânticos: {TICKET} {tipo}({escopo}): {descrição} (ex: COF-914 feat(budgets): Added field check tests)
