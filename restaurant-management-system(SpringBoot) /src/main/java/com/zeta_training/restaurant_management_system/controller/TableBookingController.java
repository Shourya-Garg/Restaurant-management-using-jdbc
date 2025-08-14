package com.zeta_training.restaurant_management_system.controller;

import com.zeta_training.restaurant_management_system.dto.StatusDTO;
import com.zeta_training.restaurant_management_system.dto.TableBookingDTO;
import com.zeta_training.restaurant_management_system.entity.TableBooking;
import com.zeta_training.restaurant_management_system.service.TableBookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/table-booking")
@Slf4j
public class TableBookingController {

    @Autowired
    private TableBookingService tableBookingService;

    @PostMapping("/book")
    public ResponseEntity<String> bookTable(@RequestBody TableBookingDTO tableBookingDTO) {
        log.info("Received table booking request: {}", tableBookingDTO);
        Long bookingId = tableBookingService.bookTable(tableBookingDTO);
        return ResponseEntity.ok("Table booked successfully with ID: " + bookingId);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<TableBooking>> getAllBookings() {
        return ResponseEntity.ok(tableBookingService.getAllBookings());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TableBooking> getBookingById(@PathVariable Long id) {
        log.info("Fetching booking details for ID: {}", id);
        return ResponseEntity.ok(tableBookingService.getBookingById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBooking(@PathVariable Long id, @RequestBody TableBookingDTO tableBookingDTO) {
        log.info("Updating booking with ID: {} with details: {}", id, tableBookingDTO);
        tableBookingService.updateBooking(id, tableBookingDTO);
        return ResponseEntity.ok("Booking updated successfully with ID: " + id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
        log.info("Deleting booking with ID: {}", id);
        tableBookingService.deleteBooking(id);
        return ResponseEntity.ok("Booking with ID: " + id + " deleted successfully");
    }

    @PutMapping("/updateStatus/{id}")
        public ResponseEntity<String> updateBookingStatus(@PathVariable Long id, @RequestBody StatusDTO status) {
        log.info("Updating booking status for ID: {} with status: {}", id, status);
        tableBookingService.updateBookingStatus(id, status);
        return ResponseEntity.ok("Booking status updated successfully with ID: " + id);
    }
}
