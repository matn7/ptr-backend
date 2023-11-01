FROM mysql

COPY ./init-schema.sql /tmp

CMD [ "mysqld", "--init-file=/tmp/init-schema.sql" ]
