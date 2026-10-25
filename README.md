# Spring Book Service — AWS ECS CI/CD sample

A small Spring Boot REST API used to demonstrate GitHub → CodePipeline → CodeBuild → ECR → ECS Fargate.

## Endpoints
- `GET /health` — health response
- `GET /api/books` — list books
- `GET /api/books/{id}` — get one book
- `POST /api/books` — create a book
- `PUT /api/books/{id}` — update a book
- `DELETE /api/books/{id}` — delete a book

Data is stored in memory to keep this learning project simple and avoid a paid database. Data resets when the container restarts.

## Local run
Requires Java 17 and Maven:
```bash
mvn test
mvn spring-boot:run
```
Open `http://localhost:8080/health` or `http://localhost:8080/api/books`.

## Container run
```bash
docker build -t spring-book-app .
docker run --rm -p 8080:8080 spring-book-app
```

## AWS assumptions in buildspec.yml
- Region: `us-east-1`
- ECR repository: `spring-book-app`
- ECS task definition container name: `book-app`
- CodeBuild environment has privileged mode enabled
- CodeBuild role can push to ECR and write CloudWatch logs
- For the ECS standard deploy action, the build artifact `imagedefinitions.json` must be passed to the deploy stage.
