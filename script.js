document.addEventListener('DOMContentLoaded', function() {

});

function openTab(evt, tabName) {
    // Скрытие всех вкладок и снятие выделения с кнопок вкладок
    const tabContents = document.querySelectorAll('.tabcontent');
    tabContents.forEach(tabContent => {
        tabContent.classList.add('hidden');
    });
    const tabLinks = document.querySelectorAll('.tablinks');
    tabLinks.forEach(tabLink => {
        tabLink.classList.remove('active');
    });

    // Показ нужной вкладки и выделение соответствующей кнопки
    const selectedTab = document.getElementById(tabName);
    selectedTab.classList.remove('hidden');
    evt.currentTarget.classList.add('active');


    // Загрузка данных для выбранной вкладки
    if (tabName === 'ingredients') {
        loadIngredients();
    } else if (tabName === 'recipes') {
        document.getElementById("categorySelect").innerHTML = "";
        loadRecipes();
        loadCategoriesSelect();
        loadIngredientsSelect();
    } else if (tabName === 'categories') {
        loadCategories();
        loadRecipesSelect();
    }
}

function formatIngredients(ingredients) {
    // Функция для форматирования ингредиентов в строку
    return ingredients.map(ingredient => `${ingredient.name}`).join(', ');
}

function loadIngredients() {
    fetch('http://localhost:8080/api/ingredients')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector('#ingredientsTable tbody');
            tableBody.innerHTML = ''; // Очистка предыдущих данных
            data.forEach(ingredient => {
                const row = document.createElement('tr');
                row.innerHTML = `
	    <td>${ingredient.id}</td>
            <td>${ingredient.name}</td>
            <td>
              <button class="action-btn edit" onclick="editIngredient(event, ${ingredient.id})">Изменить</button>
	      <button class="action-btn delete" onclick="deleteIngredient(${ingredient.id})">Удалить</button>
            </td>
          `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error('Ошибка при получении данных:', error));
}

function addIngredient() {
    const ingredientNameInput = document.getElementById('ingredientName');
    const ingredientName = ingredientNameInput.value.trim();
    if (ingredientName === '') {
        showError('Имя ингредиента не может быть пустым');
        return;
    }

    fetch('http://localhost:8080/api/ingredients/addIngredients', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify([{ "name": ingredientName }])
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(error => {
                    showError(error.exceptionMessage);
                    throw new Error(error.exceptionMessage);
                });
            }
            ingredientNameInput.value = '';
            const errorElement = document.getElementById('error');
            errorElement.textContent = '';
            const successElement = document.getElementById('success');
            successElement.textContent = 'Ингредиент успешно добавлен';
            loadIngredients(); // Обновляем список ингредиентов после добавления
        })
        .catch(error => console.error('Ошибка при добавлении ингредиента:', error));
}

function showError(message) {
    const successElement = document.getElementById('success');
    successElement.textContent = '';
    const errorElement = document.getElementById('error');
    errorElement.textContent = message;
}

function loadRecipes() {
    fetch('http://localhost:8080/api/recipes')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector('#recipesTable tbody');
            tableBody.innerHTML = ''; // Очистка предыдущих данных
            data.forEach(recipe => {
                const row = document.createElement('tr');
                row.innerHTML = `
	    <td>${recipe.id}</td>
            <td>${recipe.name}</td>
            <td>${recipe.category ? recipe.category.name : '<span class="absent">Отсутствует</span>'}</td>
            <td>${formatIngredients(recipe.ingredients)}</td>
            <td>
              <button class="action-btn edit" onclick="editRecipe(event, ${recipe.id})">Изменить</button>
	      <button class="action-btn delete" onclick="deleteRecipe(${recipe.id})">Удалить</button>
            </td>
          `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error('Ошибка при получении данных:', error));
}


function addRecipe() {
    const recipeNameInput = document.getElementById('recipeName');
    const recipeName = recipeNameInput.value.trim();
    if (recipeName === '') {
        showRecipeError('Название рецепта не может быть пустым');
        return;
    }

    const categorySelect = document.getElementById('categorySelect');
    categoryId = categorySelect.value;
    if (!categoryId) {
        categoryId=null;

    }
    const category = { id: categoryId };

    const ingredientSelect = document.getElementById('ingredientList');//измени функцию
    const selectedIngredients = Array.from(ingredientSelect.querySelectorAll('li.highlight')).map(li => {
        return { id: li.dataset.id };
    });

    const recipeData = {
        name: recipeName,
        category: categoryId,
        ingredients: selectedIngredients
    };
    const requestBody = JSON.stringify({
        name: recipeName,
        category: category,
        ingredients: selectedIngredients
    });
    console.log(requestBody);


    fetch('http://localhost:8080/api/recipes/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: requestBody
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(error => {
                    showRecipeError(error.exceptionMessage);
                    throw new Error(error.exceptionMessage);
                });
            }
            recipeNameInput.value = '';
            loadRecipes(); // Обновляем список рецептов после добавления
        })
        .catch(error => console.error('Ошибка при добавлении рецепта:', error));
}

function showRecipeError(message) {
    const errorElement = document.getElementById('recipeError');
    errorElement.textContent = message;
}

function loadCategoriesSelect() {
    fetch('http://localhost:8080/api/categories')
        .then(response => response.json())
        .then(data => {
            const select = document.getElementById('categorySelect');
            data.forEach(category => {
                const option = document.createElement('option');
                option.value = category.id;
                option.textContent = category.name;
                select.appendChild(option);
            });
        })
        .catch(error => console.error('Ошибка при получении данных о категориях:', error));
}

function loadIngredientsSelect() {
    fetch('http://localhost:8080/api/ingredients')
        .then(response => response.json())
        .then(data => {
            const ingredientList = document.getElementById('ingredientList');
            ingredientList.innerHTML = ''; // Очищаем список перед загрузкой новых данных
            data.forEach(ingredient => {
                const listItem = document.createElement('li');
                listItem.textContent = ingredient.name;
                listItem.dataset.id = ingredient.id; // Добавляем атрибут data-id с идентификатором ингредиента
                listItem.addEventListener('click', function() {
                    toggleHighlight(listItem);
                });
                ingredientList.appendChild(listItem);
            });
        })
        .catch(error => console.error('Ошибка при получении данных об ингредиентах:', error));
}

function loadCategories() {
    fetch('http://localhost:8080/api/categories')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector('#categoriesTable tbody');
            tableBody.innerHTML = ''; // Очистка предыдущих данных
            data.forEach(category => {
                const row = document.createElement('tr');
                row.innerHTML = `
            <td>${category.id}</td>
            <td>${category.name}</td>
            <td>${category.recipes.map(recipe => recipe.name).join(', ')}</td>
            <td>
              <button class="action-btn edit" onclick="editCategory(event, ${category.id})">Изменить</button>
	      <button class="action-btn delete" onclick="deleteCategory(${category.id})">Удалить</button>
            </td>
          `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error('Ошибка при получении данных:', error));
}

function addCategory() {
    const categoryNameInput = document.getElementById('categoryName');
    const categoryName = categoryNameInput.value.trim();
    if (categoryName === '') {
        showRecipeError('Название рецепта не может быть пустым');
        return;
    }

    const recipeSelect = document.getElementById('recipeList');//измени функцию
    const selectedRecipes = Array.from(recipeSelect.querySelectorAll('li.highlight')).map(li => {
        return { id: li.dataset.id };
    });

    const categoryData = {
        name: categoryName,
        recipes: selectedRecipes
    };
    const requestBody = JSON.stringify({
        name: categoryName,
        recipes: selectedRecipes
    });
    console.log(requestBody);


    fetch('http://localhost:8080/api/categories/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: requestBody
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(error => {
                    showRecipeError(error.exceptionMessage);
                    throw new Error(error.exceptionMessage);
                });
            }
            categoryNameInput.value = '';
            loadCategories(); // Обновляем список категорий после добавления
        })
        .catch(error => console.error('Ошибка при добавлении категории:', error));
}

function showRecipeError(message) {
    const errorElement = document.getElementById('categoryError');
    errorElement.textContent = message;
}

function loadRecipesSelect() {
    fetch('http://localhost:8080/api/recipes')
        .then(response => response.json())
        .then(data => {
            const ingredientList = document.getElementById('recipeList');
            ingredientList.innerHTML = ''; // Очищаем список перед загрузкой новых данных
            data.forEach(recipe => {
                const listItem = document.createElement('li');
                listItem.textContent = recipe.name;
                listItem.dataset.id = recipe.id; // Добавляем атрибут data-id с идентификатором ингредиента
                listItem.addEventListener('click', function() {
                    toggleHighlight(listItem);
                });
                ingredientList.appendChild(listItem);
            });
        })
        .catch(error => console.error('Ошибка при получении данных об рецептах:', error));
}


function editRecipe(event, id) {
    const rowIndex = event.target.closest('tr').rowIndex;
    const tableRow = document.getElementById(`recipesTable`).rows[rowIndex];
    const editMode = tableRow.classList.contains('edit-mode');

    // Переключение режима редактирования
    if (!editMode) {
        const actionButtonsCell = tableRow.querySelectorAll('td')[4]; // Получаем ячейку с кнопками по позиции
        const editButton = actionButtonsCell.querySelector('.edit');
        const deleteButton = actionButtonsCell.querySelector('.delete');
        editButton.classList.add('hidden'); // Скрываем кнопку "Изменить"
        deleteButton.classList.add('hidden'); // Скрываем кнопку "Удалить"

        const nameCell = tableRow.querySelectorAll('td')[1]; // Получаем ячейку с названием рецепта по позиции
        const name = nameCell.textContent;
        nameCell.innerHTML = `<input type="text"  class="nameInput" value="${name}">`;

        const categoryCell = tableRow.querySelectorAll('td')[2]; // Получаем ячейку с названием рецепта по позиции
        const category = categoryCell.textContent;
        categoryCell.innerHTML = `<input type="text"  class="categoryInput" value="${category}">`;

        const actionsCell = tableRow.querySelectorAll('td')[4];
        actionsCell.innerHTML = `
        <div class="edit-actions">
          <button class="action-btn accept" onclick="updateRecipe(event, ${id})">Принять</button>
          <button class="action-btn delete" onclick="cancelEdit(event, ${id}, '${name}', '${category}')">Отмена</button>
        </div>
      `;

        tableRow.classList.add('edit-mode');
    }
}

function updateRecipe(event, id) {
    const rowIndex = event.target.closest('tr').rowIndex;
    const tableRow = document.getElementById(`recipesTable`).rows[rowIndex];
    const nameInput = tableRow.querySelector('.nameInput');
    const newName = nameInput.value;
    const categoryInput = tableRow.querySelector('.categoryInput');
    const newCategory = categoryInput.value;

    // Отправка запроса на обновление рецепта
    fetch(`http://localhost:8080/api/recipes/update?id=${id}&name=${encodeURIComponent(newName)}&category=${encodeURIComponent(newCategory)}`, {
        method: 'PUT'
    })
        .then(response => {
            if (response.ok) {
                console.log(`Рецепт с ID ${id} успешно обновлен.`);
                const nameCell = tableRow.querySelectorAll('td')[1]; // Получаем ячейку с названием рецепта по позиции
                const categoryCell = tableRow.querySelectorAll('td')[2];
                nameCell.textContent = newName; // Обновляем содержимое ячейки
                categoryCell.textContent = newCategory;
                cancelEdit(event, id, newName, newCategory); // Отменяем режим редактирования
            } else {
                console.error(`Не удалось обновить рецепт с ID ${id}.`);
            }
        })
        .catch(error => console.error('Ошибка при обновлении рецепта:', error));
}

function cancelEdit(event, id, name, category) {
    const rowIndex = event.target.closest('tr').rowIndex;
    const tableRow = document.getElementById(`recipesTable`).rows[rowIndex];
    const nameCell = tableRow.querySelector('td:nth-child(2)'); // Находим ячейку с названием рецепта
    const categoryCell = tableRow.querySelector('td:nth-child(3)');
    const actionsCell = tableRow.querySelectorAll('td')[4];

    // Возвращение в режим просмотра
    nameCell.innerHTML = name;
    categoryCell.innerHTML = category;
    actionsCell.innerHTML = `
      <button class="action-btn edit" onclick="editRecipe(${id})">Изменить</button>
      <button class="action-btn delete" onclick="deleteRecipe(${id})">Удалить</button>
    `;

    tableRow.classList.remove('edit-mode');
    loadRecipes();
}


function editIngredient(event, id) {
    const rowIndex = event.target.closest('tr').rowIndex;
    const tableRow = document.getElementById(`ingredientsTable`).rows[rowIndex];
    const editMode = tableRow.classList.contains('edit-mode');

    // Переключение режима редактирования
    if (!editMode) {
        const actionButtonsCell = tableRow.querySelectorAll('td')[2]; // Получаем ячейку с кнопками по позиции
        const editButton = actionButtonsCell.querySelector('.edit');
        const deleteButton = actionButtonsCell.querySelector('.delete');
        editButton.classList.add('hidden'); // Скрываем кнопку "Изменить"
        deleteButton.classList.add('hidden'); // Скрываем кнопку "Удалить"

        const nameCell = tableRow.querySelectorAll('td')[1]; // Получаем ячейку с названием ингредиента по позиции
        const name = nameCell.textContent;
        nameCell.innerHTML = `<input type="text" value="${name}">`;

        const actionsCell = tableRow.querySelectorAll('td')[2];
        actionsCell.innerHTML = `
        <div class="edit-actions">
          <button class="action-btn accept" onclick="updateIngredient(event, ${id})">Принять</button>
          <button class="action-btn delete" onclick="cancelEditIngr(event, ${id}, '${name}')">Отмена</button>
        </div>
      `;

        tableRow.classList.add('edit-mode');
    }
}

function updateIngredient(event, id) {
    const rowIndex = event.target.closest('tr').rowIndex;
    const tableRow = document.getElementById(`ingredientsTable`).rows[rowIndex];
    const nameInput = tableRow.querySelector('input');
    const newName = nameInput.value;

    // Отправка запроса на обновление рецепта
    fetch(`http://localhost:8080/api/ingredients/update?id=${id}&name=${encodeURIComponent(newName)}`, {
        method: 'PUT'
    })
        .then(response => {
            if (response.ok) {
                console.log(`Ингредиент с ID ${id} успешно обновлен.`);
                const nameCell = tableRow.querySelectorAll('td')[1]; // Получаем ячейку с названием ингредиента по позиции
                nameCell.textContent = newName; // Обновляем содержимое ячейки
                cancelEditIngr(event, id, newName); // Отменяем режим редактирования
            } else {
                console.error(`Не удалось обновить рецепт с ID ${id}.`);
            }
        })
        .catch(error => console.error('Ошибка при обновлении рецепта:', error));
}

function cancelEditIngr(event, id, name) {
    const rowIndex = event.target.closest('tr').rowIndex;
    const tableRow = document.getElementById(`ingredientsTable`).rows[rowIndex];
    const nameCell = tableRow.querySelector('td:nth-child(2)'); // Находим ячейку с названием ингредиента
    const actionsCell = tableRow.querySelectorAll('td')[2];

    // Возвращение в режим просмотра
    nameCell.innerHTML = name;
    actionsCell.innerHTML = `
      <button class="action-btn edit" onclick="editRecipe(${id})">Изменить</button>
      <button class="action-btn delete" onclick="deleteRecipe(${id})">Удалить</button>
    `;

    tableRow.classList.remove('edit-mode');
    loadIngredients();
}

function editCategory(event, id) {
    const rowIndex = event.target.closest('tr').rowIndex;
    const tableRow = document.getElementById(`categoriesTable`).rows[rowIndex];
    const editMode = tableRow.classList.contains('edit-mode');

    // Переключение режима редактирования
    if (!editMode) {
        const actionButtonsCell = tableRow.querySelectorAll('td')[3]; // Получаем ячейку с кнопками по позиции
        const editButton = actionButtonsCell.querySelector('.edit');
        const deleteButton = actionButtonsCell.querySelector('.delete');
        editButton.classList.add('hidden'); // Скрываем кнопку "Изменить"
        deleteButton.classList.add('hidden'); // Скрываем кнопку "Удалить"

        const nameCell = tableRow.querySelectorAll('td')[1];
        const name = nameCell.textContent;
        nameCell.innerHTML = `<input type="text" value="${name}">`;

        const actionsCell = tableRow.querySelectorAll('td')[3];
        actionsCell.innerHTML = `
        <div class="edit-actions">
          <button class="action-btn accept" onclick="updateCategory(event, ${id})">Принять</button>
          <button class="action-btn delete" onclick="cancelEditCat(event, ${id}, '${name}')">Отмена</button>
        </div>
      `;

        tableRow.classList.add('edit-mode');
    }
}

function updateCategory(event, id) {
    const rowIndex = event.target.closest('tr').rowIndex;
    const tableRow = document.getElementById(`categoriesTable`).rows[rowIndex];
    const nameInput = tableRow.querySelector('input');
    const newName = nameInput.value;

    // Отправка запроса на обновление рецепта
    fetch(`http://localhost:8080/api/categories/update?id=${id}&name=${encodeURIComponent(newName)}`, {
        method: 'PUT'
    })
        .then(response => {
            if (response.ok) {
                console.log(`Категория с ID ${id} успешно обновлена.`);
                const nameCell = tableRow.querySelectorAll('td')[1]; // Получаем ячейку с названием ингредиента по позиции
                nameCell.textContent = newName; // Обновляем содержимое ячейки
                cancelEditCat(event, id, newName); // Отменяем режим редактирования
            } else {
                console.error(`Не удалось обновить категорию с ID ${id}.`);
            }
        })
        .catch(error => console.error('Ошибка при обновлении категории:', error));
}

function cancelEditCat(event, id, name) {
    const rowIndex = event.target.closest('tr').rowIndex;
    const tableRow = document.getElementById(`categoriesTable`).rows[rowIndex];
    const nameCell = tableRow.querySelector('td:nth-child(2)'); // Находим ячейку с названием ингредиента
    const actionsCell = tableRow.querySelectorAll('td')[3];

    // Возвращение в режим просмотра
    nameCell.innerHTML = name;
    actionsCell.innerHTML = `
      <button class="action-btn edit" onclick="editCategory(${id})">Изменить</button>
      <button class="action-btn delete" onclick="deleteCategory(${id})">Удалить</button>
    `;

    tableRow.classList.remove('edit-mode');
    loadCategories();
}

function deleteIngredient(id) {
    console.log(`Рецепт с ID ${id} успешно удален.`);

    fetch(`http://localhost:8080/api/ingredients/delete/${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (response.ok) {
                console.log(`Ингредиент с ID ${id} успешно удален.`);
                loadIngredients();
            } else {
                console.error(`Не удалось удалить ингредиент с ID ${id}.`);
            }
        })
        .catch(error => console.error('Ошибка при удалении ингредиента:', error));
}

function deleteRecipe(id) {
    fetch(`http://localhost:8080/api/recipes/delete/${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (response.ok) {
                console.log(`Рецепт с ID ${id} успешно удален.`);
                loadRecipes();
            } else {
                console.error(`Не удалось удалить рецепт с ID ${id}.`);
            }
        })
        .catch(error => console.error('Ошибка при удалении рецепта:', error));
}

function deleteCategory(id) {
    fetch(`http://localhost:8080/api/categories/delete/${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (response.ok) {
                console.log(`Категория с ID ${id} успешно удалена.`);
                loadCategories();
            } else {
                console.error(`Не удалось удалить категорию с ID ${id}.`);
            }
        })
        .catch(error => console.error('Ошибка при удалении категории:', error));
}

function sortTable(tableId, columnIndex) {
    const table = document.getElementById(tableId);
    const tbody = table.querySelector('tbody');
    const rows = Array.from(tbody.querySelectorAll('tr'));
    const sortBtns = Array.from(table.querySelectorAll('th button'));

    rows.sort((a, b) => {
        const aData = a.querySelectorAll('td')[columnIndex].textContent;
        const bData = b.querySelectorAll('td')[columnIndex].textContent;
        if (!isNaN(aData) && !isNaN(bData)) {
            return parseFloat(aData) - parseFloat(bData);
        } else {
            return aData.localeCompare(bData);
        }
    });

    if (sortBtns[columnIndex].classList.contains('asc')) {
        rows.reverse();
        sortBtns[columnIndex].classList.replace('asc', 'desc');
        sortBtns[columnIndex].innerHTML = '&darr;';
    } else {
        sortBtns.forEach(btn => {
            btn.classList.remove('asc', 'desc');
            btn.innerHTML = '';
        });
        sortBtns[columnIndex].classList.add('asc');
        sortBtns[columnIndex].innerHTML = '&uarr;';
    }

    tbody.innerHTML = '';
    rows.forEach(row => tbody.appendChild(row));
}



function toggleHighlight(element) {
    element.classList.toggle('highlight');
}
