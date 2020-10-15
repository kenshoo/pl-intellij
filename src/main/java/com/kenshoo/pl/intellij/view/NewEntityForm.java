package com.kenshoo.pl.intellij.view;

import com.kenshoo.pl.intellij.model.FieldFlags;
import com.kenshoo.pl.intellij.model.FieldType;
import org.jetbrains.annotations.NotNull;
import org.jooq.impl.SQLDataType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

import static java.util.Arrays.asList;

public class NewEntityForm {
    public JPanel mainPanel;
    private JTable fields;
    private JTextField entityName;
    private JTextField tableName;

    public static final Integer FIELD_NAME_COL = 0;
    public static final Integer FIELD_TYPE_COL = 1;
    public static final Integer FIELD_FLAGS_COL = 2;


    public NewEntityForm() {
        setupTable();
    }

    private void setupTable() {
        final int rowCount = 100;

        fields.setModel(new DefaultTableModel(new Vector<>(asList(
                "Name",
                "Type",
                "Flags"

        )), rowCount));
        fields.getColumnModel().getColumn(FIELD_TYPE_COL).setCellEditor(new DefaultCellEditor(supportedSqlTypes()));
        fields.getColumnModel().getColumn(FIELD_FLAGS_COL).setCellEditor(new DefaultCellEditor(supportedFieldFlags()));
    }

    @NotNull
    private JComboBox<FieldType> supportedSqlTypes() {
        JComboBox<FieldType> fieldTypes = new JComboBox<>();
        fieldTypes.addItem(new FieldType(SQLDataType.INTEGER));
        fieldTypes.addItem(new FieldType(SQLDataType.VARCHAR(0)));
        fieldTypes.addItem(new FieldType(SQLDataType.BOOLEAN));
        fieldTypes.addItem(new FieldType(SQLDataType.FLOAT));
        fieldTypes.addItem(new FieldType(SQLDataType.BIGINT));
        fieldTypes.addItem(new FieldType(SQLDataType.TIMESTAMP));
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
