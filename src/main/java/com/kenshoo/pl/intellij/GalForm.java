package com.kenshoo.pl.intellij;

import org.jetbrains.annotations.NotNull;
import org.jooq.DataType;
import org.jooq.impl.SQLDataType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

import static java.util.Arrays.asList;

public class GalForm {
    public JPanel mainPanel;
    public JTable table1;
    private JTextField entityNameTextField;


    public GalForm() {
        setupTable();
    }

    private void setupTable() {

        final int rowCount = 100;

        table1.setModel(new DefaultTableModel(new Vector<>(asList(
                "Name",
                "Type",
                "Type length",
                "Flags"

        )), rowCount));

        table1.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(supportedSqlTypes()));
        table1.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(supportedFieldFlags()));
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

    public static class FieldFlags {
        public FieldFlags(String displayName, boolean pk, boolean autoInc) {
            this.displayName = displayName;
            this.autoInc = autoInc;
            this.pk = pk;
        }

        private final String displayName;
        private final boolean autoInc;
        private final boolean pk;

        @Override
        public String toString() {
            return displayName;
        }

    }

    public static class FieldType {
        private final String sqlType;
        private final Class javaType;

        public FieldType(DataType type) {
            this.sqlType = type.getTypeName();
            this.javaType = type.getType();
        }

        @Override
        public String toString() {
            return sqlType;
        }
    }
}
