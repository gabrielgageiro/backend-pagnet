import React, { useCallback, useState } from "react";
import { Button } from "react-aria-components";
import GitHubButton from "./components/GitHubButton";

function App() {
  const [file, setFile] = useState<File | null>(null);
  const [transactions, setTransactions] = useState([]);
  const [isDragging, setIsDragging] = useState(false);

  const handleDragEnter = useCallback((e: React.DragEvent) => {
    e.preventDefault();
    e.stopPropagation();
    setIsDragging(true);
  }, []);

  const handleDragLeave = useCallback((e: React.DragEvent) => {
    e.preventDefault();
    e.stopPropagation();
    setIsDragging(false);
  }, []);

  const handleDragOver = useCallback((e: React.DragEvent) => {
    e.preventDefault();
    e.stopPropagation();
  }, []);

  const handleDrop = useCallback((e: React.DragEvent) => {
    e.preventDefault();
    e.stopPropagation();
    setIsDragging(false);
    
    if (e.dataTransfer?.files[0]) {
      setFile(e.dataTransfer.files[0]);
    }
  }, []);
  
  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target && e.target?.files[0]) {
      setFile(e.target.files[0]);
    }
  };
  
  return (
    <div className="p-4 max-w-4xl mx-auto">
      
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
            // chamar a API para enviar o arquivo
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
        onDragEnter={handleDragEnter}
        onDragOver={handleDragOver}
        onDragLeave={handleDragLeave}
        onDrop={handleDrop}
        className={`border-2 border-dashed p-4 mb-4 ${
          isDragging ? "border-blue-500 bg-blue-50" : "border-bla"
        }`}
      >
        {file ? file.name : "Arraste e solte o arquivo aqui"}
      </div>

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
