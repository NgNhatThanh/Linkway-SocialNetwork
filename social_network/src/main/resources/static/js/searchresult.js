const csrfToken = document.getElementById('csrf-token').value;

const availableTags = [];

let filterTags = [];

const tagInput = document.getElementById("filter-tag-input");
const tagSuggestions = document.getElementById("filter-tag-suggestions");

fetch('/api/tags')
    .then(response => response.json())
    .then(tagList => {
        tagList.forEach(tag => {
            availableTags.push(tag.name);
        })
        availableTags.sort();

        const hiddenTags = document.getElementById('filter-hidden-tags')

        Array.from(hiddenTags.children).forEach(tagInput => {
            filterTags.push(tagInput.value);
            availableTags.splice(availableTags.indexOf(tagInput.value), 1);
        })
        renderTags();
    })
    .catch(error => {
        console.log(error);
    });

tagInput.addEventListener("input", function() {
    const query = tagInput.value.split(" ").pop(); // Get the current word
    tagSuggestions.innerHTML = ""; // Clear previous suggestions

    if (query.length > 0) {
        const matchedTags = availableTags.filter(tag => tag.toLowerCase().includes(query.toLowerCase()));
        matchedTags.forEach(tag => {
            const suggestion = document.createElement("div");
            suggestion.className = "filter-suggestion-item";
            suggestion.innerText = tag;
            suggestion.onclick = () => selectTag(tag);
            tagSuggestions.appendChild(suggestion);
        });
    }
});

function selectTag(tag){
    tagInput.value = '';
    filterTags.push(tag);
    var idx = availableTags.indexOf(tag);
    availableTags.splice(idx, 1);
    tagSuggestions.innerHTML = '';
    renderTags();
}

function renderTags() {
    const tagsDisplay = document.getElementById('filter-tags-display');
    tagsDisplay.innerHTML = '';

    filterTags.forEach((tag, index) => {
        const tagElement = document.createElement('span');
        tagElement.className = 'tag';
        tagElement.innerText = tag;
        tagElement.onclick = () => removeTag(tag, index);
        tagsDisplay.appendChild(tagElement);
    });
}

function removeTag(tag, index) {
    filterTags.splice(index, 1);
    availableTags.push(tag);
    renderTags();
}

function filter(){
    const hiddenTagsContainer = document.getElementById('filter-hidden-tags');
    hiddenTagsContainer.innerHTML = ''; // Clear previous hidden inputs

    filterTags.forEach(tag => {
        const hiddenInput = document.createElement('input');
        hiddenInput.type = 'hidden';
        hiddenInput.name = 'tagName';
        hiddenInput.value = tag;
        hiddenTagsContainer.appendChild(hiddenInput);
    });
}