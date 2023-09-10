document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('productForm');
    const submitButton = document.getElementById('submitButton');

    submitButton.addEventListener('click', function () {
        const formData = {
            name: document.getElementById('name').value,
            description: document.getElementById('description').value,
            sku: document.getElementById('sku').value,
            price: parseFloat(document.getElementById('price').value),
            quantity: parseInt(document.getElementById('quantity').value),
        };

        // Отримуємо токен з локального сховища
        const token = localStorage.getItem('token'); // Перед цим ви маєте зберегти токен в локальному сховищі

        // Перевірка, чи є токен
        if (!token) {
            console.error('Токен не знайдено');
            return;
        }

        // Відправляємо POST-запит на бекенд разом із токеном
        fetch('http://localhost:8081/product', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`, // Додаємо токен до заголовків
            },
            body: JSON.stringify(formData),
        })
            .then(response => response.json())
            .then(data => {
                console.log('Дані успішно відправлені на бекенд:', data);
                // Додайте код для відображення успішного повідомлення користувачу
            })
            .catch(error => {
                console.error('Помилка під час відправлення запиту:', error);
                // Додайте код для відображення повідомлення про помилку користувачу
            });
    });
});







/*

document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('productForm');
    const submitButton = document.getElementById('submitButton');

    submitButton.addEventListener('click', function () {
        const formData = {
            name: document.getElementById('name').value,
            description: document.getElementById('description').value,
            sku: document.getElementById('sku').value,
            price: parseFloat(document.getElementById('price').value),
            quantity: parseInt(document.getElementById('quantity').value),
        };

        // Отримуємо токен з локального сховища
        const token = localStorage.getItem('token');

        // Відправляємо POST-запит на бекенд з токеном у заголовку
        fetch('http://localhost:8081/product', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}` // Додаємо токен у заголовок
            },
            body: JSON.stringify(formData),
        })
            .then(response => response.json())
            .then(data => {
                console.log('Дані успішно відправлені на бекенд:', data);
                // Додайте код для відображення успішного повідомлення користувачу
            })
            .catch(error => {
                console.error('Помилка під час відправлення запиту:', error);
                // Додайте код для відображення повідомлення про помилку користувачу
            });
    });
});
*/






