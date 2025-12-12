
# 📚 Bookstore Platform (Microservices)

Uma plataforma distribuída para gestão de livraria, implementada com arquitetura de microsserviços em Java/Spring Boot e orquestrada via Kubernetes.


---

## 🏗️ Arquitetura da Solução

O sistema adota o padrão de arquitetura distribuída com comunicação síncrona (REST) via **OpenFeign**.

```mermaid
graph TD
    User((Cliente HTTP))

    subgraph Kubernetes Cluster [Minikube]
        Ingress[NodePort / Service Discovery]

        subgraph Services
            direction TB
            SVCLBL@{shape: text, label: "Services", style: "text-align: left; font-weight:bold;"}
            Shop["🛒 Shop Service<br/>(Consumer)"]
            Book["📚 Book Service<br/>(Provider)"]
        end

        subgraph DataPersistence
            direction TB
            DBLBL@{shape: text, label: "Data Persistence", style: "text-align: left; font-weight:bold;"}
            DB[("🐘 PostgreSQL<br/>Shared Instance")]
        end
    end

    User -->|"POST /sales"| Ingress
    Ingress --> Shop
    Shop -->|"GET /books/{id}"| Book
    Shop -->|"Persist Sale"| DB
    Book -->|"Persist Book"| DB
````

### 📋 Justificativas Técnicas (Assessment)

- **Persistência (Item 10b):** Optou-se pelo **PostgreSQL** devido à sua robustez, conformidade ACID e suporte nativo a tipos de dados complexos, essenciais para garantir a integridade financeira das vendas.

- **Orquestração (Item 10c):** A infraestrutura utiliza **Minikube**.

  - *Motivo:* É o padrão da indústria para simulação local fidedigna da API do Kubernetes, permitindo validar manifestos (Deployments, Services) com baixo overhead de recursos e custo zero.
- **Qualidade de Código (Item 10a):** Todos os serviços possuem cobertura de testes unitários (JUnit 5 + Mockito) superior a **80%**, validados via plugin JaCoCo.


---

## 🚀 Como Executar (Local Environment)

### Pré-requisitos

- Java 21 (JDK)

- Docker & Minikube

- Maven Wrapper (incluso)


### Passo a Passo

1. **Inicialize o Cluster:**

  ```bash
  minikube start --driver=docker --listen-address='0.0.0.0'
  eval $(minikube -p minikube docker-env)
  ```

2. **Build das Imagens (Docker):**

  ```bash
  # Na raiz do projeto
  docker build -t book-service:latest ./book-service
  docker build -t shop-service:latest ./shop-service
  ```

3. **Deploy no Kubernetes:**

  ```bash
  kubectl apply -f k8s/
  ```

4. **Acesso aos Endpoints:**

  ```bash
  minikube service book-service --url
  minikube service shop-service --url
  ```

---

## 📂 Estrutura do Repositório (Item 10d)

O projeto segue a estrutura de **Monorepo** para facilitar a gestão de dependências e versionamento atômico.

- `/book-service`: Microsserviço Provedor (Catálogo).

- `/shop-service`: Microsserviço Consumidor (Vendas).

- `/k8s`: Manifestos de infraestrutura (Deployment, Services).
