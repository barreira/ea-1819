FROM ubuntu:18.04

WORKDIR /server 

COPY . .
COPY ./wait-for-it.sh .
RUN chmod +x wait-for-it.sh

RUN apt-get update
RUN apt-get install openjdk-8-jdk-headless -y
RUN apt-get install openjdk-8-jre-headless -y
RUN apt-get install maven -y
RUN apt-get install mysql-client -y
