document.getElementById('addCategoryButton').addEventListener('click', function () {
    switchPage('addCategory');
});

function showAddCategoryForm() {
    const addCategoryFormContainer = document.getElementById('addCategoryFormContainer');
    addCategoryFormContainer.style.display = 'block';
}

/*function hideAddCategoryForm() {
    const addCategoryFormContainer = document.getElementById('addCategoryFormContainer');
    addCategoryFormContainer.style.display = 'none';
}*/


document.getElementById('addCategoryForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    const categoryName = document.getElementById('categoryName').value;
    const categoryDescription = document.getElementById('categoryDescription').value;

    const categoryData = {
        id: 0,
        name: categoryName,
        description: categoryDescription,
        createdAt: new Date().toISOString(),
        modifiedAt: new Date().toISOString(),
        deletedAt: null
    };

    const token = localStorage.getItem('token');

    try {
        const response = await createCategory(categoryData, token);
    } catch (error) {
        console.error('Помилка створення категорії:', error);
    }
});

async function createCategory(categoryData, token) {
    const response = await fetch('http://localhost:8081/product-categories', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify(categoryData),
    });

    if (!response.ok) {
        throw new Error('Помилка створення категорії.');
    } else {
        document.getElementById('categoryName').value = '';
        document.getElementById('categoryDescription').value = '';
        const addCategoryFormContainer = document.getElementById('addCategoryFormContainer');
        addCategoryFormContainer.style.display = 'none';

        // Додай код для оновлення списку категорій або іншої потрібної дії
    }

    return response.json();
}

document.getElementById('categoriesButton').addEventListener('click', function () {
    switchPage('categories');
});

function loadCategories() {
    // Виконайте запит на сервер за списком категорій
    fetch('http://localhost:8081/product-categories')
        .then(response => {
            if (!response.ok) {
                throw new Error('Помилка отримання категорій.');
            }
            return response.json();
        })
        .then(data => {
            // Очистіть вміст контейнера для відображення категорій
            const categoriesContainer = document.getElementById('categoriesContainer');
            categoriesContainer.innerHTML = '';

            // Переберіть кожну категорію та додайте її на сторінку
            data.forEach(category => {
                const categoryElement = document.createElement('div');
                categoryElement.className = 'category-item'; // Оновлений клас для окремої категорії
                categoryElement.innerHTML = `
                    <h3>${category.name}</h3>
                    <p>${category.description}</p>
                    <p>Створено: ${category.createdAt}</p>
                    <button class="delete-category-button" data-category-id="${category.id}">Видалити категорію</button>`; // Додайте кнопку "Видалити категорію"

                categoriesContainer.appendChild(categoryElement);
            });

            // Відобразіть контейнер для категорій
            categoriesContainer.style.display = 'block';

            // Додайте обробник події для видалення категорії
            const deleteCategoryButtons = document.querySelectorAll('.delete-category-button');
            deleteCategoryButtons.forEach(button => {
                button.addEventListener('click', () => {
                    const categoryId = button.getAttribute('data-category-id');
                    deleteCategory(categoryId);
                });
            });
        })
        .catch(error => {
            console.error('Помилка завантаження категорій:', error);
        });
}

function deleteCategory(categoryId) {
    // Отримайте токен з локального сховища
    const token = localStorage.getItem('token');

    // Виконайте DELETE-запит на сервер для видалення категорії з використанням правильного URL та токена
    fetch(`http://localhost:8081/product-categories/${categoryId}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Помилка видалення категорії.');
            }
            // Після успішного видалення оновіть список категорій або виконайте інші дії за потребою
            loadCategories();
        })
        .catch(error => {
            console.error('Помилка видалення категорії:', error);
        });
}