
# AI Powered Quiz Application

This is a professional end-to-end Quiz Web Application built using:

- Spring Boot + Thymeleaf UI
- OpenAI (ChatGPT) API for dynamic MCQ generation
- Jenkins CI/CD
- Docker
- Kubernetes (k3s)

## Features
- Enter any topic (React, Java, AWS, Kubernetes, etc.)
- Dynamically generates 10 MCQs using ChatGPT
- Answer quiz and view score & percentage
- Fully containerized and deployable on Kubernetes

## Requirements
- Java 21
- Maven
- Docker
- OpenAI API Key

## Run locally
```bash
export OPENAI_API_KEY=sk-xxxx
mvn spring-boot:run
```

Access: http://localhost:8080
