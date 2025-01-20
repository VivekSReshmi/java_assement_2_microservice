package com.shopping.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shopping.entity.Billing;
import com.shopping.entity.Payment;
import com.shopping.entity.Shopping;
import com.shopping.repository.ShoppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    @Autowired
    private ShoppingRepository shoppingRepository;

    @Autowired
    RestTemplate restTemplate;



    @GetMapping("/cart/{customerId}")
    public ResponseEntity<List<Shopping>> getCartItems(@PathVariable Long customerId) {
        List<Shopping> cartItems = shoppingRepository.findByCustomerId(customerId);
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping("/cart")
    public ResponseEntity<Shopping> addToCart(@RequestBody Shopping shoppingItem) throws JsonProcessingException {
        Shopping response= new Shopping();
        try {
                response = shoppingRepository.save(shoppingItem);
            if (null != response) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                Billing billing = new Billing();
                LocalDate dt = LocalDate.now();
                billing.setBillingDate(dt);
                billing.setAmount(response.getPrice());
                billing.setCustomerId(response.getCustomerId());
                billing.setStatus(response.getStatus());
                billing.setDueDate(dt);
                billing.setInvoiceNumber(String.valueOf(UUID.randomUUID()));
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
                String json = ow.writeValueAsString(billing);
                HttpEntity<String> entity = new HttpEntity<String>(json, headers);
                Billing billReponse = restTemplate.postForObject("http://localhost:8086/billing/invoices", entity, Billing.class);

                if (billReponse != null) {
                    LocalDateTime dt1 = LocalDateTime.now();
                    Payment pt= new Payment();
                    pt.setAmount(billReponse.getAmount());
                    pt.setCustomerId(billReponse.getCustomerId());
                    pt.setStatus(billReponse.getStatus());
                    pt.setInvoiceId(billReponse.getInvoiceNumber());
                    pt.setPaymentDate(dt1);
                    pt.setPaymentMethod("online");
                    pt.setTransactionId(String.valueOf(UUID.randomUUID()));
                   // mapper.registerModule(new JavaTimeModule());
                   // mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                    String json1 = ow.writeValueAsString(pt);
                    HttpEntity<String> entity1 = new HttpEntity<String>(json1, headers);
                    Payment paymentRes = restTemplate.postForObject("http://localhost:8086/payment/pay", entity1, Payment.class);
                    if(paymentRes!=null){
                        shoppingItem.setStatus("success");
                        response = shoppingRepository.save(shoppingItem);
                        paymentRes.setStatus("success");
                        String paymt = ow.writeValueAsString(paymentRes);
                        HttpEntity<String> payEntity = new HttpEntity<String>(paymt, headers);
                        restTemplate.postForObject("http://localhost:8086/payment/pay", payEntity, Payment.class);
                        billReponse.setStatus("success");
                        String billPayload = ow.writeValueAsString(billReponse);
                        HttpEntity<String> billEntity = new HttpEntity<String>(billPayload, headers);
                        restTemplate.postForObject("http://localhost:8086/billing/invoices", billEntity, Billing.class);
                    }

                } else {
                    response.setStatus("Failed to place the order!!");
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
            response.setStatus("Failed to place the order!!");
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/cart/{id}")
    public ResponseEntity<Shopping> updateCartItem(@PathVariable Long id, @RequestBody Shopping updatedItem) {
        if (!shoppingRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedItem.setId(id);
        Shopping savedItem = shoppingRepository.save(updatedItem);
        return ResponseEntity.ok(savedItem);
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long id) {
        if (!shoppingRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        shoppingRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

