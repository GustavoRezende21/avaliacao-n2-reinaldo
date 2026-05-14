import React, { useState, useEffect } from 'react';
import api from '../../services/api';
import Navbar from '../navbar/Navbar';
import './Produtos.css';

const Produtos = () => {
    const [produtos, setProdutos] = useState([]);
    const [categorias, setCategorias] = useState([]);
    const [modalAberto, setModalAberto] = useState(false);

    const [idEdicao, setIdEdicao] = useState(null);
    const [nome, setNome] = useState('');
    const [preco, setPreco] = useState('');
    const [categoria, setCategoria] = useState('');

    useEffect(() => {
        carregarDados();
    }, []);

    const carregarDados = async () => {
        try {
            // Traz os produtos
            const resProd = await api.get('/produtos');
            setProdutos(resProd.data);

            // Tenta buscar as categorias da API
            try {
                const resCat = await api.get('/produtos/categorias');
                setCategorias(resCat.data);
            } catch (errCat) {
                console.warn("Rota de categorias não encontrada. Usando categorias padrão.");
                setCategorias(['HARDWARE', 'PERIFERICOS', 'JOGOS', 'COMPUTADORES']);
            }

        } catch (err) {
            console.error("Erro ao carregar dados:", err);
        }
    };

    const handleSalvar = async (e) => {
        e.preventDefault();
        const prod = { nome, preco, categoria };

        try {
            if (idEdicao) {
                // 🟢 CORRIGIDO: Usando a crase para embutir o ID na URL!
                await api.put(`/produtos/${idEdicao}`, prod);
            } else {
                await api.post('/produtos', prod);
            }
            fecharModal();
            carregarDados();
        } catch (err) {
            console.error("Erro ao salvar produto:", err);
            alert("Erro ao salvar produto. Verifique o console ou a conexão.");
        }
    };

    const handleExcluir = async (id) => {
        if (window.confirm("Excluir este produto?")) {
            try {
                await api.delete(`/produtos/${id}`);
                carregarDados();
            } catch (err) {
                console.error("Erro ao excluir:", err);
            }
        }
    };

    const abrirModal = (p = null) => {
        if (p) {
            setIdEdicao(p.id);
            setNome(p.nome);
            setPreco(p.preco);
            setCategoria(p.categoria);
        } else {
            setIdEdicao(null);
            setNome('');
            setPreco('');
            setCategoria('');
        }
        setModalAberto(true);
    };

    const fecharModal = () => setModalAberto(false);

    return (
        <div>
            <Navbar />
            <h1>Produtos</h1>
            <button onClick={() => abrirModal()}>Novo Produto</button>

            <table className="tabela-produtos">
                <thead>
                <tr>
                    <th>Nome</th>
                    <th>Preço</th>
                    <th>Categoria</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                {produtos.map(p => (
                    <tr key={p.id}>
                        <td>{p.nome}</td>
                        <td>{p.preco}</td>
                        <td>{p.categoria}</td>
                        <td>
                            <button className="btn-acao" onClick={() => abrirModal(p)}>Editar</button>
                            <button className="btn-acao" onClick={() => handleExcluir(p.id)}>Excluir</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>

            {modalAberto && (
                <div className="modal-overlay">
                    <div className="modal-content">
                        <h2>{idEdicao ? 'Editar Produto' : 'Novo Produto'}</h2>
                        <form onSubmit={handleSalvar}>
                            <input
                                placeholder="Nome"
                                value={nome}
                                onChange={e => setNome(e.target.value)}
                                required
                            /><br/><br/>

                            <input
                                type="number"
                                placeholder="Preço"
                                value={preco}
                                onChange={e => setPreco(e.target.value)}
                                required
                                step="0.01"
                            /><br/><br/>

                            <select value={categoria} onChange={e => setCategoria(e.target.value)} required>
                                <option value="">Selecione a categoria...</option>
                                {categorias.map(c => <option key={c} value={c}>{c}</option>)}
                            </select><br/><br/>

                            <button type="submit">Salvar</button>
                            <button type="button" onClick={fecharModal}>Cancelar</button>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Produtos;