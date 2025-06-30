# 🎓 ClassLine - Sistema de Gestão Educacional

<div align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java" alt="Java 21">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen?style=for-the-badge&logo=springboot" alt="Spring Boot 3.4.5">
  <img src="https://img.shields.io/badge/PostgreSQL-Database-blue?style=for-the-badge&logo=postgresql" alt="PostgreSQL">
  <img src="https://img.shields.io/badge/JWT-Security-red?style=for-the-badge&logo=jsonwebtokens" alt="JWT">
  <img src="https://img.shields.io/badge/Swagger-Documentation-green?style=for-the-badge&logo=swagger" alt="Swagger">
  <img src="https://img.shields.io/badge/Docker-Containerized-blue?style=for-the-badge&logo=docker" alt="Docker">
</div>

<br>

<div align="center">
  <p><strong>🚀 Uma solução completa para gestão educacional moderna</strong></p>
  <p>Sistema desenvolvido para facilitar a administração de instituições de ensino, oferecendo controle total sobre alunos, professores, cursos, turmas e muito mais.</p>
</div>

---

## 📋 Índice

- [🌟 Sobre o Projeto](#-sobre-o-projeto)
- [✨ Funcionalidades](#-funcionalidades)
- [🛠️ Tecnologias](#️-tecnologias)
- [📦 Instalação e Configuração](#-instalação-e-configuração)
- [🔧 Como Usar](#-como-usar)
- [📚 Documentação da API](#-documentação-da-api)
- [🏗️ Arquitetura](#️-arquitetura)
- [🔐 Autenticação e Autorização](#-autenticação-e-autorização)
- [🧪 Testes](#-testes)
- [🤝 Contribuição](#-contribuição)
- [📄 Licença](#-licença)

---

## 🌟 Sobre o Projeto

O **ClassLine** é um sistema de gestão educacional desenvolvido para **SENAI**, que oferece uma plataforma robusta e escalável para administração completa de instituições de ensino. O projeto foi desenvolvido com foco na experiência do usuário, segurança e performance.

### 🎯 Objetivos
- **Centralizar** todas as informações acadêmicas em um só lugar
- **Facilitar** a comunicação entre instituição, professores e alunos
- **Automatizar** processos de gestão educacional
- **Fornecer** relatórios e análises detalhadas
- **Garantir** segurança e privacidade dos dados

---

## ✨ Funcionalidades

### 🏫 **Gestão Institucional**
- ✅ **Cadastro e gerenciamento de instituições**
- ✅ **Autenticação segura com JWT**
- ✅ **Dashboard administrativo completo**

### 👨‍🏫 **Gestão de Professores**
- ✅ **Cadastro, edição e desativação de professores**
- ✅ **Controle de permissões e acessos**
- ✅ **Gestão de disciplinas por professor**

### 👨‍🎓 **Gestão de Alunos**
- ✅ **Cadastro completo de estudantes**
- ✅ **Matrícula em turmas e cursos**
- ✅ **Boletim acadêmico detalhado**
- ✅ **Comparativo de desempenho**

### 📚 **Gestão Acadêmica**
- ✅ **Criação e administração de cursos**
- ✅ **Gestão de disciplinas e grades curriculares**
- ✅ **Controle de turmas e semestres**
- ✅ **Sistema de avaliações e notas**

### 📊 **Controle de Frequência**
- ✅ **Lançamento de presença/falta**
- ✅ **Relatórios de frequência**
- ✅ **Controle por disciplina e período**

### 📈 **Relatórios e Analytics**
- ✅ **Desempenho acadêmico por aluno**
- ✅ **Estatísticas de frequência**
- ✅ **Comparativos entre turmas**
- ✅ **Relatórios exportáveis**

---

## 🛠️ Tecnologias

### **Backend Core**
- **Java 21** - Linguagem principal
- **Spring Boot 3.4.5** - Framework principal
- **Spring Security** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados

### **Banco de Dados**
- **PostgreSQL** - Banco de dados principal
- **Neon** - Hosting de banco de dados em nuvem

### **Segurança**
- **JWT (JSON Web Tokens)** - Autenticação stateless
- **Spring Security** - Controle de acesso baseado em roles

### **Documentação e Testes**
- **SpringDoc OpenAPI** - Documentação automática da API
- **Swagger UI** - Interface interativa da API
- **Spring Boot Test** - Framework de testes

### **Ferramentas de Desenvolvimento**
- **Maven** - Gerenciamento de dependências
- **Lombok** - Redução de boilerplate code
- **Docker** - Containerização
- **Spring DevTools** - Hot reload em desenvolvimento

---

## 📦 Instalação e Configuração

### **Pré-requisitos**
- Java 21 ou superior
- Maven 3.8+
- PostgreSQL 14+
- Git

### **1️⃣ Clone o repositório**
```bash
git clone https://github.com/seu-usuario/ClassLine-Backend.git
cd ClassLine-Backend
```

### **2️⃣ Configure o banco de dados**
```properties
# src/main/resources/application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/classline
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### **3️⃣ Configure a chave JWT**
```properties
api.security.token.secret=sua_chave_secreta_aqui
```

### **4️⃣ Execute com Maven**
```bash
# Instalar dependências
mvn clean install

# Executar aplicação
mvn spring-boot:run
```

### **🐳 Usando Docker**
```bash
# Construir imagem
docker build -t classline-backend .

# Executar container
docker run -p 3000:3000 classline-backend
```

---

## 🔧 Como Usar

### **🚀 Inicialização**
1. A aplicação será executada na porta `3000`
2. Acesse a documentação em: `http://localhost:3000/swagger-ui.html`
3. O banco de dados será criado automaticamente

### **👤 Primeiro Acesso**
1. **Registre uma instituição:**
   ```bash
   POST /instituicao/auth/register
   ```

2. **Faça login:**
   ```bash
   POST /instituicao/auth/login
   ```

3. **Use o token JWT** retornado nos headers das próximas requisições:
   ```
   Authorization: Bearer <seu_token_aqui>
   ```

---

## 📚 Documentação da API

### **🌐 Swagger UI**
Acesse a documentação interativa completa em:
```
http://localhost:3000/swagger-ui.html
```

### **📋 Principais Endpoints**

#### **🔐 Autenticação**
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/instituicao/auth/register` | Registro de instituição |
| `POST` | `/instituicao/auth/login` | Login de instituição |
| `POST` | `/professor/auth/login` | Login de professor |
| `POST` | `/aluno/auth/login` | Login de aluno |

#### **👨‍🎓 Gestão de Alunos**
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/aluno` | Cadastrar novo aluno |
| `GET` | `/aluno/{id}` | Buscar aluno por ID |
| `PUT` | `/aluno/{id}` | Editar dados do aluno |
| `DELETE` | `/aluno/{id}` | Desativar aluno |
| `GET` | `/aluno/boletim/{id}` | Buscar boletim do aluno |

#### **📚 Gestão de Cursos**
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/curso/{id_instituicao}` | Criar novo curso |
| `GET` | `/curso/instituicao/{id}` | Listar cursos da instituição |
| `PUT` | `/curso/{id_instituicao}/{id_curso}` | Editar curso |
| `DELETE` | `/curso/{id_instituicao}/{id_curso}` | Desativar curso |

#### **📊 Frequência e Avaliações**
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/frequencia/disciplina/{id}/professor/{id}` | Lançar frequência |
| `POST` | `/avaliacao/disciplina/{id}/professor/{id}/turma/{id}` | Criar avaliação |
| `POST` | `/nota/avaliacao/{id}/notas` | Lançar notas |

---

## 🏗️ Arquitetura

### **📁 Estrutura do Projeto**
```
src/main/java/com/senai/classline/
├── 🎯 controllers/          # Controladores REST
├── 🏛️ domain/              # Entidades JPA
├── 📦 dto/                 # Data Transfer Objects
├── ⚙️ enums/               # Enumerações
├── ⚠️ exceptions/          # Exceções customizadas
├── 🔧 infra/               # Configurações de infraestrutura
├── 🗄️ repositories/        # Repositórios JPA
└── 🧠 service/             # Regras de negócio
```

### **🎨 Padrões Utilizados**
- **MVC (Model-View-Controller)** - Separação de responsabilidades
- **Repository Pattern** - Abstração da camada de dados
- **DTO Pattern** - Transferência de dados entre camadas
- **Service Layer** - Lógica de negócio centralizada

### **🗄️ Principais Entidades**
- **Instituição** - Escola/Universidade
- **Professor** - Docentes
- **Aluno** - Estudantes
- **Curso** - Programas acadêmicos
- **Turma** - Classes de alunos
- **Disciplina** - Matérias/Cadeiras
- **Avaliação** - Provas e trabalhos
- **Nota** - Pontuações das avaliações
- **Frequência** - Presença/ausência

---

## 🔐 Autenticação e Autorização

### **🛡️ Sistema de Roles**
O sistema implementa controle de acesso baseado em funções:

- **🏫 INSTITUICAO** - Acesso administrativo completo
- **👨‍🏫 PROFESSOR** - Gestão de suas turmas e disciplinas
- **👨‍🎓 ALUNO** - Acesso aos próprios dados acadêmicos

### **🔑 JWT Configuration**
```java
// Configuração de segurança
@PreAuthorize("hasRole('INSTITUICAO')")          // Apenas instituições
@PreAuthorize("hasRole('PROFESSOR')")            // Apenas professores
@PreAuthorize("hasRole('ALUNO')")                // Apenas alunos
@PreAuthorize("hasRole('INSTITUICAO') or hasRole('PROFESSOR')") // Multi-role
```

### **🔒 Endpoints Protegidos**
Todos os endpoints (exceto login/register) requerem autenticação JWT válida.

---

## 🧪 Testes

### **🚀 Executar Testes**
```bash
# Executar todos os testes
mvn test

# Executar com relatório de cobertura
mvn test jacoco:report
```

### **📊 Tipos de Teste**
- **Unit Tests** - Testes de unidade para services
- **Integration Tests** - Testes de integração com banco
- **Controller Tests** - Testes dos endpoints REST

---

## 🤝 Contribuição

### **💡 Como Contribuir**
1. **Fork** o projeto
2. **Crie** uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. **Commit** suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. **Push** para a branch (`git push origin feature/AmazingFeature`)
5. **Abra** um Pull Request

### **📋 Diretrizes**
- Siga os padrões de código existentes
- Adicione testes para novas funcionalidades
- Mantenha a documentação atualizada
- Use commits semânticos

---

## 📄 Licença

Este projeto está licenciado sob a **MIT License**. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

<div align="center">
  <p><strong>🎓 Desenvolvido com ❤️ para revolucionar a educação</strong></p>
  <p>ClassLine - Conectando instituições, professores e alunos</p>
  
  <br>
  
  <a href="#-classline---sistema-de-gestão-educacional">⬆️ Voltar ao topo</a>
</div>
