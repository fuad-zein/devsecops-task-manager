# Task Manager API — DevSecOps Training Project

REST API sederhana dengan Spring Boot + MySQL, dilengkapi DevSecOps pipeline lengkap.

## Tech Stack
- **Java 17** + **Spring Boot 3.2**
- **MySQL 8** (production via Docker)
- **H2** (in-memory untuk unit test — tidak perlu MySQL saat test)
- **Docker** + **Docker Compose**
- **GitHub Actions** CI/CD

## Security Pipeline
| Tool | Fungsi | Waktu |
|------|--------|-------|
| Semgrep | SAST — cari bug di kode Java | ~30-60 detik |
| Trivy (fs) | SCA — scan pom.xml untuk CVE | ~30-60 detik |
| Gitleaks | Secret Scan — deteksi credential | ~10-30 detik |
| Trivy (image) | Container Scan — scan Docker image | ~1-2 menit |

> Mengapa Trivy bukan OWASP? OWASP butuh download 345k+ CVE dari NVD,
> sering error 429 rate limit. Trivy: database lokal, tidak perlu API key,
> selesai dalam detik.

## Cara Menjalankan

```bash
# Clone project
git clone https://github.com/[username]/devsecops-task-manager.git
cd devsecops-task-manager

# Jalankan App + MySQL sekaligus
docker-compose up -d

# Cek status
docker-compose ps
curl http://localhost:8080/api/health
```

## Unit Test (tidak perlu MySQL)
```bash
mvn test -Dspring.profiles.active=test
```

## API Endpoints
| Method | URL | Deskripsi |
|--------|-----|-----------|
| GET | /api/health | Status aplikasi |
| GET | /api/tasks | Semua task |
| POST | /api/tasks | Buat task baru |
| PUT | /api/tasks/{id} | Update task |
| DELETE | /api/tasks/{id} | Hapus task |
| GET | /api/tasks/status/TODO | Filter by status |
