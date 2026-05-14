import React, { useState, useEffect } from 'react';
import api from '../../services/api';
import Navbar from '../navbar/Navbar';
import './Usuarios.css';

const Usuarios = () => {
    const [usuarios, setUsuarios] = useState([]);
    const [modalAberto, setModalAberto] = useState(false);

    const [idEdicao, setIdEdicao] = useState(null);
    const [nome, setNome] = useState('');
    const [cpf, setCpf] = useState('');
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');

    useEffect(() => {
        carregarUsuarios();
    }, []);

    const carregarUsuarios = async () => {
        try {
            const response = await api.get('/usuarios');
            setUsuarios(response.data);
        } catch (err) {
            console.error("Erro ao carregar usuários:", err);
        }
    };

    const formatarCPF = (valor) => {
        let v = valor.replace(/\D/g, "");

        if (v.length > 11) {
            v = v.slice(0, 11);
        }

        if (v.length > 9) {
            v = v.replace(/(\d{3})(\d{3})(\d{3})(\d{1,2})/, "$1.$2.$3-$4");
        } else if (v.length > 6) {
            v = v.replace(/(\d{3})(\d{3})(\d{1,3})/, "$1.$2.$3");
        } else if (v.length > 3) {
            v = v.replace(/(\d{3})(\d{1,3})/, "$1.$2");
        }

        return v;
    };

    const handleSalvar = async (e) => {
        e.preventDefault();

        const cpfLimpo = cpf.replace(/\D/g, '');

        if (cpfLimpo.length !== 11) {
            alert("O CPF deve estar completo com 11 números!");
            return;
        }

        const usuarioDados = { nome, cpf: cpfLimpo, email, senha };

        try {
            if (idEdicao) {
                await api.put(`/usuarios/${idEdicao}`, usuarioDados);
            } else {
                await api.post('/usuarios', usuarioDados);
            }
            fecharModal();
            carregarUsuarios();
        } catch (err) {
            console.error("Erro ao salvar usuário:", err);
            if (err.response && err.response.status === 409) {
                alert(err.response.data);
            } else {
                alert("Erro ao salvar o usuário. Verifique o console.");
            }
        }
    };

    const handleExcluir = async (id) => {
        if (window.confirm("Deseja excluir este usuário permanentemente?")) {
            try {
                await api.delete(`/usuarios/${id}`);
                carregarUsuarios();
            } catch (err) {
                console.error("Erro ao excluir:", err);
            }
        }
    };

    const abrirModal = (u = null) => {
        if (u) {
            setIdEdicao(u.id);
            setNome(u.nome);
            setCpf(formatarCPF(u.cpf || ''));
            setEmail(u.email);
            setSenha(u.senha || '');
        } else {
            setIdEdicao(null);
            setNome('');
            setCpf('');
            setEmail('');
            setSenha('');
        }
        setModalAberto(true);
    };

    const fecharModal = () => setModalAberto(false);

    return (
        <div>
            <Navbar />
            <h1>Gerenciamento de Usuários</h1>
            <button onClick={() => abrirModal()}>Novo Usuário</button>

            <table className="tabela-usuarios">
                <thead>
                <tr>
                    <th>Nome</th>
                    <th>CPF</th>
                    <th>Email</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                {usuarios.map(u => (
                    <tr key={u.id}>
                        <td>{u.nome}</td>
                        {/* Exibe o CPF mascarado na tabela também */}
                        <td>{formatarCPF(u.cpf || '')}</td>
                        <td>{u.email}</td>
                        <td>
                            <button className="btn-acao" onClick={() => abrirModal(u)}>Editar</button>
                            <button className="btn-acao" onClick={() => handleExcluir(u.id)}>Excluir</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>

            {modalAberto && (
                <div className="modal-overlay">
                    <div className="modal-content">
                        <h2>{idEdicao ? 'Editar Usuário' : 'Novo Usuário'}</h2>
                        <form onSubmit={handleSalvar}>
                            <input
                                placeholder="Nome Completo"
                                value={nome}
                                onChange={e => setNome(e.target.value)}
                                required
                            /><br/><br/>

                            <input
                                placeholder="CPF"
                                value={cpf}
                                onChange={e => setCpf(formatarCPF(e.target.value))}
                                required
                            /><br/><br/>

                            <input
                                type="email"
                                placeholder="E-mail"
                                value={email}
                                onChange={e => setEmail(e.target.value)}
                                required
                            /><br/><br/>

                            <input
                                type="password"
                                placeholder="Senha"
                                value={senha}
                                onChange={e => setSenha(e.target.value)}
                                required={!idEdicao}
                            /><br/><br/>

                            <button type="submit">Salvar</button>
                            <button type="button" onClick={fecharModal}>Cancelar</button>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Usuarios;