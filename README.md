# Sistema de Análise Patológica - Spring Boot Application

Este é um sistema Spring Boot para gestão de amostras patológicas, convertido da aplicação CLI Java original.

## ✅ O que foi implementado

### 1. Estrutura do Projeto

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

### 2. Configuração de Perfis

#### Profile `local` (H2 Database)
- Banco em memória H2
- Console H2 disponível em: http://localhost:8080/h2-console
- DDL auto-create-drop
- Ideal para desenvolvimento e testes

#### Profile `dev` (Oracle Database)
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

### 3. Entidades Implementadas

Todas as entidades foram criadas com:
- ✅ Anotações JPA completas
- ✅ Validações Jakarta Bean Validation
- ✅ Relacionamentos bidirecionais
- ✅ Composite keys onde necessário
- ✅ Métodos de negócio

**Entidades:**
1. **Paciente** - Gestão de pacientes
2. **Medico** - Médicos solicitantes e patologistas
3. **Amostra** - Amostras patológicas
4. **Medicao** - Medições de amostras
5. **ImagemAmostra** - Imagens das amostras
6. **Laudo** - Laudos médicos
7. **AuditLog** - Log de auditoria

### 4. Enums Implementados

- `Sexo` - MASCULINO, FEMININO, OUTRO
- `TipoMedico` - SOLICITANTE, PATOLOGISTA
- `StatusProcessamento` - RECEBIDA, EM_PROCESSAMENTO, MEDIDA, ANALISADA, LAUDADA, LIBERADA, CANCELADA
- `StatusLaudo` - RASCUNHO, REVISAO, EMITIDO, LIBERADO, CANCELADO
- `TipoArquivo` - JPG, JPEG, PNG, TIFF, BMP, GIF

### 5. Repositórios JPA

Todos os repositórios foram criados com:
- Métodos de busca customizados
- Queries JPQL para operações complexas
- Queries de contagem e estatísticas

### 6. Tratamento de Exceções

- `GlobalExceptionHandler` - Manipulador global de exceções
- `ResourceNotFoundException` - Recurso não encontrado (HTTP 404)
- `BusinessException` - Violação de regras de negócio (HTTP 422)
- `ErrorResponse` - Estrutura padrão de resposta de erro

### 7. Implementação Completa do Domínio Paciente

Como exemplo de implementação completa, o domínio **Paciente** está 100% funcional:

#### Endpoints REST `/api/pacientes`:

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/pacientes` | Cria novo paciente |
| GET | `/api/pacientes` | Lista todos os pacientes |
| GET | `/api/pacientes/{id}` | Busca paciente por ID |
| GET | `/api/pacientes/cpf/{cpf}` | Busca paciente por CPF |
| GET | `/api/pacientes/search?nome={nome}` | Busca por nome |
| GET | `/api/pacientes/sexo/{sexo}` | Busca por sexo |
| GET | `/api/pacientes/data-nascimento?inicio={data}&fim={data}` | Busca por data de nascimento |
| GET | `/api/pacientes/com-amostras` | Pacientes com amostras |
| GET | `/api/pacientes/count/sexo/{sexo}` | Conta pacientes por sexo |
| PUT | `/api/pacientes/{id}` | Atualiza paciente |
| DELETE | `/api/pacientes/{id}` | Remove paciente |

## 📋 O que precisa ser implementado

### Domínios Restantes

Seguindo o padrão implementado para **Paciente**, você precisa criar:

#### 1. Domínio Médico (`Medico`)

**DTOs:**
- ✅ `MedicoRequestDTO` (criado)
- ✅ `MedicoResponseDTO` (criado)

**Pendente:**
- `MedicoMapper`
- `MedicoService`
- `MedicoController`

**Endpoints sugeridos:**
- `POST /api/medicos` - Criar médico
- `GET /api/medicos` - Listar todos
- `GET /api/medicos/{id}` - Buscar por ID
- `GET /api/medicos/crm/{crm}/uf/{uf}` - Buscar por CRM
- `GET /api/medicos/tipo/{tipo}` - Listar por tipo (SOLICITANTE/PATOLOGISTA)
- `GET /api/medicos/especialidade?q={especialidade}` - Buscar por especialidade
- `PUT /api/medicos/{id}` - Atualizar
- `PUT /api/medicos/{id}/ativar` - Ativar médico
- `PUT /api/medicos/{id}/desativar` - Desativar médico
- `DELETE /api/medicos/{id}` - Remover

#### 2. Domínio Amostra (`Amostra`)

**DTOs:**
- ✅ `AmostraRequestDTO` (criado)
- ✅ `AmostraResponseDTO` (criado)

**Pendente:**
- `AmostraMapper`
- `AmostraService`
- `AmostraController`

**Endpoints sugeridos:**
- `POST /api/amostras` - Criar amostra
- `GET /api/amostras` - Listar todas
- `GET /api/amostras/codigo/{codigo}` - Buscar por código de rastreio
- `GET /api/amostras/paciente/{id}` - Amostras do paciente
- `GET /api/amostras/medico/{id}` - Amostras do médico
- `GET /api/amostras/status/{status}` - Filtrar por status
- `GET /api/amostras/prontas-para-analise` - Amostras prontas (com medições e imagens)
- `GET /api/amostras/sem-laudo` - Amostras sem laudo
- `PUT /api/amostras/{amostraId}/status` - Atualizar status
- `PUT /api/amostras/{amostraId}` - Atualizar amostra
- `DELETE /api/amostras/{amostraId}` - Remover

#### 3. Domínio Medição (`Medicao`)

**DTOs pendentes:**
- `MedicaoRequestDTO`
- `MedicaoResponseDTO`
- `MedicaoMapper`
- `MedicaoService`
- `MedicaoController`

**Endpoints sugeridos:**
- `POST /api/amostras/{amostraId}/medicoes` - Adicionar medição
- `GET /api/amostras/{amostraId}/medicoes` - Listar medições da amostra
- `GET /api/amostras/{amostraId}/medicoes/ativa` - Medição ativa
- `GET /api/medicoes/{id}` - Buscar medição por ID
- `PUT /api/medicoes/{id}/ativar` - Ativar medição
- `DELETE /api/medicoes/{id}` - Remover medição

#### 4. Domínio Imagem (`ImagemAmostra`)

**DTOs pendentes:**
- `ImagemAmostraRequestDTO`
- `ImagemAmostraResponseDTO`
- `ImagemAmostraMapper`
- `ImagemAmostraService`
- `ImagemAmostraController`

**Endpoints sugeridos:**
- `POST /api/amostras/{amostraId}/imagens` - Adicionar imagem
- `GET /api/amostras/{amostraId}/imagens` - Listar imagens da amostra
- `GET /api/imagens/{id}` - Buscar imagem por ID
- `PUT /api/imagens/{id}/ativar` - Ativar imagem
- `DELETE /api/imagens/{id}` - Remover imagem

#### 5. Domínio Laudo (`Laudo`)

**DTOs pendentes:**
- `LaudoRequestDTO`
- `LaudoResponseDTO`
- `LaudoMapper`
- `LaudoService`
- `LaudoController`

**Endpoints sugeridos:**
- `POST /api/amostras/{amostraId}/laudo` - Criar laudo
- `GET /api/laudos/{id}` - Buscar laudo por ID
- `GET /api/amostras/{amostraId}/laudo` - Buscar laudo da amostra
- `GET /api/laudos` - Listar todos
- `GET /api/laudos/status/{status}` - Filtrar por status
- `GET /api/laudos/patologista/{id}` - Laudos do patologista
- `PUT /api/laudos/{id}` - Atualizar laudo
- `PUT /api/laudos/{id}/emitir` - Emitir laudo
- `PUT /api/laudos/{id}/liberar` - Liberar laudo
- `PUT /api/laudos/{id}/cancelar` - Cancelar laudo
- `DELETE /api/laudos/{id}` - Remover laudo

### 6. Relatórios e Análises (do menu original)

Criar endpoints para os relatórios que existiam no menu CLI:

**Controller: `RelatorioController`**
- `GET /api/relatorios/processamento-amostras` - Relatório de processamento
- `GET /api/relatorios/qualidade-medicoes` - Relatório de qualidade
- `GET /api/relatorios/estatisticas-gerais` - Estatísticas do sistema
- `GET /api/relatorios/analise-epidemiologica` - Análise epidemiológica
- `GET /api/relatorios/priorizacao-inteligente` - Sistema de priorização
- `GET /api/relatorios/dashboard-performance` - Dashboard de performance
- `GET /api/relatorios/auditoria-compliance` - Auditoria e compliance

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
   - Queries JPQL

3. **DTOs** (`dto/PacienteRequestDTO.java` e `dto/PacienteResponseDTO.java`)
   - RequestDTO: validações de entrada
   - ResponseDTO: dados de saída (sem anotações JPA)

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
