package ru.leadexsystems.startup.jobmonitor.taskmanager.utils;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by Maxim Ivanov.
 */
@Component
public class MongoDbManager {
  @Value("${mongo.host}")
  private String dbhost;
  @Value("${mongo.port}")
  private Integer dbport;
  @Value("${mongo.db}")
  private String dbname;
  @Value("${mongo.user}")
  private String dbuser;
  @Value("${mongo.password}")
  private String dbpassword;

  private MongoClient client = null;
  final Morphia morphia = new Morphia();
  Datastore datastore = null;

  public MongoDbManager() {
  }

  public MongoClient getClient() {
    if (client == null) {
      client = new MongoClient(new ServerAddress(dbhost, dbport),
          Arrays.asList(MongoCredential.createCredential(dbuser, dbname, dbpassword.toCharArray())));
    }

    return client;
  }

  public Datastore getDatastore() {
    if (datastore == null) {
      datastore = morphia.createDatastore(getClient(), dbname);
    }

    return datastore;
  }

  public MongoDatabase getDatabase() {
    return getClient().getDatabase(dbname);
  }
}
