package br.com.myaccounts.my_finance_account_ms.controller.doc;

import br.com.myaccounts.my_finance_account_ms.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Tag(name = "Users", description = "Operações relacionadas aos usuários do sistema")
public interface UserControllerDoc {

    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna os dados de um usuário específico pelo seu identificador único"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<UserDTO> getUser(
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID id
    );

    @Operation(
            summary = "Buscar usuário com suas categorias",
            description = "Retorna os dados de um usuário incluindo todas as suas categorias de receitas e despesas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário com categorias encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserWithCategoriesDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<UserWithCategoriesDTO> getUserWithCategories(
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID id
    );

    @Operation(
            summary = "Criar novo usuário",
            description = "Cria um novo usuário no sistema com nome e email únicos"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou email já existe")
    })
    ResponseEntity<UserDTO> createUser(
            @Parameter(description = "Dados do usuário a ser criado", required = true)
            @Valid @RequestBody CreateUserDTO dto
    );

    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponseEntity<UserDTO> updateUser(
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Novos dados do usuário", required = true)
            @Valid @RequestBody UpdateUserDTO dto
    );

    @Operation(
            summary = "Deletar usuário",
            description = "Remove um usuário do sistema (soft delete - o usuário é marcado como excluído mas não removido fisicamente)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID id
    );

    @Operation(
            summary = "Listar todos os usuários",
            description = "Retorna uma lista com todos os usuários ativos do sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    ResponseEntity<List<UserDTO>> getAllUsers();

    @Operation(
            summary = "Contar usuários",
            description = "Retorna o número total de usuários ativos no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contagem retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = Long.class)))
    })
    ResponseEntity<Long> getUserCount();

    @Operation(
            summary = "Atualizar último acesso",
            description = "Atualiza o timestamp do último acesso do usuário"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Último acesso atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<Void> updateLastAccess(
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID id
    );
}
