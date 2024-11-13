
const csrfToken = document.getElementById('csrf-token').value;

const easyMDE = new EasyMDE({
    element: document.getElementById('post-editor'),
    placeholder: 'Nội dung',
    uploadImage: true,
    toolbar: [
        "bold", "italic", "heading", "|" , "code", "unordered-list", "|",
        "link", "upload-image", "|", "preview", "side-by-side", "fullscreen", "|",
        "guide"
    ],
    imageUploadFunction: function(file, onSuccess, onError) {
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

let tags = [];



function addTag(event) {
    const input = document.getElementById('tag-input');
    if (event.key === 'Enter' && input.value.trim()) {
        event.preventDefault();
        const newTag = input.value.trim();

        // Ensure no duplicates and max of 5 tags
        if (!tags.includes(newTag) && tags.length < 5) {
            tags.push(newTag);
            input.value = '';
            renderTags();
        }
        if(tags.length === 5){
            input.disabled = true;
        }
    }
}

function renderTags() {
    const tagsDisplay = document.getElementById('tags-display');
    tagsDisplay.innerHTML = '';

    tags.forEach((tag, index) => {
        const tagElement = document.createElement('span');
        tagElement.className = 'tag';
        tagElement.innerText = tag;
        tagElement.onclick = () => removeTag(index);
        tagsDisplay.appendChild(tagElement);
    });
}

function removeTag(index) {
    tags.splice(index, 1);
    const input = document.getElementById('tag-input');
    input.disabled = false;
    renderTags();
}

function prepareTagsForSubmit() {
    const hiddenTagsContainer = document.getElementById('hidden-tags');
    hiddenTagsContainer.innerHTML = ''; // Clear previous hidden inputs

    tags.forEach(tag => {
        const hiddenInput = document.createElement('input');
        hiddenInput.type = 'hidden';
        hiddenInput.name = 'tagNames';
        hiddenInput.value = tag;
        hiddenTagsContainer.appendChild(hiddenInput);
    });
}