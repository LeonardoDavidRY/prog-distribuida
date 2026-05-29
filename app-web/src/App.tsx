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
      <main className="app">
        <header className="app-header">
          <h1>Catalogo</h1>
        </header>

        <section className="panel">
          <div className="panel-header">
            <h2>Authors</h2>
            <button className="btn" onClick={handleListarAutores}>Consultar</button>
          </div>

          <div className="table-wrap">
            <table className="table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Nombre</th>
                </tr>
              </thead>
              <tbody>
                {authors.length === 0 ? (
                    <tr>
                      <td colSpan={2} className="empty">Sin resultados</td>
                    </tr>
                ) : (
                    authors.map((author: Author) => (
                        <tr key={author.id}>
                          <td>{author.id}</td>
                          <td>{author.name}</td>
                        </tr>
                    ))
                )}
              </tbody>
            </table>
          </div>
        </section>

        <section className="panel">
          <div className="panel-header">
            <h2>Books</h2>
            <button className="btn" onClick={handleListarLibros}>Consultar</button>
          </div>

          <div className="table-wrap">
            <table className="table">
              <thead>
                <tr>
                  <th>ISBN</th>
                  <th>Titulo</th>
                </tr>
              </thead>
              <tbody>
                {books.length === 0 ? (
                    <tr>
                      <td colSpan={2} className="empty">Sin resultados</td>
                    </tr>
                ) : (
                    books.map((book: Book) => (
                        <tr key={book.isbn}>
                          <td>{book.isbn}</td>
                          <td>{book.title}</td>
                        </tr>
                    ))
                )}
              </tbody>
            </table>
          </div>
        </section>
      </main>
  );
}

export default App;
