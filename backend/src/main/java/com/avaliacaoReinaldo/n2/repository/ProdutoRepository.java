/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avaliacaoReinaldo.n2.repository;

import com.avaliacaoReinaldo.n2.entity.Produto;
import com.avaliacaoReinaldo.n2.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Gabriel Ribeiro
 */
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
}
