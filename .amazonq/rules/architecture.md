# Arquitetura da Aplicação


### Estrutura de Módulo de Domínio
Cada domínio de negócio segue uma estrutura consistente:
```
domain/
├── Model.java              # Entidade JPA
├── ModelController.java    # Controlador REST
├── ModelService.java       # Lógica de negócio
├── ModelRepository.java    # Acesso a dados
├── dto/                    # Objetos de transferência de dados
├── mapper/                 # Mapeadores MapStruct
└── enums/                  # Enums específicos do domínio
```

## Dependências Externas
> Para informações completas sobre tecnologias e dependências, consulte o [README.md](../../README.md).

- **Flyway**: Migrações de banco de dados em `src/main/resources/db/migration/`
