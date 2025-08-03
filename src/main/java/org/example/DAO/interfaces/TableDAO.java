package org.example.DAO.interfaces;

import org.example.model.Table;
import java.util.List;

public interface TableDAO {
    void addTable(Table table);
    Table getTableById(int tableId);
    List<Table> getAllTables();
    void updateTable(Table table);
    void deleteTable(int tableId);
}
