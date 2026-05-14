/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avaliacaoReinaldo.n2.service;

import com.avaliacaoReinaldo.n2.entity.Produto;
import com.avaliacaoReinaldo.n2.entity.Usuario;
import com.avaliacaoReinaldo.n2.entity.Venda;
import com.avaliacaoReinaldo.n2.repository.ProdutoRepository;
import com.avaliacaoReinaldo.n2.repository.UsuarioRepository;
import com.avaliacaoReinaldo.n2.repository.VendaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gabriel Ribeiro
 */
@Service
public class VendaService {
    
    private VendaRepository vendaRepository;
    private ProdutoRepository produtoRepository;
    private UsuarioRepository usuarioRepository;

    public VendaService(VendaRepository vendaRepository, ProdutoRepository produtoRepository, UsuarioRepository usuarioRepository) {
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Venda salvar(Venda venda) {
        Produto produtoDB = produtoRepository.findById(venda.getProduto().getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado! ID: " + venda.getProduto().getId()));

        Usuario usuarioDB = usuarioRepository.findById(venda.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado! ID: " + venda.getUsuario().getId()));

        venda.setProduto(produtoDB);
        venda.setUsuario(usuarioDB);

        venda.setValorTotal(produtoDB.getPreco());
        venda.setDataVenda(LocalDateTime.now());

        return vendaRepository.save(venda);
    }

    public List<Venda> listarVendas() {
        return vendaRepository.findAll();
    }

    public Optional<Venda> getVenda(Long id) {
        return vendaRepository.findById(id);
    }

    public Venda editarVenda(Venda venda) {
        Venda vendaExistente = vendaRepository.findById(venda.getId())
                .orElseThrow(() -> new RuntimeException("Venda não encontrada! ID: " + venda.getId()));

        Produto produtoDB = produtoRepository.findById(venda.getProduto().getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));

        Usuario usuarioDB = usuarioRepository.findById(venda.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        vendaExistente.setUsuario(usuarioDB);
        vendaExistente.setProduto(produtoDB);
        vendaExistente.setFormaPagamento(venda.getFormaPagamento());
        vendaExistente.setStatus(venda.getStatus());
        vendaExistente.setValorTotal(produtoDB.getPreco());

        return vendaRepository.save(vendaExistente);
    }

    public void deleteVenda(Long id) {
        vendaRepository.deleteById(id);
    }
    
}
