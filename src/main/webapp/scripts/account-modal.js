
const userModal = document.getElementById('userModal');
userModal.style.display = 'none';

function openModal() {
    document.getElementById('userModal').style.display = "block";
}

function closeModal() {
    document.getElementById('userModal').style.display = "none";
}

// When the user clicks anywhere outside the modal, close it
window.onclick = function(event) {
    const modal = document.getElementById('userModal');
    if (event.target === modal) {
        modal.style.display = "none";
    }
}

// Logout function
function logout() {
    fetch("/logout_servlet", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({}),
    }).then(response => {
        location.reload();
    }).catch(console.error);
}