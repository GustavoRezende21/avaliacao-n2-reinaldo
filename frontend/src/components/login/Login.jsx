import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../../services/api';

const Login = () => {
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [erro, setErro] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setErro('');

        try {
            const response = await api.post('/usuarios/login', {
                email: email,
                senha: senha
            });

            localStorage.setItem('usuarioLogado', email);
            navigate('/produtos');

        } catch (err) {
            if (err.response && err.response.status === 401) {
                setErro('E-mail ou senha incorretos.');
            } else {
                setErro('Servidor indisponível no momento.');
            }
            console.error("Detalhes do erro:", err);
        }
    };

    return (
        <div>
            <h2>Acesso ao Sistema</h2>
            {erro && <p style={{ color: 'red' }}>{erro}</p>}
            <form onSubmit={handleLogin}>
                <div>
                    <label>E-mail: </label>
                    <input
                        type="email"
                        placeholder="seuemail@dominio.com"
                        required
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </div>
                <br />
                <div>
                    <label>Senha: </label>
                    <input
                        type="password"
                        placeholder="******"
                        required
                        value={senha}
                        onChange={(e) => setSenha(e.target.value)}
                    />
                </div>
                <br />
                <button type="submit">Entrar</button>
            </form>
        </div>
    );
};

export default Login;