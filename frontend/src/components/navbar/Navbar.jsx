import React from 'react';
import { Link, useNavigate } from 'react-router-dom';

const Navbar = () => {
    const navigate = useNavigate();
    const usuario = localStorage.getItem('usuarioLogado');

    const handleSair = () => {
        localStorage.removeItem('usuarioLogado');
        navigate('/');
    };

    return (
        <nav>
            <div className="nav-links">
                <Link to="/usuarios">Usuários</Link> | 
                <Link to="/produtos"> Produtos</Link> | 
                <Link to="/vendas"> Vendas</Link>
            </div>
            <div>
                <span>Usuário Logado: {usuario}</span>
                <button onClick={handleSair}>Sair</button>
            </div>
            <hr />
        </nav>
    );
};

export default Navbar;