package com.oop.cwk;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class VendorController {
    @GetMapping("/vendors")
    public List<Vendor> getVendors() {
        return Main.getVendors();

    }

    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return Main.getCustomers();
    }
}
