package com.mycompany.myapp;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class MongoDbTestResource implements QuarkusTestResourceLifecycleManager {
  private MongodExecutable mongodExecutable;

  @Override
  public Map<String, String> start() {
    String ip = "localhost";
    int port = 37017;

    try {
      IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
          .net(new Net(ip, port, Network.localhostIsIPv6()))
          .build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();
        return Collections.emptyMap();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void stop() {
    mongodExecutable.stop();
  }
}
