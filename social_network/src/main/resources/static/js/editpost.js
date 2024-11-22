const csrfToken = document.getElementById('csrf-token').value;

const availableTags = [];

let addedTags = [];

fetch('/api/tags')
    .then(response => response.json())
    .then(tagList => {
        tagList.forEach(tag => {
            availableTags.push(tag.name);
        })
        availableTags.sort();

        const hiddenTags = document.getElementById('hidden-tags')

        Array.from(hiddenTags.children).forEach(tagInput => {
            addedTags.push(tagInput.value);
            availableTags.splice(availableTags.indexOf(tagInput.value), 1);
        })

        renderTags();
    })
    .catch(error => {
        console.log(error);
    });

const easyMDE = new EasyMDE({
    element: document.getElementById('post-content'),
    placeholder: 'Nội dung',
    uploadImage: true,
    toolbar: [
        "bold", "italic", "heading", "|", "code", "unordered-list", "|",
        "link", "upload-image", "|", "preview", "side-by-side", "fullscreen", "|",
        "guide"
    ],
    imageUploadFunction: function (file, onSuccess, onError) {
        const formData = new FormData();
        formData.append("image", file);
        fetch("/upload", {
            method: "POST",
            body: formData,
            headers: {
                'X-CSRF-TOKEN': csrfToken
            }
        })
            .then(response => response.json())
            .then(data => {
                if (data.imageUrl) {
                    onSuccess(data.imageUrl); // Use the uploaded image URL
                } else {
                    onError("Upload failed");
                }
            })
            .catch(() => onError("Upload failed"));
    }
});

const tagInput = document.getElementById("tag-input");
const tagSuggestions = document.getElementById("tag-suggestions");

tagInput.addEventListener("input", function () {
    const query = tagInput.value.split(" ").pop(); // Get the current word
    tagSuggestions.innerHTML = ""; // Clear previous suggestions

    if (query.length > 0) {
        const matchedTags = availableTags.filter(tag => tag.toLowerCase().includes(query.toLowerCase()));
        matchedTags.forEach(tag => {
            const suggestion = document.createElement("div");
            suggestion.className = "suggestion-item";
            suggestion.innerText = tag;
            suggestion.onclick = () => selectTag(tag);
            tagSuggestions.appendChild(suggestion);
        });
    }
});

function selectTag(tag) {
    tagInput.value = '';
    addedTags.push(tag);
    var idx = availableTags.indexOf(tag);
    availableTags.splice(idx, 1);
    tagSuggestions.innerHTML = '';
    if (addedTags.length === 5) tagInput.disabled = true;
    renderTags();
}

function renderTags() {
    const tagsDisplay = document.getElementById('tags-display');
    tagsDisplay.innerHTML = '';

    addedTags.forEach((tag, index) => {
        const tagElement = document.createElement('span');
        tagElement.className = 'tag';
        tagElement.innerText = tag;
        tagElement.onclick = () => removeTag(tag, index);
        tagsDisplay.appendChild(tagElement);
    });
}

function removeTag(tag, index) {
    addedTags.splice(index, 1);
    availableTags.push(tag);
    const input = document.getElementById('tag-input');
    input.disabled = false;
    renderTags();
}

document.getElementById('submit-button').addEventListener('click', function (event) {
    const title = document.getElementById('post-title').value.trim();
    const content = easyMDE.value().trim();
    if (title.length > 0 && content.length > 0 && addedTags.length > 0) prepareTagsForSubmit();
    else {
        event.preventDefault();
        if (title.length === 0) {
            showErrorMessage('post-title', 'Tiêu đề không được trống');
        }
        if (content.length === 0) {
            showErrorMessage('post-content', 'Nội dung không được trống');
        }
        if (addedTags.length === 0) {
            showErrorMessage('tag-input', 'Bài viết cần có tối thiểu 1 thẻ');
        }
    }
});

function showErrorMessage(id, message) {
    const error = document.createElement('p');
    error.className = 'error';
    error.textContent = message;
    const tx = document.getElementById(id);
    tx.parentNode.insertBefore(error, tx);
}

function prepareTagsForSubmit() {
    const hiddenTagsContainer = document.getElementById('hidden-tags');
    hiddenTagsContainer.innerHTML = ''; // Clear previous hidden inputs

    addedTags.forEach(tag => {
        const hiddenInput = document.createElement('input');
        hiddenInput.type = 'hidden';
        hiddenInput.name = 'tagNames';
        hiddenInput.value = tag;
        hiddenTagsContainer.appendChild(hiddenInput);
    });
}