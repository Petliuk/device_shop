document.getElementById('saveDiscountButton').addEventListener('click', function () {
    saveDiscount();
});

function saveDiscount() {
    const discountName = document.getElementById('discountName').value;
    const discountDescription = document.getElementById('discountDescription').value;
    const discountPercent = document.getElementById('discountPercent').value;

    const token = localStorage.getItem('token');

    const newDiscount = {
        name: discountName,
        description: discountDescription,
        discountPercent: discountPercent,
        createdAt: new Date().toISOString(),
        modifiedAt: new Date().toISOString(),
    };

    fetch('http://localhost:8081/discount', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(newDiscount),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Помилка запиту до сервера');
            }
            return response.json();
        })
        .then(data => {
            resetCreateDiscountForm();
            hideCreateDiscountForm();
            loadDiscounts();
        })
        .catch(error => {
            console.error('Помилка:', error);
        });
}

function resetCreateDiscountForm() {
    document.getElementById('discountName').value = '';
    document.getElementById('discountDescription').value = '';
    document.getElementById('discountPercent').value = '';
}

function loadDiscounts() {
    const token = localStorage.getItem('token');

    fetch('http://localhost:8081/discount', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Помилка запиту до сервера');
            }
            return response.json();
        })
        .then(discounts => {
            displayDiscounts(discounts);
        })
        .catch(error => {
            console.error('Помилка:', error);
        });
}

function displayDiscounts(discounts) {
    const discountListContainer = document.getElementById('discountListContainer');

    discountListContainer.innerHTML = '';

    discounts.forEach(discount => {
        const discountCard = document.createElement('div');
        discountCard.classList.add('discount-card');
        discountCard.innerHTML = `
            <div class="discount-id">ID: ${discount.id}</div>
            <div class="discount-name">Назва: ${discount.name}</div>
            <div class="discount-description">Опис: ${discount.description}</div>
            <div class="discount-percent">Величина знижки: ${discount.discountPercent}</div>
            <div class="discount-created">Створено: ${new Date(discount.createdAt).toLocaleString()}</div>
            <div class="discount-modified">Змінено: ${new Date(discount.modifiedAt).toLocaleString()}</div>
            <button class="edit-discount-button" data-id="${discount.id}">Змінити</button>
            <button class="delete-discount-button" data-id="${discount.id}">Видалити</button>
        `;

        const editDiscountButton = discountCard.querySelector('.edit-discount-button');
        editDiscountButton.addEventListener('click', () => {
            const discountId = discount.id;
            switchPage('editDiscount');
            showEditDiscountForm(discountId);
        });


        const deleteDiscountButton = discountCard.querySelector('.delete-discount-button');
        deleteDiscountButton.addEventListener('click', () => {
            const discountId = discount.id;
            if (confirm(`Ви впевнені, що хочете видалити знижку з ID ${discountId}?`)) {
                deleteDiscount(discountId);
            }
        });
        discountListContainer.appendChild(discountCard);
    });
}

function deleteDiscount(discountId) {
    const token = localStorage.getItem('token');

    fetch(`http://localhost:8081/discount/${discountId}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Помилка запиту до сервера');
            }
            loadDiscounts();
        })
        .catch(error => {
            console.error('Помилка:', error);
        });
}
function showEditDiscountForm(discountId) {
    const editDiscountFormContainer = document.getElementById('editDiscountFormContainer');
    editDiscountFormContainer.style.display = 'block';

    const token = localStorage.getItem('token');

    fetch(`http://localhost:8081/discount/${discountId}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Помилка запиту до сервера');
            }
            return response.json();
        })
        .then(discount => {
            // Заповнюємо поля редагування інформацією про знижку
            document.getElementById('editDiscountId').value = discount.id;
            document.getElementById('editDiscountName').value = discount.name;
            document.getElementById('editDiscountDescription').value = discount.description;
            document.getElementById('editDiscountPercent').value = discount.discountPercent;
        })
        .catch(error => {
            console.error('Помилка:', error);
        });
}
document.getElementById('saveEditedDiscountButton').addEventListener('click', function () {
    saveEditedDiscount();
});

document.getElementById('cancelEditDiscountButton').addEventListener('click', function () {
    switchPage('discounts');
    hideEditDiscountForm();
});

function saveEditedDiscount() {
    const discountId = document.getElementById('editDiscountId').value;
    const discountName = document.getElementById('editDiscountName').value;
    const discountDescription = document.getElementById('editDiscountDescription').value;
    const discountPercent = document.getElementById('editDiscountPercent').value;

    const token = localStorage.getItem('token');

    const updatedDiscount = {
        id: discountId,
        name: discountName,
        description: discountDescription,
        discountPercent: discountPercent,
        createdAt: new Date().toISOString(),
        modifiedAt: new Date().toISOString(),
    };

    fetch(`http://localhost:8081/discount/${discountId}`, {
        method: 'PUT',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedDiscount),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Помилка запиту до сервера');
            }
            loadDiscounts();
            hideEditDiscountForm();

            switchPage('discounts');
        })
        .catch(error => {
            console.error('Помилка:', error);
        });
}

function hideEditDiscountForm() {
    const editDiscountFormContainer = document.getElementById('editDiscountFormContainer');
    editDiscountFormContainer.style.display = 'none';
}