package br.com.dasa.analisepatologica.entity;

import br.com.dasa.analisepatologica.enums.Sexo;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 * Patient entity representing a patient in the pathology system.
 */
@Entity
@Table(name = "PACIENTE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_paciente")
    @SequenceGenerator(name = "seq_paciente", sequenceName = "SEQ_PACIENTE", allocationSize = 1)
    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @NotBlank(message = "Nome completo é obrigatório")
    @Size(min = 3, max = 255, message = "Nome deve ter entre 3 e 255 caracteres")
    @Column(name = "nome_completo", nullable = false, length = 255)
    private String nomeCompleto;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotNull(message = "Sexo é obrigatório")
    @Column(name = "sexo", nullable = false, length = 1)
    private Sexo sexo;

    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
    @Column(name = "cpf", unique = true, length = 11)
    private String cpf;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    @Column(name = "telefone", length = 20)
    private String telefone;

    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    @Column(name = "email", length = 255)
    private String email;

    @Size(max = 500, message = "Endereço deve ter no máximo 500 caracteres")
    @Column(name = "endereco_completo", length = 500)
    private String enderecoCompleto;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @NotBlank(message = "Criado por é obrigatório")
    @Size(max = 100, message = "Criado por deve ter no máximo 100 caracteres")
    @Column(name = "created_by", nullable = false, length = 100)
    private String createdBy;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Amostra> amostras = new ArrayList<>();

    /**
     * Calculates the patient's age based on birth date.
     */
    public int calcularIdade() {
        if (dataNascimento == null) {
            return 0;
        }
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    /**
     * Validates CPF checksum digits.
     */
    public boolean validarCpf() {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            return false;
        }

        // Check if all digits are the same
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Validate first check digit
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int firstCheckDigit = 11 - (sum % 11);
        if (firstCheckDigit >= 10) firstCheckDigit = 0;

        if (firstCheckDigit != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }

        // Validate second check digit
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int secondCheckDigit = 11 - (sum % 11);
        if (secondCheckDigit >= 10) secondCheckDigit = 0;

        return secondCheckDigit == Character.getNumericValue(cpf.charAt(10));
    }

    @PrePersist
    @PreUpdate
    private void validateSexo() {
        if (sexo != null) {
            // Convert enum to char for database storage
            // This is handled by JPA converter automatically
        }
    }
}
