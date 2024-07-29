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






# ci-app-deployment
## Part 1 (To demonstrate individually)
 - Running the spring-boot web app
    - The webapp is placed in the app/ folder
    - Navigate into the app/ folder
    - Enter the following commands
      - `./mvnw clean install`
      - `./mvnw spring-boot:run`
  - This will start the spring boot server on port `8080`. It will load the data from DatabaseSeeder.java into in memory H2 database
  - It will be available to serve requests. For example: `curl http://localhost/api/jobs/`

## Part 2 (To demonstrate individually) Python tests scripts
  - Required: Execute this command on cmd - `export ci_app_host=localhost`
  - Prerequisite: The Part 1 Webapp must be running on localhost:8080
  - Navigate into the tests/ folder.
  - Run the tests using the following command `pytest -v test-ci-app.py --log-cli-level=DEBUG`
  - In the output you will see it execute the tests and show you output

## Part 3 (Combined webapp and pytests)
  - Navigate to the repository root folder
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




