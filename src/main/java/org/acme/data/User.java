package org.acme.data;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends PanacheEntity {

    /**
     * The unique identifier for the user. We'll use this to tie
     * trades and assets to a specific person.
     */
    @Column(unique = true, nullable = false)
    public String userId;

    /**
     * A name or alias for the user.
     */
    public String name;
}
