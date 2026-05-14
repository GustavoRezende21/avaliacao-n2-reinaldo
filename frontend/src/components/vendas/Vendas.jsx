import React, { useState, useEffect } from 'react';
import api from '../../services/api';
import Navbar from '../navbar/Navbar';

const Vendas = () => {
    const [vendas, setVendas] = useState([]);
    const [usuarios, setUsuarios] = useState([]);
    const [produtos, setProdutos] = useState([]);
    const [formasPagamento, setFormasPagamento] = useState([]);
    const [statusOpcoes, setStatusOpcoes] = useState([]);

    const [modalAberto, setModalAberto] = useState(false);

    const [idEdicao, setIdEdicao] = useState(null);
    const [usuarioId, setUsuarioId] = useState('');
    const [produtoId, setProdutoId] = useState('');
    const [formaPagamento, setFormaPagamento] = useState('');
    const [status, setStatus] = useState('');

    useEffect(() => {
        carregarDados();
    }, []);

    const carregarDados = async () => {
        try {
            const [resVendas, resUsers, resProd, resFormas, resStatus] = await Promise.all([
                api.get('/vendas'),
                api.get('/usuarios'),
                api.get('/produtos'),
                api.get('/vendas/formas-pagamento'),
                api.get('/vendas/status')
            ]);
            setVendas(resVendas.data);
            setUsuarios(resUsers.data);
            setProdutos(resProd.data);
            setFormasPagamento(resFormas.data);
            setStatusOpcoes(resStatus.data);
        } catch (err) {
            console.error("Erro ao carregar dados", err);
        }
    };

    const handleSalvarVenda = async (e) => {
        e.preventDefault();

        const vendaDados = {
            usuario: { id: Number(usuarioId) },
            produto: { id: Number(produtoId) },
            formaPagamento: formaPagamento,
            status: status
        };

        try {
            if (idEdicao) {
                await api.put('/vendas', { ...vendaDados, id: idEdicao });
            } else {
                await api.post('/vendas', vendaDados);
            }
            fecharModal();
            carregarDados();
        } catch (err) {
            alert(err.response?.data || "Erro ao processar venda");
        }
    };

    const abrirModal = (v = null) => {
        if (v) {
            setIdEdicao(v.id);
            setUsuarioId(v.usuario?.id || '');
            setProdutoId(v.produto?.id || '');
            setFormaPagamento(v.formaPagamento);
            setStatus(v.status);
        } else {
            setIdEdicao(null);
            setUsuarioId('');
            setProdutoId('');
            setFormaPagamento('');
            setStatus('');
        }
        setModalAberto(true);
    };

    const fecharModal = () => {
        setModalAberto(false);
    };

    const handleExcluir = async (id) => {
        if (window.confirm("Deseja deletar esta venda?")) {
            try {
                await api.delete(`/vendas/${id}`);
                carregarDados();
            } catch (err) {
                console.error(err);
            }
        }
    };

    return (
        <div>
            <Navbar />
            <h1>Histórico de Vendas</h1>
            <button onClick={() => abrirModal()}>Registrar Venda</button>

            <table className="tabela-produtos">
                <thead>
                    <tr>
                        <th>Data</th>
                        <th>Cliente</th>
                        <th>Produto</th>
                        <th>Pagamento</th>
                        <th>Total</th>
                        <th>Status</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {vendas.map(v => (
                        <tr key={v.id}>
                            <td>{new Date(v.dataVenda).toLocaleString()}</td>
                            <td>{v.usuario ? v.usuario.nome : 'Sem Cliente'}</td>
                            <td>{v.produto ? v.produto.nome : 'Sem Produto'}</td>
                            <td>{v.formaPagamento}</td>
                            <td>R$ {v.valorTotal?.toFixed(2)}</td>
                            <td>{v.status}</td>
                            <td>
                                <button className="btn-acao" onClick={() => abrirModal(v)}>Editar</button>
                                <button className="btn-acao" onClick={() => handleExcluir(v.id)}>Excluir</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

            {modalAberto && (
                <div className="modal-overlay">
                    <div className="modal-content">
                        <h2>{idEdicao ? 'Editar Venda' : 'Registrar Venda'}</h2>
                        <form onSubmit={handleSalvarVenda}>

                            <label>Cliente:</label><br />
                            <select value={usuarioId} onChange={e => setUsuarioId(e.target.value)} required>
                                <option value="">Selecione...</option>
                                {usuarios.map(u => <option key={u.id} value={u.id}>{u.nome}</option>)}
                            </select>
                            <br /><br />

                            <label>Produto:</label><br />
                            <select value={produtoId} onChange={e => setProdutoId(e.target.value)} required>
                                <option value="">Selecione...</option>
                                {produtos.map(p => <option key={p.id} value={p.id}>{p.nome} - R$ {p.preco}</option>)}
                            </select>
                            <br /><br />

                            <label>Forma de Pagamento:</label><br />
                            <select value={formaPagamento} onChange={e => setFormaPagamento(e.target.value)} required>
                                <option value="">Selecione...</option>
                                {formasPagamento.map(f => <option key={f} value={f}>{f}</option>)}
                            </select>
                            <br /><br />

                            <label>Status:</label><br />
                            <select value={status} onChange={e => setStatus(e.target.value)} required>
                                <option value="">Selecione...</option>
                                {statusOpcoes.map(s => <option key={s} value={s}>{s}</option>)}
                            </select>
                            <br /><br />

                            <button type="submit">{idEdicao ? 'Atualizar' : 'Finalizar'}</button>
                            <button type="button" onClick={fecharModal}>Cancelar</button>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Vendas;