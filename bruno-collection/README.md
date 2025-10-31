# Bruno API Collection - Análise Patológica

This is a complete Bruno API collection for testing all 67 endpoints of the Análise Patológica REST API.

## 📦 What is Bruno?

Bruno is a fast, open-source API client that uses `.bru` files stored directly in your filesystem. Unlike Postman, Bruno collections are just files that can be version-controlled with Git.

Learn more: https://www.usebruno.com/

## 🚀 Getting Started

### 1. Install Bruno

Download and install Bruno from: https://www.usebruno.com/downloads

### 2. Open Collection

1. Launch Bruno
2. Click "Open Collection"
3. Navigate to this folder: `bruno-collection/`
4. Bruno will automatically load all endpoints

### 3. Start the API Server

Make sure your Spring Boot application is running:

```bash
cd analisepatologica
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

## 📁 Collection Structure

```
bruno-collection/
├── bruno.json                    # Collection configuration
├── environments/
│   └── Local.bru                 # Local environment variables
├── Paciente/                     # Patient endpoints (11)
├── Medico/                       # Doctor endpoints (13)
├── Amostra/                      # Sample endpoints (15)
├── Medicao/                      # Measurement endpoints (4)
├── ImagemAmostra/                # Image endpoints (9)
└── Laudo/                        # Report endpoints (15)
```

## 🔧 Environment Variables

The collection uses these environment variables (defined in `environments/Local.bru`):

- `baseUrl`: `http://localhost:8080`
- `apiPath`: `/api`

You can create additional environments (e.g., Dev, Prod) by creating new `.bru` files in the `environments/` folder.

## 📝 Endpoint Categories

### 1. Paciente (Patients) - 11 endpoints
- Create Patient
- Get All Patients
- Get Patient by ID
- Get Patient by CPF
- Search Patients by Name
- Get Patients by Gender
- Get Patients by Birth Date Range
- Get Patients with Samples
- Count Patients by Gender
- Update Patient
- Delete Patient

### 2. Médico (Doctors) - 13 endpoints
- Create Doctor
- Get All Doctors
- Get Doctor by ID
- Get Doctor by CRM
- Search Doctors by Name
- Get Doctors by Type
- Get Active Doctors
- Search by Specialty
- Count Doctors by Type
- Update Doctor
- Activate Doctor
- Deactivate Doctor
- Delete Doctor

### 3. Amostra (Samples) - 15 endpoints
- Create Sample
- Get All Samples
- Get Sample by Code
- Get Samples by Patient
- Get Samples by Doctor
- Get Samples by Status
- Search by Tissue Type
- Get Samples by Collection Date
- Get Samples Ready for Analysis
- Get Samples Without Report
- Count Samples by Status
- Update Sample Status
- Update Sample
- Delete Sample

### 4. Medição (Measurements) - 4 endpoints
- Create Measurement
- Get All Measurements for Sample
- Get Active Measurement
- Activate Measurement Version

### 5. Imagem Amostra (Images) - 9 endpoints
- Create Image
- Get Image by ID
- Get All Images for Sample
- Get Active Images
- Update Image
- Activate Image
- Deactivate Image
- Delete Image

### 6. Laudo (Reports) - 15 endpoints
- Create Report
- Get All Reports
- Get Report by ID
- Get Report by Sample
- Get Reports by Status
- Get Reports by Pathologist
- Get Reports Pending Review
- Get Reports Ready for Release
- Count Reports by Status
- Update Report
- Issue Report
- Release Report
- Cancel Report
- Send Report to Review
- Delete Report

## 🧪 Testing Workflow

### Complete Test Flow

Follow this order to test the complete workflow:

1. **Create a Patient** (`Paciente/Create Patient.bru`)
2. **Create Doctors** (one SOLICITANTE, one PATOLOGISTA)
   - `Medico/Create Doctor.bru` (change `tipoMedico` to `SOLICITANTE`)
   - `Medico/Create Doctor.bru` (with `tipoMedico` as `PATOLOGISTA`)
3. **Create a Sample** (`Amostra/Create Sample.bru`)
   - Note the sample code (e.g., `AMO-2024-001`)
4. **Add Measurement** (`Medicao/Create Measurement.bru`)
5. **Add Image** (`ImagemAmostra/Create Image.bru`)
6. **Create Report** (`Laudo/Create Report.bru`)
7. **Issue Report** (`Laudo/Issue Report.bru`)
8. **Release Report** (`Laudo/Release Report.bru`)

### Quick Tests

- **List all data**: Use any "Get All" endpoint
- **Search**: Use "Search" endpoints with query parameters
- **Update**: Modify existing records with "Update" endpoints
- **Status changes**: Test workflow transitions (Sample status, Report status)

## 💡 Tips

1. **Tests**: Many requests include built-in tests that validate responses
2. **Sequential IDs**: The API uses auto-generated IDs, so adjust ID parameters based on your data
3. **Sample Codes**: Use the actual tracking codes (e.g., `AMO-2024-001`) in your requests
4. **Enum Values**:
   - Sexo: `MASCULINO`, `FEMININO`, `OUTRO`
   - TipoMedico: `SOLICITANTE`, `PATOLOGISTA`
   - StatusProcessamento: `RECEBIDA`, `EM_PROCESSAMENTO`, `MEDIDA`, `ANALISADA`, `LAUDADA`, `LIBERADA`, `CANCELADA`
   - StatusLaudo: `RASCUNHO`, `REVISAO`, `EMITIDO`, `LIBERADO`, `CANCELADO`
   - TipoArquivo: `JPG`, `JPEG`, `PNG`, `TIFF`, `BMP`, `GIF`

## 📊 Response Codes

- `200 OK` - Successful GET/PUT
- `201 Created` - Successful POST
- `204 No Content` - Successful DELETE
- `400 Bad Request` - Validation errors
- `404 Not Found` - Resource not found
- `409 Conflict` - Illegal state
- `422 Unprocessable Entity` - Business rule violation
- `500 Internal Server Error` - Server error

## 🔗 Related Documentation

- API Documentation: `../API_DOCUMENTATION.md`
- Implementation Guide: `../IMPLEMENTATION_GUIDE.md`
- Project README: `../README.md`

## 📞 Support

For issues or questions:
- Check the API Documentation
- Review the Implementation Guide
- Test with H2 database first (local profile)

---

**Total Endpoints**: 67
**Collection Version**: 1.0.0
**API Version**: 1.0.0
**Last Updated**: October 27, 2024
