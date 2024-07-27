FROM python:3.12.4-slim-bullseye
ENV TZ="America/New_York"
USER root
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN apt update && apt install ca-certificates wget -y && apt install git -y
RUN pip install -r requirements.txt
ENV ci_app_host 10.5.0.6
ENTRYPOINT ["pytest","test-ci-app.py", "--log-cli-level=DEBUG"]