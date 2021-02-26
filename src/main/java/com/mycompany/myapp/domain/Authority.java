package com.mycompany.myapp.domain;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import org.bson.codecs.pojo.annotations.BsonId;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * An authority (a security role).
 */
@MongoEntity(collection="jhi_authority")
@RegisterForReflection
public class Authority extends PanacheMongoEntityBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 50)
    @BsonId
    public String name;

    public Authority() {
        //empty
    }

    public Authority(String name) {
        //for jsonb
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Authority)) {
            return false;
        }
        return Objects.equals(name, ((Authority) o).name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "Authority{" + "name='" + name + '\'' + "}";
    }
}
