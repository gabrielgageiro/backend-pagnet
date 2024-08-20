import React, { useCallback, useEffect, useState } from "react";
import { Button } from "react-aria-components";
import GitHubButton from "./components/GitHubButton";
import axios from "axios";

function App() {
  interface Transaction {
    tipo: number;
    data: string;
    valor: number;
    cpf: string;
    cartao: string;
    hora: string;
    donoLoja: string;
    nomeLoja: string;
  }

  interface TransactionWrapper {
    total: number;
    nomeLoja: string;
    transacoes: Transaction[];
  }
  const [file, setFile] = useState<null>(null);
  const [TransactionWrapper, setTransactionWrapper] = useState([]);

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target && e.target?.files[0]) {
      setFile(e.target.files[0]);
    }
  };

  const getTransactions = async () => {
    axios.get("http://localhost:8080/api/transacoes").then((response) => {
      console.log(response.data);
      setTransactionWrapper(response.data);
    });
  }

  const uploadFile = async (file: File) => {
    if(!file){
      return;
    }
    const formData = new FormData();
    formData.append('file', file);
    axios.post("http://localhost:8080/api/transacoes/upload", formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
  }

  const formatCurrency = (value) => {
    const formattedValue = new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    }).format(parseFloat(value));

    return formattedValue;
  };

  useEffect(() => {
    getTransactions();
  }, []);

  return (
    <div className="p-4 mx-auto">
      
      <GitHubButton href="https://github.com/gabrielgageiro/backend-pagnet"/>
      
      <h1 className="text-2xl font-bold mb-4">Transações CNAB</h1>

      <div className="flex gap-2 mb-4">
        <Button
          className="bg-blue-500 text-white px-4 py-2 rounded"
          onPress={() => document.getElementById("fileInput")?.click()}
        >
          Escolher Arquivo
        </Button>
        <Button
          className="bg-blue-500 text-white px-4 py-2 rounded"
          onPress={() => {
            console.log('enviando arquivo', file)
            uploadFile(file);
          }}
        >
          Enviar Arquivo
        </Button>
        <input
          id="fileInput"
          type="file"
          className="hidden"
          onChange={handleFileChange}
          accept=".txt"
        />
      </div>

      <div
        className={`border-2 border-dashed p-4 mb-4 ${
            !file ? "hidden" : ""
        }`}
      >
        <span className="font-bold">Arquivo: {file? file.name : ""}</span>
      </div>

      <h2 className="text-xl font-bold mb-2">Transações</h2>
      <ul className="bg-white shadow-md rounded-md p-4">
        {TransactionWrapper.length == 0 ? (
            <p className="mb-4 text-gray-500 text-center">Sem transações disponíveis.</p>
        ) : (
            TransactionWrapper.map((wrapper, key) => (
                <li
                    key={key}
                    className="mb-4 p-4 border-b border-gray-300 flex flex-col"
                >
                  <div className="flex justify-between items-center mb-2">
                    <div className="text-xl font-semibold">{wrapper.nomeLoja}</div>
                    <div className={`font-semibold`}>
                      Total: {formatCurrency(parseFloat(wrapper.total))}
                    </div>
                  </div>

                  <table className="table-auto w-full">
                  <thead>
                    <tr>
                      <th className="px-4 py-2">Cartão</th>
                      <th className="px-4 py-2">CPF</th>
                      <th className="px-4 py-2">Data</th>
                      <th className="px-4 py-2">Dono da Loja</th>
                      <th className="px-4 py-2">Hora</th>
                      <th className="px-4 py-2">Nome da Loja</th>
                      <th className="px-4 py-2">Tipo</th>
                      <th className="px-4 py-2">Valor</th>
                    </tr>
                    </thead>
                    <tbody>
                    {wrapper.transacoes.map((transacao, index) => (
                        <tr key={index} className={index % 2 === 0 ? 'bg-gray-100' : ''}>
                          <td className="px-4 py-2">{transacao.cartao}</td>
                          <td className="px-4 py-2">{transacao.cpf}</td>
                          <td className="px-4 py-2">{transacao.data}</td>
                          <td className="px-4 py-2">{transacao.donoLoja}</td>
                          <td className="px-4 py-2">{transacao.hora}</td>
                          <td className="px-4 py-2">{transacao.nomeLoja}</td>
                          <td className="px-4 py-2">{transacao.tipo}</td>
                          <td className="px-4 py-2">{formatCurrency(transacao.valor)}</td>
                        </tr>
                    ))}
                    </tbody>
                  </table>
                </li>
            ))
        )}
      </ul>
    </div>
  );
}

export default App;
