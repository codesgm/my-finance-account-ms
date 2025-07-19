package br.com.myaccounts.my_finance_account_ms.controller.doc;

import br.com.myaccounts.my_finance_account_ms.dto.CategoryDTO;
import br.com.myaccounts.my_finance_account_ms.dto.CreateCategoryDTO;
import br.com.myaccounts.my_finance_account_ms.enums.TransactionType;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Tag(name = "Categories", description = "Operações relacionadas às categorias de receitas e despesas")
public interface CategoryControllerDoc {

    @Operation(
            summary = "Listar categorias do usuário",
            description = "Retorna todas as categorias de um usuário, com opção de filtrar por tipo (REVENUE ou EXPENSE)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<List<CategoryDTO>> getCategories(
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Tipo de transação para filtrar (REVENUE ou EXPENSE)")
            @RequestParam(required = false) TransactionType type
    );

    @Operation(
            summary = "Buscar categoria por ID",
            description = "Retorna uma categoria específica do usuário pelo seu identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso",
                    content = @Content(schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Categoria ou usuário não encontrado")
    })
    ResponseEntity<CategoryDTO> getCategory(
            @Parameter(description = "ID único da categoria", required = true)
            @PathVariable UUID id,
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId
    );

    @Operation(
            summary = "Criar nova categoria",
            description = "Cria uma nova categoria de receita ou despesa para o usuário"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso",
                    content = @Content(schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<CategoryDTO> createCategory(
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Dados da categoria a ser criada", required = true)
            @Valid @RequestBody CreateCategoryDTO dto
    );

    @Operation(
            summary = "Atualizar categoria",
            description = "Atualiza os dados de uma categoria existente do usuário"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoria ou usuário não encontrado")
    })
    ResponseEntity<CategoryDTO> updateCategory(
            @Parameter(description = "ID único da categoria", required = true)
            @PathVariable UUID id,
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Novos dados da categoria", required = true)
            @Valid @RequestBody CreateCategoryDTO dto
    );

    @Operation(
            summary = "Deletar categoria",
            description = "Remove uma categoria do usuário (soft delete)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria ou usuário não encontrado")
    })
    ResponseEntity<Void> deleteCategory(
            @Parameter(description = "ID único da categoria", required = true)
            @PathVariable UUID id,
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId
    );

    @Operation(
            summary = "Contar categorias do usuário",
            description = "Retorna o número total de categorias ativas do usuário"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contagem retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<Long> getCategoryCount(
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId
    );

    @Operation(
            summary = "Criar categorias padrão",
            description = "Cria um conjunto de categorias padrão para o usuário (Salário, Freelance, Alimentação, Transporte, etc.)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categorias padrão criadas com sucesso",
                    content = @Content(schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<List<CategoryDTO>> createDefaultCategories(
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId
    );
}
