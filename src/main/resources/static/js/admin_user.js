function getUsers() {
    const token = localStorage.getItem('token');
    fetch('http://localhost:8081/users', {
        method: 'GET', headers: {
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Помилка запиту до сервера');
            }
            return response.json();
        })
        .then(data => {
            displayUsers(data);
        })
        .catch(error => {
            console.error('Помилка:', error);
        });
}

function deleteUserData(userId) {
    const token = localStorage.getItem('token');

    fetch(`http://localhost:8081/users/${userId}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${token}`,
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Помилка запиту до сервера');
            }
            getUsers();
        })
        .catch(error => {
            console.error('Помилка:', error);
        });
}


function displayUsers(users) {
    const userListContainer = document.getElementById('userListContainer');

    userListContainer.innerHTML = '';

    users.forEach(user => {
        const userCard = document.createElement('div');
        userCard.classList.add('user-card');
        userCard.innerHTML = `
            <div>ID: ${user.id}</div>
            <div>Ім'я: ${user.name}</div>
            <div>Прізвище: ${user.surname}</div>
            <div>Телефон: ${user.phone}</div>
            <div>Email: ${user.email}</div>
            <button class="edit-user-button" data-id="${user.id}">Змінити</button>
            <button class="delete-user-button" data-id="${user.id}">Видалити</button>
           
        `;

        const editUserButton = userCard.querySelector('.edit-user-button');
        editUserButton.addEventListener('click', () => {
            const userId = user.id;
            const token = localStorage.getItem('token');

            fetch(`http://localhost:8081/users/${userId}`, {
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
                .then(userData => {
                    switchPage('editUser');
                    showEditUserDialog(userId);
                })
                .catch(error => {
                    console.error('Помилка:', error);
                });
        });

        const deleteUserButton = userCard.querySelector('.delete-user-button');
        deleteUserButton.addEventListener('click', () => {
            const userId = user.id;
            if (confirm(`Ви впевнені, що хочете видалити користувача з ID ${userId}?`)) {
                deleteUserData(userId);
            }
        });

        userListContainer.appendChild(userCard);
    });
}

function showEditUserDialog(userId) {
    const editUserDialog = document.getElementById('editUserDialog');
    const token = localStorage.getItem('token');

    fetch(`http://localhost:8081/users/${userId}`, {
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
        .then(userData => {
            const idInput = editUserDialog.querySelector('#editUserId');
            const nameInput = editUserDialog.querySelector('#editUserName');
            const surnameInput = editUserDialog.querySelector('#editUserSurname');
            const phoneInput = editUserDialog.querySelector('#editUserPhone');
            const emailInput = editUserDialog.querySelector('#editUserEmail');

            idInput.value = userData.id;
            nameInput.value = userData.name;
            surnameInput.value = userData.surname;
            phoneInput.value = userData.phone;
            emailInput.value = userData.email;

            editUserDialog.style.display = 'block';

            const saveButton = editUserDialog.querySelector('#editUserSaveButton');
            saveButton.addEventListener('click', () => {
                const editedUserData = {
                    id: idInput.value,
                    name: nameInput.value,
                    surname: surnameInput.value,
                    phone: phoneInput.value,
                    email: emailInput.value,
                };

                fetch(`http://localhost:8081/users/${userData.id}`, {
                    method: 'PUT',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(editedUserData),
                })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Помилка запиту до сервера');
                        }
                        switchPage('users');
                    })
                    .catch(error => {
                        console.error('Помилка:', error);
                    });
            });

            const cancelButton = editUserDialog.querySelector('#editUserCancelButton');
            cancelButton.addEventListener('click', () => {
                switchPage('users');
            });

            const otherElements = document.querySelectorAll('.user-card button');
            otherElements.forEach(element => {
                element.addEventListener('click', () => {
                    editUserDialog.style.display = 'none';
                });
            });
        })
        .catch(error => {
            console.error('Помилка:', error);
        });
}



