package org.example.DAO.interfaces;

import org.example.model.SalesReport;

import java.util.List;

public interface SalesReportDAO {
    void generateReport(SalesReport report);
    List<SalesReport> getAllReports();
}

