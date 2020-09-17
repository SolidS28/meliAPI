FROM openjdk:8-jre-alpine
ENV ITEM_SERVICE item-service-0.0.1-SNAPSHOT.jar
ENV DISCOVERY_SERVICE discovery-service-0.0.1-SNAPSHOT.jar
ENV GATEWAY_SERVICE gateway-service-0.0.1-SNAPSHOT.jar
ENV PERSISTANCE_SERVICE persistance-service-0.0.1-SNAPSHOT.jar
ENV APP_HOME /app
EXPOSE 8080
COPY challlenge/item-service/target/$ITEM_SERVICE $APP_HOME/
COPY challlenge/discovery-service/target/$DISCOVERY_SERVICE $APP_HOME/
COPY challlenge/gateway-service/target/$GATEWAY_SERVICE $APP_HOME/
COPY challlenge/persistance-service/target/$PERSISTANCE_SERVICE $APP_HOME/
WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $DISCOVERY_SERVICE"]
CMD ["exec java -jar $ITEM_SERVICE"]
CMD ["exec java -jar $GATEWAY_SERVICE"]
CMD ["exec java -jar $PERSISTANCE_SERVICE"]