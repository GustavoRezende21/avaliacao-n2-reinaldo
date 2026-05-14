/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avaliacaoReinaldo.n2.controller;

import com.avaliacaoReinaldo.n2.entity.Venda;
import com.avaliacaoReinaldo.n2.enuns.FormaPagamento;
import com.avaliacaoReinaldo.n2.enuns.StatusVenda;
import com.avaliacaoReinaldo.n2.service.VendaService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Gabriel Ribeiro
 */
@RestController
@RequestMapping("/vendas")
@CrossOrigin(origins = "*")
public class VendaController {
    
    private VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping
    public ResponseEntity<?> salvarVenda(@RequestBody Venda venda) {
        try {
            Venda nova = vendaService.salvar(venda);
            return ResponseEntity.ok(nova);
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @GetMapping
    public List<Venda> listarVendas() {
        return vendaService.listarVendas();
    }

    @GetMapping("/{id}")
    public Venda getVenda(@PathVariable("id") Long id) {
        return vendaService.getVenda(id).orElse(null);
    }

    @GetMapping("/formas-pagamento")
    public List<String> listarFormasPagamento() {
        return Arrays.stream(FormaPagamento.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @GetMapping("/status")
    public List<String> listarStatus() {
        return Arrays.stream(StatusVenda.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @PutMapping
    public Venda editarVenda(@RequestBody Venda venda) {
        return vendaService.editarVenda(venda);
    }

    @DeleteMapping("/{id}")
    public void deleteVenda(@PathVariable Long id) {
        vendaService.deleteVenda(id);
    }
}
