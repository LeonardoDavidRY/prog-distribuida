import { useState } from 'react';
import './App.css';
import axios from "axios";

// 1. Definimos la interfaz del objeto Author según lo que retorna Quarkus
interface Author {
  id: number;
  name: string;
}
interface Book {
  isbn: string;
  price: number;
  title: string;
  author: Array<Author>;
}

function App() {
  // 2. Creamos el estado para almacenar la lista de autores


  const [authors, setAuthors] = useState<Author[]>([]);
  const [books, setBooks] = useState<Book[]>([]);

  const handleListarAutores = () => {
    axios.get<Author[]>("http://localhost/app-authors/authors")
        .then(response => {
          setAuthors(response.data);
        })
        .catch(error => alert(error));
  };

  const handleListarLibros = () => {
    axios.get<Book[]>("http://localhost/app-books/books")
        .then(response => {
          // Corregido: antes usaba setAuthors por error
          setBooks(response.data);
        })
        .catch(error => alert(error));
  };


  return (
      <>
        <section id="center">
          <div>
            <h1>Get started</h1>
            <p>
              Edit <code>src/App.tsx</code> and save to test <code>HMR</code>
            </p>
          </div>

          <button onClick={handleListarAutores}>Consultar</button>

          <br />
          {/* 4. Renderizamos dinámicamente la lista guardada en el estado */}
          {
            authors.map((author: Author) => (
                <p key={author.id}>{author.id} - {author.name}</p>
            ))
          }
        </section>
        <div className="ticks"></div>
        <section id="spacer"></section>
        <br />

        <section id="center">
          <div>
            <h2>Books</h2>
          </div>

          <button onClick={handleListarLibros}>Consultar</button>

          <br />
          {
            books.map((author: Book) => (
                <p key={author.isbn}>{author.isbn} - {author.title}</p>
            ))
          }
        </section>

      </>
  );
}

export default App;
