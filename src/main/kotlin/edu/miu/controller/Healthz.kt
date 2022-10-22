package edu.miu.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Healthz {

    @GetMapping("/health")
    fun healthz(): ResponseEntity<HashMap<String, String>> {
        val map = HashMap<String, String>()
        map["status"] = "OK"
        return ResponseEntity.ok(map);
    }

    @GetMapping
    fun health(): ResponseEntity<HashMap<String, String>> {
        return healthz()
    }

}