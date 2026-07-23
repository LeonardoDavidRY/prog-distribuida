import { useState } from 'react';
import './App.css';
import axios from 'axios';

// DTO devuelto por TODO-REST (GET /todos/user/{userId}):
// la DB de TODO-REST solo guarda id/title/completed, username y name
// se resuelven consultando el sistema externo de usuarios.
interface TodoDto {
  id: number;
  username: string;
  name: string;
  title: string;
  completed: boolean;
}

function App() {
  const [userId, setUserId] = useState('1');
  const [todos, setTodos] = useState<TodoDto[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [searched, setSearched] = useState(false);

  const handleBuscar = (event: React.FormEvent) => {
    event.preventDefault();

    if (!userId.trim()) {
      return;
    }

    setLoading(true);
    setError(null);

    axios
      .get<TodoDto[]>(`http://localhost/app-todo/todos/user/${userId}`)
      .then((response) => {
        setTodos(response.data);
        setSearched(true);
      })
      .catch((err) => {
        setTodos([]);
        setSearched(true);
        setError(err?.message ?? 'No se pudo consultar los TO-DO');
      })
      .finally(() => setLoading(false));
  };

  return (
    <main className="app">
      <header className="app-header">
        <h1>TO-DO por usuario</h1>
      </header>

      <section className="panel">
        <div className="panel-header">
          <h2>Buscar</h2>
        </div>

        <form className="search-form" onSubmit={handleBuscar}>
          <input
            type="number"
            min={1}
            placeholder="ID de usuario (ej. 1)"
            value={userId}
            onChange={(event) => setUserId(event.target.value)}
          />
          <button className="btn" type="submit" disabled={loading}>
            {loading ? 'Consultando...' : 'Consultar'}
          </button>
        </form>

        {error && <p className="error">{error}</p>}

        <div className="table-wrap">
          <table className="table">
            <thead>
              <tr>
                <th>Username</th>
                <th>Name</th>
                <th>Title</th>
                <th>Completed</th>
              </tr>
            </thead>
            <tbody>
              {todos.length === 0 ? (
                <tr>
                  <td colSpan={4} className="empty">
                    {searched ? 'Este usuario no tiene TO-DO' : 'Ingresa un ID de usuario y presiona Consultar'}
                  </td>
                </tr>
              ) : (
                todos.map((todo) => (
                  <tr key={todo.id}>
                    <td>{todo.username}</td>
                    <td>{todo.name}</td>
                    <td>{todo.title}</td>
                    <td>
                      <span className={`badge ${todo.completed ? 'badge-done' : 'badge-pending'}`}>
                        {todo.completed ? 'Completado' : 'Pendiente'}
                      </span>
                    </td>
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
