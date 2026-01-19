package com.poc.tableentryservice.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "table_entry")
public class TableEntry extends PanacheEntity {

    @Column(name = "number_value", nullable = false)
    public Integer numberValue;

    @Column(name = "selector_value", nullable = false)
    public String selectorValue;

    @Column(name = "free_text", nullable = false)
    public String freeText;

    public TableEntry() {
    }

    public TableEntry(Integer numberValue, String selectorValue, String freeText) {
        this.numberValue = numberValue;
        this.selectorValue = selectorValue;
        this.freeText = freeText;
    }
}
