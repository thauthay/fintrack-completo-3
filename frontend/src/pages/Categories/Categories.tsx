import { useEffect, useState } from "react";
import "./Categories.css";
import CategoryModal from "../../components/CategoryModal/CategoryModal";
import {
  createCategory,
  deleteCategory,
  getCategories,
  updateCategory,
} from "../../api/finance";
import { getErrorMessage } from "../../api/client";
import type { Category, TransactionType } from "../../api/types";

function Categories() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [categoryToEdit, setCategoryToEdit] = useState<Category | null>(null);
  const [categories, setCategories] = useState<Category[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [reloadKey, setReloadKey] = useState(0);

  useEffect(() => {
    let cancelled = false;

    getCategories()
      .then((data) => {
        if (!cancelled) {
          setCategories(data);
        }
      })
      .catch((err: unknown) => {
        if (!cancelled) {
          setError(getErrorMessage(err));
        }
      });

    return () => {
      cancelled = true;
    };
  }, [reloadKey]);

  async function runAction(action: () => Promise<unknown>) {
    try {
      await action();
      setError(null);
      setReloadKey((key) => key + 1);
    } catch (err) {
      setError(getErrorMessage(err));
    }
  }

  function handleAddCategory(name: string, type: TransactionType) {
    void runAction(() => createCategory(name, type));
  }

  function handleEditCategory(name: string, type: TransactionType) {
    if (!categoryToEdit) {
      return;
    }

    const id = categoryToEdit.id;
    setCategoryToEdit(null);
    void runAction(() => updateCategory(id, name, type));
  }

  function handleDeleteCategory(id: number) {
    const category = categories.find((category) => category.id === id);

    if (!category) {
      return;
    }

    const confirmed = window.confirm(
      `Tem certeza que deseja excluir a categoria "${category.name}"?`,
    );

    if (!confirmed) {
      return;
    }

    void runAction(() => deleteCategory(id));
  }

  return (
    <div className="categories-page">
      <div className="categories-header">
        <div>
          <h2>Categorias</h2>
          <p>Organize suas receitas e despesas.</p>
        </div>

        <button
          className="add-category-button"
          onClick={() => setIsModalOpen(true)}
        >
          + Nova categoria
        </button>
      </div>

      {error && <p style={{ color: "#f87171" }}>{error}</p>}

      <div className="categories-grid">
        {categories.map((category) => (
          <div className="category-card" key={category.id}>
            <div className="category-icon">
              {category.type === "income" ? "💼" : "📁"}
            </div>

            <div className="category-info">
              <strong>{category.name}</strong>

              <span>
                {category.type === "income" ? "Receitas" : "Despesas"}
              </span>
            </div>

            <div className="category-actions">
              <button
                className="edit-category-button"
                onClick={() => setCategoryToEdit(category)}
              >
                Editar
              </button>

              <button
                className="delete-category-button"
                onClick={() => handleDeleteCategory(category.id)}
              >
                Excluir
              </button>
            </div>
          </div>
        ))}
      </div>

      {isModalOpen && (
        <CategoryModal
          onClose={() => setIsModalOpen(false)}
          onAdd={handleAddCategory}
        />
      )}

      {categoryToEdit && (
        <CategoryModal
          onClose={() => setCategoryToEdit(null)}
          onAdd={handleEditCategory}
          initialName={categoryToEdit.name}
          initialType={categoryToEdit.type}
          isEditing
        />
      )}
    </div>
  );
}

export default Categories;
