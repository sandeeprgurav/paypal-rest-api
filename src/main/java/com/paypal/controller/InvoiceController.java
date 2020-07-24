package com.paypal.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.entity.Invoice;
import com.paypal.service.IInvoiceService;

@CrossOrigin
@RestController
@RequestMapping(value = "/invoice")
public class InvoiceController {
	private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
	
	@Autowired
	private IInvoiceService invoiceService;
			
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Invoice>> list(@RequestParam(name="processDate", required=true) String processDate) {
		logger.info("Get all Invoices");
		List<Invoice> invoices = invoiceService.findInvoices(processDate);
		
		if(invoices != null) {
			return new ResponseEntity<List<Invoice>>(invoices, HttpStatus.OK);
		}else {
			return new ResponseEntity<List<Invoice>>(HttpStatus.NOT_FOUND);
		}
	}
		
}

