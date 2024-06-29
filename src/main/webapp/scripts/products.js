document.addEventListener('DOMContentLoaded', function() {
    const editButtons = document.querySelectorAll('.edit');
    const editModal = document.getElementById('editModal');
    const editId = document.getElementById('edit-id');
    const editName = document.getElementById('edit-name');
    const editPrice = document.getElementById('edit-price');
    const editStock = document.getElementById('edit-stock');
    const editDescription = document.getElementById('edit-description');
    const editCategory = document.getElementById('edit-category');

    editButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            const id = this.getAttribute('data-id');
            const name = this.getAttribute('data-name');
            const price = this.getAttribute('data-price');
            const stock = this.getAttribute('data-stock');
            const description = this.getAttribute('data-description');
            const category = this.getAttribute('data-category');

            editId.value = id;
            editName.value = name;
            editPrice.value = price;
            editStock.value = stock;
            editDescription.value = description;
            editCategory.value = category;

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

function filterProductsHandler() {
    const searchInput = document.getElementById('searchInput');
    const searchValue = searchInput.value.toLowerCase();

    const selectInput = document.getElementById('selectInput');
    const categoryValue = selectInput.value.toLowerCase();

    const listItems = document.querySelectorAll('.product-item');

    listItems.forEach(function(item) {
        const name = item.querySelector('.product-name').textContent.toLowerCase();
        const categoryId = item.querySelector('.product-category').value.toLowerCase();

        if (name.includes(searchValue)) {

            if(categoryValue === "0") {
                item.style.display = 'flex';
            }
            else if (categoryId.includes(categoryValue)) {
                item.style.display = 'flex';
            } else {
                item.style.display = 'none';
            }
        } else {
            item.style.display = 'none';
        }
    });
}