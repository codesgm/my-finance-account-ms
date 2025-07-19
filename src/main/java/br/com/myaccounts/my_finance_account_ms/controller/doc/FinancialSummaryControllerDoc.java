package br.com.myaccounts.my_finance_account_ms.controller.doc;

import br.com.myaccounts.my_finance_account_ms.dto.FinancialSummaryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Tag(name = "Financial Summary", description = "Operações relacionadas aos resumos e relatórios financeiros")
public interface FinancialSummaryControllerDoc {

    @Operation(
            summary = "Obter resumo financeiro por período",
            description = "Retorna um resumo completo das finanças do usuário em um período específico, incluindo receitas, despesas, saldo e contadores de transações"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resumo financeiro retornado com sucesso",
                    content = @Content(schema = @Schema(implementation = FinancialSummaryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Período inválido (data inicial maior que final)"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<FinancialSummaryDTO> getFinancialSummary(
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Data inicial do período (formato: YYYY-MM-DD)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "Data final do período (formato: YYYY-MM-DD)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    );

    @Operation(
            summary = "Calcular saldo por período",
            description = "Retorna o saldo (receitas - despesas) do usuário em um período específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saldo calculado com sucesso",
                    content = @Content(schema = @Schema(implementation = BigDecimal.class))),
            @ApiResponse(responseCode = "400", description = "Período inválido"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<BigDecimal> getBalance(
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Data inicial do período (formato: YYYY-MM-DD)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "Data final do período (formato: YYYY-MM-DD)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    );

    @Operation(
            summary = "Obter resumo do mês atual",
            description = "Retorna o resumo financeiro do usuário para o mês corrente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resumo do mês atual retornado com sucesso",
                    content = @Content(schema = @Schema(implementation = FinancialSummaryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<FinancialSummaryDTO> getCurrentMonthSummary(
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId
    );

    @Operation(
            summary = "Obter resumo anual",
            description = "Retorna o resumo financeiro do usuário para um ano específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resumo anual retornado com sucesso",
                    content = @Content(schema = @Schema(implementation = FinancialSummaryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<FinancialSummaryDTO> getYearSummary(
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Ano para o resumo (ex: 2025)", required = true)
            @PathVariable int year
    );

    @Operation(
            summary = "Obter resumo dos últimos meses",
            description = "Retorna uma lista com os resumos financeiros dos últimos N meses do usuário"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de resumos mensais retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = FinancialSummaryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Número de meses deve ser positivo"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<List<FinancialSummaryDTO>> getLastMonthsSummary(
            @Parameter(description = "ID único do usuário", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Número de meses para incluir no histórico (ex: 6)", required = true)
            @PathVariable int months
    );
}
