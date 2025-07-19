package br.com.myaccounts.my_finance_account_ms.controller.doc;

import br.com.myaccounts.my_finance_account_ms.dto.CreateExpenseDTO;
import br.com.myaccounts.my_finance_account_ms.dto.ExpenseDTO;
import br.com.myaccounts.my_finance_account_ms.dto.UpdateExpenseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Tag(name = "Expenses", description = "Operações relacionadas às despesas dos usuários")
public interface ExpenseControllerDoc {

    @Operation(
            summary = "Listar despesas do usuário",
            description = "Retorna todas as despesas de um usuário, com opção de filtrar por período"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de despesas retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = ExpenseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Período inválido (data inicial maior que final)")
    })
    ResponseEntity<List<ExpenseDTO>> getExpenses(
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Data inicial do período (formato: YYYY-MM-DD)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "Data final do período (formato: YYYY-MM-DD)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    );

    @Operation(
            summary = "Buscar despesa por ID",
            description = "Retorna uma despesa específica do usuário pelo seu identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Despesa encontrada com sucesso",
                    content = @Content(schema = @Schema(implementation = ExpenseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Despesa ou usuário não encontrado")
    })
    ResponseEntity<ExpenseDTO> getExpense(
            @Parameter(description = "ID único da despesa", required = true)
            @PathVariable UUID id,
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId
    );

    @Operation(
            summary = "Criar nova despesa",
            description = "Cria uma nova despesa para o usuário"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Despesa criada com sucesso",
                    content = @Content(schema = @Schema(implementation = ExpenseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou categoria não é do tipo EXPENSE"),
            @ApiResponse(responseCode = "404", description = "Usuário ou categoria não encontrado")
    })
    ResponseEntity<ExpenseDTO> createExpense(
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Dados da despesa a ser criada", required = true)
            @Valid @RequestBody CreateExpenseDTO dto
    );

    @Operation(
            summary = "Atualizar despesa",
            description = "Atualiza os dados de uma despesa existente do usuário"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Despesa atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = ExpenseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Despesa ou usuário não encontrado")
    })
    ResponseEntity<ExpenseDTO> updateExpense(
            @Parameter(description = "ID único da despesa", required = true)
            @PathVariable UUID id,
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Novos dados da despesa", required = true)
            @Valid @RequestBody UpdateExpenseDTO dto
    );

    @Operation(
            summary = "Deletar despesa",
            description = "Remove uma despesa do usuário (soft delete)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Despesa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Despesa ou usuário não encontrado")
    })
    ResponseEntity<Void> deleteExpense(
            @Parameter(description = "ID único da despesa", required = true)
            @PathVariable UUID id,
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId
    );

    @Operation(
            summary = "Calcular total de despesas por período",
            description = "Retorna o valor total das despesas do usuário em um período específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total calculado com sucesso",
                    content = @Content(schema = @Schema(implementation = BigDecimal.class))),
            @ApiResponse(responseCode = "400", description = "Período inválido"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<BigDecimal> getTotalByPeriod(
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Data inicial do período (formato: YYYY-MM-DD)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "Data final do período (formato: YYYY-MM-DD)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    );

    @Operation(
            summary = "Contar despesas do usuário",
            description = "Retorna o número total de despesas ativas do usuário"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contagem retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<Long> getExpenseCount(
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId
    );

    @Operation(
            summary = "Listar despesas recentes",
            description = "Retorna as 10 despesas mais recentes do usuário"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de despesas recentes retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = ExpenseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<List<ExpenseDTO>> getRecentExpenses(
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId
    );
}
