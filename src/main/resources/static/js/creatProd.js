async function uploadFile() {
    const fileInput = document.getElementById('image');
    if (fileInput.files.length === 0) {
        alert('Please select a file to upload.');
        return;
    }

    const accessToken = localStorage.getItem('token');
    if (!accessToken) {
        alert('You need to log in to upload a file.');
        return;
    }

    const formData = new FormData();
    formData.append('image', fileInput.files[0]);

    const requestOptions = {
        method: 'POST',
        headers: {
            Authorization: `Bearer ${accessToken}`,
        },
        body: formData,
    };

    try {
        const response = await fetch('http://localhost:8081/upload', requestOptions);
        if (response.ok) {
            const { photoId } = await response.json();
            alert('File uploaded successfully.');
        } else {
            alert('An error occurred while uploading the file to the server.');
        }
    } catch (error) {
        console.error('An error has occurred:', error);
    }
}
