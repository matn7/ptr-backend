FROM centos

# Execute command relative to this path
#WORKDIR /usr/app

# Copy required properties files

RUN mkdir -p /root/.pandatronik

COPY ./application-common.properties /root/.pandatronik

RUN yum install -y java

VOLUME /tmp
ADD /pandatronik-rest-app-1.0-SNAPSHOT.jar pandatronik.jar
RUN sh -c 'touch /pandatronik.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/pandatronik.jar"]