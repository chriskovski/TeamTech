FROM ubuntu:latest
RUN apt-get update && apt-get install -y postgresql-client
RUN cp /usr/bin/pg_config /usr/local/bin/

FROM python:3.7-alpine
WORKDIR /code
ENV FLASK_APP=python_implimentation.py
ENV FLASK_RUN_HOST=0.0.0.0
RUN apk add --no-cache gcc musl-dev linux-headers
COPY requirements.txt requirements.txt
RUN pip install -r requirements.txt
EXPOSE 5000
COPY . .
CMD ["flask", "run"]
