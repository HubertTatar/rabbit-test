FROM rabbitmq:3.8.2-management-alpine

RUN rabbitmq-plugins enable rabbitmq_top

COPY rabbitmq.conf /etc/rabbitmq/

CMD [ "rabbitmq-server" ]
