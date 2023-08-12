function goToProductsPage() {
    window.location.href = 'http://localhost:63342/MyProjectWithSpring2/src/main/resources/static/products.html?_ijt=nmg0npme92fg5762odeqe9rg7r&_ij_reload=RELOAD_ON_SAVE';
}

async function login() {
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const formData = new URLSearchParams();
    formData.append('username', email);
    formData.append('password', password);

    try {
        const response = await fetch('http://localhost:8080/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: formData.toString()
        });

        if (!response.ok) {
            throw new Error('Не вдалось увійти');
        }

        const accessToken = await response.text();

        console.log(accessToken);
        localStorage.setItem('token', accessToken);

        const userResponse = await fetch('http://localhost:8080/users/principal', {
            headers: {
                Authorization: `Bearer ${accessToken}`
            }
        });

        if (userResponse.ok) {
            const user = await userResponse.json();
            localStorage.setItem('userId', user.id);
        }

        goToProductsPage();

    } catch (error) {
        console.error('Помилка входу:', error);
        alert('Не вдалось увійти. Перевірте ваші облікові дані та спробуйте знову.');
    }
}

const registrationForm = document.getElementById('registrationForm');

function register() {
    // Отримайте дані з форми реєстрації
    const name = document.getElementById('name').value;
    const surname = document.getElementById('surname').value;
    const phone = document.getElementById('phone').value;
    const email = document.getElementById('regEmail').value;
    const password = document.getElementById('regPassword').value;

    const user = {
        name,
        surname,
        phone,
        email,
        password
    };

    fetch('http://localhost:8080/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Registration failed');
            }
        })
        .then(data => {
            console.log('Registration successful:', data);
            const registrationSuccess = document.getElementById('registrationSuccess');
            registrationSuccess.style.display = 'block';
            document.getElementById('registrationForm').reset();
        })
        .catch(error => {
            console.error('Registration error:', error);
        });
}

document.getElementById('loginForm').addEventListener('submit', async (event) => {
    event.preventDefault();
    login();
});


document.getElementById('registrationForm').addEventListener('submit', async (event) => {
    event.preventDefault();
    register();
});

