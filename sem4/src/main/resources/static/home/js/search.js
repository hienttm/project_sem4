function search() {
    var keyword = document.getElementById('searchInput').value;

    $.ajax({
        type: 'GET',
        url: '/search',
        data: { keyword: keyword },
        success: function (result) {
            displayResults(result);
        },
        error: function (error) {
            console.error('Error:', error);
        }
    });
}

function displayResults(results) {
    var searchResults = document.getElementById('searchResults');
    searchResults.innerHTML = '';

    var parsedResults = $.parseHTML(results);

    $(searchResults).append(parsedResults);
}

