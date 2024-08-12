import React, { useState } from 'react';
import { Button, FileTrigger } from 'react-aria-components';

function App() {
  const [file, setFile] = useState(null);
  const [transactions, setTransactions] = useState([]);

  const handleFileDrop = (files) => {
    if (files && files.length > 0) {
      setFile(files[0]);
    }
  };

  return (
    <div className="p-4 max-w-4xl mx-auto">
      <h1 className="text-2xl font-bold mb-4">Transações CNAB</h1>
      
      <div className="flex gap-2 mb-4">
        <FileTrigger onSelect={handleFileDrop}>
          <Button className="bg-blue-500 text-white px-4 py-2 rounded">
            Choose File
          </Button>
        </FileTrigger>
        <Button 
          className="bg-blue-500 text-white px-4 py-2 rounded"
          onPress={() => {
            // chamar a API para enviar o arquivo
          }}
        >
          Upload File
        </Button>
      </div>

      <FileTrigger onSelect={handleFileDrop}>
        <div className="border-2 border-dashed p-4 mb-4 border-gray-300 cursor-pointer">
          {file ? file.name : 'Drag and drop file here'}
        </div>
      </FileTrigger>

      <Button className="bg-gray-200 text-gray-700 px-4 py-2 rounded mb-4">
        Atualizar Transações
      </Button>

      <div className="mb-4">Espaço para mensagens</div>

      <h2 className="text-xl font-bold mb-2">Transações</h2>
      <div className="border rounded overflow-hidden">
        <div className="bg-gray-100 p-2 font-bold flex justify-between">
          <span>NOME DA LOJA</span>
          <span>Total: R$ 0,00</span>
        </div>
        <table className="w-full">
          <thead>
            <tr className="bg-gray-50">
              <th className="p-2 text-left">Cartao</th>
              <th className="p-2 text-left">CPF</th>
              <th className="p-2 text-left">Data</th>
              <th className="p-2 text-left">Dono da Loja</th>
              <th className="p-2 text-left">Hora</th>
              <th className="p-2 text-left">Nome da Loja</th>
              <th className="p-2 text-left">Tipo</th>
              <th className="p-2 text-left">Valor</th>
            </tr>
          </thead>
          <tbody>
            {transactions.map((transaction, index) => (
              <tr key={index}>
                <td className="p-2">{transaction.cartao}</td>
                <td className="p-2">{transaction.cpf}</td>
                <td className="p-2">{transaction.data}</td>
                <td className="p-2">{transaction.donoDaLoja}</td>
                <td className="p-2">{transaction.hora}</td>
                <td className="p-2">{transaction.nomeDaLoja}</td>
                <td className="p-2">{transaction.tipo}</td>
                <td className="p-2">{transaction.valor}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default App;