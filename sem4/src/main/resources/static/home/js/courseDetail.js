function getVideo(id, index) {

    $.ajax({
        type: 'GET',
        url: '/api/course/getVideo',
        data: { id: id, index:index },
        success: function (result) {
            displayResults(result);
        },
        error: function (error) {
            console.error('Error:', error);
        }
    });
}

function displayResults(results) {
    let Results = document.getElementById('display-video');
    Results.innerHTML = `<video controls src="${results}">`;
}