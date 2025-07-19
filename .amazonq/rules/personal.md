Antes de realizar alterações nos meus arquivos, sempre perguntar se quero a alteração ou apenas que você escreva no chat


Não faça importações diretas na variável
não faça isso:
@org.springframework.context.annotation.Lazy
private AuthenticationService authenticationService;


Faça isso:
import org.springframework.context.annotation.Lazy;
// ...other imports...

@Lazy
private AuthenticationService authenticationService;
