package br.com.myaccounts.my_finance_account_ms.controller.doc;

import br.com.myaccounts.my_finance_account_ms.dto.CreateRevenueDTO;
import br.com.myaccounts.my_finance_account_ms.dto.RevenueDTO;
import br.com.myaccounts.my_finance_account_ms.dto.UpdateRevenueDTO;
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

@Tag(name = "Revenues", description = "Operações relacionadas às receitas dos usuários")
public interface RevenueControllerDoc {

    @Operation(
            summary = "Listar receitas do usuário",
            description = "Retorna todas as receitas de um usuário, com opção de filtrar por período"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de receitas retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = RevenueDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Período inválido (data inicial maior que final)")
    })
    ResponseEntity<List<RevenueDTO>> getRevenues(
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Data inicial do período (formato: YYYY-MM-DD)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "Data final do período (formato: YYYY-MM-DD)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    );

    @Operation(
            summary = "Buscar receita por ID",
            description = "Retorna uma receita específica do usuário pelo seu identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receita encontrada com sucesso",
                    content = @Content(schema = @Schema(implementation = RevenueDTO.class))),
            @ApiResponse(responseCode = "404", description = "Receita ou usuário não encontrado")
    })
    ResponseEntity<RevenueDTO> getRevenue(
            @Parameter(description = "ID único da receita", required = true)
            @PathVariable UUID id,
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId
    );

    @Operation(
            summary = "Criar nova receita",
            description = "Cria uma nova receita para o usuário"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Receita criada com sucesso",
                    content = @Content(schema = @Schema(implementation = RevenueDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou categoria não é do tipo REVENUE"),
            @ApiResponse(responseCode = "404", description = "Usuário ou categoria não encontrado")
    })
    ResponseEntity<RevenueDTO> createRevenue(
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Dados da receita a ser criada", required = true)
            @Valid @RequestBody CreateRevenueDTO dto
    );

    @Operation(
            summary = "Atualizar receita",
            description = "Atualiza os dados de uma receita existente do usuário"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receita atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = RevenueDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Receita ou usuário não encontrado")
    })
    ResponseEntity<RevenueDTO> updateRevenue(
            @Parameter(description = "ID único da receita", required = true)
            @PathVariable UUID id,
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Novos dados da receita", required = true)
            @Valid @RequestBody UpdateRevenueDTO dto
    );

    @Operation(
            summary = "Deletar receita",
            description = "Remove uma receita do usuário (soft delete)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Receita deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Receita ou usuário não encontrado")
    })
    ResponseEntity<Void> deleteRevenue(
            @Parameter(description = "ID único da receita", required = true)
            @PathVariable UUID id,
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId
    );

    @Operation(
            summary = "Calcular total de receitas por período",
            description = "Retorna o valor total das receitas do usuário em um período específico"
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
            summary = "Contar receitas do usuário",
            description = "Retorna o número total de receitas ativas do usuário"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contagem retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<Long> getRevenueCount(
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId
    );

    @Operation(
            summary = "Listar receitas recentes",
            description = "Retorna as 10 receitas mais recentes do usuário"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de receitas recentes retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = RevenueDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<List<RevenueDTO>> getRecentRevenues(
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId
    );
}
