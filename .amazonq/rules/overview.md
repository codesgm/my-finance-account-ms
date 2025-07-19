# Visão Geral do Projeto

> Para informações detalhadas sobre o projeto, consulte o [README.md](../../README.md).

## Observações Importantes Específicas
- O sistema usa chaves primárias UUID para todas as entidades
- Soft delete é implementado via campo `deletedAt`
- Todas as entidades têm campos de auditoria (timestamps de criação/atualização e IDs de usuário)
- Arquitetura multi-tenant requer tratamento cuidadoso de queries de banco de dados
- Uploads de arquivos são tratados através da integração S3