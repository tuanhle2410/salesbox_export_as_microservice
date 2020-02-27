package com.salesbox.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by lent on 6/6/2015.
 */
@Embeddable
public class Metadata implements Serializable {
    @Column(name="metadatas", nullable = false, columnDefinition = "uuid")
    @Type(type = "pg-uuid")
    private UUID metadatas;

    public Metadata() {}

    public Metadata(String uuidString) {
        this.metadatas = UUID.fromString(uuidString);
    }

    public Metadata(UUID uuid) {
        this.metadatas = uuid;
    }

    public UUID getMetadatas() {
        return metadatas;
    }

    public void setMetadatas(UUID metadata) {
        this.metadatas = metadata;
    }
}
