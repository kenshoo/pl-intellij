package com.kenshoo.pl.intellij;

import com.kenshoo.pl.intellij.entities.FieldFlags;
import com.kenshoo.pl.intellij.entities.FieldType;
import org.jetbrains.annotations.NotNull;
import org.jooq.impl.SQLDataType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

import static java.util.Arrays.asList;

public class GalForm {
    public JPanel mainPanel;
    public JTable fields;
    private JTextField entityName;
    private JTextField tableName;


    public GalForm() {
        setupTable();
    }

    private void setupTable() {
        final int rowCount = 100;

        fields.setModel(new DefaultTableModel(new Vector<>(asList(
                "Name",
                "Type",
                "Type length",
                "Flags"

        )), rowCount));
        fields.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(supportedSqlTypes()));
        fields.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(supportedFieldFlags()));
    }

    @NotNull
    private JComboBox<FieldType> supportedSqlTypes() {
        JComboBox<FieldType> fieldTypes = new JComboBox<>();
        fieldTypes.addItem(new FieldType(SQLDataType.INTEGER));
        fieldTypes.addItem(new FieldType(SQLDataType.VARCHAR(0)));
        fieldTypes.addItem(new FieldType(SQLDataType.BOOLEAN));
        fieldTypes.addItem(new FieldType(SQLDataType.FLOAT));
        fieldTypes.addItem(new FieldType(SQLDataType.BIGINT));
        return fieldTypes;
    }

    @NotNull
    private JComboBox<FieldFlags> supportedFieldFlags() {
        JComboBox<FieldFlags> fieldTypes = new JComboBox<>();
        fieldTypes.addItem(new FieldFlags("(none)", false, false));
        fieldTypes.addItem(new FieldFlags("PK", true, false));
        fieldTypes.addItem(new FieldFlags("PK auto incremented", true, true));
        return fieldTypes;
    }

    public JTable getFields() {
        return fields;
    }

    public JTextField getEntityName() {
        return entityName;
    }

    public JTextField getTableName() {
        return tableName;
    }
}
