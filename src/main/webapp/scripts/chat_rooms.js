document.addEventListener('DOMContentLoaded', function() {
    const editButtons = document.querySelectorAll('.edit');
    const editModal = document.getElementById('editModal');
    const editId = document.getElementById('edit-id');
    const editName = document.getElementById('edit-name');
    const editDescription = document.getElementById('edit-description');

    editButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            const id = this.getAttribute('data-id');
            const name = this.getAttribute('data-name');
            const description = this.getAttribute('data-description');

            editId.value = id;
            editName.value = name;
            editDescription.value = description;

            editModal.style.display = 'block';
        });
    });

    // Add an event listener for clicks on the window
    window.addEventListener('click', function(event) {
        // If the click was outside the modal, hide the modal
        if (event.target === editModal) {
            editModal.style.display = 'none';
        }
    });
});