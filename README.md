# ci-app-deployment

Requirements:
- codebase
- docker installation


## Steps to run:

1. build docker image of app
```bash
cd app/
docker build -t ci-app:latest . --no-cache
```

2. build docker image of tests
```bash
cd tests/
docker build -t ci-app-test:latest . --no-cache
```

3. Run both app and tests using docker-compose
```bash
docker compose up
```
