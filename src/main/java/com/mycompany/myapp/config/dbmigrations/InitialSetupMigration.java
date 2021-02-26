package com.mycompany.myapp.config.dbmigrations;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoDatabase;
import com.mycompany.myapp.security.AuthoritiesConstants;
import com.mycompany.myapp.domain.Authority;
import java.time.Instant;
import java.util.Arrays;

import com.mycompany.myapp.domain.User;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 * Creates the initial database setup.
 */
@ChangeLog(order = "001")
public class InitialSetupMigration {

  @ChangeSet(order = "01", author = "initiator", id = "01-addAuthorities")
  public void addAuthorities(MongoDatabase db) {
    Authority adminAuthority = new Authority(AuthoritiesConstants.ADMIN);
    Authority userAuthority = new Authority(AuthoritiesConstants.USER);

    db.createCollection("jhi_authority");
    db
      .getCollection("jhi_authority", Authority.class)
      .withCodecRegistry(getCodecRegistry())
      .insertMany(Arrays.asList(adminAuthority, userAuthority));
  }

  @ChangeSet(order = "02", author = "initiator", id = "02-addUsers")
  public void addUsers(MongoDatabase db) {
    Authority adminAuthority = new Authority(AuthoritiesConstants.ADMIN);
    Authority userAuthority = new Authority(AuthoritiesConstants.USER);

    User anonymousUser = new User();
    anonymousUser.id = "user-1";
    anonymousUser.login = "anonymoususer";
    anonymousUser.password = "$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO";
    anonymousUser.firstName = "Anonymous";
    anonymousUser.lastName = "User";
    anonymousUser.email = "anonymous@localhost";
    anonymousUser.activated = true;
    anonymousUser.langKey = "en";
    anonymousUser.createdDate = Instant.now();

    User adminUser = new User();
    adminUser.id = "user-2";
    adminUser.login = "admin";
    adminUser.password = "$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC";
    adminUser.firstName = "admin";
    adminUser.lastName = "Administrator";
    adminUser.email = "admin@localhost";
    adminUser.activated = true;
    adminUser.langKey = "en";
    adminUser.createdDate = Instant.now();
    adminUser.authorities.add(adminAuthority);
    adminUser.authorities.add(userAuthority);

    User userUser = new User();
    userUser.id = "user-3";
    userUser.login = "user";
    userUser.password = "$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K";
    userUser.firstName = "";
    userUser.lastName = "User";
    userUser.email = "user@localhost";
    userUser.activated = true;
    userUser.langKey = "en";
    userUser.createdDate = Instant.now();
    userUser.authorities.add(userAuthority);

    db.createCollection("jhi_user");
    db
      .getCollection("jhi_user", User.class)
      .withCodecRegistry(getCodecRegistry())
      .insertMany(Arrays.asList(adminUser, anonymousUser, userUser));
  }

  private CodecRegistry getCodecRegistry() {
    CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
    return fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
  }
}
