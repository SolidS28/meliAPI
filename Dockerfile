FROM openjdk:8-jre-alpine
ENV ITEM_SERVICE item-service-1.1.jar
ENV DISCOVERY_SERVICE discovery-service-0.0.1-SNAPSHOT.jar
ENV GATEWAY_SERVICE gateway-service-0.0.1-SNAPSHOT.jar
ENV APP_HOME /app
EXPOSE 8080
COPY challlenge/item-service/target/$ITEM_SERVICE $APP_HOME/
COPY challlenge/discovery-service/target/$DISCOVERY_SERVICE $APP_HOME/
COPY challlenge/gateway-service/target/$GATEWAY_SERVICE $APP_HOME/
COPY app.sh $APP_HOME/
WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]
RUN ["exec java -jar $DISCOVERY_SERVICE"]
RUN ["exec java -jar $ITEM_SERVICE"]
CMD ["exec java -jar $GATEWAY_SERVICE"]