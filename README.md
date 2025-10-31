# Sistema de Análise Patológica - Spring Boot Application

Este é um sistema Spring Boot para gestão de amostras patológicas.

## Integrantes

| Nome                                 | RM     |
|--------------------------------------|--------|
| Bruno Gabriel Silva Dominicheli      | 554981 |
| Larissa Rodrigues Lapa               | 554517 |
| Nicolly Ramalho Bleinat              | 555359 |
| Paulo Henrique Monteiro Golovanevsky | 555300 |

## Estrutura do Projeto

```
analisepatologica/
├── src/main/java/br/com/dasa/analisepatologica/
│   ├── controller/         # REST Controllers
│   ├── service/           # Business Logic Layer
│   ├── repository/        # JPA Repositories
│   ├── entity/            # JPA Entities
│   ├── dto/               # Data Transfer Objects
│   ├── mapper/            # Entity-DTO Mappers
│   ├── enums/             # Enumerations
│   ├── exception/         # Exception Handling
│   └── AnalisepatologicaApplication.java
└── src/main/resources/
    ├── application.yml                # Main configuration
    ├── application-local.yml          # H2 Profile
    └── application-dev.yml            # Oracle Profile
```

## Configuração de Perfis

### Profile `local` (H2 Database)
- Banco em memória H2
- Console H2 disponível em: http://localhost:8080/h2-console
- DDL auto-create-drop
- Ideal para desenvolvimento e testes

### Profile `dev` (Oracle Database)
- Conexão com Oracle Database FIAP
- Host: oracle.fiap.com.br:1521
- SID: ORCL
- User: RM554981
- DDL validate (não recria tabelas)

**Para ativar um perfil:**
```bash
# Local (H2)
mvn spring-boot:run

# Dev (Oracle)
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=dev
```

## 🚀 Como executar

### Pré-requisitos
- Java 17+
- Maven 3.8+
- (Opcional) Oracle Database para profile `dev`

### Executar com H2 (local)
```bash
cd analisepatologica
mvn clean install
mvn spring-boot:run
```

### Executar com Oracle (dev)
```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=dev
```

### Acessar a aplicação
- API Base URL: http://localhost:8080/api
- Documentação da API: http://localhost:8080/swagger-ui/index.html
- H2 Console: http://localhost:8080/h2-console (profile local)

## 📝 Exemplos de Requisições

### Criar Paciente
```bash
curl -X POST http://localhost:8080/api/pacientes \
  -H "Content-Type: application/json" \
  -d '{
    "nomeCompleto": "João da Silva",
    "dataNascimento": "1990-05-15",
    "sexo": "MASCULINO",
    "cpf": "12345678901",
    "telefone": "(11)98765-4321",
    "email": "joao@email.com"
  }'
```

### Buscar Todos os Pacientes
```bash
curl http://localhost:8080/api/pacientes
```

### Buscar Paciente por ID
```bash
curl http://localhost:8080/api/pacientes/1
```

### Buscar Paciente por CPF
```bash
curl http://localhost:8080/api/pacientes/cpf/12345678901
```

### Atualizar Paciente
```bash
curl -X PUT http://localhost:8080/api/pacientes/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nomeCompleto": "João da Silva Santos",
    "dataNascimento": "1990-05-15",
    "sexo": "MASCULINO",
    "cpf": "12345678901",
    "telefone": "(11)98765-4321",
    "email": "joao.santos@email.com"
  }'
```

### Deletar Paciente
```bash
curl -X DELETE http://localhost:8080/api/pacientes/1
```

## 📐 Padrões de Código

### Estrutura de um Domínio Completo

Para cada domínio, siga este padrão (exemplificado com Paciente):

1. **Entity** (`entity/Paciente.java`)
   - Anotações JPA (@Entity, @Table, @Id, etc.)
   - Validações (@NotNull, @NotBlank, @Size, etc.)
   - Relacionamentos (@OneToMany, @ManyToOne, etc.)
   - Métodos de negócio

2. **Repository** (`repository/PacienteRepository.java`)
   - Extends JpaRepository
   - Métodos de busca customizados

3. **DTOs** (`dto/PacienteRequestDTO.java` e `dto/PacienteResponseDTO.java`)
   - RequestDTO: validações de entrada
   - ResponseDTO: dados de saída

4. **Mapper** (`mapper/PacienteMapper.java`)
   - toEntity(RequestDTO)
   - toResponseDTO(Entity)
   - updateEntityFromDTO(RequestDTO, Entity)

5. **Service** (`service/PacienteService.java`)
   - @Service, @Transactional
   - Lógica de negócio
   - Validações
   - Chamadas ao repository

6. **Controller** (`controller/PacienteController.java`)
   - @RestController, @RequestMapping
   - Endpoints REST
   - HTTP status codes apropriados
   - Logging

### Códigos HTTP

Siga estes códigos para as respostas:

- **200 OK** - GET, PUT bem-sucedidos
- **201 Created** - POST bem-sucedido
- **204 No Content** - DELETE bem-sucedido
- **400 Bad Request** - Validação de dados falhou
- **404 Not Found** - Recurso não encontrado
- **409 Conflict** - Estado inválido (IllegalStateException)
- **422 Unprocessable Entity** - Violação de regra de negócio
- **500 Internal Server Error** - Erro não tratado

## 🔍 Validações Implementadas

### Paciente
- Nome completo: 3-255 caracteres
- Data de nascimento: deve ser passada
- Sexo: obrigatório (MASCULINO, FEMININO, OUTRO)
- CPF: 11 dígitos + validação de checksum
- Email: formato válido
- CPF único no sistema

### Médico
- Nome completo: 3-255 caracteres
- CRM: obrigatório, validação básica
- UF: 2 letras maiúsculas
- Tipo: SOLICITANTE ou PATOLOGISTA
- CRM+UF únicos no sistema

### Amostra
- Código de rastreio: único
- Paciente e médico: obrigatórios e devem existir
- Data de coleta: não pode ser futura
- Data de recebimento: não pode ser anterior à coleta

## 📚 Tecnologias Utilizadas

- **Spring Boot 3.5.7**
- **Spring Data JPA**
- **Spring Validation**
- **Spring Web (REST)**
- **H2 Database** (desenvolvimento)
- **Oracle JDBC Driver** (produção)
- **Lombok** (redução de boilerplate)
- **Jakarta Validation** (Bean Validation)

**Desenvolvido como parte do Challenge DASA - Sprint 4**
