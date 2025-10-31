package br.com.dasa.analisepatologica.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Standard error response structure.
 */
@Schema(description = "Estrutura padronizada de resposta de erro da API")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    @Schema(description = "Data e hora em que o erro ocorreu", example = "2023-10-31T14:30:00")
    private LocalDateTime timestamp;

    @Schema(description = "Código de status HTTP", example = "400")
    private int status;

    @Schema(description = "Nome do erro HTTP", example = "Bad Request")
    private String error;

    @Schema(description = "Mensagem descritiva do erro", example = "Dados inválidos fornecidos na requisição")
    private String message;

    @Schema(description = "Caminho da URL que gerou o erro", example = "/api/pacientes")
    private String path;

    @Schema(description = "Lista de erros de validação de campos (presente apenas em erros 400)")
    private List<FieldError> fieldErrors;

    @Schema(description = "Erro de validação de um campo específico")
    @Data
    @AllArgsConstructor
    public static class FieldError {
        @Schema(description = "Nome do campo que falhou na validação", example = "email")
        private String field;

        @Schema(description = "Mensagem de erro do campo", example = "E-mail inválido")
        private String message;
    }
}
