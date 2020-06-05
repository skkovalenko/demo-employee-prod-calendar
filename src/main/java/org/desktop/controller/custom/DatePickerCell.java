package org.desktop.controller.custom;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;

import static java.time.format.FormatStyle.MEDIUM;

public class DatePickerCell<T> extends TableCell<T, LocalDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(MEDIUM);
    private final DatePicker datePicker;

    public DatePickerCell(TableColumn<T, LocalDate> column) {
        this.datePicker = new DatePicker();
        this.datePicker.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalDate object) {
                String rv = null;
                if(object != null) {
                    rv = formatter.format(object);
                }
                return rv;
            }
            @Override
            public LocalDate fromString(String string) {
                LocalDate rv = null;
                if(!Optional.ofNullable(string).orElse("").isEmpty()) {
                    rv = LocalDate.parse(string, formatter);
                }
                return rv;
            }
        });
        this.datePicker.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                final TableView<T> tableView = getTableView();
                tableView.getSelectionModel().select(getTableRow().getIndex());
                tableView.edit(tableView.getSelectionModel().getSelectedIndex(), column);
            }
        });
        this.datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(isEditing()) {
                commitEdit(newValue);
            }
        });
        editableProperty().bind(column.editableProperty());
        contentDisplayProperty().bind(Bindings
                .when(editableProperty())
                .then(ContentDisplay.GRAPHIC_ONLY)
                .otherwise(ContentDisplay.TEXT_ONLY)
        );
    }

    @Override
    protected void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);
        if(empty) {
            setText(null);
            setGraphic(null);
        } else {
            this.datePicker.setValue(item);
            setGraphic(this.datePicker);
            if(item == null) {
                setText(null);
            } else {
                setText(formatter.format(item));
            }
        }
    }
}